package meew0.mewtwo.context;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.irc.ChatLog;
import meew0.mewtwo.modules.ModuleManager;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by miras on 14.11.14.
 */
public class PermanentContext {
    private final ChatLog log;

    private final HierarchicalINIConfiguration aliases, admins, disable, ignore;

    private final ModuleManager moduleManager;

    private boolean slowmodeEnabled = false;
    private int slowmodeTime = 0;
    private long slowmodeTS = 0;

    /**
     * Make a new permanent context
     */
    public PermanentContext() {
        moduleManager = new ModuleManager();
        log = new ChatLog();
        aliases = MewtwoMain.getConfig("aliases.cfg");
        admins = MewtwoMain.getConfig("admins.cfg");
        disable = MewtwoMain.getConfig("disable.cfg");
        ignore = MewtwoMain.getConfig("ignore.cfg");
    }

    /**
     * Get this context's module manager
     * @return the module manager
     */
    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    /**
     *
     * @return whether or not slowmode is enabled
     */
    public boolean isSlowmodeEnabled() {
        return slowmodeEnabled;
    }

    /**
     * @return this context's chat log
     */
    public ChatLog getLog() {
        return log;
    }

    /**
     * Adds a message to the chat log
     * @param msg the message
     * @param nick the nick of the user who wrote the message
     */
    public void logMessage(String msg, String nick) {
        log.add(msg, nick);
    }

    /**
     *
     * @return A linked list of the last (ChatLog.limit) messages, newest first
     */
    public LinkedList<ChatLog.Message> getLastLogged() {
        return log.messages;
    }

    /**
     * Gets the last message written by a specific user
     * @param userNick the user's nick
     * @return the user's last message
     */
    public ChatLog.Message getLastOfUser(String userNick) {
        return log.getLatestFromUser(userNick);
    }

    /**
     * Gets the last message that matches a specific regex
     * @param regex the regex
     * @return the last message that matches
     */
    public ChatLog.Message getLastMatch(String regex) {
        return log.getLatestThatMatches(regex);
    }

    /**
     * Enables slowmode with a specific duration
     * @param time the duration in milliseconds
     */
    public void enableSlowmode(int time) {
        slowmodeTime = time;
        slowmodeEnabled = true;
    }

    /**
     * Disables slowmode
     */
    public void disableSlowmode() {
        slowmodeTime = 0;
        slowmodeEnabled = false;
        slowmodeTS = 0;
    }

    private boolean checkConfigFile(HierarchicalINIConfiguration config, String thing) {
        return config.containsKey(thing) && config.getBoolean(thing);
    }

    /**
     * @param userNick the user's nick
     * @return whether or not the user is admin
     */
    public boolean isUserAdmin(String userNick) {
        return checkConfigFile(admins, userNick);
    }

    /**
     *
     * @param userNick the user's nick
     * @return whether or not the user should be ignored
     */
    public boolean shouldIgnoreUser(String userNick) {
        return checkConfigFile(ignore, userNick);
    }

    /**
     *
     * @param commandName the command's name
     * @return whether or not the command is enabled
     */
    public boolean isCommandEnabled(String commandName) {
        return checkConfigFile(disable, commandName);
    }

    /**
     * Get the command from an alias, as defined by aliases.cfg
     * If no alias is defined, the original alias is returned
     * @param commandName the alias
     * @return the actual command
     */
    public String getAliasForCommand(String commandName) {
        if(!aliases.containsKey(commandName)) return commandName;
        else return aliases.getString(commandName);
    }

    /**
     *
     * @return the prefix Mewtwo uses for commands
     */
    public String getMewtwoPrefix() {
        return MewtwoMain.prefix;
    }

    /**
     * Returns whether or not slowmode is currently active and, if it is active, whether or not the time has run out yet
     * @return is slowmode active?
     */
    public boolean isSlowmodeActive() {
        if(!slowmodeEnabled) return false;

        long d = new Date().getTime();
        if(slowmodeEnabled && ((d - slowmodeTime) > slowmodeTS)) {
            slowmodeTS = d;
            return true;
        }
        return false;
    }

    private void reloadConfig(HierarchicalINIConfiguration config) {
        config.clear();
        try {
            config.load();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload all config files
     */
    public void reloadConfigs() {
        reloadConfig(admins);
        reloadConfig(disable);
        reloadConfig(aliases);
        reloadConfig(ignore);
        moduleManager.reloadConfigs();
    }
}
