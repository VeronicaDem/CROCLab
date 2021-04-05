package ProcessingServices;

import InputFile.InputFile;
import org.apache.xmlbeans.impl.regex.Match;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AcronymService {

    public static void acronymsInQuarantine(ArrayList<InputFile> inputFiles){
        for (InputFile inputFile : inputFiles){
            handleAcronyms(inputFile);
        }
    }

    private static void handleAcronyms(InputFile fileWithAcronym){
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
