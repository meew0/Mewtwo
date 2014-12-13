package meew0.mewtwo.thread;

import meew0.mewtwo.MewtwoMain;
import meew0.mewtwo.irc.MewtwoListener;

import java.nio.file.*;

/**
 * Class that watches the file system, specifically the modules/ folder, for changes.
 */
public class FileWatchThread implements Runnable {
    private WatchService watcher;

    public FileWatchThread() {
        try {
            watcher = FileSystems.getDefault().newWatchService();

            Path dir = Paths.get("modules/");
            dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (Throwable t) {
            MewtwoMain.mewtwoLogger.error("Error while creating FileWatchThread!", t);
        }
    }

    @Override
    public void run() {
        while(true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                MewtwoMain.mewtwoLogger.error("Error while waiting for events!", e);
                continue;
            }

            // Only reload the configs if there actually is an item, so we don't waste time
            if(!key.pollEvents().isEmpty()) {
                MewtwoListener.getPCtx().getModuleManager().reloadConfigs();
            }
        }
    }
}
