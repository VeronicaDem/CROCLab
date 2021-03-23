package ProcessingServices;

import FileToProcess.ProcessedFile;

import java.util.ArrayList;

public class WhitespaceService {

    public static void handle(ArrayList<ProcessedFile>handledFiles){
        for (ProcessedFile file : handledFiles){
            removeWhitespace(file);
        }
    }

    //Удаляем лишние пробелы из предложения.
    private static void removeWhitespace(ProcessedFile handledFile) {
        ArrayList<String>sentences = handledFile.getSentences();
        ArrayList<String>cleanSentences = new ArrayList<>();
        for (String sentence : sentences) {
            sentence =  sentence.trim().replaceAll("\\s+", " ").
                    replaceAll("\\s+(?=[!?,.])", "");
            cleanSentences.add(sentence);
        }
        handledFile.setSentences(cleanSentences);
    }

}
