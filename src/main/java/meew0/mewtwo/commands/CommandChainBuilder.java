package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;
import org.pircbotx.User;

/**
 * Created by miras on 14.11.14.
 */
public class CommandChainBuilder {
    private MewtwoContext ctx;
    private String bareChain;
    public CommandChainBuilder(MewtwoContext ctx, String message) {
        this.ctx = ctx;

        this.bareChain = message.substring(1, message.length());
    }

    private void replaceChainArguments() {
        // Replace %csul and %ssul with a comma-separated and a space-separated
        // user list
        if (bareChain.contains("%csul") || bareChain.contains("%ssul")) {
            String csul = "", ssul = "";
            for (User u : ctx.getChannel().getUsers()) {
                String nick = u.getNick();
                nick = nick.replace("[", "{").replace("]", "}");
                nick = nick.substring(0,1) + ((char) 8206) + nick.substring(1,nick.length());
                csul += (nick + ",");
                ssul += (nick + " ");
            }
            bareChain = bareChain.replace("%csul", csul.substring(0, csul.length() - 1))
                    .replace("%ssul", ssul.substring(0, ssul.length() - 1));
        }

        bareChain = bareChain.replace("%msg", ctx.getPCtx().getMewtwoPrefix() + bareChain);
        bareChain = bareChain.replace("%user", ctx.getUser().getNick());
        bareChain = bareChain.replace("%chan", ctx.getChannel().getName());
        ctx.benchmark("chain.replace");
    }

    public ICommandChain buildChain() {
        if(ctx.getPCtx().shouldIgnoreUser(ctx.getUserNick())) return new StaticChain("");   // ignore user if I should
        if(ctx.getPCtx().isSlowmodeActive()) return new StaticChain("");                    // ignore if slowmode is active
        if(bareChain.contains("admin/") && !ctx.getPCtx().isUserAdmin(ctx.getUserNick()))
            return new StaticChain("I'm sorry. I can't let you do that.");                  // enforce security
        ctx.benchmark("chain.prebuild");

        replaceChainArguments();
        return new CommandChain(":" + bareChain);
    }
}
