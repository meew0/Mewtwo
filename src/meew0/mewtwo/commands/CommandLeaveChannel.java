package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandLeaveChannel implements ICommand {

	@Override
	public String getCommandName() {
		return "admin/leave";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
        event.getChannel().send().part("NOOOOOOOOooooooooooo.....");
	}

}
