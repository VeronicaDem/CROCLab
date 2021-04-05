package ProcessingServices;

import InputFile.InputFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class CleanerSentenceService {

    public static void removeOneLetterSentences(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            removeEmptySentence(inputFile.getSentences());
        }
    }

    private static void removeEmptySentence(ArrayList<String>fileSentences){
        Iterator<String> sentenceIterator = fileSentences.iterator();
        while(sentenceIterator.hasNext()){
            String sentence = sentenceIterator.next();
            if (Pattern.matches("[а-яА-ЯёЁA-Za-z]\\.", sentence) || Pattern.matches("\\s*\\.", sentence )){
                sentenceIterator.remove();
            }
        }
    }
}
