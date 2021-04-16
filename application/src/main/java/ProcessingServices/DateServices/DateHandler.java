package ProcessingServices.DateServices;

import InputFile.InputFile;

import java.util.ArrayList;

public class DateHandler {

    public static void processDate(ArrayList<InputFile> inputFiles){
        StringToData.generatePatternsQueue();
        for (InputFile inputFile : inputFiles){
            processFile(inputFile);
        }
    }

    private static void processFile(InputFile inputFile){
        String fileText = inputFile.getFileText();
        String processedText = StringToData.isPatterned(fileText);
        inputFile.setFileText(processedText);
    }
}
