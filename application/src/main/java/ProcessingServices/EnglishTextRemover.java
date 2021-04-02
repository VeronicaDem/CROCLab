package ProcessingServices;

import FileToProcess.ProcessedFile;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishTextRemover {

    public static void handle(ArrayList<ProcessedFile> processedFiles) {
        for (ProcessedFile currentFile : processedFiles) {
            handleEnglishLetters(currentFile);
        }
    }

    private static void handleEnglishLetters(ProcessedFile fileWithEnglishLetters){
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
