package ProcessingServices;

import Handler.Handler;
import InformationFiles.FileWithEnglishText;
import InputFile.InputFile;
import ReportLog.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishTextFinder {


    public static void findEnglishText(ArrayList<InputFile>inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.FIND_ENGLISH, inputFile.getFileName());
            processFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Find english words ");
    }

    private static void processFile(InputFile inputFile){
        ArrayList<String>fileSentences = inputFile.getSentences();
        FileWithEnglishText fileWithEnglishText = inputFile.getFileWithEnglishText();
        for (String sentence : fileSentences) {
            Pattern englishTextPattern = Pattern.compile("[A-Za-z]");
            Matcher matcher = englishTextPattern.matcher(sentence);
            if (matcher.find()){
                fileWithEnglishText.addEnglishSentences(sentence);
            }
        }
    }
}
