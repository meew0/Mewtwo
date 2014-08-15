package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by meew0 on 22.07.14.
 */
public class CommandText implements ICommand {
    String text, name;

    public CommandText(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public void onExecution(String[] args, MessageEvent<PircBotX> event) {
        if(args.length < 1) {
            event.getChannel().send().message(text);
        } else {
            event.getChannel().send().message(args[0] + ": " + text);
        }
    }
}
