package ProcessingServices;

import InputFile.InputFile;
import ReportLog.ReportLog;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceSeparator {


    //Принимает текст файла. Возвращает текст разбитый на список предложений.
    public static void splitOnSentences(ArrayList<InputFile>inputFiles){
        ReportLog.logCurrentOperation("Разделение текста на предложения.");
        for (InputFile inputFile : inputFiles){
            processFile(inputFile);
        }
    }

    private static void processFile(InputFile inputFile){
        String fileText = inputFile.getFileText();
        ArrayList<String>fileSentences = new ArrayList<>();
        //Регулярное выражение для поиска предложений в строке.
        Pattern pattern = Pattern.compile("[^\\!\\?\\.].*?[\\!\\?\\.]");
        Matcher matcher = pattern.matcher(fileText);
        while(matcher.find()) {
            String foundSentence = fileText.substring(matcher.start(), matcher.end());
            fileSentences.add(foundSentence);
        }
        inputFile.setSentences(fileSentences);
    }


}
