package no.akademiet.app.ws.mobileappws.WebSocket;

import java.io.IOException;
import java.nio.file.*;

abstract class FileChangeListener {
    private volatile boolean stop = false;

    void stopListening() {
        stop = true;
    }

    void listen() {
        stop = false;

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();


            String currentDir = Paths.get("").toAbsolutePath().toString();
            Path path = Paths.get(currentDir + "/src/main/java/no/akademiet/app/ws/mobileappws/XmlConverter/");

            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;

            while ((key = watchService.take()) != null && !stop) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Modified file: " + event.context() + " - Notifying web socket");
                    notifyFileChanged();
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done Listening");
    }

    abstract void notifyFileChanged();
}
