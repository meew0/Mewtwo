package meew0.mewtwo.ruby;

import com.google.common.base.Joiner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by meew0 on 23.07.14.
 */
public class CommandWrapper {

    public static final String commandFolder = "commands/";

    String name;

    public CommandWrapper(String name) {
        File f = new File(commandFolder + name + ".rb");
        if(!f.exists()) throw new IllegalArgumentException("Command file " + name + ".rb does not exist!");

        this.name = name;
    }

    public String execute(String userNick, String channel, String args) throws IOException {
        return CommandWrapper.genericExecute(commandFolder + name + ".rb", userNick, channel, args);
    }

    public static String genericExecute(String path, String userNick, String channel, String args) throws IOException {
        String result = "", line = "";
        Process proc = Runtime.getRuntime().exec("ruby " + path + " " + userNick + " "
                + channel + " " + args);

        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        while((line = br.readLine()) != null) {
            result += (line + "\n");
        }

        br.close();

        return result;

    }
}
