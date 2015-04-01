package meew0.mewtwo;

import meew0.mewtwo.thread.IRCBot;

public class MewtwoMain {

    /**
     * Main method, shouldn't be called
     * @param args The command line arguments, unused
     */
	public static void main(String[] args) {
        IRCBot bot = new IRCBot("127.0.0.1", 6667, "Mewtwo");
        bot.start();
	}
}
