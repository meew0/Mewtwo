package meew0.mewtwo.context;

import meew0.mewtwo.irc.IChannel;
import meew0.mewtwo.irc.IRCBot;
import meew0.mewtwo.irc.User;

/**
 * Created by meew0 on 08.11.14.
 */
public class MewtwoContext {
    private final IRCBot bot;
    private final IChannel channel;
    private final User user;
    private String id = "";
    private String output = "";
    private String input = "";
    private String currentId = "";
    private final PermanentContext permanent;

    /**
     * Get the bot object
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

    /**
     * Get the user nick. Equivalent to getUser().getNick()
     * @return the user nick
     */
    public String getUserNick() {
        return getUser().getNick();
    }

    /**
     * Get the channel name. Equivalent to getChannel().getName()
     * @return the channel name
     */
    public String getChannelName() {
        return getChannel().getName();
    }

    /**
     * Get the context's id.
     *
     * The id is a concatenation of all the things appended to this context, separated by semicola. It's used for debugging purposes.
     * The last thing is the most recent.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Clear the result of the previous command and return the pre-cleaned result
     * @return the result, before it was cleaned
     */
    public String clearResult() {
        String result = output;
        output = "";
        return result;
    }

    /**
     * Append something to the id. For more information, see {@link #getId}
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
     * @param input thing to bind
     */
    public void bindInput(String input) {
        this.input = input;
    }

    /**
     * Get the input data set by previous commands.
     * @return the input data
     */
    public String getInput() {
        return input;
    }

    /**
     *
     * @return this context's permanent context
     */
    public PermanentContext getPCtx() {
        return permanent;
    }

    /**
     * Store something in the command data with this command's ID (set by append())
     * If you want to store something under a different ID, use getPCtx().put(...)
     * @param key The key under which to store the value
     * @param value The value to store
     */
    public void put(String key, Object value) {
        permanent.put(getCurrentId(), key, value);
    }

    /**
     * Get something from the command data using this command's ID
     * @param key The key under which the value is stored
     * @return The value
     */
    public Object get(String key) {
        return permanent.get(getCurrentId(), key);
    }

    /**
     * Check whether or not a key exists in the command data
     * @param key The key to check
     * @return Whether or not it exists
     */
    public boolean has(String key) {
        return permanent.has(getCurrentId(), key);
    }

    /**
     * Method to write something to the channel for scripts that take long to execute
     * @param s String to write
     */
    public void puts(String s) {
        channel.sendMessage(s);
    }

    /**
     * Create a new context, should only be used by ContextManager
     * @param bot the bot
     * @param channel the current channel (can be a UserChannel)
     * @param user the user who executed something
     * @param permanent the permanent context
     */
    MewtwoContext(IRCBot bot, IChannel channel, User user, PermanentContext permanent) {
        this.bot = bot;
        this.channel = channel;
        this.user = user;
        this.permanent = permanent;
    }

    public String getCurrentId() {
        return currentId;
    }
}