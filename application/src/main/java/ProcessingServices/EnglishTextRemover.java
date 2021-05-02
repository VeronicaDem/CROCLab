package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishTextRemover {

    public static void removeEnglishText(ArrayList<InputFile>inputFiles) {
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles) {
            Handler.reportLog.startCurrentOperation(LogOperation.REMOVE_ENGLISH, inputFile.getFileName());
           handleFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Remove english words ");
    }

    private static void handleFile(InputFile fileWithEnglishLetters){
        ArrayList<String>fileSentences = new ArrayList<>(fileWithEnglishLetters.getSentences());
        for (String sentence : fileSentences){
            Pattern englishLettersPattern = Pattern.compile("[A-Za-z]");
            Matcher matcher = englishLettersPattern.matcher(sentence);
            if (matcher.find()){
                fileWithEnglishLetters.moveToQuarantine(sentence);
            }
        }
    }
}
