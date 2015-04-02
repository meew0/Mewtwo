package meew0.mewtwo.core;

import meew0.mewtwo.irc.IRCBot;

/**
 * Created by meew0 on 02.04.15.
 */
public class ShutdownHook extends Thread {
    public ShutdownHook() {
        super("ShutdownHook");
    }

    @Override
    public void run() {
        MewtwoLogger.info("ShutdownHook registered JVM shutdown, making all IRCBots shut down");
        IRCBot.shouldShutDown = true;
    }
}
