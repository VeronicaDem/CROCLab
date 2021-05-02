package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.util.ArrayList;

public class WhitespaceService {

    public static void removeExtraWhitespace(ArrayList<InputFile>inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.REMOVE_EXTRAS_WHITESPACE, inputFile.getFileName());
            handleFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Extras whitespaces ");
    }

    //Удаляем лишние пробелы из предложения.
    private static void handleFile(InputFile inputFile) {
        ArrayList<String>sentences = inputFile.getSentences();
        ArrayList<String>cleanSentences = new ArrayList<>();
        for (String sentence : sentences) {
            sentence =  sentence.trim().replaceAll("\\s+", " ").
                    replaceAll("\\s+(?=[!?,.])", "");
            cleanSentences.add(sentence);
        }
        inputFile.setSentences(cleanSentences);
    }

}
