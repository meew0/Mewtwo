package meew0.mewtwo.timers;

import java.time.Instant;

import javax.annotation.Nullable;

import meew0.mewtwo.irc.Channel;
import meew0.mewtwo.irc.IChannel;
import meew0.mewtwo.irc.IRCBot;
import meew0.mewtwo.irc.User;

public class TimerEvent {
    private final int id;
    private final Instant instant;
    private final String name, nick, fullHostmask, hostmask, channel;

    public TimerEvent(int id, Instant instant, String name, String nick, String fullHostmask, String hostmask,
            String channel) {
        this.id = id;
        this.instant = instant;
        this.name = name;
        this.nick = nick;
        this.fullHostmask = fullHostmask;
        this.hostmask = hostmask;
        this.channel = channel;
    }

    public int getId() {
        return id;
    }

    public Instant getInstant() {
        return instant;
    }

    public String getName() {
        return name;
    }

    public User getUser(IRCBot bot) {
        return new User(nick, fullHostmask, hostmask, bot);
    }

    public IChannel getChannel(IRCBot bot) {
        if (channel != null) {
            return new Channel(channel, bot);
        } else {
            return getUser(bot);
        }
    }

    @Nullable
    public String getChannelName() {
        return channel;
    }

    public String getIdString() {
        return "timer:" + name + ":" + id;
    }
}
