package ReplacingWords;

import Dictionary.*;
import Dictionary.DictionaryWhitespaceWords;
import InputFile.InputFile;
import ReportLog.ReportLog;


import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacerSpaceWords {

    public static void handleWhitespaceWords(ArrayList<DictionaryWhitespaceWords>dictionaries, ArrayList<InputFile>inputFiles){
        ReportLog.logCurrentOperation("Первичная обработка слов из словаря.");
        for (DictionaryWhitespaceWords dictionary : dictionaries){
            for (InputFile inputfile : inputFiles){
                processInputFile(dictionary, inputfile);
            }
        }
    }


    private static void processInputFile(DictionaryWhitespaceWords whiteSpaceWordsDictionary, InputFile inputFile){
        Map<String, UnreadableWordHandler> wordsReplacements = whiteSpaceWordsDictionary.getWordReplacement();
        for (Map.Entry<String, UnreadableWordHandler> wordReplacement : wordsReplacements.entrySet()){
            String fileText = inputFile.getFileText();
            StringBuffer cleanText = new StringBuffer();
            UnreadableWordHandler unreadableWordHandler = wordReplacement.getValue();
            Pattern unreadableWordPattern = unreadableWordHandler.getUnreadableWordPattern();
            String wordFromDictionary = wordReplacement.getKey();

            Matcher matcher = unreadableWordPattern.matcher(fileText);
            while (matcher.find()){
                String unreadableWordReplacement = unreadableWordHandler.getReplacement();
                matcher.appendReplacement(cleanText, unreadableWordReplacement);
                inputFile.getReplacementFile().addReplacement(wordFromDictionary, unreadableWordReplacement, "dictionaryWhitespaceWords");
            }
            matcher.appendTail(cleanText);
            inputFile.setFileText(cleanText.toString());
        }
    }
}
