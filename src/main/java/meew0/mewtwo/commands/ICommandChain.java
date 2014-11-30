package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;

/**
 * Created by miras on 15.11.14.
 */
public interface ICommandChain {
    public String execute(MewtwoContext ctx);
}
