package meew0.mewtwo.modules;

import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import org.apache.commons.io.FileUtils;
import org.jcodings.specific.UTF8Encoding;
import org.joni.Option;
import org.joni.Regex;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meew0 on 12.08.14.
 */
public class ModuleManager {

    private List<Module> modules;

    public static final String modulesFolder = "modules/";

    public ModuleManager() {
        reloadConfigs();
    }

    private List<Module> getSingleModuleForPath(File child) {
        if (child.getName().endsWith(".rb")) {
            try {
                @SuppressWarnings("unchecked")
                List<String> lines = FileUtils.readLines(child);
                if (lines.size() > 1) {
                    String regex = lines.get(0);
                    regex = regex.substring(1, regex.length()).trim();

                    String filename = child.getName();
                    String name = filename.substring(0, filename.length() - 2);
                    MewtwoLogger.info("Adding module " + name + " - regex = " + regex);

                    Module m = new Module(
                            new Regex(regex.getBytes(), 0, regex.length(), Option.NONE, UTF8Encoding.INSTANCE),
                            filename.substring(0, filename.length() - 2), filename);

                    return Arrays.asList(m);
                } else MewtwoLogger.info("Skipping file " + child.getAbsolutePath() + " - shorter than two lines!");
            } catch (Throwable t) {
                MewtwoLogger.errorThrowable(t);
            }

        } else MewtwoLogger.info("Skipping file " + child.getAbsolutePath() + " - not a ruby file!");

        return Arrays.asList();
    }

    private List<Module> traverseDirectoryForModules(Path directory) {
        List<Module> modulesList = new ArrayList<>();
        File[] files = directory.toFile().listFiles();
        for (File child : files != null ? files : new File[0]) {
            if (child.isDirectory()) modulesList.addAll(traverseDirectoryForModules(child.toPath()));
            else modulesList.addAll(getSingleModuleForPath(child));
        }
        return modulesList;
    }

    public void reloadConfigs() {
        modules = traverseDirectoryForModules(Paths.get(ModuleManager.modulesFolder));
    }

    public String executeModules(String message, MewtwoContext ctx) {
        String result = "";
        for (Module m : modules) {
            if (m.activatesOn(message)) result += m.execute(message, ctx);
        }
        return result;
    }
}