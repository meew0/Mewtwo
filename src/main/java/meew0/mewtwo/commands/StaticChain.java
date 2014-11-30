package meew0.mewtwo.commands;

import meew0.mewtwo.context.MewtwoContext;

/**
 * Created by miras on 15.11.14.
 */
public class StaticChain implements ICommandChain {
    private String message;

    public StaticChain(String message) {
        this.message = message;
    }

    @Override
    public String execute(MewtwoContext ctx) {
        return message;
    }
}
