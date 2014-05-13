package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandRandomNumber implements ICommand {

	@Override
	public String getCommandName() {
		return "rand";
	}

	@Override
	public String getAlias() {
		return "rn";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		float low = 0.0f, high = 1.0f;
		boolean round = false;
		if(args.length > 2) {
			round = true;
		}
		if(args.length > 1) {
			high = Float.parseFloat(args[1]);
		}
		if(args.length > 0) {
			low = Float.parseFloat(args[0]);
		}
		double rnd = Math.random() * high + low;
		if(round) rnd = Math.round(rnd);
		event.respond("" + rnd);
	}

	@Override
	public String getHelpEntry() {
		return "rand|rn [lower] [upper] [+r]: Generates a random number between 0 and 1, the lower bound and 1 or the upper bound and the lower bound. The flag +r will round the number to the nearest integer";
	}
}
