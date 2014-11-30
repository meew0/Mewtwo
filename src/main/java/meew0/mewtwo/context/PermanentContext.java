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
    private ChatLog log;

    private HierarchicalINIConfiguration aliases, admins, disable, ignore;

    private ModuleManager moduleManager;

    private boolean slowmodeEnabled = false;
    private int slowmodeTime = 0;
    private long slowmodeTS = 0;

    public PermanentContext() {
        moduleManager = new ModuleManager();
        log = new ChatLog();
        try {
            aliases = new HierarchicalINIConfiguration("aliases.cfg");
            admins = new HierarchicalINIConfiguration("admins.cfg");
            disable = new HierarchicalINIConfiguration("disable.cfg");
            ignore = new HierarchicalINIConfiguration("ignore.cfg");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public boolean isSlowmodeEnabled() {
        return slowmodeEnabled;
    }

    public ChatLog getLog() {
        return log;
    }

    public void logMessage(String msg, String nick) {
        log.add(msg, nick);
    }

    public LinkedList<ChatLog.Message> getLastLogged() {
        return log.messages;
    }

    public ChatLog.Message getLastOfUser(String userNick) {
        return log.getLatestFromUser(userNick);
    }

    public ChatLog.Message getLastMatch(String regex) {
        return log.getLatestThatMatches(regex);
    }

    public void enableSlowmode(int time) {
        slowmodeTime = time;
        slowmodeEnabled = true;
    }

    public void disableSlowmode() {
        slowmodeTime = 0;
        slowmodeEnabled = false;
        slowmodeTS = 0;
    }

    private boolean checkConfigFile(HierarchicalINIConfiguration config, String thing) {
        return config.containsKey(thing) && config.getBoolean(thing);
    }

    public boolean isUserAdmin(String userNick) {
        return checkConfigFile(admins, userNick);
    }

    public boolean shouldIgnoreUser(String userNick) {
        return checkConfigFile(ignore, userNick);
    }

    public boolean isCommandEnabled(String commandName) {
        return checkConfigFile(disable, commandName);
    }

    public String getAliasForCommand(String commandName) {
        if(!aliases.containsKey(commandName)) return commandName;
        else return aliases.getString(commandName);
    }

    public String getMewtwoPrefix() {
        return MewtwoMain.prefix;
    }

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

    public void reloadConfigs() {
        reloadConfig(admins);
        reloadConfig(disable);
        reloadConfig(aliases);
        reloadConfig(ignore);
        moduleManager.reloadConfigs();
    }
}
