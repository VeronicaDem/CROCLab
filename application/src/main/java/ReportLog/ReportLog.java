package ReportLog;

public class ReportLog {

    private static volatile String currentOperation;

    public static void logCurrentOperation(String operation){
        currentOperation = operation;
        System.out.println("Выполняется - " + currentOperation);
    }
}
