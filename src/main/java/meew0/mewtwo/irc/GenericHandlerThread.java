package meew0.mewtwo.irc;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;

/**
 * Created by meew0 on 03.04.15.
 * <p>
 * Thread to handle command chains being executed
 */
public abstract class GenericHandlerThread extends Thread {
    private static int threadNumber = 0;

    private final MewtwoContext ctx;
    private final String target, message;
    private final Thread executeAfter;

    private static final int maxLineChars = 430;

    public GenericHandlerThread(MewtwoContext ctx, String target, String message, Thread executeAfter) {
        super("HT-" + (++threadNumber));
        this.ctx = ctx;
        this.target = target;
        this.message = message;
        this.executeAfter = executeAfter;
    }

    private void sendMessage(String message) {
        if (!message.isEmpty()) {
            ctx.getBot().writePrivmsg(target, message);
        }
    }

    protected abstract String handle(MewtwoContext ctx, String message);

    @Override
    public void run() {
        if (executeAfter != null) {
            try {
                executeAfter.join();
            } catch (InterruptedException e) {
                MewtwoLogger.errorThrowable(e);
            }
        }

        String result = handle(ctx, message);

        if (result.length() > MewtwoMain.maxChars && !ctx.getPCtx().userIsAdmin(ctx.getUser())) {
            // Result is too long
            sendMessage("Sorry, the result length exceeds the limit of " + MewtwoMain.maxChars + " characters");
            return;
        }

        // Split result into lines
        String[] splitResult = result.split("\n");

        if (splitResult.length > MewtwoMain.maxLines && !ctx.getPCtx().userIsAdmin(ctx.getUser())) {
            // Result has too many lines
            sendMessage("Sorry, the result line count exceeds the limit of " + MewtwoMain.maxLines + " lines");
        }

        for (String line : splitResult) {
            // Make sure lines are not too long for IRC to handle
            while (line.length() > maxLineChars) {
                String lineToWrite = line.substring(0, maxLineChars);
                line = line.substring(maxLineChars);
                sendMessage(lineToWrite);
            }

            // Send remainder of line
            sendMessage(line);
        }
    }
}
