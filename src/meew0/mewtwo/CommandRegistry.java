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
		commands.add(command);
	}
	
	public void execute(String name, String[] args, MessageEvent<PircBotX> event) {
		boolean found = false;
		for(ICommand c : commands) {
			if(c.getCommandName().equalsIgnoreCase(name) || c.getAlias().equalsIgnoreCase(name)) {
				found = true;
				c.onExecution(args, event);
			}
		}
		if(!found) {
			event.respond("Could not find command " + name + ", try " + MewtwoListener.prefix + "help");
		}
	}
}
