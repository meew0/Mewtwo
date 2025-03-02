package meew0.mewtwo.modules;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
import meew0.mewtwo.core.MewtwoLogger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
                List<String> lines = FileUtils.readLines(child, "UTF-8");
                if (lines.size() > 1) {
                    String regex = lines.getFirst();
                    regex = regex.substring(1).trim();

                    String filename = child.getName();
                    String name = filename.substring(0, filename.length() - 2);
                    MewtwoLogger.info("Adding module " + name + " - regex = " + regex);

                    Module m = new Module(Pattern.compile(regex), name,
                            filename);

                    return List.of(m);
                } else {
                    MewtwoLogger.info("Skipping file " + child.getAbsolutePath() + " - shorter than two lines!");
                }
            } catch (Throwable t) {
                MewtwoLogger.errorThrowable(t);
            }

        } else {
            MewtwoLogger.info("Skipping file " + child.getAbsolutePath() + " - not a ruby file!");
        }

        return List.of();
    }

    private List<Module> traverseDirectoryForModules(Path directory) {
        List<Module> modulesList = new ArrayList<>();
        File[] files = directory.toFile().listFiles();
        for (File child : files != null ? files : new File[0]) {
            if (child.isDirectory()) {
                modulesList.addAll(traverseDirectoryForModules(child.toPath()));
            } else {
                modulesList.addAll(getSingleModuleForPath(child));
            }
        }
        return modulesList;
    }

    public void reloadConfigs() {
        modules = traverseDirectoryForModules(getModulesPath());
    }

    public static Path getModulesPath() {
        return Paths.get(MewtwoMain.getInstanceDirectory(), modulesFolder);
    }

    public String executeModules(String message, MewtwoContext ctx) {
        StringBuilder result = new StringBuilder();
        for (Module m : modules) {
            if (m.activatesOn(message)) {
                MewtwoLogger.info("Executing module: " + m.getName());
                result.append(m.execute(message, ctx));
            }
        }
        return result.toString();
    }

    public boolean doesModuleExistForMessage(String message) {
        for (Module m : modules) {
            if (m.activatesOn(message)) return true;
        }
        return false;
    }
}