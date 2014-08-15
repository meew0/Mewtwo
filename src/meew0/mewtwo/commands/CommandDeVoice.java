package meew0.mewtwo.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import meew0.mewtwo.MewtwoListener;

public class CommandDeVoice extends UserActionCommand {

	@Override
	public String getCommandName() {
		return "devoice";
	}

	@Override
	public void doAction(User u, Channel c, PircBotX bot) {
		MewtwoListener.voiceList.remove(u, c);
		c.send().deVoice(u);
	}

}
