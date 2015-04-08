package meew0.mewtwo.irc;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.commands.CommandChainHandlerThread;
import meew0.mewtwo.context.ContextManager;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import meew0.mewtwo.modules.ModuleHandlerThread;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by meew0 on 01.04.15.
 */
public class IRCBot extends Thread {
    private final String serverHostname;
    private final int port;

    private String nick, nickservPW;

    public static final String realName = "Mewtwo";
    public static final String newLine = "\r\n";

    private final ContextManager ctxMgr;

    public static boolean shouldShutDown = false;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private static int botNumber = 0;

    private HashMap<String, ChannelUserList> channelUserLists = new HashMap<>();

    public IRCBot(String serverHostname, int port, String nick, String nickservPW) {
        super("Bot-" + (++botNumber));
        this.serverHostname = serverHostname;
        this.port = port;
        this.nick = nick;
        this.nickservPW = nickservPW;

        ctxMgr = new ContextManager();
    }

    @Override
    public void run() {
        // Connect
        try {
            socket = new Socket(serverHostname, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            MewtwoLogger.errorThrowable(e);
            MewtwoLogger.error("Fatal error occurred while connecting to IRC server, exiting now");
            return;
        }

        // Log in
        writeRaw("NICK", nick);
        writeRaw("USER", nick + " 0 * :" + realName);

        // Message loop
        while (!shouldShutDown) {
            try {
                String message = reader.readLine();

                // We don't want to parse our own commands
                if (message.startsWith(":" + nick + "!")) continue;

                MewtwoLogger.incoming(message);

                // Handle PINGs accordingly
                if (message.startsWith("PING")) {
                    writeRaw("PONG", message.substring(5));
                    continue;
                }

                // Handle other commands

                parseCommand(message.split(" "), message);
            } catch (IOException e) {
                MewtwoLogger.errorThrowable(e);
            }
        }

        MewtwoLogger.info("IRCBot shutting down");
        writeRaw("QUIT", ":JVM terminated");
    }

    public void parseCommand(String[] arguments, String message) {
        // User command?
        if (arguments[0].matches(":.+!.+@.+")) {
            String[] hostmask = arguments[0].split("[:!]");
            String nick = hostmask[1];

            // Get the actual command
            String command = arguments[1];

            String target = getReturnTargetForArguments(arguments, hostmask);

            User user = new User(nick, arguments[0].substring(1), hostmask[2], this);
            IChannel channel = (targetIsChannel(target)) ? new Channel(target, this) : user;

            MewtwoContext ctx = ctxMgr.makeContext(this, channel, user);

            if (command.equals("PRIVMSG")) {
                String data = String.join(" ", Arrays.copyOfRange(arguments, 3, arguments.length)).substring(1);

                if (data.startsWith(MewtwoMain.prefix)) {
                    // We have a command chain!

                    CommandChainHandlerThread ccht = new CommandChainHandlerThread(ctx, getReturnTarget(target, nick),
                            data);

                    ccht.start();
                }
            }

            // NAMES list
            if (command.equals("353")) {
                String channelName = arguments[4];

                String[] names = new String[arguments.length - 5];
                names[0] = arguments[5].substring(1);
                for (int i = 6; i < arguments.length; i++) {
                    names[i - 5] = arguments[i];
                }

                ChannelUserList list = new ChannelUserList(this, new Channel(channelName, this), names);
                channelUserLists.put(channelName, list);
            }

            // MOTD finished
            if (command.equals("376")) {
                // Identify to NickServ
                writeRaw("NICKSERV", "IDENTIFY " + nickservPW);
            }

            // Invalidate channel lists when a user joins or leaves a channel or changes their nick
            if (command.equals("JOIN")) {
                String channelName = arguments[2].substring(1);
                ChannelUserList list = channelUserLists.get(channelName);
                if (list != null) list.invalidate();
            }
            if (command.equals("PART")) {
                String channelName = arguments[2];
                ChannelUserList list = channelUserLists.get(channelName);
                if (list != null) list.invalidate();
            }
            if (command.equals("NICK")) {
                // Invalidate all channels
                for (ChannelUserList list : channelUserLists.values()) {
                    list.invalidate();
                }
            }

            // Handle modules
            if (ctxMgr.getPermanent().getModuleManager().doesModuleExistForMessage(message)) {
                MewtwoLogger.info("Module found");
                ModuleHandlerThread mht = new ModuleHandlerThread(ctx, target, message);
                mht.start();
            }
        } else if (arguments[0].matches(":[^ ]+")) {
            String hostmask = arguments[0].substring(1);

            // Get the actual command
            String command = arguments[1];

            // NAMES list
            if (command.equals("353")) {
                String channelName = arguments[4];

                String[] names = new String[arguments.length - 5];
                names[0] = arguments[5].substring(1);
                for (int i = 6; i < arguments.length; i++) {
                    names[i - 5] = arguments[i];
                }

                ChannelUserList list = new ChannelUserList(this, new Channel(channelName, this), names);
                channelUserLists.put(channelName, list);
            }

            // MOTD finished
            if (command.equals("376")) {
                // Identify to NickServ
                writeRaw("NICKSERV", "IDENTIFY " + nickservPW);
            }

            MewtwoContext ctx = ctxMgr.makeContext(this, new Channel("", this), new User("", hostmask, hostmask, this));

            // Handle modules
            if (ctxMgr.getPermanent().getModuleManager().doesModuleExistForMessage(message)) {
                MewtwoLogger.info("Module found");
                ModuleHandlerThread mht = new ModuleHandlerThread(ctx, "", message);
                mht.start();
            }

        }
    }

    public String getReturnTargetForArguments(String[] arguments, String[] hostmask) {
        String command = arguments[1];
        if (command.equals("PRIVMSG")) {
            if (targetIsChannel(arguments[2])) return arguments[2];
            else return hostmask[1];
        }
        if (command.equals("NICK") || command.equals("AWAY")) return hostmask[1];
        if (command.equals("JOIN")) return arguments[2].substring(1);
        if (command.equals("PART")) return arguments[2];
        return "";
    }

    public void writePrivmsg(String target, String data) {
        writeRaw("PRIVMSG", target + " :" + data);
    }

    public void writeRaw(String command, String data) {
        try {
            writer.write(command + " " + data + newLine);
            writer.flush();

            MewtwoLogger.outcoming(command + " " + data);
        } catch (IOException e) {
            MewtwoLogger.errorThrowable(e);
        }
    }

    public ChannelUserList getUserListForChannel(String channelName) {
        return channelUserLists.get(channelName);
    }

    public boolean targetIsChannel(String target) {
        return target.startsWith("#");
    }

    public String getReturnTarget(String currentTarget, String nick) {
        return targetIsChannel(currentTarget) ? currentTarget : nick;
    }

    public String getNick() {
        return nick;
    }
}
