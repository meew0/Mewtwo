package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoListener;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandHelp implements ICommand {

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		if(args.length < 1 || args[0].equals("+d")) {
			
			if(args.length > 0 && args[0].equals("+d")) {
				event.respond("Detailed list of available commands:");
				for(ICommand c : MewtwoListener.registry.commands) {
					event.respond(" " + c.getHelpEntry());
				}
			} else {
				String s = "Available commands: ";
				for(ICommand c : MewtwoListener.registry.commands) {
					s += c.getCommandName();
					s += " ";
				}
				event.respond(s);
				event.respond("Try help [commandName] for more info");
				
			}
		}
		if(args.length > 0 && !(args[0].equals("+d"))) {
			boolean found = false;
			for(ICommand c : MewtwoListener.registry.commands) {
				if(c.getCommandName().equals(args[0])) {
					found = true;
					event.respond(c.getHelpEntry());
				}
			}
			if(!found) {
				event.respond("Could not find command " + args[0] + ", try help for a list of commands");
			}
		}
	}

	@Override
	public String getHelpEntry() {
		return "help|h [+d|commandName]: Gets a list of commands. Flag +d gets a detailed list, a command name gets the help for that command.";
	}

	@Override
	public String getAlias() {
		return "h";
	}

}
