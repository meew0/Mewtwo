package meew0.mewtwo;

import meew0.mewtwo.irc.MewtwoListener;
import meew0.mewtwo.thread.BotWrapperThread;
import meew0.mewtwo.thread.FileWatchThread;
import meew0.mewtwo.thread.InputWatchThread;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@SuppressWarnings("WeakerAccess")
public class MewtwoMain {
	public static String nick, server, login, password, prefix;
    public static int port, maxChainLength, maxChars, maxLines;
    public static int bwtCounter = 0;
    public static boolean shouldBenchmark, shouldProfile = false;

    public static Configuration config;
    public static MewtwoListener listener;

    public static org.pircbotx.Configuration.Builder configuration;

    public static Logger mewtwoLogger;

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
     * @throws ConfigurationException in case something happens while loading configs
     */
	public static void main(String[] args) throws ConfigurationException, IOException {
        // Make logger
        mewtwoLogger = LoggerFactory.getLogger("Mewtwo");
        config = getConfig("mewtwo.cfg");
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
        shouldProfile = config.getBoolean("shouldProfile", false);

        listener = new MewtwoListener();

        // Make configuration
		configuration = new org.pircbotx.Configuration.Builder()
		        .setLogin(login)
		        .setRealName("Mewtwo")
		        .setAutoNickChange(true)
		        .setCapEnabled(true)
                .addListener(listener);

        // Only set password if it is there
        if(!password.equals("")) configuration.setNickservPassword(password);

        // Start bot in new thread
        String threadName = "bwt-" + (bwtCounter++);
        BotWrapperThread mewtwo = new BotWrapperThread(configuration, nick, server, port, threadName);
        Thread mewtwoThread = new Thread(mewtwo, threadName);
        mewtwoLogger.info("Starting core BotWrapperThread with name '" + threadName + "'");
        mewtwoThread.start();
        mewtwoLogger.info("Successfully started core BotWrapperThread");

        Thread iwt = new Thread(new InputWatchThread(), "InputWatchThread");
        mewtwoLogger.info("Starting input watch thread with name '" + iwt.getName() + "'");
        iwt.start();
        mewtwoLogger.info("Successfully started file watch thread");

        Thread fwt = new Thread(new FileWatchThread(), "FileWatchThread");
        mewtwoLogger.info("Starting file watch thread with name '" + fwt.getName() + "'");
        fwt.start();
        mewtwoLogger.info("Successfully started file watch thread");
	}
}
