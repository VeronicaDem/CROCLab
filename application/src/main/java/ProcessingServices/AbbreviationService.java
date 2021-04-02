package ProcessingServices;

import Handler.Handler;
import InputFile.InputFile;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbbreviationService {

    private static final String DESCRIPTION_REPLACEMENT = "(abbreviation)";

    private static Map<String, String> daysOfWeek = new LinkedHashMap<>();
    static{
        daysOfWeek.put("пн", "понедельник");
        daysOfWeek.put("вт", "вторник");
        daysOfWeek.put("ср", "среда");
        daysOfWeek.put("чт", "четверг");
        daysOfWeek.put("пт", "пятница");
        daysOfWeek.put("сб", "суббота");
        daysOfWeek.put("вс", "воскресенье");
    }

    //Принимает строку, в которую записан файл. Возвращает строку с обработанными сокращениями.
    public static void handle(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            handleAbbreviation(inputFile);
            handleDaysOfWeek(inputFile);
            handleInitials(inputFile);
        }
    }

    //Ищутся сокращения вида: "гор.", если для найденного сокращения существует замена в словаре, то сокращение будет
    //заменено и произведённая замена добавиться в файл замен.
    private static void handleAbbreviation(InputFile inputFile){
        String fileText = inputFile.getFileText();
        StringBuffer result = new StringBuffer();
        //regex, который находит аббревиатуры
        Pattern pattern = Pattern.compile("(\\b[а-яА-ЯёЁ]+?\\.)|([А-Яа-яёЁ]{1,4}-[А-Яа-яёЁ]+)");
        Matcher matcher = pattern.matcher(fileText);
        while(matcher.find()){
            //аббревиатура найденная в тексте
            String abbreviation = fileText.substring(matcher.start(), matcher.end()).trim();
            //ищем для неё замену в словаре
            String replacement = Handler.getDictionaries().getReplacement(abbreviation);
            if (replacement != null) {
                matcher.appendReplacement(result, " " + replacement + " ");
                inputFile.getReplacementFile().addReplacement(abbreviation, replacement, DESCRIPTION_REPLACEMENT);
            }
        }
        matcher.appendTail(result);
        inputFile.setFileText(result.toString());
    }

    //Раскрывает найденные в тексте сокращения дней недели
    private static void handleDaysOfWeek(InputFile inputfile){
        String fileText = inputfile.getFileText();
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("(\\b[а-яА-Я]{2}[;:,.!?\\s\b])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileText);
        while(matcher.find()){
            String foundWord = fileText.substring(matcher.start(), matcher.end()).toLowerCase();
            String punctuationMark = foundWord.substring(2);
            foundWord = foundWord.substring(0,2);
            if (daysOfWeek.containsKey(foundWord)){
                String replacement = daysOfWeek.get(foundWord);
                matcher.appendReplacement(result, " " + replacement + punctuationMark);
            }
        }
        matcher.appendTail(result);
        inputfile.setFileText(result.toString());
    }

    //Удаление инициалов
    private static void handleInitials(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String resultText = fileText.replaceAll("\\s+[А-ЯЁ]\\.\\s*[А-ЯЁ]\\.", " ");
        inputFile.setFileText(resultText);
    }
}
