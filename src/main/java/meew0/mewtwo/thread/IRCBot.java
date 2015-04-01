package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoLogger;

import java.io.*;
import java.net.Socket;

/**
 * Created by meew0 on 01.04.15.
 */
public class IRCBot extends Thread {
    private final String serverHostname;
    private final int port;

    private String nick;

    public static final String realName = "Mewtwo";
    public static final String newLine = "\r\n";

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public IRCBot(String serverHostname, int port, String nick) {
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
        while(true) {
            try {
                if (reader.ready()) {
                    String message = reader.readLine();
                    MewtwoLogger.incoming(message);
                }
            } catch (IOException e) {
                MewtwoLogger.errorThrowable(e);
            }
        }
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
}
