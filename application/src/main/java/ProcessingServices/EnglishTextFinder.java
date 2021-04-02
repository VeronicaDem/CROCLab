package ProcessingServices;

import FileToProcess.ProcessedFile;
import InformationFiles.FileWithEnglishText;
import ReportLog.ReportLog;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishTextFinder {


    public static void findEnglishText(ArrayList<ProcessedFile>processedFiles){
        ReportLog.logCurrentOperation("Поиск английского текста.");
        for (ProcessedFile processedFile : processedFiles){
        processFile(processedFile);
        }
    }

    private static void processFile(ProcessedFile processedFile){
        ArrayList<String>fileSentences = processedFile.getSentences();
        for (String sentence : fileSentences) {
            FileWithEnglishText fileWithEnglishText = processedFile.getFileWithEnglishText();
            Pattern englishTextPattern = Pattern.compile("[A-Za-z]");
            Matcher matcher = englishTextPattern.matcher(sentence);
            if (matcher.find()){
                fileWithEnglishText.addEnglishSentences(sentence);
            }
        }
    }
}
