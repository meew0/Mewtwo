package meew0.mewtwo.irc;

import com.google.common.collect.ImmutableSortedSet;
import meew0.mewtwo.MewtwoMain;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserLevel;
import org.pircbotx.dcc.DccState;
import org.pircbotx.dcc.SendChat;
import org.pircbotx.dcc.SendFileTransfer;
import org.pircbotx.output.OutputUser;
import org.pircbotx.snapshot.UserSnapshot;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by miras on 07.12.14.
 */
public class ConsoleUser extends User {
    public class NullSendFileTransfer extends SendFileTransfer {
        public NullSendFileTransfer(User user, File file) {
            super(MewtwoMain.configuration.buildConfiguration(), new Socket(), user, file, 0);
        }

        @Override
        protected void transferFile() throws IOException {
        }

        @Override
        public void transfer() throws IOException {
        }

        @Override
        protected void onAfterSend() {
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public User getUser() {
            return user;
        }

        @Override
        public File getFile() {
            return file;
        }

        @Override
        public long getStartPosition() {
            return 0;
        }

        @Override
        public long getBytesTransfered() {
            return 0;
        }

        @Override
        public DccState getState() {
            return DccState.DONE;
        }
    }
    public class ConsoleSendChat extends SendChat {
        public ConsoleSendChat(User user) throws IOException {
            super(user, new Socket(), Charset.defaultCharset());
        }

        @Override
        public String readLine() throws IOException {
            return "";
        }

        @Override
        public void sendLine(String line) throws IOException {
            System.out.println("[DCC MESSAGE] " + line);
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public User getUser() {
            return user;
        }

        @Override
        public BufferedReader getBufferedReader() {
            return new BufferedReader(new StringReader(""));
        }

        @Override
        public BufferedWriter getBufferedWriter() {
            return new BufferedWriter(new OutputStreamWriter(System.out));
        }

        @Override
        public Socket getSocket() {
            return socket;
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class ConsoleUserOutput extends OutputUser {
        public ConsoleUserOutput(PircBotX bot, User user) {
            super(bot, user);
        }

        @Override
        public void invite(String channel) {
            MewtwoMain.mewtwoLogger.info("[INVITE] " + channel);
        }

        @Override
        public void invite(Channel channel) {
            MewtwoMain.mewtwoLogger.info("[INVITE] " + channel.getName());
        }

        @Override
        public void notice(String notice) {
            MewtwoMain.mewtwoLogger.info("[NOTICE] " + notice);
        }

        @Override
        public void action(String action) {
            MewtwoMain.mewtwoLogger.info("[ACTION] " + action);
        }

        @Override
        public void message(String message) {
            MewtwoMain.mewtwoLogger.info("[MESSAGE] " + message);
        }

        @Override
        public void ctcpCommand(String command) {
            MewtwoMain.mewtwoLogger.info("[CTCP COMMAND] " + command);
        }

        @Override
        public void ctcpResponse(String message) {
            MewtwoMain.mewtwoLogger.info("[CTCP RESPONSE] " + message);
        }

        @Override
        public void mode(String mode) {
            MewtwoMain.mewtwoLogger.info("[MODE] " + mode);
        }
    }

    private static final String consoleUserNick = "CONSOLE";
    private static final int consoleUserHash = consoleUserNick.hashCode();
    public ConsoleUser(PircBotX bot) {
        super(new ConsoleUserHostmask(bot));
    }

    @Override
    public OutputUser send() {
        return new ConsoleUserOutput(getBot(), this);
    }

    @Override
    public boolean isVerified() {
        return true;
    }

    @Override
    public UserSnapshot createSnapshot() {
        return super.createSnapshot();
    }

    @Override
    public ImmutableSortedSet<UserLevel> getUserLevels(Channel channel) {
        return ImmutableSortedSet.of();
    }

    @Override
    public ImmutableSortedSet<Channel> getChannels() {
        return ImmutableSortedSet.of();
    }

    @Override
    public ImmutableSortedSet<Channel> getChannelsOpIn() {
        return ImmutableSortedSet.of();
    }

    @Override
    public ImmutableSortedSet<Channel> getChannelsVoiceIn() {
        return ImmutableSortedSet.of();
    }

    @Override
    public ImmutableSortedSet<Channel> getChannelsOwnerIn() {
        return ImmutableSortedSet.of();
    }

    @Override
    public ImmutableSortedSet<Channel> getChannelsHalfOpIn() {
        return ImmutableSortedSet.of();
    }

    @Override
    public ImmutableSortedSet<Channel> getChannelsSuperOpIn() {
        return ImmutableSortedSet.of();
    }

    @Override
    public String getServer() {
        return super.getServer();
    }

    @Override
    public int getHops() {
        return 0;
    }

    @Override
    public boolean isAway() {
        return true;
    }

    @Override
    public String getNick() {
        return consoleUserNick;
    }

    @Override
    public String getRealName() {
        return "realname-" + consoleUserNick;
    }

    @Override
    public String getLogin() {
        return "login-" + consoleUserNick;
    }

    @Override
    public String getHostmask() {
        return "host-" + consoleUserNick;
    }

    @Override
    public String getAwayMessage() {
        return "This is the console user.";
    }

    @Override
    public boolean isIrcop() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ConsoleUser;
    }

    @Override
    public boolean canEqual(Object other) {
        return other instanceof ConsoleUser;
    }

    @Override
    public int hashCode() {
        return consoleUserHash;
    }

    @Override
    protected void setNick(String nick) {}

    @Override
    protected void setRealName(String realName) {}

    @Override
    protected void setAwayMessage(String awayMessage) {}

    @Override
    protected void setIrcop(boolean ircop) {}

    @Override
    protected void setServer(String server) {}

    @Override
    protected void setHops(int hops) {}
}
