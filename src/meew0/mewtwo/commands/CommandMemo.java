package meew0.mewtwo.commands;

import java.util.ArrayList;
import java.util.Arrays;

import meew0.mewtwo.MewtwoListener;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

import com.google.common.base.Joiner;

public class CommandMemo implements ICommand {
	public static class Memo {
		public String user = "";
		public String orig = "";
		public String data = "";
		
		public Memo(String u, String o, String d) {
			data = d; user = u; orig = o;
		}
	}
	
	public static ArrayList<Memo> memoData = new ArrayList<Memo>();

	@Override
	public String getCommandName() {
		return "memo";
	}
	
	public static void readMemos(JoinEvent<PircBotX> event) {
		int i = 0;
		for(Memo m : memoData) {
			if(m.user.equalsIgnoreCase(event.getUser().getNick())) {
				i++;
			}
		}
		if(i > 0) {
			event.getChannel().send().message(event.getUser(), "You have " + i + " memo" + ((i > 1) ? "s" : "") + ", use '" + MewtwoListener.prefix + "memo get' to read");
		}
	}

	@Override
	public void onExecution(String[] args, MessageEvent<PircBotX> event) {
		if(args.length < 1) {
			event.respond(getHelpEntry());
		} else {
			if(args.length > 0) {
				if(args.length > 2 && (args[0].equals("s") || args[0].equals("set"))) {
					// set
					String term = Joiner.on(" ").join(Arrays.copyOfRange(args, 2, args.length));
					
					Memo m = new Memo(args[1], event.getUser().getNick(), term);
					memoData.add(m);
					event.respond("Memo set: \u0002" + m.data + "\u000F");
				} else if(args[0].equals("g") || args[0].equals("get")) {
					// get
					for(Memo m : memoData) {
						if(m.user.equalsIgnoreCase(event.getUser().getNick())) {
							event.respond("Memo from " + m.orig + ": \u0002" + m.data + "\u000F");
						}
					}
				} else if(args[0].equals("c") || args[0].equals("clear")) {
					// clear
					int i = 0;
					Memo[] mArray = memoData.toArray(new Memo[]{}); // FUCK CMEs
					for(Memo m : mArray) {
						if(m.user.equalsIgnoreCase(event.getUser().getNick())) {
							i++;
							memoData.remove(m);
						}
					}
					if(i > 0) {
						event.respond("" + i + " memo" + ((i > 1) ? "s" : "") + " cleared");
					} else {
						event.respond("No memos to clear");
					}
				}
			}
		}
	}

	@Override
	public String getHelpEntry() {
		return "memo|m <(set|s) <user> <term>|(get|g)|(clear|c)>: Sets a memo for a specified user, gets all memos for you or clears your memos.";
	}

	@Override
	public String getAlias() {
		return "m";
	}
}
