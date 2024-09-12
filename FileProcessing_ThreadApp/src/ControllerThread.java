import java.util.List;

public class ControllerThread implements Runnable {
    private final ReaderThread reader;
    private final ProcessorThread processor;
    private final WriterThread writer;
    private final List<FileProcessingEventListener> listeners;

    public ControllerThread(ReaderThread reader, ProcessorThread processor, WriterThread writer, List<FileProcessingEventListener> listeners) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
        this.listeners = listeners;
    }

    private void fireEvent(FileProcessingEvent event, String line) {
        for (FileProcessingEventListener listener : listeners) {
            listener.onEvent(event, line);
        }
    }

    @Override
    public void run() {
        new Thread(reader).start();
        new Thread(processor).start();
        new Thread(writer).start();

        try {
            while (true) {
                synchronized (reader) {
                    reader.notifyReadComplete();
                }
                synchronized (processor) {
                    processor.notifyProcessComplete();
                }
                synchronized (writer) {
                    writer.notifyWriteComplete();
                }

                // Add delay to control the flow, if necessary
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
