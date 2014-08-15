package meew0.mewtwo.commands;

import com.google.common.base.Joiner;
import meew0.mewtwo.ruby.CommandWrapper;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by meew0 on 23.07.14.
 */
public class CommandExecute implements ICommand {
    @Override
    public String getCommandName() {
        return "admin/execute";
    }

    @Override
    public void onExecution(String[] args, MessageEvent<PircBotX> event) {
        CommandWrapper cmd = new CommandWrapper(args[0]);
        try {
            String result = cmd.execute(event.getUser().getNick(), event.getChannel().getName(),
                    Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length)));
            for(String s : result.split("\n")) {
                event.getChannel().send().message(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
