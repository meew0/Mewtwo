package meew0.mewtwo;

import meew0.mewtwo.irc.BotWrapperThread;
import meew0.mewtwo.irc.MewtwoListener;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MewtwoMain {
	public static String nick, server, login, password, prefix;
    public static int port, maxChainLength, maxChars, maxLines;
    public static boolean shouldBenchmark;

    public static Configuration config;

    public static org.pircbotx.Configuration.Builder<PircBotX> configuration;

    public static Logger mewtwoLogger;

    /**
     * Main method, shouldn't be called
     * @param args The command line arguments, unused
     * @throws ConfigurationException in case something happens while loading configs
     */
	public static void main(String[] args) throws ConfigurationException {
        // Make logger
        mewtwoLogger = LoggerFactory.getLogger("Mewtwo");

        // Load config file
        config = new HierarchicalINIConfiguration("mewtwo.cfg");

        mewtwoLogger.info("Loaded config");

        // Load all arguments from config file
        nick = config.getString("nick", "Mewtwo");
        server = config.getString("server", "irc.esper.net");
        login = config.getString("login", "Mewtwo");
        password = config.getString("password", "");
        port = config.getInt("port", 6667);
        prefix = config.getString("prefix", "%");

        maxChainLength = config.getInt("maxChainLength", 1000);
        maxChars = config.getInt("maxChars", 600);
        maxLines = config.getInt("maxLines", 10);

        shouldBenchmark = config.getBoolean("shouldBenchmark", false);

        // Make configuration
		configuration = new org.pircbotx.Configuration.Builder<PircBotX>()
		        .setLogin(login)
		        .setRealName("Mewtwo")
		        .setAutoNickChange(true)
		        .setCapEnabled(true)
                .addListener(new MewtwoListener());

        if(!password.equals("")) configuration.setNickservPassword(password);

        // Start bot in new thread
        BotWrapperThread mewtwo = new BotWrapperThread(configuration, nick, server, port);
        Thread mewtwoThread = new Thread(mewtwo);
        mewtwoThread.start();
	}
}
