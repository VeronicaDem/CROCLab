package ProcessingServices;

import InputFile.InputFile;
import ReportLog.ReportLog;

import java.util.ArrayList;

public class PunctuationMarkService {

    public static void handle(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            handlePunctuationMarks(inputFile);
        }
    }

    private static void handlePunctuationMarks(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String handledFileText = fileText.replaceAll("\\.{2,}", "\\.").replaceAll("%", "процент")
                .replaceAll("\\+", " плюс ").replaceAll(";", "\\.")
                .replaceAll("<(/?[^<>]*)>", " ").replaceAll(":", " ")
                .replaceAll("[\\W&&[^а-яА-ЯёЁ,?.!\\s]]", " ");
        inputFile.setFileText(handledFileText);
    }

}
