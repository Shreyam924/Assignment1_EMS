import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileProcessorApp implements Runnable {
    private final Path directoryToWatch;

    public FileProcessorApp(Path directoryToWatch) {
        this.directoryToWatch = directoryToWatch;
    }

    @Override
    public void run() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directoryToWatch.register(watchService, ENTRY_CREATE);

            System.out.println("Watching directory: " + directoryToWatch);

            while (true) {
                WatchKey key;

                try {
                    key = watchService.take(); // Blocks until an event occurs
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    if (kind == ENTRY_CREATE) {
                        Path fileToProcess = directoryToWatch.resolve(fileName);
                        System.out.println("File created: " + fileToProcess);

                        // Skip processing if the file starts with "processed_"
                        if (shouldSkipProcessing(fileToProcess)) {
                            System.out.println("Skipping file: " + fileToProcess);
                        } else {
                            System.out.println("Processing file: " + fileToProcess);
                            new Thread(() -> startProcessing(fileToProcess)).start();
                        }
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    System.out.println("WatchKey is no longer valid. Stopping the watcher.");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean shouldSkipProcessing(Path fileToProcess) {
        return fileToProcess.getFileName().toString().startsWith("processed_");
    }

    private void startProcessing(Path file) {
        BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
        FileProcessingEventManager eventManager = new FileProcessingEventManager();
        List<FileProcessingEventListener> listeners = List.of(eventManager);

        ReaderThread reader = new ReaderThread(file, inputQueue, listeners);
        ProcessorThread processor = new ProcessorThread(inputQueue, outputQueue, listeners);
        WriterThread writer = new WriterThread(file.resolveSibling("processed_" + file.getFileName()), outputQueue, listeners);
        ControllerThread controller = new ControllerThread(reader, processor, writer, listeners);

        new Thread(controller).start();
    }

    public static void main(String[] args) {
        Path directoryToWatch = Paths.get("/home/shreyam/Files");
        FileProcessorApp fileWatcher = new FileProcessorApp(directoryToWatch);
        new Thread(fileWatcher).start();
    }
}
