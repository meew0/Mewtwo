package meew0.mewtwo.irc;

/**
 * Created by meew0 on 03.04.15.
 *
 * Represents the users in a channel
 */
public class ChannelUserList {
    private String[] nicks;
    private boolean isValid;
    private final Channel channel;
    private final IRCBot bot;

    public ChannelUserList(IRCBot bot, Channel channel) {
        nicks = new String[0];
        this.bot = bot;
        this.channel = channel;
        invalidate();
    }

    public ChannelUserList(IRCBot bot, Channel channel, String[] nicks) {
        this.nicks = nicks;
        this.bot = bot;
        this.channel = channel;
        isValid = true;
    }

    public IRCBot getBot() {
        return bot;
    }

    public String[] getNicks() {
        if(!isValid) revalidate();
        return nicks;
    }

    public void invalidate() {
        isValid = false;
    }

    public void revalidate() {
        bot.writeRaw("NAMES", channel.getName());
    }
}
