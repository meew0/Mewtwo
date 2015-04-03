package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.ruby.CommandExecutor;

/**
 * Created by meew0 on 30.10.14.
 */
@SuppressWarnings("ALL")
public class Command {
    private String commandName;
    private String arguments;
    private CommandExecutor wrapper;
    private MewtwoContext ctx;

    public Command(String commandName, String arguments, MewtwoContext ctx) {
        this.commandName = commandName;
        this.ctx = ctx;

        this.commandName = ctx.getPCtx().getAliasForCommand(commandName);
        this.arguments = arguments;
    }

    public boolean isAdmin() {
        return commandName.startsWith("admin/");
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArguments() {
        return arguments;
    }

    public CommandExecutor getWrapper() {
        CommandExecutor cw = new CommandExecutor(commandName);
        wrapper = cw;
        return cw;

    }

    public boolean isDisabled() {
        return ctx.getPCtx().isCommandEnabled(commandName);
    }

    public boolean isOutsideScope() {
        // TODO: More secure way to check if a command is outside the scope
        return commandName.contains("..");
    }


    public String execute(MewtwoContext ctx) {
        if(wrapper == null) getWrapper();

        ctx.append("cmd:" + commandName);

        String result;
        try {
            result = wrapper.execute(ctx.getUser().getNick(),
                    ctx.getChannel().getName().substring(1, ctx.getChannel().getName().length()), arguments, ctx);
        } catch(Throwable t) {
            throw new RuntimeException(t);
        }

        return result;
    }
}