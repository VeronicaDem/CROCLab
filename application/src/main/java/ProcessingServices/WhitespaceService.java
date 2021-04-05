package ProcessingServices;

import InputFile.InputFile;
import java.util.ArrayList;

public class WhitespaceService {

    public static void removeExtraWhitespace(ArrayList<InputFile>inputFiles){
        for (InputFile file : inputFiles){
            handleFile(file);
        }
    }

    //Удаляем лишние пробелы из предложения.
    private static void handleFile(InputFile inputFile) {
        ArrayList<String>sentences = inputFile.getSentences();
        ArrayList<String>cleanSentences = new ArrayList<>();
        for (String sentence : sentences) {
            sentence =  sentence.trim().replaceAll("\\s+", " ").
                    replaceAll("\\s+(?=[!?,.])", "");
            cleanSentences.add(sentence);
        }
        inputFile.setSentences(cleanSentences);
    }

}
