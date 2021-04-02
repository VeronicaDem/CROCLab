package NumberService;

import FileToProcess.ProcessedFile;
import ReportLog.ReportLog;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberService {

    public static void handle(ArrayList<ProcessedFile>handledFile){
        ReportLog.logCurrentOperation("Раскрытие цифр в текстовое представление.");
        for (ProcessedFile currentFile : handledFile){
            ArrayList<String>cleanSentence = handleNumbers(currentFile.getSentences());
            currentFile.setSentences(cleanSentence);
        }
    }

    //Принимает список предложений из файла. Возвращает список предложений с числами, раскрытыми в текст.
    private static ArrayList<String> handleNumbers(ArrayList<String>fileSentences){
        ArrayList<String>cleanSentences = new ArrayList<>();
        Pattern numbersPattern = Pattern.compile("[\\d]+");
        for (String sentence : fileSentences){
            Matcher matcher = numbersPattern.matcher(sentence);
            StringBuffer result = new StringBuffer();
            while(matcher.find()){
                String foundNumber = sentence.substring(matcher.start(), matcher.end()).trim();
                Long number = Long.parseLong(foundNumber);
                String replacement = NumberHandler.numberToSymbol(number, number);
                matcher.appendReplacement(result, " " + replacement + " ");
            }
            matcher.appendTail(result);
            cleanSentences.add(result.toString());
        }
        return cleanSentences;
    }

    public static String numberToSymbol(String number){
        String result;
        Long numberToReplace = Long.parseLong(number);
        result = NumberHandler.numberToSymbol(numberToReplace, numberToReplace);
        return result;
    }
}
