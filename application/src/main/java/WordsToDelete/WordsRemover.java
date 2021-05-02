package WordsToDelete;

import Handler.Handler;
import InputFile.InputFile;
import Properties.PropertyLoader;
import ReportLog.LogOperation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsRemover {

    private static ArrayList<String> wordsToDelete;

    public static void removeWords(PropertyLoader property, ArrayList<InputFile>inputFiles){
        Handler.reportLog.startModule();
        Handler.reportLog.startCurrentOperation(LogOperation.LOAD_WORDS_TO_DELETE);
        WordsToDeleteStorage wordsToDeleteStorage = new WordsToDeleteStorage(property);
        wordsToDelete = wordsToDeleteStorage.getWordsToDelete();
        Handler.reportLog.startCurrentOperation(LogOperation.REMOVE_WORDS_TO_DELETE);
        processFiles(inputFiles);
        wordsToDelete = null;
        Handler.reportLog.endModule("Remove words ");
    }

    private static void processFiles(ArrayList<InputFile> inputFiles){
        for (String wordToDelete : wordsToDelete){
            Pattern deleteWordPattern = Pattern.compile("(?<=[\\W&&[^А-Яа-яёЁ]])" + wordToDelete + "(?=[\\W&&[^А-Яа-яёЁ]])",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            for (InputFile inputFile : inputFiles){
                handleFile(inputFile, deleteWordPattern);
            }
        }
    }

    private static void handleFile(InputFile inputFile, Pattern wordToDeletePattern){
        String fileText = inputFile.getFileText();
        StringBuilder handledText = new StringBuilder();
        Matcher matcher = wordToDeletePattern.matcher(fileText);
        while(matcher.find()){
            String foundWord = fileText.substring(matcher.start(), matcher.end());
            inputFile.getDeletedWordsStorage().addDeletedWord(foundWord);
            matcher.appendReplacement(handledText, "");
        }
        matcher.appendTail(handledText);
        inputFile.setFileText(handledText.toString());
    }
}
