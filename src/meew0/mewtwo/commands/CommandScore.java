package meew0.mewtwo.commands;

import com.google.common.base.Joiner;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by meew0 on 17.05.14.
 */
public class CommandScore implements ICommand {

    class Challenge {
        public String user1, user2;
        public int score1, score2;

        public Challenge(String u1, String u2, int s1, int s2) {
            user1 = u1; user2 = u2; score1 = s1; score2 = s2;
        }
    }

    private HashMap<String, Challenge> challenges = new HashMap<String, Challenge>();

    @Override
    public String getCommandName() {
        return "score";
    }

    private String getStats(Challenge c) {
        return c.user1 + " " + c.score1 + ":" + c.score2 + " " + c.user2;
    }

    @Override
    public void onExecution(String[] args, MessageEvent<PircBotX> event) {

        if(args.length < 1) {
            event.respond("You need to provide a command");
        } else {
            if(args.length > 0) {
                if(args.length > 3 && (args[0].equals("n") || args[0].equals("new"))) {
                    // new
                    Challenge c = new Challenge(args[2], args[3], 0, 0);
                    challenges.put(args[1], c);
                    event.respond(getStats(c));
                } else if(args.length > 2 && (args[0].equals("+") || args[0].equals("add"))) {
                    // add
                    for(String s : challenges.keySet()) {
                        if(s.equalsIgnoreCase(args[1])) {
                            Challenge c = challenges.get(s);
                            if(args[2].equalsIgnoreCase(c.user1)) {
                                c.score1++;
                                event.respond(getStats(c));
                            }
                            else if(args[2].equalsIgnoreCase(c.user2)) {
                                c.score2++;
                                event.respond(getStats(c));
                            }
                            else event.respond("Could not find user");
                        }
                    }
                }
            } else if(args.length > 2 && (args[0].equals("-") || args[0].equals("sub"))) {
                // add
                for(String s : challenges.keySet()) {
                    if(s.equalsIgnoreCase(args[1])) {
                        Challenge c = challenges.get(s);
                        if(args[2].equalsIgnoreCase(c.user1)) {
                            c.score1++;
                            event.respond(getStats(c));
                        }
                        else if(args[2].equalsIgnoreCase(c.user2)) {
                            c.score2++;
                            event.respond(getStats(c));
                        }
                        else event.respond("Could not find user");
                    }
                }
            } else if(args.length > 1 && (args[0].equals("g") || args[0].equals("get"))) {
                // get
                for(String s : challenges.keySet()) {
                    if(s.equalsIgnoreCase(args[1])) {
                        event.respond(getStats(challenges.get(s)));
                    }
                }
            }
        }
    }
}
