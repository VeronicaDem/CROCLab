package ProcessingServices;

import InformationFiles.FileWithEnglishText;
import InputFile.InputFile;
import ReportLog.ReportLog;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishTextFinder {


    public static void findEnglishText(ArrayList<InputFile>inputFiles){
        ReportLog.logCurrentOperation("Поиск английского текста.");
        for (InputFile inputFile : inputFiles){
        processFile(inputFile);
        }
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
