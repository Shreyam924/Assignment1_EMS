import java.util.ArrayList;
import java.util.List;

public class FileProcessingEventManager implements FileProcessingEventListener {
    private final List<String> eventLogs = new ArrayList<>();

    @Override
    public void onEvent(FileProcessingEvent event, String line) {
        String message = switch (event) {
            case READER_STARTED -> "Reader thread started.";
            case READER_FINISHED -> "Reader thread finished.";
            case PROCESSOR_STARTED -> "Processor thread started.";
            case PROCESSOR_FINISHED -> "Processor thread finished.";
            case WRITER_STARTED -> "Writer thread started.";
            case WRITER_FINISHED -> "Writer thread finished.";
            case PROCESSING_COMPLETED -> "File processing completed.";
            case LINE_READ -> "Line read: " + line;
            case LINE_PROCESSED -> "Line processed: " + line;
            case LINE_WRITTEN -> "Line written: " + line;
            default -> "Unknown event.";
        };
        eventLogs.add(message);
        System.out.println(message);
    }

//    public List<String> getEventLogs() {
//        return eventLogs;
//    }
}
