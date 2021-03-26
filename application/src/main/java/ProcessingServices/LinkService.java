package ProcessingServices;

import InputFile.InputFile;
import java.util.ArrayList;



public class LinkService {

    private static final String linksRegex = "https?:\\/\\/\\S+[^.?!\\s]";

    public static void handle(ArrayList<InputFile> inputFiles){
        for (InputFile inputFile : inputFiles){
            removeLinks(inputFile);
        }
    }


    private static void removeLinks(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String resultText = fileText.replaceAll(linksRegex, " ");
        inputFile.setFileText(resultText);
    }


}
