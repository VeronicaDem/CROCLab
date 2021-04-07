package ProcessingServices;

import InputFile.InputFile;
import java.util.ArrayList;



public class LinkService {

    private static final String[] linksRegex = {"(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*",
    "([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}"};

    public static void handle(ArrayList<InputFile> inputFiles){
        for (InputFile inputFile : inputFiles){
            removeLinks(inputFile);
        }
    }


    private static void removeLinks(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String cleanText = fileText;
        cleanText = cleanText.replaceAll("e mail", "email")
                .replaceAll("e-mail", "email").replaceAll("E-mail", "email");
        for (String linkRegexp : linksRegex) {
            cleanText = cleanText.replaceAll(linkRegexp, " ");
        }
        inputFile.setFileText(cleanText);
    }


}
