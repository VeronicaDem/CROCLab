package ProcessingServices;

import Dictionary.*;
import InputFile.InputFile;
import ReportLog.ReportLog;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacementFromDictionaryService {

    public static void handle(ArrayList<Dictionary>dictionaries, ArrayList<InputFile>inputFiles){
        ReportLog.logCurrentOperation("Первичная обработка слов из словаря.");
        for (Dictionary dictionary : dictionaries){
            for (InputFile inputfile : inputFiles){
                processInputFile(dictionary, inputfile);
            }
        }
    }


    private static void processInputFile(Dictionary dictionary, InputFile inputFile){
        Map<String, UnreadableWordHandler> wordsReplacements = dictionary.getWordReplacement();
        for (Map.Entry<String, UnreadableWordHandler> wordReplacement : wordsReplacements.entrySet()){
            String fileText = inputFile.getFileText();
            StringBuffer cleanText = new StringBuffer();
            UnreadableWordHandler unreadableWordHandler = wordReplacement.getValue();
            Pattern unreadableWordPattern = unreadableWordHandler.getUnreadableWordPattern();
            String wordFromDictionary = wordReplacement.getKey();

            Matcher matcher = unreadableWordPattern.matcher(fileText);
//            System.out.println(Calendar.getInstance().getTime().toString());
            while (matcher.find()){
                String unreadableWordReplacement = " " + unreadableWordHandler.getReplacement() + " ";
                matcher.appendReplacement(cleanText, unreadableWordReplacement);
                inputFile.getReplacementFile().addReplacement(wordFromDictionary, unreadableWordReplacement, "fromDictionary");
            }
//            System.out.println(Calendar.getInstance().getTime().toString());
            matcher.appendTail(cleanText);
            inputFile.setFileText(cleanText.toString());
        }
    }
}
