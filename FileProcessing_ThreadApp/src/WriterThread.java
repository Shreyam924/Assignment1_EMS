import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class WriterThread implements Runnable {
    private final Path outputFile;
    private final BlockingQueue<String> outputQueue;
    private final List<FileProcessingEventListener> listeners;

    public WriterThread(Path outputFile, BlockingQueue<String> outputQueue, List<FileProcessingEventListener> listeners) {
        this.outputFile = outputFile;
        this.outputQueue = outputQueue;
        this.listeners = listeners;
    }

    private void fireEvent(FileProcessingEvent event, String line) {
        for (FileProcessingEventListener listener : listeners) {
            listener.onEvent(event, line);
        }
    }

    @Override
    public void run() {
        fireEvent(FileProcessingEvent.WRITER_STARTED, null);
        try (BufferedWriter bw = Files.newBufferedWriter(outputFile)) {
            String line;
            while (!(line = outputQueue.take()).equals("EOF")) {
                bw.write(line);
                bw.newLine();
                fireEvent(FileProcessingEvent.LINE_WRITTEN, line);
                // Notify to start reading the next line
                synchronized (this) {
                    notify();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        fireEvent(FileProcessingEvent.WRITER_FINISHED, null);
    }

    public synchronized void notifyWriteComplete() {
        notify();
    }
}
