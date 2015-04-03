package meew0.mewtwo;

import meew0.mewtwo.core.ShutdownHook;
import meew0.mewtwo.irc.IRCBot;
import org.apache.commons.configuration.HierarchicalINIConfiguration;

public class MewtwoMain {
    public static String prefix = "%";

    /**
     * Returns a config file, creating it if it doesn't exist
     * @param configName The path to the file
     * @return a config file
     */
    public static HierarchicalINIConfiguration getConfig(String configName) {
        try {
            File cfgFile = new File(configName);
            if (!cfgFile.exists() && !cfgFile.createNewFile())
                mewtwoLogger.error("Could not create config file " + configName);
            return new HierarchicalINIConfiguration(configName);
        } catch(Throwable t) {
            mewtwoLogger.error("Error while creating config file " + configName + "!", t);
        }

        return null;
    }

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
