package meew0.mewtwo.context;

import java.time.Instant;
import java.util.Map;

import javax.annotation.Nullable;

import meew0.mewtwo.irc.IChannel;
import meew0.mewtwo.irc.IRCBot;
import meew0.mewtwo.irc.User;
import meew0.mewtwo.storage.Database;
import meew0.mewtwo.timers.TimerEvent;

/**
 * Created by meew0 on 08.11.14.
 */
public class MewtwoContext {
    private final IRCBot bot;
    private final IChannel channel;
    private final User user;
    @Nullable
    private final TimerEvent timerEvent;
    private String id = "";
    private String output = "";
    private String input = "";
    private String currentId = "";
    private final PermanentContext permanent;

    /**
     * Get the bot object
     *
     * @return the bot object
     */
    public IRCBot getBot() {
        return bot;
    }

    public IChannel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    @Nullable
    public TimerEvent getTimer() {
        return timerEvent;
    }

    /**
     * Get the user nick. Equivalent to getUser().getNick()
     *
     * @return the user nick
     */
    public String getUserNick() {
        return getUser().nick();
    }

    /**
     * Get the channel name. Equivalent to getChannel().getName()
     *
     * @return the channel name
     */
    public String getChannelName() {
        return getChannel().name();
    }

    /**
     * Get the context's id.
     * <p>
     * The id is a concatenation of all the things appended to this context,
     * separated by semicola. It's used for debugging purposes.
     * The last thing is the most recent.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Clear the result of the previous command and return the pre-cleaned result
     *
     * @return the result, before it was cleaned
     */
    public String clearResult() {
        String result = output;
        output = "";
        return result;
    }

    /**
     * Append something to the id. For more information, see {@link #getId}
     *
     * @param s the thing to append.
     */
    public void append(String s) {
        id += (";" + s);
        currentId = s;
    }

    public void write(String s) {
        output += (s + "\n");
    }

    /**
     * Set something as the stdin for future commands in the same chain.
     *
     * @param input thing to bind
     */
    public void bindInput(String input) {
        this.input = input;
    }

    /**
     * Get the input data set by previous commands.
     *
     * @return the input data
     */
    public String getInput() {
        return input;
    }

    /**
     * @return this context's permanent context
     */
    public PermanentContext getPCtx() {
        return permanent;
    }

    /**
     * @return the database underlying the permanent context
     */
    public Database getDb() {
        return permanent.getDb();
    }

    /**
     * Store something in the command data with this command's ID (set by append())
     * If you want to store something under a different ID, use getPCtx().put(...)
     *
     * @param key   The key under which to store the value
     * @param value The value to store
     */
    public void put(String key, Object value) {
        permanent.put(getCurrentId(), key, value);
    }

    /**
     * Get something from the command data using this command's ID
     *
     * @param key The key under which the value is stored
     * @return The value
     */
    public Object get(String key) {
        return permanent.get(getCurrentId(), key);
    }

    /**
     * Check whether or not a key exists in the command data
     *
     * @param key The key to check
     * @return Whether or not it exists
     */
    public boolean has(String key) {
        return permanent.has(getCurrentId(), key);
    }

    public Map<String, Object> list() {
        return permanent.list(getCurrentId());
    }

    public void delete(String key) {
        permanent.delete(getCurrentId(), key);
    }

    /**
     * Method to write something to the channel for scripts that take long to
     * execute
     *
     * @param s String to write
     */
    public void puts(String s) {
        channel.sendMessage(s);
    }

    /**
     * Add a new timer to be executed at the specified point in time.
     * 
     * @param type The name of the timer, must correspond to a Ruby script in the
     *             timers/ folder
     * @param at   The time point at which the timer should be executed
     * @return The context in which the new timer will be executed. Can be used to
     *         set properties in advance.
     */
    public MewtwoContext addTimer(String type, Instant at) {
        TimerEvent newTimer = permanent.getTimerManager().addTimerEvent(at, type, user, channel);
        MewtwoContext delegateContext = new MewtwoContext(bot, channel, user, newTimer, permanent);
        delegateContext.append(newTimer.getIdString());
        return delegateContext;
    }

    public MewtwoContext readdTimer(Instant at) {
        if (timerEvent == null) {
            throw new RuntimeException("Tried to readd timer but no timer is present");
        }

        permanent.getTimerManager().readdTimerEvent(timerEvent, at);
        return this;
    }

    /**
     * Create a new context, should only be used by ContextManager
     *
     * @param bot       the bot
     * @param channel   the current channel (can be a UserChannel)
     * @param user      the user who executed something
     * @param permanent the permanent context
     */
    MewtwoContext(IRCBot bot, IChannel channel, User user, TimerEvent timerEvent, PermanentContext permanent) {
        this.bot = bot;
        this.channel = channel;
        this.user = user;
        this.timerEvent = timerEvent;
        this.permanent = permanent;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void send(String command, String data) {
        bot.writeRaw(command, data);
    }
}
