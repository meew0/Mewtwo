package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;

/**
 * Created by meew0 on 14.11.14.
 */
public class CommandChainBuilder {
    private final MewtwoContext ctx;
    private String bareChain;
    public CommandChainBuilder(MewtwoContext ctx, String message) {
        this.ctx = ctx;

        this.bareChain = message.substring(1, message.length());
    }

    private void replaceChainArguments() {
        // TODO: Replace %[cs]sul, %msg, %user, %chan with commands
    }

    public ICommandChain buildChain() {
        if(ctx.getPCtx().shouldIgnoreUser(ctx.getUserNick())) return new StaticChain("");   // ignore user if I should
        if(ctx.getPCtx().isSlowmodeActive()) return new StaticChain("");                    // ignore if slowmode is active
        // TODO: Check commands individually and not the chain as a whole
        if(bareChain.contains("admin/") && !ctx.getPCtx().isUserAdmin(ctx.getUser()))
            return new StaticChain("I'm sorry. I can't let you do that.");                  // enforce security

        replaceChainArguments();
        return new CommandChain(":" + bareChain);
    }
}