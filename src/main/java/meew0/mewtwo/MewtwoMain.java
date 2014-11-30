package meew0.mewtwo;

import meew0.mewtwo.irc.BotWrapperThread;
import meew0.mewtwo.irc.MewtwoListener;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.io.FileUtils;
import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

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

        password = "";

        // Load password from file
        try {
            password = FileUtils.readFileToString(Paths.get("NickservPassword").toFile());
            mewtwoLogger.info("Password file loaded successfully");
        } catch (IOException e) {
            mewtwoLogger.warn("Password file not found! Either you won't be able to identify or you've made your bot really insecure!");
        }

        mewtwoLogger.info("Loaded config");

        // Load all arguments from config file
        nick = config.getString("nick", "Mewtwo");
        server = config.getString("server", "irc.esper.net");
        login = config.getString("login", "Mewtwo");
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

        // Only set password if it is there
        if(!password.equals("")) configuration.setNickservPassword(password);

        // Start bot in new thread
        BotWrapperThread mewtwo = new BotWrapperThread(configuration, nick, server, port);
        Thread mewtwoThread = new Thread(mewtwo);
        mewtwoThread.start();
	}
}
