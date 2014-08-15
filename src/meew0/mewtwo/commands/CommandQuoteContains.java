package meew0.mewtwo.commands;

import com.google.common.base.Joiner;
import meew0.mewtwo.ChatLog;
import meew0.mewtwo.MewtwoListener;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by meew0 on 21.07.14.
 */
public class CommandQuoteContains implements ICommand {
    @Override
    public String getCommandName() {
        return "qfind";
    }

    @Override
    public void onExecution(String[] args, MessageEvent<PircBotX> event) {
        if(args.length < 1) {
            event.respond("There's nothing to match");
        } else {
            String regex = Joiner.on(" ").join(args);
            ChatLog.Message m = MewtwoListener.log.getLatestThatMatches(regex);
            if(m == null) {
                event.respond("Could not find a message that contains/matches " + regex);
                return;
            }

            event.getChannel().send().message("<" + m.nick + "> " + m.message);
        }
    }
}
