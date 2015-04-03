package meew0.mewtwo.irc;

import meew0.mewtwo.core.MewtwoLogger;

import java.util.Date;

/**
 * Created by meew0 on 03.04.15.
 * <p>
 * Represents the users in a channel
 */
public class ChannelUserList {
    private String[] nicks;
    private boolean isValid;
    private final Channel channel;
    private final IRCBot bot;
    private long lastRevalidationTimestamp;

    // Delay before channel list gets revalidated, in milliseconds
    // 1000000 ms = 1000 s = ~17 min
    public static final int channelRevalidationDelay = 1000000;

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
        lastRevalidationTimestamp = new Date().getTime();
    }

    public IRCBot getBot() {
        return bot;
    }

    public String[] getNicks() {
        if (!isValid) revalidate();
        return nicks;
    }

    public void invalidate() {
        if (isValid) MewtwoLogger.info("Invalidating channel " + channel.getName());
        isValid = false;

        // Revalidate channel after a certain amount of time
        if (((new Date().getTime()) - lastRevalidationTimestamp) > channelRevalidationDelay) revalidate();
    }

    public void revalidate() {
        MewtwoLogger.info("Revalidating channel " + channel.getName());
        bot.writeRaw("NAMES", channel.getName());
    }
}
