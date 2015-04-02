package meew0.mewtwo;

import meew0.mewtwo.core.ShutdownHook;
import meew0.mewtwo.irc.IRCBot;

public class MewtwoMain {
    public static String prefix = "%";

    /**
     * Main method, shouldn't be called
     * @param args The command line arguments, unused
     */
	public static void main(String[] args) {
        // Register shutdown hook

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        // Start initial bot

        IRCBot bot = new IRCBot("127.0.0.1", 6667, "Mewtwo");
        bot.start();
	}
}
