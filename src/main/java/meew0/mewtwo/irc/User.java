package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public class User implements IChannel {
    private final String nick;
    private final String fullHostmask;
    private final String hostmask;
    private final IRCBot bot;

    public User(String nick, String fullHostmask, String hostmask, IRCBot bot) {
        this.nick = nick;
        this.fullHostmask = fullHostmask;
        this.hostmask = hostmask;
        this.bot = bot;
    }

    public String getNick() {
        return nick;
    }

    public String getFullHostmask() {
        return fullHostmask;
    }

    public String getHostmask() {
        return hostmask;
    }

    public IRCBot getBot() {
        return bot;
    }

    public void sendMessage(String message) {
        bot.writePrivmsg(nick, message);
    }

    @Override
    public String[] getUserNicks() {
        return new String[] { nick };
    }

    @Override
    public String getName() {
        return nick;
    }
}
