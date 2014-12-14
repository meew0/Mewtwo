package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.irc.ConsoleUser;
import meew0.mewtwo.irc.ConsoleUserHostmask;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by meew0 on 31.08.14.
 */
public class BotWrapperThread implements Runnable {
    private final Configuration.Builder configuration;
    private final String threadName;

    public BotWrapperThread(Configuration.Builder configuration, String nick, String serverHostName, int serverPort, String threadName) {
        this.configuration = configuration;

        this.configuration.setName(nick);
        this.configuration.setServer(serverHostName, serverPort);

        this.threadName = threadName;
    }

    @Override
    public void run() {
        final PircBotX mewtwo = new PircBotX(configuration.buildConfiguration());

        MewtwoMain.mewtwoLogger.info("Bot created, starting up...");

        MewtwoMain.mewtwoLogger.info("Starting up processing thread...");
        new Thread(() -> {
            while(true) {
                if(InputWatchThread.hasNextFor(threadName)) {
                    MewtwoMain.mewtwoLogger.info("Hey! There's something for me to do!");
                    InputWatchThread.InputEntry entry = InputWatchThread.getNext();
                    Channel channel = mewtwo.getUserChannelDao().getChannel(entry.getChannel());
                    User user = new ConsoleUser(mewtwo);
                    MessageEvent event = new MessageEvent(mewtwo, channel, "not sure what this is",
                            new ConsoleUserHostmask(mewtwo), user, entry.getMessage());
                    // TODO: fix channel source and hostmask

                    try {
                        MewtwoMain.listener.onMessage(event);
                    } catch(Throwable t) {
                        MewtwoMain.mewtwoLogger.error("Error while triggering console event!", t);
                    }
                }
            }
        }, threadName + "-InputProcessingThread").start();
        MewtwoMain.mewtwoLogger.info("Successfully started input processing thread");

        try {
            mewtwo.startBot();
        } catch(Throwable t) {
            MewtwoMain.mewtwoLogger.error("Error while starting/running bot! This is most likely fatal and you should restart Mewtwo now", t);
        }
    }
}
