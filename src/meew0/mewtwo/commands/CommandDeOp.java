package meew0.mewtwo.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import meew0.mewtwo.MewtwoListener;

public class CommandDeOp extends UserActionCommand {

	@Override
	public String getCommandName() {
		return "deop";
	}

	@Override
	public String getHelpEntry() {
		return "deop <user>: Deops a user";
	}

	@Override
	public void doAction(User u, Channel c, PircBotX bot) {
		MewtwoListener.opList.remove(u, c);
		c.send().deOp(u);
	}

}
