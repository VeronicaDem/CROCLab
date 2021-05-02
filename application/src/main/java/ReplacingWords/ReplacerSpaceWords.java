package ReplacingWords;

import Dictionary.*;
import Dictionary.DictionaryWhitespaceWords;
import Handler.Handler;
import InputFile.InputFile;
import ReportLog.*;


import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacerSpaceWords {

    public static void handleWhitespaceWords(DictionaryWhitespaceWords dictionaryWhitespaceWords, ArrayList<InputFile> inputFiles) {
        Handler.reportLog.startModule();
        for (InputFile inputfile : inputFiles) {
            Handler.reportLog.startCurrentOperation(LogOperation.WHITESPACE_DICTIONARY, inputfile.getFileName());
            processInputFile(dictionaryWhitespaceWords, inputfile);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("Whitespace dictionary words ");
    }


    private static void processInputFile(DictionaryWhitespaceWords whiteSpaceWordsDictionary, InputFile inputFile){
        Map<String, UnreadableWordHandler> wordsReplacements = whiteSpaceWordsDictionary.getWordReplacement();
        for (Map.Entry<String, UnreadableWordHandler> wordReplacement : wordsReplacements.entrySet()){
            String fileText = inputFile.getFileText();
            StringBuilder cleanText = new StringBuilder();
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
