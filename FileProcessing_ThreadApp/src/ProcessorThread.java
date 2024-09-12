import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ProcessorThread implements Runnable {
    private final BlockingQueue<String> inputQueue;
    private final BlockingQueue<String> outputQueue;
    private final List<FileProcessingEventListener> listeners;

    public ProcessorThread(BlockingQueue<String> inputQueue, BlockingQueue<String> outputQueue, List<FileProcessingEventListener> listeners) {
        this.inputQueue = inputQueue;
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
        fireEvent(FileProcessingEvent.PROCESSOR_STARTED, null);
        try {
            String line;
            while (!(line = inputQueue.take()).equals("EOF")) {
                String processedLine = capitalizeWords(line);
                fireEvent(FileProcessingEvent.LINE_PROCESSED, processedLine);
                // Wait for user input to continue writing
                synchronized (this) {
                    wait();
                }
                outputQueue.put(processedLine);
            }
            outputQueue.put("EOF");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fireEvent(FileProcessingEvent.PROCESSOR_FINISHED, null);
    }

    private String capitalizeWords(String str) {
        String[] words = str.trim().split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalized.toString().trim();
    }

    public synchronized void notifyProcessComplete() {
        notify();
    }
}
