package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoListener;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by meew0 on 12.08.14.
 */
public class CommandReload implements ICommand{
    @Override
    public String getCommandName() {
        return "admin/reload";
    }

    @Override
    public void onExecution(String[] args, MessageEvent<PircBotX> event) {
        MewtwoListener.moduleManager.reloadConfigs();
        try {
            MewtwoListener.aliases.clear();
            MewtwoListener.aliases.load();

            MewtwoListener.admins.clear();
            MewtwoListener.admins.load();

            MewtwoListener.enable.clear();
            MewtwoListener.enable.load();

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        event.respond("Successfully reloaded configs");
    }
}
