package meew0.mewtwo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

import meew0.mewtwo.commands.*;

import meew0.mewtwo.ruby.CommandWrapper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;
import org.pircbotx.hooks.types.GenericMessageEvent;

import com.google.common.base.Joiner;

public class MewtwoListener extends ListenerAdapter<PircBotX> {
	public static CommandRegistry registry;
	public static String prefix = "%";

	public static Random rnd = new Random();
	
	public static ChatLog log = new ChatLog();
	
	public static UserActionList opList = new UserActionList(new File("ops.cfg"));
	public static UserActionList voiceList = new UserActionList(new File("voice.cfg"));
	public static UserActionList kickList = new UserActionList(new File("kick.cfg"));

    public static ArrayList<HighFiveEntry> highFives = new ArrayList<HighFiveEntry>();

    public static HierarchicalINIConfiguration aliases, admins, enable;

    public static ModuleManager moduleManager;

	public MewtwoListener(String prefix) {
        MewtwoListener.prefix = prefix;
        try {
            aliases = new HierarchicalINIConfiguration("aliases.cfg");
            admins = new HierarchicalINIConfiguration("admins.cfg");
            enable = new HierarchicalINIConfiguration("enable.cfg");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        registry = new CommandRegistry();

		registry.addCommand(new CommandJoinChannel());
		registry.addCommand(new CommandLeaveChannel());

        registry.addCommand(new CommandScore()); //TODO: migrate to ruby

        registry.addCommand(new CommandQuoteLast());
        registry.addCommand(new CommandQuoteContains());

        registry.addCommand(new CommandReload());

		/*registry.addCommand(new CommandOp()); //TODO: remove
		registry.addCommand(new CommandVoice());
		registry.addCommand(new CommandKick());
		registry.addCommand(new CommandDeOp());
		registry.addCommand(new CommandDeVoice());
		registry.addCommand(new CommandDeKick());*/

        registry.addCommand(new CommandExecute());

        moduleManager = new ModuleManager();
	}
	
	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {
        try {
            String result = moduleManager.executeModules("join", event.getUser().getNick(),
                    event.getChannel().getName().replace("#", ""), "");

            if(result != "") {
                for (String s : result.split("\n")) {
                    event.getChannel().send().message(s);
                }
            }
        } catch(Throwable t) {
            event.respond("ERROR: An exception has occurred while executing a module! "
                    + t.getClass().getName() + ": " + t.getMessage());
            event.respond("See console for details.");
            t.printStackTrace();
        }
	}

    @Override
    public void onNickChange(NickChangeEvent<PircBotX> event) throws Exception {
        try {
            String result = moduleManager.executeModules("nickchange", event.getNewNick(),
                    event.getOldNick(), "");

            if(result != "") {
                for (String s : result.split("\n")) {
                    event.getUser().send().message(s);
                }
            }
        } catch(Throwable t) {
            event.respond("ERROR: An exception has occurred while executing a module! "
                    + t.getClass().getName() + ": " + t.getMessage());
            event.respond("See console for details.");
            t.printStackTrace();
        }
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
        try {
            String result = moduleManager.executeModules("pm", event.getUser().getNick(),
                    event.getUser().getNick(), event.getMessage());

            if(result != "") {
                for (String s : result.split("\n")) {
                    event.getUser().send().message(s);
                }
            }
        } catch(Throwable t) {
            event.respond("ERROR: An exception has occurred while executing a module! "
                    + t.getClass().getName() + ": " + t.getMessage());
            event.respond("See console for details.");
            t.printStackTrace();
        }
    }

    @Override
    public void onAction(ActionEvent<PircBotX> event) throws Exception {
        try {
            String result = moduleManager.executeModules("action", event.getUser().getNick(),
                    event.getChannel().getName(), event.getMessage());

            if(result != "") {
                for (String s : result.split("\n")) {
                    event.getChannel().send().message(s);
                }
            }
        } catch(Throwable t) {
            event.respond("ERROR: An exception has occurred while executing a module! "
                    + t.getClass().getName() + ": " + t.getMessage());
            event.respond("See console for details.");
            t.printStackTrace();
        }
    }

    @Override
	public void onGenericMessage(GenericMessageEvent<PircBotX> event)
			throws Exception {
		if (event.getMessage().startsWith(prefix + "join")) {
			event.getBot().sendIRC()
					.joinChannel(event.getMessage().split(" ")[1]);
		}
        if (event.getMessage().startsWith(prefix + "joinall")) {
            for(String c : event.getMessage().split(" ")) {
                event.getBot().sendIRC()
                        .joinChannel(c);
            }
        }
		if (event.getMessage().startsWith(prefix + "say")) {
			String[] args = event.getMessage().split(" ");
			for(Channel c : 
			event.getBot().getUserBot().getChannels()) {
				if(c.getName().equals(args[1])) c.send().message(Joiner.on(" ").join(Arrays.copyOfRange(args, 2, args.length)));
			}
		}
	}

    public boolean isHighFive(String message) {
        return message.equalsIgnoreCase("o/") || message.equalsIgnoreCase("\\o") || message.equalsIgnoreCase("\\o/") ||
                message.equalsIgnoreCase("0/") || message.equalsIgnoreCase("\\0") || message.equalsIgnoreCase("\\0/");
    }

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		try {
			String msg = event.getMessage();

            ChatLog.Message msgBefore = null;
            try {
                msgBefore = log.messages.getFirst();
            } catch(NoSuchElementException e) {
                System.out.println("warning: element not found");
            }

			log.add(msg, event.getUser().getNick());

            // execute module
            try {
                String result = moduleManager.executeModules("message", event.getUser().getNick(),
                        event.getChannel().getName().replace("#", ""), msg);

                if(!result.equals("")) {
                    for (String s : result.split("\n")) {
                        event.getChannel().send().message(s);
                    }
                }
            } catch(Throwable t) {
                event.respond("ERROR: An exception has occurred while executing a module! "
                        + t.getClass().getName() + ": " + t.getMessage());
                event.respond("See console for details.");
                t.printStackTrace();
            }
			
			SedRegex sr = SedRegex.getSedRegex(msg);
			if(sr.isValid()) {
				boolean found = false;
				for(ChatLog.Message m2 : log.messages) {
                    String m = m2.message;
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

            if(isHighFive(msg)) {
                if(msgBefore != null) {
                    if (isHighFive(msgBefore.message)) {
                        boolean found = false;
                        for (HighFiveEntry e : highFives) {
                            if ((e.getNick1().equalsIgnoreCase(event.getUser().getNick()) || e.getNick2().equalsIgnoreCase(event.getUser().getNick())) &&
                                    (e.getNick1().equalsIgnoreCase(msgBefore.nick) || e.getNick2().equalsIgnoreCase(msgBefore.nick))) {
                                found = true;
                                e.setCount(e.getCount() + 1);
                                event.getChannel().send().message(e.getNick1() + " \\o : o/ " + e.getNick2() + " - " + e.getCount() + ". time");
                            }
                        }
                        if (!found) {
                            HighFiveEntry e = new HighFiveEntry();
                            e.setNick1(event.getUser().getNick());
                            e.setNick2(msgBefore.nick);
                            e.setCount(1);
                            event.getChannel().send().message(e.getNick1() + " \\o : o/ " + e.getNick2() + " - " + e.getCount() + ". time");
                        }
                    }
                }

            }

            if(!(msg.startsWith(":") || msg.startsWith(";") || msg.endsWith(":") || msg.endsWith(";"))) {
                float s = 0.f;
                for (int i = 0; i < msg.length(); i++) {
                    if (Character.isUpperCase(msg.charAt(i))) s++;
                }

                if ((s / (float) msg.length()) > 0.75f) {
                    //event.respond("Please don't talk in more than the necessary amount of caps");
                }
            }

			if (msg.startsWith(prefix)) {
                if((msg.startsWith(prefix + "admin/") && admins.containsKey(event.getUser().getNick())
                        && admins.getBoolean(event.getUser().getNick())) || !msg.startsWith(prefix + "admin/")) {
                    String[] data = msg.split(" ");
                    String name = data[0].substring(1);

                    if(enable.containsKey(name) && enable.getBoolean(name)) {
                        event.respond("This command is disabled");
                    } else {
                        if (registry.hasCommand(name)) {
                            registry.execute(name,
                                    Arrays.copyOfRange(data, 1, data.length), event);
                        } else {
                            if (aliases.containsKey(name)) name = aliases.getString(name);
                            CommandWrapper cw = new CommandWrapper(name);
                            String result = cw.execute(event.getUser().getNick(), event.getChannel().getName().replace("#", ""), Joiner.on(" ")
                                    .join(Arrays.copyOfRange(data, 1, data.length)));

                            for (String s : result.split("\n")) {
                                event.getChannel().send().message(s);
                            }
                        }
                    }
                } else {
                    event.respond("I'm sorry, I can't let you do that.");
                }
			}
		} catch (Exception e) {
			event.respond("ERROR: An exception has occurred! "
					+ e.getClass().getName() + ": " + e.getMessage());
			event.respond("See console for details.");
			e.printStackTrace();
		}
	}
}
