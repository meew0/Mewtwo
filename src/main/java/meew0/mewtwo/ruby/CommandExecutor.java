package meew0.mewtwo.ruby;

import com.google.common.base.Joiner;
import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
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
    private static final JRubyWrapper rb = new JRubyWrapper(MewtwoMain.shouldProfile);

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
        ctx.benchmark("chain.parse_end");

        String absolutePath = Paths.get(path).toAbsolutePath().toString();

        if(!(absolutePath.startsWith(Paths.get("commands").toAbsolutePath().toString())
                || absolutePath.startsWith(Paths.get("modules").toAbsolutePath().toString()))) {
            MewtwoMain.mewtwoLogger.info("Script path: " + absolutePath);
            MewtwoMain.mewtwoLogger.info("Commands path: " + Paths.get("commands").toAbsolutePath().toString());
            return "Script path must be inside commands or modules path!";
        }

        String script = Joiner.on('\n').join(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)); // read script from file

        script = "# encoding: utf-8\n" + script;

        ctx.benchmark("execute.file.read");

        String[] argsAry = args.split(" ");
        argsAry = (String[]) ArrayUtils.addAll(new String[]{userNick, channel}, argsAry);

        rb.bindArgv(argsAry);
        rb.bindStdin(ctx.getInput());
        rb.bindCtx(ctx); // add ctx to script container

        ctx.benchmark("execute.container.init");

        try {
            rb.run(script);
            ctx.benchmark("execute.run");
        } catch(Throwable t) {
            MewtwoMain.mewtwoLogger.error("An exception occurred during JRuby execution! (ctx-id=" +
                    ctx.getId() + ")", t);
        }

        ctx.benchmark("execute.finish");

        System.err.print(rb.getError()); // print stderr result to stderr,
        return rb.getResult(); // and return stdout result



    }
}