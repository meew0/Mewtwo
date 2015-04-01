package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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

    }

    public void writeRaw(String command, String data) {
        try {
            writer.write(command + " " + data + newLine);
            writer.flush();

            MewtwoMain.mewtwoLogger.info("\\u001B[32m→ \\u001B[0m" + command + " " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
