package meew0.mewtwo.modules;

import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import meew0.mewtwo.ruby.CommandExecutor;
import org.joni.Option;
import org.joni.Regex;

/**
 * Created by meew0 on 25.11.14.
 */
@SuppressWarnings("WeakerAccess")
public class Module {
    private final String name, filename;
    private final Regex regex;

    public Module(Regex regex, String name, String filename) {
        this.regex = regex;
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public boolean activatesOn(String message) {
        return (regex.matcher(message.getBytes()).search(0, message.length(), Option.DEFAULT) == 0);
    }

    public String execute(String message, MewtwoContext ctx) {
        ctx.append("module:" + name);
        try {
            return CommandExecutor.genericExecute(ModuleManager.modulesFolder + filename,
                    ctx.getUserNick(), ctx.getChannelName(), message, ctx);
        } catch (Throwable t) {
            MewtwoLogger.errorThrowable(t);
        }
        return "";
    }
}