package meew0.mewtwo.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import meew0.mewtwo.MewtwoListener;

public class CommandVoice extends UserActionCommand {

	@Override
	public String getCommandName() {
		return "voice";
	}

	@Override
	public String getHelpEntry() {
		return "voice <user>: Gives voice to a user.";
	}

	@Override
	public void doAction(User u, Channel c, PircBotX bot) {
		MewtwoListener.voiceList.add(u, c);
	}

}
