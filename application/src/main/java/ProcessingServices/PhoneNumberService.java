package ProcessingServices;

import InputFile.InputFile;
import NumberService.NumberService;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberService {

    private static final String regexMobilePhoneNumbers = "(\\+7|8)[- _]*\\(?[- _]*(\\d{3}[- _]*\\)?([- _]*\\d){7}|\\d\\d[- _]*\\d\\d[- _]*\\)?([- _]*\\d){6})";

    public static void handle(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            handlePhoneNumbers(inputFile);
        }
    }

    private static void handlePhoneNumbers(InputFile inputFile){
        String fileText = inputFile.getFileText();
        Pattern phoneNumberPattern = Pattern.compile(regexMobilePhoneNumbers);
        Matcher matcher = phoneNumberPattern.matcher(fileText);
        StringBuffer processedText = new StringBuffer();
        while(matcher.find()){
            String foundPhoneNumber = fileText.substring(matcher.start(), matcher.end());
            boolean containsPlus = foundPhoneNumber.contains("+");
            foundPhoneNumber = foundPhoneNumber.replaceAll("[\\s+()-]", "");
            String replacement = NumberService.numberToSymbol(foundPhoneNumber.substring(0, 1))
                    + NumberService.numberToSymbol(foundPhoneNumber.substring(1,4))
                    + NumberService.numberToSymbol(foundPhoneNumber.substring(4, 7))
                    + NumberService.numberToSymbol(foundPhoneNumber.substring(7, 9))
                    + NumberService.numberToSymbol(foundPhoneNumber.substring(9));
            if (containsPlus){
                replacement = "+" + replacement;
            }
            matcher.appendReplacement(processedText, replacement);
        }
        matcher.appendTail(processedText);
        inputFile.setFileText(processedText.toString());
    }
}
