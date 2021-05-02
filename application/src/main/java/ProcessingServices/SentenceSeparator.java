package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceSeparator {


    //Принимает текст файла. Возвращает текст разбитый на список предложений.
    public static void splitOnSentences(ArrayList<InputFile>inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.SEPARATE_ON_SENTENCES, inputFile.getFileName());
            processFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Separation of sentences ");
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
        removeUselessSentences(fileSentences);
        inputFile.setSentences(fileSentences);
        inputFile.setFileText(null);
    }


    private static void removeUselessSentences(ArrayList<String>sentences){
        sentences.removeIf(token -> Pattern.matches("(^\\s*[А-Яа-яёЁA-Za-z][\\W&&[^А-Яа-яёЁ]]+\\s*$)|(^\\s*[\\W&&[^А-Яа-яёЁ]]+\\s*$)", token));
    }

}
