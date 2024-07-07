package meew0.mewtwo.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import meew0.mewtwo.irc.User;
import meew0.mewtwo.timers.TimerEvent;

public class Database implements AutoCloseable {
    private final Connection connection;

    public Database() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:mewtwo.db");
            createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS admins (hostmask TEXT PRIMARY KEY ON CONFLICT IGNORE)");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS aliases (alias TEXT PRIMARY KEY, command TEXT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS disable (command TEXT PRIMARY KEY ON CONFLICT IGNORE)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ignore (nick TEXT PRIMARY KEY ON CONFLICT IGNORE)");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS commandData (command TEXT, key TEXT, value, PRIMARY KEY (command, key))");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS timers (id INTEGER PRIMARY KEY, name TEXT, instant INTEGER, nick TEXT, fullHostmask TEXT, hostmask TEXT, channel TEXT)");
        }
    }

    public int getAdminCount() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM admins");
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userIsAdmin(String hostmask) {
        return checkExistence("SELECT * FROM admins WHERE hostmask = ?", hostmask);
    }

    public void makeUserAdmin(String hostmask) {
        parameterisedUpdate("INSERT INTO admins (hostmask) VALUES (?)", hostmask);
    }

    public void removeAdmin(String hostmask) {
        parameterisedUpdate("DELETE FROM admins WHERE hostmask = ?", hostmask);
    }

    public boolean commandIsDisabled(String command) {
        return checkExistence("SELECT * FROM disable WHERE command = ?", command);
    }

    public void disableCommand(String command) {
        parameterisedUpdate("INSERT INTO disable (command) VALUES (?)", command);
    }

    public void enableCommand(String command) {
        parameterisedUpdate("DELETE FROM disable WHERE command = ?", command);
    }

    public boolean userIsIgnored(String nick) {
        return checkExistence("SELECT * FROM ignore WHERE nick = ?", nick);
    }

    public void ignoreUser(String nick) {
        parameterisedUpdate("INSERT INTO ignore (nick) VALUES (?)", nick);
    }

    public void unignoreUser(String nick) {
        parameterisedUpdate("DELETE FROM ignore WHERE nick = ?", nick);
    }

    @Nullable
    public String getAliasedCommand(String alias) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT command FROM aliases WHERE alias = ?")) {
            statement.setString(1, alias);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("command");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAlias(String alias, String command) {
        try (PreparedStatement statement = connection
                .prepareStatement("INSERT INTO aliases (alias, command) VALUES (?, ?)")) {
            statement.setString(1, alias);
            statement.setString(2, command);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAlias(String alias) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM aliases WHERE alias = ?")) {
            statement.setString(1, alias);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public Object getCommandData(String command, String key) {
        try (PreparedStatement statement = connection
                .prepareStatement("SELECT value FROM commandData WHERE command = ? AND key = ?")) {
            statement.setString(1, command);
            statement.setString(2, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getObject("value");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCommandData(String command, String key, Object value, boolean replace) {
        String query = replace ? "INSERT OR REPLACE INTO commandData (command, key, value) VALUES (?, ?, ?)"
                : "INSERT INTO commandData (command, key, value) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, command);
            statement.setString(2, key);
            statement.setObject(3, value);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCommandData(String command, String key) {
        try (PreparedStatement statement = connection
                .prepareStatement("DELETE FROM commandData WHERE command = ? AND key = ?")) {
            statement.setString(1, command);
            statement.setString(2, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TimerEvent> getPendingTimerEventsUntil(Instant instant) {
        try (PreparedStatement statement = connection
                .prepareStatement("SELECT * FROM timers WHERE instant <= ? ORDER BY instant ASC")) {
            statement.setLong(1, instant.toEpochMilli());
            ResultSet resultSet = statement.executeQuery();
            List<TimerEvent> timers = new ArrayList<>();
            while (resultSet.next()) {
                timers.add(new TimerEvent(
                        resultSet.getInt("id"),
                        Instant.ofEpochMilli(resultSet.getLong("instant")),
                        resultSet.getString("name"),
                        resultSet.getString("nick"),
                        resultSet.getString("fullHostmask"),
                        resultSet.getString("hostmask"),
                        resultSet.getString("channel")));
            }
            return timers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addNewTimer(Instant instant, String name, User user, String channelName) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO timers (name, instant, nick, fullHostmask, hostmask, channel) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, name);
            statement.setLong(2, instant.toEpochMilli());
            setIrcProperties(statement, 3, user, channelName);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            return generatedKeys.getInt(1); // Return generated ID
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readdTimer(int id, Instant instant, String name, User user, String channelName) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO timers (id, name, instant, nick, fullHostmask, hostmask, channel) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setLong(3, instant.toEpochMilli());
            setIrcProperties(statement, 4, user, channelName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIrcProperties(PreparedStatement timerCreationStatement, int startAt, User user, String channelName)
            throws SQLException {
        timerCreationStatement.setString(startAt, user.nick());
        timerCreationStatement.setString(startAt + 1, user.fullHostmask());
        timerCreationStatement.setString(startAt + 2, user.hostmask());
        timerCreationStatement.setString(startAt + 3, channelName);
    }

    public void deleteTimer(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM timers WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkExistence(String query, String parameter) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int parameterisedUpdate(String query, String parameter) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
