package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.irc.GenericHandlerThread;

/**
 * Created by meew0 on 03.04.15.
 * <p>
 * Thread to handle command chains being executed
 */
public class CommandChainHandlerThread extends GenericHandlerThread {
    public CommandChainHandlerThread(MewtwoContext ctx, String target, String message) {
        super(ctx, target, message);
    }

    @Override
    protected String handle(MewtwoContext ctx, String message) {
        CommandChainBuilder ccBuilder = new CommandChainBuilder(ctx, message);
        ICommandChain chain = ccBuilder.buildChain();
        return chain.execute(ctx);
    }
}
