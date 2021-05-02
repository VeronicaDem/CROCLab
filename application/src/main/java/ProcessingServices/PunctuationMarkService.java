package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.*;

import java.util.ArrayList;

public class PunctuationMarkService {

    public static void handle(ArrayList<InputFile>inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.PROCESS_PUNCTUATIONS, inputFile.getFileName());
            handlePunctuationMarks(inputFile);
            Handler.reportLog.endOperation();
            System.gc();
        }
        Handler.reportLog.endModule("Punctuation marks ");
    }

    private static void handlePunctuationMarks(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String handledFileText = fileText.replaceAll("\\.{2,}", "\\.")
                .replaceAll("#", " номер ").replaceAll("№", " номер ")
                .replaceAll("%", " процент ").replaceAll("\\+", " плюс ")
                .replaceAll("₽", " рублей ").replaceAll("\\$", " долларов ")
                .replaceAll(";", "\\.").replaceAll("<(/?[^<>]*)>", " ")
                .replaceAll(":", " ").replaceAll("[\\W&&[^а-яА-ЯёЁ,?.!\\s]]", " ");
        inputFile.setFileText(handledFileText);
    }

}
