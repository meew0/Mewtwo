package meew0.mewtwo.ruby;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
import org.jruby.CompatVersion;
import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.runtime.profile.builtin.ProfileOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miras on 09.11.14.
 */
@SuppressWarnings("WeakerAccess")
public class JRubyWrapper {
    private final ScriptingContainer rb;
    private final StringWriter swOut, swErr;

    /**
     * Create a new JRuby wrapper.
     * @param profile whether or not profiling is enabled
     */
    public JRubyWrapper(boolean profile) {
        rb = new ScriptingContainer(LocalContextScope.THREADSAFE);
        rb.setCompatVersion(CompatVersion.RUBY1_9);
        rb.setCurrentDirectory(Paths.get("").toAbsolutePath().toString()); // set working directory of scripts to working directory of application

        @SuppressWarnings("unchecked")
        Map<String, String> env = new HashMap<>(rb.getEnvironment());

        env.put("GEM_PATH", Paths.get("lib/gems").toAbsolutePath().toString());
        MewtwoMain.mewtwoLogger.info("Setting GEM_PATH of container to " + env.get("GEM_PATH"));

        rb.setEnvironment(env);

        ArrayList<String> loadPaths = new ArrayList<>();

        File gemsFile = Paths.get("lib/gems").toFile();
        File[] files = gemsFile.listFiles();

        for(File child : files != null ? files : new File[0]) {
            String subPath = Paths.get(child.getAbsolutePath()).resolve("lib").toString();
            MewtwoMain.mewtwoLogger.info("Adding '" + subPath + "' to loadPaths");
            loadPaths.add(subPath);
        }

        rb.setLoadPaths(loadPaths);


        swOut = new StringWriter();
        swErr = new StringWriter();

        PrintWriter pwOut = new PrintWriter(swOut);
        PrintWriter pwErr = new PrintWriter(swErr);

        rb.setOutput(pwOut);
        rb.setError(pwErr);

        if(profile) {
                rb.setProfile(RubyInstanceConfig.ProfilingMode.GRAPH);
            try {
                rb.setProfileOutput(new ProfileOutput(new File("profile.txt")));
            } catch(IOException e) {
                MewtwoMain.mewtwoLogger.error("Could not initialize JRuby profiler! Disabling profiling.");
            }
        }

        long time = System.currentTimeMillis();
        MewtwoMain.mewtwoLogger.info("Initializing ScriptingContainer - this might take a few seconds!");

        rb.runScriptlet("require 'java'; Java::Meew0Mewtwo::MewtwoMain.mewtwoLogger.info('Hello world! This is JRuby')");

        MewtwoMain.mewtwoLogger.info("ScriptingContainer successfully initialized in " +
                (System.currentTimeMillis() - time) + " ms");
    }

    private String getResultAndClear(StringWriter w) {
        String result = w.getBuffer().toString();
        w.getBuffer().setLength(0);
        return result;
    }

    /**
     * Get the standard output of the script
     * @return standard output of the script
     */
    public String getResult() {
        return getResultAndClear(swOut);
    }

    /**
     * Get the standard error of the script (i.e. all debug messages that have been written using STDERR.puts)
     * @return standard error of the script
     */
    public String getError() {
        return getResultAndClear(swErr);
    }

    /**
     * Bind a context to the script.
     * @param ctx the context to bind
     */
    public void bindCtx(MewtwoContext ctx) {
        rb.put("ctx", ctx);
    }

    /**
     * Bind command line arguments to the script. ARGV[0] should be the user nick, ARGV[1] the channel name and ARGV[2 .. end] the rest.
     * @param argv ARGV to bind
     */
    public void bindArgv(String[] argv) {
        rb.put("ARGV", argv);
    }

    /**
     * Set the standard input of the script, usually got using MewtwoContext.getInput
     * @param stdin standard input
     */
    public void bindStdin(String stdin) {
        rb.setInput(new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Actually run a script.
     * @param script script to run
     */
    public void run(String script) {
        rb.runScriptlet(script);
    }
}
