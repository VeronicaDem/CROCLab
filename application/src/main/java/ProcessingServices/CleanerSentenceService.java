package ProcessingServices;

import FileToProcess.ProcessedFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class CleanerSentenceService {

    public static void handle(ArrayList<ProcessedFile>processedFiles){
        for (ProcessedFile currentFile : processedFiles){
            removeEmptySentence(currentFile.getSentences());
        }
    }

    private static void removeEmptySentence(ArrayList<String>fileSentences){
//        ArrayList<String>processedFileSentences = new ArrayList<>(sentences);
        Iterator<String> sentenceIterator = fileSentences.iterator();
        while(sentenceIterator.hasNext()){
            String sentence = sentenceIterator.next();
            if (Pattern.matches("[а-яА-ЯёЁA-Za-z]\\.", sentence) || Pattern.matches("\\s*\\.", sentence )){
                sentenceIterator.remove();
            }
        }
    }
}
