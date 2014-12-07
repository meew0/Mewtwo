package meew0.mewtwo.thread;

/**
 * Created by miras on 03.12.14.
 */
public class ListenerPoolThreadInfo {
    private String message;
    private String user;
    private long executionBeginTimestamp;

    public ListenerPoolThreadInfo(String message, String user, long executionBeginTimestamp) {
        this.message = message;
        this.user = user;
        this.executionBeginTimestamp = executionBeginTimestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public long getExecutionBeginTimestamp() {
        return executionBeginTimestamp;
    }

    public int getElapsedTime() {
        return (int)(System.currentTimeMillis() - executionBeginTimestamp);
    }
}
