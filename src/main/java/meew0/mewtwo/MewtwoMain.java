package meew0.mewtwo;

import meew0.mewtwo.core.MewtwoLogger;
import meew0.mewtwo.core.ShutdownHook;
import meew0.mewtwo.irc.IRCBot;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by meew0 on 13.05.14.
 */
public class MewtwoMain {
    public static String prefix = "%";
    public static int maxChainLength = 1000, maxChars = 600, maxLines = 4;

    // TODO find a better way to do configuration

    /**
     * Returns a config file, creating it if it doesn't exist
     *
     * @param configName The path to the file
     * @return a config file
     */
    public static HierarchicalINIConfiguration getConfig(String configName) {
        try {
            File cfgFile = new File(configName);
            if (!cfgFile.exists() && !cfgFile.createNewFile())
                MewtwoLogger.error("Could not create config file " + configName);
            return new HierarchicalINIConfiguration(configName);
        } catch (Throwable t) {
            MewtwoLogger.errorThrowable(t);
        }

        return null;
    }

    /**
     * Main method, shouldn't be called
     *
     * @param args The command line arguments, unused
     */
    public static void main(String[] args) {
        // Register shutdown hook

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        // Load config

        HierarchicalINIConfiguration config = getConfig("mewtwo.cfg");

        if (config == null) {
            MewtwoLogger.error("mewtwo.cfg must exist");
            return;
        }

        prefix = config.getString("prefix");
        String nick = config.getString("nick");
        String serverHostname = config.getString("server");
        int port = config.getInt("port");
        maxChainLength = config.getInt("maxChainLength");
        maxChars = config.getInt("maxChars");
        maxLines = config.getInt("maxLines");

        // TODO remaining mewtwo.cfg values

        // Load password from file

        String password = "";

        try {
            password = FileUtils.readFileToString(Paths.get("NickservPassword").toFile(), "US-ASCII");
            MewtwoLogger.info("Password file loaded successfully");
        } catch (IOException e) {
            MewtwoLogger.warn("Password file not found! Either you won't be able to identify or you've made your bot really insecure!");
        }

        // Start initial bot

        IRCBot bot = new IRCBot(serverHostname, port, nick, password);
        bot.start();
    }
}
