package meew0.mewtwo.context;

import meew0.mewtwo.irc.IChannel;
import meew0.mewtwo.irc.IRCBot;
import meew0.mewtwo.irc.User;
import meew0.mewtwo.modules.ModuleManager;
import meew0.mewtwo.storage.Database;
import meew0.mewtwo.timers.TimerEvent;
import meew0.mewtwo.timers.TimerManager;

/**
 * Created by meew0 on 15.11.14.
 */
public class ContextManager {
    private final PermanentContext permanent;

    /**
     * Creates a new context manager with a new permanent context.
     */
    public ContextManager(IRCBot bot, Database database) {
        ModuleManager moduleManager = new ModuleManager();
        TimerManager timerManager = new TimerManager(bot, database, this);

        permanent = new PermanentContext(moduleManager, timerManager);
    }

    /**
     * Creates a new non-permanent context with this manager's permanent context and
     * the given arguments.
     *
     * @param bot        The IRC bot.
     * @param channel    The channel that replies should be sent to and that should
     *                   be available for querying.
     * @param user       The user that initiated the contextuated event.
     * @param timerEvent The timer event that was triggered, if it exists.
     * @return A new nonpermanent context.
     */
    public MewtwoContext makeContext(IRCBot bot, IChannel channel, User user, TimerEvent timerEvent) {
        return new MewtwoContext(bot, channel, user, timerEvent, permanent);
    }

    /**
     * Creates a new non-permanent context with this manager's permanent context and
     * the given arguments.
     *
     * @param bot     The IRC bot.
     * @param channel The channel that replies should be sent to and that should
     *                be available for querying.
     * @param user    The user that initiated the contextuated event.
     * @return A new nonpermanent context.
     */
    public MewtwoContext makeContext(IRCBot bot, IChannel channel, User user) {
        return new MewtwoContext(bot, channel, user, null, permanent);
    }

    public PermanentContext getPermanent() {
        return permanent;
    }
}
