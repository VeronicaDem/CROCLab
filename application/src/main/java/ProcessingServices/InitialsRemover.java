package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;
import java.util.ArrayList;


public class InitialsRemover {

    public static void removeInitials(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.REMOVE_INITIALS, inputFile.getFileName());
            processFile(inputFile);
        }
    }

    private static void processFile(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String cleanText = fileText.replaceAll("(?<=^|[\\W&&[^А-Яа-яёЁ]])[А-Я]\\.\\s*[А-Я]\\.", "");
        inputFile.setFileText(cleanText);
    }
}
