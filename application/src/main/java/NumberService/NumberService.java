package NumberService;

import InputFile.InputFile;
import ReportLog.ReportLog;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberService {

    public static void handleNumbers(ArrayList<InputFile>inputFiles){
        ReportLog.logCurrentOperation("Раскрытие цифр в текстовое представление.");
        for (InputFile inputFile : inputFiles){
            handleFile(inputFile);
        }
    }

    //Принимает список предложений из файла. Возвращает список предложений с числами, раскрытыми в текст.
    private static void handleFile(InputFile inputFile){
        ArrayList<String>fileSentences = inputFile.getSentences();
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
        inputFile.setSentences(cleanSentences);
    }

    public static String numberToSymbol(String number){
        String result;
        Long numberToReplace = Long.parseLong(number);
        result = NumberHandler.numberToSymbol(numberToReplace, numberToReplace);
        return result;
    }
}
