package meew0.mewtwo.commands;

import java.util.Arrays;

import meew0.mewtwo.MewtwoListener;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Joiner;

public class CommandLMGTFY implements ICommand {

	@Override
	public String getCommandName() {
		return "lmgtfy";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		if (args.length < 2)
			event.getChannel().send().message(MewtwoListener.prefix + "help l");
		else {
			event.getChannel()
					.send()
					.message(
							args[0]
									+ ", http://lmgtfy.com/?q="
									+ Joiner.on("+").join(
											Arrays.copyOfRange(args, 1,
													args.length))); // wtf
																	// eclipse,
																	// that's
																	// even more
																	// unreadable
																	// than it
																	// was
																	// before
		}
	}

	@Override
	public String getHelpEntry() {
		return "lmgtfy|l <user> {term}: Creates a lmgtfy for the specified term.";
	}

	@Override
	public String getAlias() {
		return "l";
	}

}
