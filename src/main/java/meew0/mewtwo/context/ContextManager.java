package meew0.mewtwo.context;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

/**
 * Created by miras on 15.11.14.
 */
public class ContextManager {
    private PermanentContext permanent;


    /**
     * Creates a new context manager with a new permanent context.
     */
    public ContextManager() {
        permanent = new PermanentContext();
    }

    /**
     * Creates a new non-permanent context with this manager's permanent context and the given arguments.
     * @param bot The bot object.
     * @param channel The channel object.
     * @param user The user object.
     * @return A new nonpermanent context.
     */
    public MewtwoContext makeContext(PircBotX bot, Channel channel, User user) {
        return new MewtwoContext(bot, channel, user, permanent);
    }
}
