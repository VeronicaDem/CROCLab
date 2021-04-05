package ReplacingWords;

import Dictionary.DictionarySingleWords;
import InputFile.InputFile;

import java.util.ArrayList;

public class ReplacerSingleWords {

    public static void handleSingleWords(ArrayList<DictionarySingleWords> dictionariesSingleWords,
                                         ArrayList<InputFile> inputFiles){
        for (DictionarySingleWords dictionarySingleWords : dictionariesSingleWords){
            for (InputFile inputFile : inputFiles) {
                processFile(inputFile, dictionarySingleWords);
            }
        }
    }

    private static void processFile(InputFile inputFile, DictionarySingleWords dictionarySingleWords){
        String[] wordTokens = tokenizeFile(inputFile);
        for (int i = 0; i < wordTokens.length; i++){
            String foundReplacement = dictionarySingleWords.getSingleWordReplacement(wordTokens[i]);
            if (foundReplacement != null){
                inputFile.getReplacementFile().addReplacement(wordTokens[i], foundReplacement, "dictionarySingleWord");
                wordTokens[i] = foundReplacement;
            }
        }
        String result = String.join(" ", wordTokens);
        inputFile.setFileText(result);
    }

    private static String[] tokenizeFile(InputFile inputFile){
        String fileText = inputFile.getFileText();
        return fileText.split(" ");
    }
}
