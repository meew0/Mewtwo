package meew0.mewtwo.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Joiner;

public class CommandRainbow implements ICommand {

	@Override
	public String getCommandName() {
		return "rainbow";
	}
	
	public static final String[] colors = {
		"04", // red
		"05", // orange
		"08", // yellow
		"09", // green
		"12", // blue
		"13"  // violet
	};
	
	public String colorText(String color, String text) {
		return "\u0003" + color + text;
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		String msg = Joiner.on(" ").join(args);
		String newMsg = "";
		
		for(int i = 0; i < msg.length(); i++) {
			String x = String.valueOf(msg.charAt(i));
			newMsg += colorText(colors[i % 6], x);
		}

		event.getChannel().send().message(newMsg);
	}

	@Override
	public String getHelpEntry() {
		return "rainbow|rb <term>: Rainbowifies a term.";
	}

	@Override
	public String getAlias() {
		return "rb";
	}

}
