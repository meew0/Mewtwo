package meew0.mewtwo.commands;

import meew0.mewtwo.MewtwoListener;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public abstract class UserActionCommand implements ICommand {

	@Override
	public String getAlias() {
		return getCommandName();
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		if (event.getChannel().isOp(event.getUser()) || event.getUser().getNick().equals("meew0")) {
			if (event.getChannel().isOp(event.getBot().getUserBot())) {
				User u = null;
				for(User u2 : event.getChannel().getUsers()) {
					if(u2.getNick().equals(args[0])) u = u2;
				}
				if(u != null) {
					doAction(u, event.getChannel(), event.getBot());
					MewtwoListener.doUserActions(u,
							event.getChannel());
				} else {
					event.respond("User not found");
				}
			} else {
				event.respond("I need to be op to do this");
			}
		} else {
			event.respond("You aren't op");
		}
	}
	
	public abstract void doAction(User u, Channel c, PircBotX bot);

}
