package meew0.mewtwo.context;

import meew0.mewtwo.irc.IChannel;
import meew0.mewtwo.irc.IRCBot;
import meew0.mewtwo.irc.User;

/**
 * Created by meew0 on 15.11.14.
 */
public class ContextManager {
    private final PermanentContext permanent;


    /**
     * Creates a new context manager with a new permanent context.
     */
    public ContextManager() {
        permanent = new PermanentContext();
    }

    /**
     * Creates a new non-permanent context with this manager's permanent context and the given arguments.
     *
     * @param bot     The bot object.
     * @param channel The channel object.
     * @param user    The user object.
     * @return A new nonpermanent context.
     */
    public MewtwoContext makeContext(IRCBot bot, IChannel channel, User user) {
        return new MewtwoContext(bot, channel, user, permanent);
    }

    public PermanentContext getPermanent() {
        return permanent;
    }
}