import java.util.ArrayList;
import java.util.regex.Pattern;

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
                            .replaceAll(";", "\\.").replaceAll(":", "");
        return fileText;
    }

}
