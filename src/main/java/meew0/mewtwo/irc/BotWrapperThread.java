package meew0.mewtwo.irc;

import meew0.mewtwo.MewtwoMain;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

/**
 * Created by meew0 on 31.08.14.
 */
public class BotWrapperThread implements Runnable {
    private final Configuration.Builder<PircBotX> configuration;

    public BotWrapperThread(Configuration.Builder<PircBotX> configuration, String nick, String serverHostName, int serverPort) {
        this.configuration = configuration;

        this.configuration.setName(nick);
        this.configuration.setServer(serverHostName, serverPort);
    }

    @Override
    public void run() {
        PircBotX mewtwo = new PircBotX(configuration.buildConfiguration());

        MewtwoMain.mewtwoLogger.info("Bot created, starting up...");

        try {
            mewtwo.startBot();
            MewtwoMain.mewtwoLogger.info("Bot started, listening now");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
