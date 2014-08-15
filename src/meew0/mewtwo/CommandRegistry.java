package meew0.mewtwo;

import java.util.ArrayList;

import meew0.mewtwo.commands.ICommand;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandRegistry {
	public ArrayList<ICommand> commands;
	
	public CommandRegistry() {
		commands = new ArrayList<ICommand>();
	}
	
	public void addCommand(ICommand command) {
        if(MewtwoMain.config.getBoolean("enable" + command.getCommandName(), true)) {
            MewtwoMain.mewtwoLogger.info("Command " + command.getCommandName() + " enabled, adding command");
            commands.add(command);
        } else {
            MewtwoMain.mewtwoLogger.info("Command " + command.getCommandName() + " not enabled");
        }
	}
	
	public void execute(String name, String[] args, MessageEvent<PircBotX> event) {
		boolean found = false;
		for(ICommand c : commands) {
			if(c.getCommandName().equalsIgnoreCase(name)) {
				found = true;
				c.onExecution(args, event);
			}
		}
		if(!found) {
			event.respond("Could not find command " + name + ", try " + MewtwoListener.prefix + "help");
		}
	}

    public boolean hasCommand(String name) {
        for(ICommand c : commands) {
            if(c.getCommandName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
