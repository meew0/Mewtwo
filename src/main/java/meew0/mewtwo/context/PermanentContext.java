package meew0.mewtwo.context;

import java.util.Date;
import java.util.Map;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.irc.User;
import meew0.mewtwo.modules.ModuleManager;
import meew0.mewtwo.storage.Database;
import meew0.mewtwo.timers.TimerManager;

//import meew0.mewtwo.irc.ChatLog;

/**
 * Created by meew0 on 14.11.14.
 */
public class PermanentContext {
    // TODO: Replace ChatLog with a better way to log stuff
    // private final ChatLog log;

    private final Database database;

    private final ModuleManager moduleManager;
    private final TimerManager timerManager;

    private boolean slowmodeEnabled = false;
    private int slowmodeTime = 0;
    private long slowmodeTS = 0;

    /**
     * Make a new permanent context
     */
    PermanentContext(ModuleManager moduleManager, TimerManager timerManager) {
        this.moduleManager = moduleManager;
        this.timerManager = timerManager;

        database = new Database();
    }

    /**
     * Get this context's module manager
     *
     * @return the module manager
     */
    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    /**
     * @return the database underlying this context
     */
    public Database getDb() {
        return database;
    }

    /**
     * @return whether or not slowmode is enabled
     */
    public boolean isSlowmodeEnabled() {
        return slowmodeEnabled;
    }

    // /**
    // * @return this context's chat log
    // */
    // public ChatLog getLog() {
    // return log;
    // }
    //
    // /**
    // * Adds a message to the chat log
    // * @param msg the message
    // * @param nick the nick of the user who wrote the message
    // */
    // public void logMessage(String msg, String nick) {
    // log.add(msg, nick);
    // }
    //
    // /**
    // *
    // * @return A linked list of the last (ChatLog.limit) messages, newest first
    // */
    // public LinkedList<ChatLog.Message> getLastLogged() {
    // return log.messages;
    // }
    //
    // /**
    // * Gets the last message written by a specific user
    // * @param userNick the user's nick
    // * @return the user's last message
    // */
    // public ChatLog.Message getLastOfUser(String userNick) {
    // return log.getLatestFromUser(userNick);
    // }
    //
    // /**
    // * Gets the last message that matches a specific regex
    // * @param regex the regex
    // * @return the last message that matches
    // */
    // public ChatLog.Message getLastMatch(String regex) {
    // return log.getLatestThatMatches(regex);
    // }

    /**
     * Enables slowmode with a specific duration
     *
     * @param time the duration in milliseconds
     */
    public void enableSlowmode(int time) {
        slowmodeTime = time;
        slowmodeEnabled = true;
    }

    /**
     * Disables slowmode
     */
    public void disableSlowmode() {
        slowmodeTime = 0;
        slowmodeEnabled = false;
        slowmodeTS = 0;
    }

    /**
     * @param user the user
     * @return whether the user is an admin
     */
    public boolean userIsAdmin(User user) {
        return database.userIsAdmin(user.hostmask());
    }

    /**
     * @param userNick the user's nick
     * @return whether the user should be ignored
     */
    public boolean shouldIgnoreUser(String userNick) {
        return database.userIsIgnored(userNick);
    }

    /**
     * @param commandName the command's name
     * @return whether the command is enabled
     */
    public boolean commandIsDisabled(String commandName) {
        return database.commandIsDisabled(commandName);
    }

    /**
     * Get the command from an alias, as defined by aliases.cfg
     * If no alias is defined, the original command is returned
     *
     * @param commandName the alias
     * @return the actual command
     */
    public String getCommandFromAlias(String commandName) {
        String trueCommand = database.getAliasedCommand(commandName);
        if (trueCommand == null)
            return commandName;
        return trueCommand;
    }

    /**
     * @return the prefix Mewtwo uses for commands
     */
    public String getMewtwoPrefix() {
        return MewtwoMain.prefix;
    }

    /**
     * Returns whether slowmode is currently active and, if it is active, whether
     * the time has run out yet
     *
     * @return is slowmode active?
     */
    public boolean isSlowmodeActive() {
        if (!slowmodeEnabled)
            return false;

        long d = new Date().getTime();
        if (slowmodeEnabled && ((d - slowmodeTime) > slowmodeTS)) {
            slowmodeTS = d;
            return true;
        }
        return false;
    }

    /**
     * Add something to the command data
     *
     * @param id    The id of the executing command/module/something else
     * @param key   The key under which the value should be stored
     * @param value The value that should be stored
     */
    public void put(String id, String key, Object value) {
        database.setCommandData(id, key, value, true);
    }

    /**
     * Retrieve something from the command data
     *
     * @param id  The id of the executing command/module/something else
     * @param key The key under which the value is stored
     * @return The value that is stored
     */
    public Object get(String id, String key) {
        return database.getCommandData(id, key);
    }

    /**
     * Retrieve something from the command data
     *
     * @param id           The id of the executing command/module/something else
     * @param key          The key under which the value is stored
     * @param defaultValue The default value that should be returned if nothing is
     *                     found
     * @return The value that is stored
     */
    public Object get(String id, String key, Object defaultValue) {
        Object result = get(id, key);
        if (result == null)
            return defaultValue;
        return result;
    }

    public Map<String, Object> list(String id) {
        return database.listCommandData(id);
    }

    public void delete(String id, String key) {
        database.deleteCommandData(id, key);
    }

    /**
     * Check if something is present in the command data
     *
     * @param id  The id of the executing command/module/something else
     * @param key The key under which the value might be stored
     * @return Whether a value exists with the given ID and key
     */
    public boolean has(String id, String key) {
        return get(id, key) != null;
    }

    public void reloadConfigs() {
        moduleManager.reloadConfigs();
    }
}
