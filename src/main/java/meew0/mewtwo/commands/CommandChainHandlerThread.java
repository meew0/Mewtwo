package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;

/**
 * Created by meew0 on 03.04.15.
 *
 * Thread to handle command chains being executed
 */
public class CommandChainHandlerThread extends Thread {
    private static int threadNumber = 0;

    private final MewtwoContext ctx;
    private final String message;

    public CommandChainHandlerThread(MewtwoContext ctx, String message) {
        super("CCHT-" + (++threadNumber));
        this.ctx = ctx;
        this.message = message;
    }

    @Override
    public void run() {
        if(!message.startsWith(MewtwoMain.prefix)) {
            MewtwoLogger.warn("CCHT created that apparently doesn't actually handle a command chain, ignoring");
            return;
        }

        CommandChainBuilder ccBuilder = new CommandChainBuilder(ctx, message);
        ICommandChain chain = ccBuilder.buildChain();

        String result = chain.execute(ctx);
    }
}
