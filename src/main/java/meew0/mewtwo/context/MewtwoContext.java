package meew0.mewtwo.context;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
import meew0.mewtwo.MewtwoMain;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;

import java.util.HashMap;

/**
 * Created by miras on 08.11.14.
 */
public class MewtwoContext {
    private final PircBotX bot;
    private final Channel channel;
    private final User user;
    private String id = "", output = "", input = "", currentId = "";
    private long lastBenchmark;
    private final HashMap<String, Long> benchmark;
    private final PermanentContext permanent;

    /**
     * Get the bot object
     * @return the bot object
     */
    public PircBotX getBot() {
        return bot;
    }

    public Channel getChannel() {
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
     * Get all benchmark data as a hash map
     * @return benchmark data
     */
    public HashMap<String, Long> getBenchmarkData() {
        return benchmark;
    }

    /**
     * Format the benchmark data into a semi-readable format. Sorted by duration (longest last)
     * @return benchmark data, formatted
     */
    public String formatBenchmark() {
        Ordering comparator = Ordering.natural().onResultOf(Functions.forMap(benchmark));
        @SuppressWarnings("unchecked")
        ImmutableSortedMap<String, Long> sorted = ImmutableSortedMap.copyOf(benchmark, comparator);
        return sorted.toString();
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
     * Benchmark something.
     *
     * Benchmarks provide a quick-and-dirty way to profile stuff.
     * @param thing the thing to benchmark, can be any string
     */
    public void benchmark(String thing) {
        if(!MewtwoMain.shouldBenchmark) return;
        long newTime = System.nanoTime();
        long elapsed = newTime - lastBenchmark;

        if(benchmark.containsKey(thing)) benchmark.put(thing, benchmark.get(thing) + elapsed);
        else benchmark.put(thing, elapsed);

        lastBenchmark = newTime;
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
        permanent.put(currentId, key, value);
    }

    /**
     * Get something from the command data using this command's ID
     * @param key The key under which the value is stored
     * @return The value
     */
    public Object get(String key) {
        return permanent.get(currentId, key);
    }

    /**
     * Check whether or not a key exists in the command data
     * @param key The key to check
     * @return Whether or not it exists
     */
    public boolean has(String key) {
        return permanent.has(currentId, key);
    }

    /**
     * Create a new context, should only be used by ContextManager
     * @param bot the bot
     * @param channel the current channel (can be a UserChannel)
     * @param user the user who executed something
     * @param permanent the permanent context
     */
    MewtwoContext(PircBotX bot, Channel channel, User user, PermanentContext permanent) {
        lastBenchmark = System.nanoTime();
        this.bot = bot;
        this.channel = channel;
        this.user = user;
        this.permanent = permanent;
        benchmark = new HashMap<>();
        benchmark("ctx.create");
    }
}
