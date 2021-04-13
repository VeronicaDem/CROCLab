package ProcessingServices;

import InputFile.InputFile;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitialsRemover {

    public static void removeInitials(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            processFile(inputFile);
        }
    }

    private static void processFile(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String cleanText = fileText.replaceAll("(?<=^|[\\W&&[^А-Яа-яёЁ]])[А-Я]\\.\\s*[А-Я]\\.", "");
        inputFile.setFileText(cleanText);
    }
}
