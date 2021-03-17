import FileToProcess.ProcessedFile;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LinkService {

    static String[] regexpsForLinks = new String[]{"([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})"
            ,"(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?"};

    public static void handle(ArrayList<ProcessedFile>handledFiles){
        for (ProcessedFile file : handledFiles){
            removeLinks(file);
        }
    }

    public static ArrayList<String> handleText(ArrayList<String> filesTexts){
        ArrayList<String>cleanTexts = new ArrayList<>();
        for(String text : filesTexts) {
            for (String regex : regexpsForLinks) {
                text = text.replaceAll(regex, "");
            }
            cleanTexts.add(text);
        }
        return cleanTexts;
    }

    private static void removeLinks(ProcessedFile handledFile){
        ArrayList<String>sentences = handledFile.getSentences();
        ArrayList<String>cleanSentences = new ArrayList<>();
        for (String sentence : sentences){
            for (String regex : regexpsForLinks){
                sentence = sentence.replaceAll(regex, "");
            }
            cleanSentences.add(sentence);
        }
        handledFile.setSentences(cleanSentences);
    }

    private static void handleLinks(ProcessedFile file){
        ArrayList<String>sentences = new ArrayList<>(file.getSentences());
        for (String sentence : sentences){
            for (String regex : regexpsForLinks){
                if (Pattern.matches(regex, sentence)){
                    file.moveToQuarantine(sentence);
                    break;
                }
            }
        }
    }

}
