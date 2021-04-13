package ReplacingWords;

import Dictionary.DictionarySingleWords;
import Handler.Handler;
import InputFile.InputFile;
import ReportLog.*;

import java.util.ArrayList;


public class ReplacerSingleWords {

    public static void handleSingleWords(DictionarySingleWords dictionarySingleWords, ArrayList<InputFile> inputFiles){
            for (InputFile inputFile : inputFiles) {
                Handler.reportLog.startCurrentOperation(LogOperation.SINGLE_DICTIONARY, inputFile.getFileName());
                processFile(inputFile, dictionarySingleWords);
                Handler.reportLog.endOperation();
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
        String dotToken = wordToken.substring(0, wordToken.lastIndexOf(".") + 1);
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
