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

    //TODO добавить раскрытие камелКейса по типу - ТриГорбаОдинДваТри

    private static void processedCurrentFile(InputFile inputFile){
        String fileSentences = inputFile.getFileText();
        Pattern camelCasePattern = Pattern.compile("[А-Яа-я]([А-ЯЁ0-9]*[а-я][а-яё0-9]*[А-ЯЁ]|[а-яё0-9]*[А-ЯЁ][А-ЯЁ0-9]*[а-яё])[А-Яа-яёЁ0-9]*");
        StringBuffer processedText = new StringBuffer();
        Matcher matcher = camelCasePattern.matcher(fileSentences);
        while (matcher.find()){

            String s = fileSentences.substring(matcher.start(), matcher.end());

//            System.out.println(s);

            Pattern pattern = Pattern.compile("[А-ЯA-Z]+");
            Matcher matcher1 = pattern.matcher(s);

            String ch = "";
            String res = "";

            while (matcher1.find()) {
                ch = matcher1.group();

                int i = s.lastIndexOf(ch);
                res = s.substring(0 , i);
                res = res + " " + s.substring(i);

            }

            matcher.appendReplacement(processedText, res);

        }

        matcher.appendTail(processedText);

//        System.out.println(processedText.toString());

        inputFile.setFileText(processedText.toString());


    }
}