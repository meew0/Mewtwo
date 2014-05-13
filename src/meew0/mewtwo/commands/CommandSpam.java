package meew0.mewtwo.commands;


import meew0.mewtwo.MewtwoListener;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Joiner;

public class CommandSpam implements ICommand {
	
	public static final int amount = 20;

	@Override
	public String getCommandName() {
		return "spam";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		int xa = MewtwoListener.rnd.nextInt(amount);
		String msg = Joiner.on(" ").join(args);
		for(int x = 0; x < xa; x++) {
			String line = msg;
			int ya = MewtwoListener.rnd.nextInt(amount);
			for(int y = 0; y < ya; y++) {
				line += " ";
				line += msg;
			}
			event.getChannel().send().message(line);
		}
	}

	@Override
	public String getHelpEntry() {
		return "spam {term}: Spams the term a random amount of times.";
	}

	@Override
	public String getAlias() {
		return "spam";
	}

}
