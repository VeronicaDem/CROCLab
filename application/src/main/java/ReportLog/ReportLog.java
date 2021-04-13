package ReportLog;

public class ReportLog {

    public ReportLog(int countInputFiles){
        this.countInputFiles = countInputFiles;
    }

    private  int countInputFiles = 0;
    private  int indexCurrentProcessedFile = 0;
    private  LogOperation previousOperation = null;
    private Long startOperationTime;
    private Long endOperationTime;

    public void startCurrentOperation(LogOperation currentOperation){
        startCurrentOperation(currentOperation, "");
    }

    public  void startCurrentOperation(LogOperation currentOperation, String fileName) {
        startOperationTime = System.currentTimeMillis();
        switch (currentOperation) {
            case DICTIONARIES_LOAD:
            case LOAD_WORDS_TO_DELETE:
            case REMOVE_WORDS_TO_DELETE:
                loadingResources(currentOperation);
                break;
            case SINGLE_DICTIONARY:
            case WHITESPACE_DICTIONARY:
            case FIND_ENGLISH:
            case REMOVE_ENGLISH:
            case CREATE_QUARANTINE_FILES:
            case CREATE_PROCESSED_FILES:
            case PROCESSING_DAYS_OF_WEEK:
            case REMOVE_INITIALS:
            case FIND_ABBREVIATIONS:
            case PROCESS_NUMBERS:
            case PROCESS_LINKS:
            case PROCESS_PHONE_NUMBERS:
            case PROCESS_PUNCTUATIONS:
            case SEPARATE_ON_SENTENCES:
            case REMOVE_EXTRAS_WHITESPACE:
            case REMOVE_ACRONYMS:
            case REMOVE_CAMEL_CASE:
            case CREATE_REPLACEMENT_FILE:
                processingFiles(currentOperation, fileName);
                break;
        }
    }

    public void endOperation(){
        endOperationTime = System.currentTimeMillis();
        System.out.println("Операция заняла " + ((float)(endOperationTime - startOperationTime)/1000) + " секунд");
    }

    private  void loadingResources(LogOperation currentOperation) {
        System.out.println(currentOperation);
    }

    private  void processingFiles(LogOperation currentOperation, String fileName) {
        updateProcessedFileIndex(currentOperation);
        System.out.println(currentOperation + "(" + fileName + " " + (1 + indexCurrentProcessedFile) + "/" + countInputFiles + ")");
    }

    private  void updateProcessedFileIndex(LogOperation currentOperation) {
        if (previousOperation != currentOperation) {
            indexCurrentProcessedFile = 0;
        } else {
            indexCurrentProcessedFile++;
        }
        previousOperation = currentOperation;
    }


}

