package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandJoinChannel implements ICommand {

	@Override
	public String getCommandName() {
		return "join";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		event.getBot().sendIRC().joinChannel(args[0]);
	}

	@Override
	public String getHelpEntry() {
		return "join #<name>: Joins a channel. Unlike other commands, this one can also be executed in private chats.";
	}

	@Override
	public String getAlias() {
		return "join";
	}

}
