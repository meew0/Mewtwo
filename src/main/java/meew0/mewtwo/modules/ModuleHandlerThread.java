package meew0.mewtwo.modules;

import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.irc.GenericHandlerThread;

/**
 * Created by meew0 on 03.04.15.
 */
public class ModuleHandlerThread extends GenericHandlerThread {
    public ModuleHandlerThread(MewtwoContext ctx, String target, String message) {
        super(ctx, target, message);
    }

    @Override
    protected String handle(MewtwoContext ctx, String message) {
        return ctx.getPCtx().getModuleManager().executeModules(message, ctx);
    }
}
