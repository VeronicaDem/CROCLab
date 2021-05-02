package NumberService;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberService {

    public static void handleNumbers(ArrayList<InputFile> inputFiles) {
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles) {
            Handler.reportLog.startCurrentOperation(LogOperation.PROCESS_NUMBERS, inputFile.getFileName());
            handleFile(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endOperation();
    }

    //Принимает список предложений из файла. Возвращает список предложений с числами, раскрытыми в текст.
    private static void handleFile(InputFile inputFile) {
        String fileText = inputFile.getFileText();
        StringBuilder cleanText = new StringBuilder();
        Pattern numbersPattern = Pattern.compile("[\\d]+");
        Matcher matcher = numbersPattern.matcher(fileText);
        while (matcher.find()) {
            String foundNumber = fileText.substring(matcher.start(), matcher.end()).trim();
            String replacement = "";
            if (foundNumber.length() > 4) {
                replacement = processLongNumber(foundNumber);
            } else {
                replacement = NumberHandler.numberToString(foundNumber);
            }
            matcher.appendReplacement(cleanText, " " + replacement + " ");
        }
        matcher.appendTail(cleanText);
        inputFile.setFileText(cleanText.toString());
    }

    private static String processLongNumber(String longNumber) {
        StringBuilder result = new StringBuilder();
        while (longNumber.length() > 0) {
            if (longNumber.length() > 3) {
                result.append(NumberHandler.numberToString(longNumber.substring(0, 3)));
                longNumber = longNumber.substring(3);
            } else {
                result.append(NumberHandler.numberToString(longNumber));
                break;
            }
        }
        return result.toString();
    }


}