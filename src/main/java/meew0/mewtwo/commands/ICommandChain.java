package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;

/**
 * Created by meew0 on 15.11.14.
 */
public interface ICommandChain {
    public String execute(MewtwoContext ctx);
}