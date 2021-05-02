package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseRemover {


    public static void removeCamelCase(ArrayList<InputFile> inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.CAMELCASE_SEPARATOR, inputFile.getFileName());
            processedCurrentFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("CamelCases ");
    }

    private static void processedCurrentFile(InputFile inputFile){
        String fileSentences = inputFile.getFileText();
        Pattern camelCasePattern = Pattern.compile("[А-Яа-я]([А-ЯЁ0-9]*[а-я][а-яё0-9]*[А-ЯЁ]|[а-яё0-9]*[А-ЯЁ][А-ЯЁ0-9]*[а-яё])[А-Яа-яёЁ0-9]*");
        StringBuffer processedText = new StringBuffer();
        Matcher matcher = camelCasePattern.matcher(fileSentences);
        while (matcher.find()){

            String s = fileSentences.substring(matcher.start(), matcher.end());

            StringBuilder str = new StringBuilder();

            Pattern pattern = Pattern.compile("[А-Я]{2,}");
            Matcher matcher5 = pattern.matcher(s);
            if (matcher5.find())
            {
                s = s.replaceAll("[А-Я]{2,}" , " " + matcher5.group() + " ").toLowerCase(Locale.ROOT);


            }

            String[] result = s.split("(?=\\p{Lu})");

            for (String r: result) {
                str.append(" ").append(r).append(" ");

            }

            matcher.appendReplacement(processedText, str.toString());

        }
        matcher.appendTail(processedText);
        inputFile.setFileText(processedText.toString());
    }
}