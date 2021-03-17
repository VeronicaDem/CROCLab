import FileToProcess.ProcessedFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class CleanerSentenceService {

    public static void handle(ArrayList<ProcessedFile>processedFiles){
        for (ProcessedFile currentFile : processedFiles){
            removeEmptySentence(currentFile);
        }
    }

    private static void removeEmptySentence(ProcessedFile file){
        ArrayList<String>processedFileSentences = new ArrayList<>(file.getSentences());
        Iterator<String> sentenceIterator = processedFileSentences.iterator();
        while(sentenceIterator.hasNext()){
            String sentence = sentenceIterator.next();
            if (Pattern.matches("[а-яА-Я]\\.", sentence) || Pattern.matches("\\.", sentence )){
                sentenceIterator.remove();
            }
        }
        file.setSentences(processedFileSentences);
    }
}
