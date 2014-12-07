package meew0.mewtwo.thread;

import com.google.common.base.Joiner;
import meew0.mewtwo.MewtwoMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by miras on 06.12.14.
 */
public class InputWatchThread implements Runnable {
    public class InputEntry {
        private String threadName;
        private String channel;
        private String message;

        public InputEntry(String threadName, String channel, String message) {
            this.setThreadName(threadName);
            this.setChannel(channel);
            this.setMessage(message);
        }


        public String getThreadName() {
            return threadName;
        }

        public void setThreadName(String threadName) {
            this.threadName = threadName;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static LinkedBlockingQueue<InputEntry> workerQueue = new LinkedBlockingQueue<InputEntry>();

    private BufferedReader stdinReader;

    public InputWatchThread() {
        stdinReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while(true) {
            String[] things;
            try {
                things = stdinReader.readLine().split(">");
            } catch(Throwable t) {
                //MewtwoMain.mewtwoLogger.warn("Error while pulling input from stdin! This can probably be ignored.");
                continue;
            }
            String msg = Joiner.on(" ").join(Arrays.copyOfRange(things, 1, things.length));
            String[] args = things[0].split(" ");
            String threadName = args[0];
            String channelName = args[1];

            InputEntry entry = new InputEntry(threadName, channelName, msg);
            try {
                workerQueue.put(entry);
                MewtwoMain.mewtwoLogger.info("Input from console successfully put into worker queue");
            } catch(Throwable t) {
                MewtwoMain.mewtwoLogger.error("Error while pulling input!", t);
            }
        }
    }

    public static boolean hasNextFor(String threadName) {
        return workerQueue != null && workerQueue.peek() != null && workerQueue.peek().getThreadName().equals(threadName);
    }

    public static InputEntry getNext() {
        return workerQueue.poll();
    }
}
