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
        for (int i = 0; i < wordTokens.length; i++){//ветка для токенов содержащих точку
            if (wordTokens[i].contains(".")){
                wordTokens[i] = getDotTokenReplacement(inputFile, wordTokens[i], dictionarySingleWords);
            }else {//ветка для токенов не содержащих точку
                String foundReplacement = dictionarySingleWords.getSingleWordReplacement(wordTokens[i]);
                if (foundReplacement != null) {
                    inputFile.getReplacementFile().addReplacement(wordTokens[i], foundReplacement, "dictionarySingleWord");
                    wordTokens[i] = foundReplacement;
                }
            }
        }
        String result = String.join(" ", wordTokens);
        inputFile.setFileText(result);
    }

    private static String getDotTokenReplacement(InputFile inputFile, String wordToken, DictionarySingleWords dictionarySingleWords){
        String dotToken = wordToken.substring(0, wordToken.indexOf(".") + 1);
        String dotTokenReplacement = dictionarySingleWords.getSingleWordReplacement(dotToken);
        if (dotTokenReplacement != null){
            inputFile.getReplacementFile().addReplacement(dotToken, dotTokenReplacement, "dictionarySingleWord");
            wordToken = wordToken.replace(dotToken, dotTokenReplacement);
        }
        return wordToken;
    }

    private static String[] tokenizeFile(InputFile inputFile){
        String fileText = inputFile.getFileText();
        return fileText.split(" ");
    }
}
