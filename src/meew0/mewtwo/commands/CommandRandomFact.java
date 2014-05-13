package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

public class CommandRandomFact implements ICommand {

	@Override
	public String getCommandName() {
		return "randomfact";
	}

	@Override
	public String getAlias() {
		return "rf";
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		String fact = "This command generates random facts.";
		try {
			Document doc = Jsoup.connect(
						//"http://randomfactgenerator.net/"
						"http://randomfunfacts.com" // replacement until RFG is back up
					).get();
			Elements facts = 
					//doc.select("#f").get(1).select("#z");
					doc.select("strong i");
			
			fact = facts.get(MewtwoListener.rnd.nextInt(facts.size())).toString().replaceAll("<[^>]*>", "").trim();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		event.getChannel().send().message(fact);
	}

	@Override
	public String getHelpEntry() {
		return "randomfact|rf: Gets a random fact and posts it to the channel.";
	}

}
