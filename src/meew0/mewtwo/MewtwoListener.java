package meew0.mewtwo;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import meew0.mewtwo.commands.CommandCompliment;
import meew0.mewtwo.commands.CommandDeKick;
import meew0.mewtwo.commands.CommandDeOp;
import meew0.mewtwo.commands.CommandDeVoice;
import meew0.mewtwo.commands.CommandDrama;
import meew0.mewtwo.commands.CommandGoogle;
import meew0.mewtwo.commands.CommandHelp;
import meew0.mewtwo.commands.CommandInsult;
import meew0.mewtwo.commands.CommandJoinChannel;
import meew0.mewtwo.commands.CommandKick;
import meew0.mewtwo.commands.CommandLMGTFY;
import meew0.mewtwo.commands.CommandLeaveChannel;
import meew0.mewtwo.commands.CommandMemo;
import meew0.mewtwo.commands.CommandOp;
import meew0.mewtwo.commands.CommandRainbow;
import meew0.mewtwo.commands.CommandRandomFact;
import meew0.mewtwo.commands.CommandRandomNumber;
import meew0.mewtwo.commands.CommandSpam;
import meew0.mewtwo.commands.CommandUrban;
import meew0.mewtwo.commands.CommandVoice;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

import com.google.common.base.Joiner;

public class MewtwoListener extends ListenerAdapter<PircBotX> {
	public static CommandRegistry registry;
	public static final String prefix = "`";

	public static Random rnd = new Random();
	
	public static ChatLog log = new ChatLog();
	
	public static UserActionList opList = new UserActionList(new File("ops.cfg"));
	public static UserActionList voiceList = new UserActionList(new File("voice.cfg"));
	public static UserActionList kickList = new UserActionList(new File("kick.cfg"));

	public MewtwoListener() {
		registry = new CommandRegistry();

		registry.addCommand(new CommandJoinChannel());
		registry.addCommand(new CommandLeaveChannel());
		registry.addCommand(new CommandHelp());
		//registry.addCommand(new CommandGoogle());
		registry.addCommand(new CommandUrban());
		registry.addCommand(new CommandInsult());
		registry.addCommand(new CommandCompliment());
		registry.addCommand(new CommandLMGTFY());
		//registry.addCommand(new CommandSpam());
		registry.addCommand(new CommandRainbow());
		registry.addCommand(new CommandMemo());
		registry.addCommand(new CommandRandomFact());
		registry.addCommand(new CommandRandomNumber());
		registry.addCommand(new CommandDrama());
		
		registry.addCommand(new CommandOp());
		registry.addCommand(new CommandVoice());
		registry.addCommand(new CommandKick());
		registry.addCommand(new CommandDeOp());
		registry.addCommand(new CommandDeVoice());
		registry.addCommand(new CommandDeKick());
	}
	
	public static void doUserActions(User u, Channel c) {
		String m = u.getHostmask(), cn = c.getName();
		for(UserAction mask : opList.users) if(mask.hostMask.equals(m) && mask.channel.equals(cn)) c.send().op(u);
		for(UserAction mask : voiceList.users) if(mask.hostMask.equals(m) && mask.channel.equals(cn)) c.send().voice(u);
		for(UserAction mask : kickList.users) if(mask.hostMask.equals(m) && mask.channel.equals(cn)) c.send().kick(u);
	}
	
	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {
		CommandMemo.readMemos(event);
		doUserActions(event.getUser(), event.getChannel());
	}

	@Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event)
			throws Exception {
		if (event.getMessage().startsWith(prefix + "join")) {
			event.getBot().sendIRC()
					.joinChannel(event.getMessage().split(" ")[1]);
		}
		if (event.getMessage().startsWith(prefix + "say")) {
			String[] args = event.getMessage().split(" ");
			for(Channel c : 
			event.getBot().getUserBot().getChannels()) {
				if(c.getName().equals(args[1])) c.send().message(Joiner.on(" ").join(Arrays.copyOfRange(args, 2, args.length)));
			}
		}
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		try {
			String msg = event.getMessage();

			log.add(msg);
			
			SedRegex sr = SedRegex.getSedRegex(msg);
			if(sr.isValid()) {
				boolean found = false;
				for(String m : log.messages) {
					if(sr.matches(m) && !SedRegex.getSedRegex(m).isValid()) { // also make sure the message isn't a regex
						found = true;
						event.respond(sr.replace(m));
						break;
					}
				}
				if(!found) {
					event.respond("Could not find a message that matches " + sr.getFirstRegex());
				}
			}
			

			if (msg.equalsIgnoreCase("Who are you, " + event.getBot().getNick()
					+ "?")
					|| msg.equalsIgnoreCase("Who are you "
							+ event.getBot().getNick() + "?")
					|| msg.equalsIgnoreCase("Who are you, "
							+ event.getBot().getNick() + "?")
					|| msg.equalsIgnoreCase("Who are you "
							+ event.getBot().getNick() + "")
					|| msg.equalsIgnoreCase("Who is "
							+ event.getBot().getNick() + "?")
					|| msg.equalsIgnoreCase("Who is "
							+ event.getBot().getNick() + "")
					|| msg.equalsIgnoreCase(event.getBot().getNick()
							+ ", Who are you?")
					|| msg.equalsIgnoreCase(event.getBot().getNick()
							+ ": Who are you?")
					|| msg.equalsIgnoreCase(event.getBot().getNick()
							+ ", Who are you")
					|| msg.equalsIgnoreCase(event.getBot().getNick()
							+ ": Who are you")) {
				event.respond("I'm Mewtwo, an IRC bot programmed by meew0. Try "
						+ prefix + "help for a list of commands");
			}

			if (msg.equalsIgnoreCase("I'm better than "
					+ event.getBot().getNick())
					|| msg.equalsIgnoreCase("Im better than "
							+ event.getBot().getNick())) {
				event.respond("It's good to dream big");
			}

			if (msg.startsWith(prefix)) {
				String[] data = msg.split(" ");
				String name = data[0].substring(1);

				registry.execute(name,
						Arrays.copyOfRange(data, 1, data.length), event);
			}
		} catch (Exception e) {
			event.respond("ERROR: An exception has occurred! "
					+ e.getClass().getName() + ": " + e.getMessage());
			event.respond("See console for details.");
			e.printStackTrace();
		}
	}
}
