package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandLeaveChannel implements ICommand {

	@Override
	public String getCommandName() {
		return "leave";
	}

	@Override
	public String getHelpEntry() {
		return "leave #<channel>: Leaves a channel, NYI";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		event.getChannel().send().message("/leave " + args[0]); // hacky hacky
	}

	@Override
	public String getAlias() {
		return "leave";
	}

}
