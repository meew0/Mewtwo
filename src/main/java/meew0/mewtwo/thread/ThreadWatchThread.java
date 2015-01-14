package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoMain;

/**
 * Automatically interrupts a thread after 10 seconds
 */
public class ThreadWatchThread implements Runnable {
    private Thread toWatch;
    public ThreadWatchThread(Thread toWatch) {
        this.toWatch = toWatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toWatch.interrupt();
        MewtwoMain.mewtwoLogger.warn("Interrupted runaway listener thread");
    }
}
