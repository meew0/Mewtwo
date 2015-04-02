package meew0.mewtwo.ruby;

import com.google.common.base.Joiner;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by meew0 on 23.07.14.
 */
public class CommandExecutor {
    private static final String commandFolder = "commands/";
    private static final JRubyWrapper rb = new JRubyWrapper();

    private String name;

    public CommandExecutor(String name) {
        File f = new File(commandFolder + name + ".rb");
        if(!f.exists()) throw new IllegalArgumentException("Command file " + name + ".rb does not exist!");

        this.name = name;
    }

    public String execute(String userNick, String channel, String args, MewtwoContext ctx) throws IOException {
        return CommandExecutor.genericExecute(commandFolder + name + ".rb", userNick, channel, args, ctx);
    }

    public static String genericExecute(String path, String userNick, String channel, String args, MewtwoContext ctx) throws IOException {
        String absolutePath = Paths.get(path).toAbsolutePath().toString();

        if(!(absolutePath.startsWith(Paths.get("commands").toAbsolutePath().toString())
                || absolutePath.startsWith(Paths.get("modules").toAbsolutePath().toString()))) {
            MewtwoLogger.info("Script path: " + absolutePath);
            MewtwoLogger.info("Commands path: " + Paths.get("commands").toAbsolutePath().toString());
            return "Script path must be inside commands or modules path!";
        }

        // TODO: possibly preload scripts instead of loading them when they're executed to save time
        String script = Joiner.on('\n').join(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)); // read script from file

        script = "# encoding: utf-8\n" + script;

        String[] argsAry = args.split(" ");
        argsAry = (String[]) ArrayUtils.addAll(new String[]{userNick, channel}, argsAry);

        rb.bindArgv(argsAry);
        rb.bindStdin(ctx.getInput());
        rb.bindCtx(ctx); // add ctx to script container

        try {
            rb.run(script);
        } catch(Throwable t) {
            MewtwoLogger.errorThrowable(t);
        }

        System.err.print(rb.getError()); // print stderr result to stderr,
        return rb.getResult(); // and return stdout result
    }
}