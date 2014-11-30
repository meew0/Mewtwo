package meew0.mewtwo.modules;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.context.MewtwoContext;
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

    private List<Module> traverseDirectoryForModules(Path directory) {
        List<Module> modulesList = new ArrayList<Module>();
        File[] files = directory.toFile().listFiles();
        for(File child : files != null ? files : new File[0]) {
            if(child.isDirectory()) modulesList.addAll(traverseDirectoryForModules(child.toPath()));
            else {
                if(child.getName().endsWith(".rb")) {
                    try {
                        @SuppressWarnings("unchecked")
                        List<String> lines = FileUtils.readLines(child);
                        if(lines.size() > 1) {
                            String regex = lines.get(0);
                            regex = regex.substring(1, regex.length()).trim();

                            String triggers = lines.get(1);
                            triggers = triggers.substring(1, triggers.length()).trim();

                            String filename = child.getName();
                            String name = filename.substring(0, filename.length() - 3);
                            MewtwoMain.mewtwoLogger.info("Adding module " + name + " - regex = " + regex +
                                    ", triggers = "+ triggers);

                            Module m = new Module(
                                    new Regex(regex.getBytes(), 0, regex.length(), Option.NONE, UTF8Encoding.INSTANCE),
                                    Arrays.asList(triggers.split(",")), filename.substring(0, filename.length() - 3),
                                    filename);

                            modulesList.add(m);
                        } else MewtwoMain.mewtwoLogger.info("Skipping file " + child.getAbsolutePath() + " - shorter than two lines!");
                    } catch (Throwable t) {
                        MewtwoMain.mewtwoLogger.error("Exception while parsing modules for " + child.getAbsolutePath() + "! ", t);
                    }

                } else MewtwoMain.mewtwoLogger.info("Skipping file " + child.getAbsolutePath() + " - not a ruby file!");
            }
        }
        return modulesList;
    }

    public void reloadConfigs() {
        modules = traverseDirectoryForModules(Paths.get(ModuleManager.modulesFolder));
    }

    public String executeModules(String trigger, String message, MewtwoContext ctx) {
        String result = "";
        for(Module m : modules) {
            if(m.activatesOn(message, trigger)) result += m.execute(message, ctx);
        }
        return result;
    }
}
