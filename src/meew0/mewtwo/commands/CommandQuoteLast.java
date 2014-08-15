package meew0.mewtwo.commands;

import meew0.mewtwo.ChatLog;
import meew0.mewtwo.MewtwoListener;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by meew0 on 21.07.14.
 */
public class CommandQuoteLast implements ICommand {
    @Override
    public String getCommandName() {
        return "qlast";
    }

    @Override
    public void onExecution(String[] args, MessageEvent<PircBotX> event) {
        if(args.length < 1) {
            event.respond("There's no user there");
        } else {
            ChatLog.Message m = MewtwoListener.log.getLatestFromUser(args[0]);
            if(m == null) {
                event.respond("Could not find a message from user " + args[0]);
                return;
            }

            event.getChannel().send().message("<" + m.nick + "> " + m.message);
        }
    }
}
