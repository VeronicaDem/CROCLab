package ProcessingServices;

import FileToProcess.ProcessedFile;
import org.apache.xmlbeans.impl.regex.Match;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AcronymService {

    public static void handle(ArrayList<ProcessedFile> processedFiles){
        for (ProcessedFile currentFile : processedFiles){
            handleAcronyms(currentFile);
        }
    }

    private static void handleAcronyms(ProcessedFile fileWithAcronym){
        ArrayList<String>fileSentences = new ArrayList<>(fileWithAcronym.getSentences());
        for (String sentence : fileSentences){
            Pattern acronymPattern = Pattern.compile("([а-я]+)(\\s[А-ЯёЁ]{2,5}\\s)([а-я]+)");
            Matcher matcher = acronymPattern.matcher(sentence);
            if (matcher.find()){
                fileWithAcronym.moveToQuarantine(sentence);
            }
        }

    }
}
