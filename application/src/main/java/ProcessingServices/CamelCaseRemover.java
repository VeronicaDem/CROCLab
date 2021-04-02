package ProcessingServices;

import FileToProcess.ProcessedFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseRemover {

    public static void removeCamelCase(ArrayList<ProcessedFile> processedFiles){
        for (ProcessedFile processedFile : processedFiles){
         processedCurrentFile(processedFile);
        }
    }

    private static void processedCurrentFile(ProcessedFile processedFile){
        ArrayList<String>fileSentences = new ArrayList<>(processedFile.getSentences());
        Pattern camelCasePattern = Pattern.compile("[А-Яа-я]([А-ЯЁ0-9]*[а-я][а-яё0-9]*[А-ЯЁ]|[а-яё0-9]*[А-ЯЁ][А-ЯЁ0-9]*[а-яё])[А-Яа-яёЁ0-9]*");
        for (String sentence : fileSentences){
            Matcher matcher = camelCasePattern.matcher(sentence);
            if (matcher.find()){
                processedFile.moveToQuarantine(sentence);
            }
        }
    }
}
