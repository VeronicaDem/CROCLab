package WordsToDelete;

import InputFile.InputFile;
import Properties.PropertyLoader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsRemover {

    private static ArrayList<String> wordsToDelete;
    private static WordsToDeleteStorage wordsToDeleteStorage;

    public static void WordsRemover(PropertyLoader property, ArrayList<InputFile>inputFiles){
        WordsToDeleteStorage wordToDeleteService = new WordsToDeleteStorage(property);
        wordsToDelete = wordToDeleteService.getWordsToDelete();
        wordsToDeleteStorage = new WordsToDeleteStorage(property);
        wordsToDelete = wordsToDeleteStorage.getWordsToDelete();
        removeWords(inputFiles);
    }

    private static void removeWords(ArrayList<InputFile> inputFiles){
        for (String wordToDelete : wordsToDelete){
            Pattern deleteWordPattern = Pattern.compile("(?<=[\\W&&[^А-Яа-яёЁ]])" + wordToDelete + "(?=[\\W&&[^А-Яа-яёЁ]])");
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
