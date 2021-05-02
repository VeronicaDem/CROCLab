package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;
import NumberService.NumberHandler;
import ReportLog.LogOperation;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeService {

    private static final String regexTime = "(([0,1][0-9])|(2[0-3]))[:.][0-5][0-9]-(([0,1][0-9])|(2[0-3]))[:.][0-5][0-9]";

    public static void handle(ArrayList<InputFile> inputFiles){
        Handler.reportLog.startModule();
        for (InputFile inputFile : inputFiles){
            Handler.reportLog.startCurrentOperation(LogOperation.PROCESSING_TIME, inputFile.getFileName());
            handleTime(inputFile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Time ");
    }

    private static void handleTime(InputFile inputFile){
        String fileText = inputFile.getFileText();
        Pattern phoneNumberPattern = Pattern.compile(regexTime);
        Matcher matcher = phoneNumberPattern.matcher(fileText);
        StringBuffer processedText = new StringBuffer();
        while(matcher.find()){
            StringBuilder replacement = new StringBuilder();
            String  foundDate = fileText.substring(matcher.start(), matcher.end());

            String[] strings = foundDate.split("[.:-]");

            int number = Integer.parseInt(strings[0] );

            int minute =  Integer.parseInt(strings[1] );

            replacement.append("с ").append(hourToString(number)).append(" ").append(minuteToString(minute));


            int number2 = Integer.parseInt(strings[2]);

            int minute2 =  Integer.parseInt(strings[3] );

            replacement.append(" до ").append(hourToString(number2)).append(" ").append(minuteToString(minute2));

            matcher.appendReplacement(processedText, replacement.toString());
        }
        matcher.appendTail(processedText);
        inputFile.setFileText(processedText.toString());
    }

    private static String minuteToString(int number){
        String out = "";

        if(number == 0){
            return "ноль ноль";
        }else if(number > 0 && number < 5 || number == 8){
            switch (number){
                case 1:
                    return "ноль одной";
                case 2:
                    return  "ноль двух";
                case 3:
                    return "ноль трех";
                case 4:
                    return "ноль четырех";
                case 8:
                    return "ноль восьми";
            }

        }else if (number > 20 && number <= 40 && number != 30) {
            if (number == 21 || number == 22 || number == 23 || number == 24 || number == 40) {

                switch (number) {
                    case 21:
                        out = "двадцати одной";
                        break;
                    case 22:
                        out = "двадцати двух";
                        break;
                    case 23:
                        out = "двадцати трех";
                        break;
                    case 24:
                        out = "двадцати четырех";
                        break;
                    case 40:
                        out = "сорока";
                        break;
                }
                return out;

            }else {

                String n = NumberHandler.numberToString(String.valueOf(number));
                String[] strings = n.split(" ");
                return strings[0].substring(0, strings[0].length() - 1) + "и " + strings[1].substring(0, strings[1].length() - 1) + "и";

            }
        }else if(number > 40 && number < 50){
            switch (number){
                case 41:
                    return "сорока одной";
                case 42:
                    return "сорока двух";
                case 43:
                    return "сорока трех";
                case 44:
                    return "сорока четырех";
                case 45:
                    return "сорока пяти";
                case 46:
                    return "сорока шести";
                case 47:
                    return "сорока семи";
                case 48:
                    return "сорока восьми";
                case 49:
                    return "сорока девяти";
            }
        }else if(number > 49 && number < 60){
            switch (number){
                case 50:
                    return "пятидесяти";
                case 51:
                    return "пятидесяти одной";
                case 52:
                    return "пятидесяти двух";
                case 53:
                    return "пятидесяти трех";
                case 54:
                    return "пятидесяти четырех";
                case 55:
                    return "пятидесяти пяти";
                case 56:
                    return "пятидесяти шести";
                case 57:
                    return "пятидесяти семи";
                case 58:
                    return "пятидесяти восьми";
                case 59:
                    return "пятидесяти девяти";
            }
        }
        else if (number >= 5 && number <= 9){
            String n = NumberHandler.numberToString(String.valueOf(number));
            return "ноль " +   n.substring(0 , n.length() - 2) + "и";
        }else if(number > 9 && number < 21 || number == 30){
            String n = NumberHandler.numberToString(String.valueOf(number));
            return n.substring(0 , n.length() - 2) + "и";
        }
        return out;
    }


    private static String hourToString(int number){
        String out = "";
        if(number > 4 && number < 21 && number != 8){
            String n = NumberHandler.numberToString(String.valueOf(number));
            out = n.substring(0 , n.length() - 2) + "и";
            return out;
        }else if(number == 3 || number == 4){
            String n = NumberHandler.numberToString(String.valueOf(number));
            return n.substring(0 , n.length() - 2) + "ех";
        }else if(number == 21 || number ==22 || number == 23){
            String res = "";
            switch (number) {
                case 21:
                    res = "двадцати одного";
                    break;
                case 22:
                    res = "двадцати двух";
                    break;
                case 23:
                    res = "двадцати трех";
                    break;
            }
            return res;
        }else if(number == 1){
            return "часу";
        }else if(number == 2){
            return "двух";
        }else if(number == 0){
            return "двенадцати";
        }else if(number == 8){
            return "восьми";
        }
        return out;
    }
}
