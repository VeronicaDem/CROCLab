package ProcessingServices;

import java.util.ArrayList;

public class PunctuationMarkService {

    public static ArrayList<String> handle(ArrayList<String>filesTexts){
        ArrayList<String>correctPunctuationTexts = new ArrayList<>();
        for (String text : filesTexts){
            correctPunctuationTexts.add(handlePunctMarks(text));
        }
        return correctPunctuationTexts;
    }

    private static String handlePunctMarks(String fileText){
        fileText = fileText.replaceAll("\\.{2,}", "\\.").replaceAll("%", "процент")
                .replaceAll("\\+", " плюс ").replaceAll(";", "\\.")
                .replaceAll("<(/?[^<>]*)>", " ").replaceAll(":", "")
                .replaceAll("[\\W&&[^а-яА-ЯёЁ,?.!\\s]]", "");
        return fileText;
    }

}
