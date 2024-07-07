package meew0.mewtwo.timers;

import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import meew0.mewtwo.irc.GenericHandlerThread;
import meew0.mewtwo.ruby.CommandExecutor;

public class TimerHandlerThread extends GenericHandlerThread {
    private final TimerEvent event;

    public TimerHandlerThread(MewtwoContext ctx, String target, TimerEvent event) {
        super(ctx, target, "");
        this.event = event;
    }

    @Override
    protected String handle(MewtwoContext ctx, String message) {
        ctx.append(event.getIdString());
        try {
            return CommandExecutor.genericExecute(TimerManager.timersFolder + event.getName() + ".rb",
                    ctx.getUserNick(), ctx.getChannelName(), "", ctx);
        } catch (Throwable t) {
            MewtwoLogger.errorThrowable(t);
        }
        return "";
    }
}
