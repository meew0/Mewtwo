package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoMain;
import org.pircbotx.PircBotX;

/**
 * Created by meew0 on 04.02.15.
 */
public class AutoShutdownThread implements Runnable {
    private final PircBotX bot;

    public AutoShutdownThread(PircBotX bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        if(MewtwoMain.autoShutdown <= 0) {
            MewtwoMain.mewtwoLogger.info("Not starting up AutoShutdownThread");
            return;
        }
        MewtwoMain.mewtwoLogger.info("AutoShutdownThread starting up, automatically shutting down Mewtwo in " + MewtwoMain.autoShutdown + " seconds");

        try {
            Thread.sleep(MewtwoMain.autoShutdown * 1000);
        } catch(InterruptedException e) {
            MewtwoMain.mewtwoLogger.info("AutoShutdownThread interrupted! This is not good. Shutting down Mewtwo now");
        }

        MewtwoMain.mewtwoLogger.info("AutoShutdownThread timer ran out! Shutting down Mewtwo");
        bot.sendIRC().quitServer("Auto shutdown after " + MewtwoMain.autoShutdown + " seconds");

        MewtwoMain.mewtwoLogger.info("Quit server, now waiting 10 seconds and exiting");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
