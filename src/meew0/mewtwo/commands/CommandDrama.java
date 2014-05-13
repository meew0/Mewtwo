package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandDrama implements ICommand {

	@Override
	public String getCommandName() {
		return "drama";
	}

	@Override
	public String getAlias() {
		return "md";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		String compliment = "";
		if (args.length > 0)
			compliment += (args[0] + ", ");
		
		String c2 = "";
		try {
			Document doc = Jsoup.connect("http://asie.pl/drama.php")
					.get();
			Elements medium = doc.select("h1");
			
			c2 = medium.get(MewtwoListener.rnd.nextInt(medium.size()))
					.toString().replaceAll("<[^>]*>", "");
		} catch(Exception e) {
			throw new RuntimeException("Exception while getting a drama", e);
		}
		
		compliment += c2;
		
		event.getChannel().send().message(compliment);
	}

	@Override
	public String getHelpEntry() {
		return "drama|md: Generates a random Minecraft modding drama";
	}

}
