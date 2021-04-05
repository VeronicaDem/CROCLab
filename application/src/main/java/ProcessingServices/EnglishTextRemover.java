package ProcessingServices;

import InputFile.InputFile;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishTextRemover {

    public static void removeEnglishText(ArrayList<InputFile>inputFiles) {
        for (InputFile inputFile : inputFiles) {
           handleFile(inputFile);
        }
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
