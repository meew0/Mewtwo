package meew0.mewtwo.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import meew0.mewtwo.MewtwoListener;

public class CommandKick extends UserActionCommand {

	@Override
	public String getCommandName() {
		return "kick";
	}

	@Override
	public String getHelpEntry() {
		return "kick <user>: Adds a user to the kick list (kicking them on join == banning them)";
	}

	@Override
	public void doAction(User u, Channel c, PircBotX bot) {
		MewtwoListener.kickList.add(u, c);
	}

}
