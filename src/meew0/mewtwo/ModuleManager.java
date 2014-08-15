package meew0.mewtwo;

import meew0.mewtwo.ruby.CommandWrapper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by meew0 on 12.08.14.
 */
public class ModuleManager {
    private HierarchicalINIConfiguration files, enable, regex, triggers;

    public static final String modulesFolder = "modules/";

    public ModuleManager() {
        try {
            files = new HierarchicalINIConfiguration("modules/files.cfg");
            enable = new HierarchicalINIConfiguration("modules/enable.cfg");
            regex = new HierarchicalINIConfiguration("modules/regex.cfg");
            triggers = new HierarchicalINIConfiguration("modules/triggers.cfg");
        } catch(ConfigurationException e) {
            throw new RuntimeException(e);
        }

        Iterator<String> it = files.getKeys();
        while(it.hasNext()) {
            String name = it.next();
            String file = files.getString(name);
            boolean enabled = enable.getBoolean(name);
            String regexString = regex.getString(name);

            MewtwoMain.mewtwoLogger.info("Loaded module " + name + ": file=" + file + " enabled=" + enabled + " regex=" + regexString);
        }

    }

    public void reloadConfigs() {
        try {
            files.clear();
            enable.clear();
            regex.clear();
            triggers.clear();
            files.load();
            enable.load();
            regex.load();
            triggers.load();
        } catch(ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public String executeModules(String type, String userNick, String channelName, String message) throws IOException {
        Iterator<String> it = files.getKeys();
        String result = "";
        while(it.hasNext()) {
            String name = it.next();
            String file = files.getString(name);
            boolean enabled = enable.getBoolean(name);
            String regexString = regex.getString(name);
            String t = triggers.getString(name);

            if(enabled && t.contains(type) && message.matches(regexString)) {
                result += CommandWrapper.genericExecute(modulesFolder + file, userNick, channelName, message);
            }
        }

        return result;
    }
}
