package meew0.mewtwo.irc;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.commands.CommandChainBuilder;
import meew0.mewtwo.commands.ICommandChain;
import meew0.mewtwo.context.ContextManager;
import meew0.mewtwo.context.MewtwoContext;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

import java.util.Date;

public class MewtwoListener extends ListenerAdapter<PircBotX> {
    private static ContextManager ctxMgr = new ContextManager();

    public MewtwoListener() {
    }

    void executeModules(String type, String msg, MewtwoContext ctx) {
        try {
            String result = ctx.getPCtx().getModuleManager().executeModules(type, msg, ctx);

            String[] ret = result.split("\n");
            for (String s : ret) {
                ctx.getChannel().send().message(s);
            }
        } catch (Throwable t) {
            MewtwoMain.mewtwoLogger.error("Error while executing a module! (" + ctx.getId() + ")", t);
        }
    }

    @Override
    public void onJoin(JoinEvent<PircBotX> event) throws Exception {
        executeModules("join", "", ctxMgr.makeContext(event.getBot(), event.getChannel(), event.getUser()));
    }

    @Override
    public void onNickChange(NickChangeEvent<PircBotX> event) throws Exception {
        executeModules("nickchange", "", ctxMgr.makeContext(event.getBot(), new UserChannel(event.getUser()),
                        event.getUser()));
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
        MewtwoContext ctx = ctxMgr.makeContext(event.getBot(), new UserChannel(event.getUser()), event.getUser());
        executeOneChain(event.getMessage(), ctx);

        executeModules("pm", event.getMessage(), ctx);
    }

    private static void executeOneChain(String msg, MewtwoContext ctx) {
        if (msg.startsWith(ctx.getPCtx().getMewtwoPrefix())) {
            CommandChainBuilder chainBuilder = new CommandChainBuilder(ctx, msg);
            ICommandChain chain = chainBuilder.buildChain();

            String result = chain.execute(ctx);
            ctx.benchmark("msg.cmd.chain.execute");

            if (result.length() <= 600 || ctx.getPCtx().isUserAdmin(ctx.getUserNick())) {
                // If the result is shorter than or equal to 500 characters,
                // post it to the channel
                String[] split = result.split("\n");
                if (split.length <= 4) {
                    for (String s : split) {
                        if (s.length() >= 445) {
                            String messageToSend = s.substring(0, 445);
                            if (!messageToSend.isEmpty())
                                ctx.getChannel().send().message(messageToSend);
                            s = s.substring(445, s.length());
                        }
                        if (!s.isEmpty()) ctx.getChannel().send().message(s);
                    }
                } else {
                    ctx.getChannel().send().message("Sorry, the response has exceeded the maximum amount of lines.");
                }
            } else {
                // Otherwise post the error message
                ctx.getChannel().send().message("Sorry, the response has exceeded the allowed character length.");
            }

            ctx.benchmark("msg.cmd.finish");

        }
    }

    @Override
    public void onAction(ActionEvent<PircBotX> event) throws Exception {
        String msg = event.getMessage();
        MewtwoContext ctx = ctxMgr.makeContext(event.getBot(), event.getChannel(), event.getUser());

        // Add message to log
        ctx.getPCtx().logMessage(msg, ctx.getUserNick());

        executeModules("action", event.getMessage(), ctx);
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        MewtwoContext ctx = ctxMgr.makeContext(event.getBot(), event.getChannel(), event.getUser());
        try {
            long counter = new Date().getTime();
            if (event.getUser().getNick().equals("Inumuta")) return;
            String msg = event.getMessage();

            ctx.getPCtx().logMessage(msg, ctx.getUserNick());
            ctx.benchmark("msg.pre");

            // Execute "message" module

            executeModules("message", msg, ctx);

            ctx.benchmark("msg.module");

            // Handle commands

            if (msg.startsWith(ctx.getPCtx().getMewtwoPrefix())) {
                executeOneChain(msg, ctx);
            }
            long diff = new Date().getTime() - counter;
            MewtwoMain.mewtwoLogger.info("Mewtwo took " + diff + " ms to execute!");
        } catch (Exception e) {
            // Error handling
            event.respond("ERROR: An exception has occurred! "
                    + e.getClass().getName() + ": " + e.getMessage());
            event.respond("See console for details.");
            e.printStackTrace();
        }
        MewtwoMain.mewtwoLogger.info("Benchmark data (times in ns): " + ctx.formatBenchmark());
    }
}
