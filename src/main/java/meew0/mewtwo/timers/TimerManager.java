package meew0.mewtwo.timers;

import meew0.mewtwo.context.ContextManager;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import meew0.mewtwo.irc.IChannel;
import meew0.mewtwo.irc.IRCBot;
import meew0.mewtwo.irc.User;
import meew0.mewtwo.storage.Database;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.List;

public class TimerManager {
    public static final String timersFolder = "timers/";
    public static final int checkSeconds = 3600;

    private final IRCBot bot;
    private final Database database;
    private final ContextManager contextManager;

    private final Thread timerThread;

    private boolean shutdown = false;

    public TimerManager(IRCBot ircBot, Database database, ContextManager contextManager) {
        this.bot = ircBot;
        this.database = database;
        this.contextManager = contextManager;

        timerThread = new Thread(this::runTimers);
        timerThread.setName("Timer");
        timerThread.start();
    }

    public TimerEvent addTimerEvent(Instant instant, String name, User user, IChannel channel) {
        String channelName = channel == null ? null : channel.name();
        int id = database.addNewTimer(instant, name, user, channelName);
        timerThread.interrupt();
        return new TimerEvent(id, instant, name, user.nick(), user.fullHostmask(), user.hostmask(), channelName);
    }

    public void readdTimerEvent(@Nonnull TimerEvent timerEvent, Instant at) {
        database.readdTimer(timerEvent.getId(), at, timerEvent.getName(), timerEvent.getUser(null),
                timerEvent.getChannelName());
        timerThread.interrupt();
    }

    private void runTimers() {
        while (!shutdown) {
            try {
                Instant later = Instant.now().plusSeconds(checkSeconds);

                List<TimerEvent> pendingEvents = database.getPendingTimerEventsUntil(later);
                MewtwoLogger.info(
                        "Timer check: " + pendingEvents.size() + " pending event"
                                + (pendingEvents.size() == 1 ? "" : "s"));
                boolean interrupted = false;
                for (TimerEvent event : pendingEvents) {
                    long millisDelay = event.getInstant().toEpochMilli() - Instant.now().toEpochMilli();
                    if (millisDelay > 0) {
                        try {
                            Thread.sleep(millisDelay);
                        } catch (InterruptedException e) {
                            // Break the inner loop, such that the list of pending events is recalculated
                            // and the inner loop is restarted.
                            interrupted = true;
                            break;
                        }
                    }

                    executeTimer(event);
                }

                // Try to sleep for the remaining time, unless we were interrupted before and
                // should re-check immediately
                if (!interrupted) {
                    try {
                        long remaining = later.toEpochMilli() - Instant.now().toEpochMilli();
                        Thread.sleep(remaining);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            } catch (Exception e) {
                MewtwoLogger.error("Error while trying to run timers:");
                MewtwoLogger.errorThrowable(e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException intE) {
                    MewtwoLogger.warn("Interrupted while sleeping after runTimers error");
                }
            }
        }
    }

    private void executeTimer(TimerEvent event) {
        database.deleteTimer(event.getId());

        MewtwoContext ctx = contextManager.makeContext(bot, event.getChannel(bot), event.getUser(bot));
        TimerHandlerThread tht = new TimerHandlerThread(ctx, ctx.getChannelName(), event);
        tht.start();
    }

    public void signalShutdown() {
        shutdown = true;
        timerThread.interrupt();
    }
}
