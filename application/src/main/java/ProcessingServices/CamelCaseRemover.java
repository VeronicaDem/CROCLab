package ProcessingServices;

import InputFile.InputFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseRemover {

    public static void removeCamelCase(ArrayList<InputFile> inputFiles){
        for (InputFile inputFile : inputFiles){
         processedCurrentFile(inputFile);
        }
    }

    private static void processedCurrentFile(InputFile inputFile){
        ArrayList<String>fileSentences = new ArrayList<>(inputFile.getSentences());
        Pattern camelCasePattern = Pattern.compile("[А-Яа-я]([А-ЯЁ0-9]*[а-я][а-яё0-9]*[А-ЯЁ]|[а-яё0-9]*[А-ЯЁ][А-ЯЁ0-9]*[а-яё])[А-Яа-яёЁ0-9]*");
        for (String sentence : fileSentences){
            Matcher matcher = camelCasePattern.matcher(sentence);
            if (matcher.find()){
                inputFile.moveToQuarantine(sentence);
            }
        }
    }
}
