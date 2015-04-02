package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public class Channel {
    private final String name;
    private final IRCBot bot;

    public Channel(String name, IRCBot bot) {
        this.name = name;
        this.bot = bot;
    }

    public String getName() {
        return name;
    }

    public IRCBot getBot() {
        return bot;
    }
}
