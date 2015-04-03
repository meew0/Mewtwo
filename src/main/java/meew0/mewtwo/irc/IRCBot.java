package meew0.mewtwo.irc;

import meew0.mewtwo.core.MewtwoLogger;

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

    private String nick;

    public static final String realName = "Mewtwo";
    public static final String newLine = "\r\n";

    public static boolean shouldShutDown = false;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private static int botNumber = 0;

    private HashMap<String, ChannelUserList> channelUserLists = new HashMap<>();

    public IRCBot(String serverHostname, int port, String nick) {
        super("Bot-" + (++botNumber));
        this.serverHostname = serverHostname;
        this.port = port;
        this.nick = nick;
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

        // Join test channel
        writeRaw("JOIN", "#test");

        // Message loop
        while(!shouldShutDown) {
            try {
                if (reader.ready()) {
                    String message = reader.readLine();

                    // We don't want to parse our own commands
                    if(message.startsWith(":" + nick + "!")) continue;

                    MewtwoLogger.incoming(message);

                    // Handle PINGs accordingly
                    if(message.startsWith("PING")) {
                        writeRaw("PONG", message.substring(5));
                        continue;
                    }

                    // Handle other commands

                    parseCommand(message.split(" "));
                }
            } catch (IOException e) {
                MewtwoLogger.errorThrowable(e);
            }
        }

        MewtwoLogger.info("IRCBot shutting down");
        writeRaw("QUIT", ":JVM terminated");
    }

    public void parseCommand(String[] arguments) {
        // User command?
        if(arguments[0].matches(":.+!.+@.+")) {
            String[] hostmask = arguments[0].split("[:!]");
            String nick = hostmask[1];

            // Get the actual command
            String command = arguments[1];

            if(command.equals("PRIVMSG")) {
                String target = arguments[2];
                String data = String.join(" ", Arrays.copyOfRange(arguments, 3, arguments.length)).substring(1);

                // TODO: Actual privmsg handling
                writePrivmsg(getReturnTarget(target, nick), nick + " (" + hostmask[2] + ") @ " + target + ": " + data);
                return;
            }

            // NAMES list
            if(command.equals("353")) {
                String channelName = arguments[4];

                String[] names = new String[arguments.length - 5];
                names[0] = arguments[5].substring(1);
                for(int i = 6; i < arguments.length; i++) {
                    names[i - 5] = arguments[i];
                }

                ChannelUserList list = new ChannelUserList(this, new Channel(channelName, this), names);
                channelUserLists.put(channelName, list);
            }
        }
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

    public boolean targetIsChannel(String target) {
        return target.startsWith("#");
    }

    public String getReturnTarget(String currentTarget, String nick) {
        return targetIsChannel(currentTarget) ? currentTarget : nick;
    }
}
