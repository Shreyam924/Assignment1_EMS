import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ReaderThread implements Runnable {
    private final Path inputFile;
    private final BlockingQueue<String> inputQueue;
    private final List<FileProcessingEventListener> listeners;

    public ReaderThread(Path inputFile, BlockingQueue<String> inputQueue, List<FileProcessingEventListener> listeners) {
        this.inputFile = inputFile;
        this.inputQueue = inputQueue;
        this.listeners = listeners;
    }

    private void fireEvent(FileProcessingEvent event, String line) {
        for (FileProcessingEventListener listener : listeners) {
            listener.onEvent(event, line);
        }
    }

    @Override
    public void run() {
        fireEvent(FileProcessingEvent.READER_STARTED, null);
        try (BufferedReader br = Files.newBufferedReader(inputFile)) {
            String line;
            while ((line = br.readLine()) != null) {
                fireEvent(FileProcessingEvent.LINE_READ, line);
                // Wait for user input to continue processing
                synchronized (this) {
                    wait();
                }
                inputQueue.put(line);
            }
            inputQueue.put("EOF");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        fireEvent(FileProcessingEvent.READER_FINISHED, null);
    }

    public synchronized void notifyReadComplete() {
        notify();
    }
}
