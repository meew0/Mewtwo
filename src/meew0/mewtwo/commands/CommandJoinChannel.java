package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandJoinChannel implements ICommand {

	@Override
	public String getCommandName() {
		return "admin/join";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		event.getBot().sendIRC().joinChannel(args[0]);
	}


}
