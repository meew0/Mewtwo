package meew0.mewtwo.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import meew0.mewtwo.MewtwoListener;

public class CommandDeKick extends UserActionCommand {

	@Override
	public String getCommandName() {
		return "dekick";
	}

	@Override
	public String getHelpEntry() {
		return "dekick <user>: Removes a user from the kick list.";
	}

	@Override
	public void doAction(User u, Channel c, PircBotX bot) {
		MewtwoListener.kickList.remove(u, c);
	}

}
