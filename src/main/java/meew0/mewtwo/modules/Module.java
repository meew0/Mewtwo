package meew0.mewtwo.modules;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.ruby.CommandExecutor;
import org.joni.Option;
import org.joni.Regex;

import java.util.List;

/**
 * Created by miras on 25.11.14.
 */
@SuppressWarnings("WeakerAccess")
public class Module {
    private String name, filename;
    private List<String> triggers;
    private Regex regex;

    public Module(Regex regex, List<String> trigger, String name, String filename) {
        this.regex = regex;
        this.triggers = trigger;
        this.name = name;
        this.filename = filename;
    }

    public boolean activatesOn(String message, String trigger) {
        return (regex.matcher(message.getBytes()).search(0, message.length(), Option.DEFAULT) == 0)
                && triggers.contains(trigger);
    }

    public String execute(String message, MewtwoContext ctx) {
        ctx.append("module:" + name);
        try {
            return CommandExecutor.genericExecute(ModuleManager.modulesFolder + filename,
                    ctx.getUserNick(), ctx.getChannelName(), message, ctx);
        } catch (Throwable t) {
            MewtwoMain.mewtwoLogger.error("Exception occurred while executing module! ", t);
        }
        return "";
    }
}
