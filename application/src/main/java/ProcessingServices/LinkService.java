package ProcessingServices;

import FileToProcess.ProcessedFile;

import java.util.ArrayList;

public class LinkService {

    private static final String linksRegex = "https?:\\/\\/\\S+[^.?!\\s]";

    public static ArrayList<String> handle(ArrayList<String> filesTexts){
        ArrayList<String>processedFilesTexts = new ArrayList<>();
        for (String fileText : filesTexts){
            processedFilesTexts.add(removeLinks(fileText));
        }
        return processedFilesTexts;
    }


    private static String removeLinks(String fileText){
        fileText = fileText.replaceAll(linksRegex, " ");
        return fileText;
    }


}
