package Properties;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PropertyLoader {
    String inputFilesDirectory = null;
    Integer outFileSize = null;
    String outDirectory = null;
    String dictionariesDirectory = null;
    String[] filesForStatisticPaths = null;
    String protectedWordsDir = null;
    String wordsToDeleteDir = null;
    boolean enableEnglishText = false;

    public PropertyLoader(String filePath){
        load(filePath);
    }

    private void load(String filePath){

        try(Scanner in = new Scanner(new File(filePath))){

            String fileContent = "";

            while(in.hasNext()) {
                fileContent += in.nextLine() + "\r\n";
            }

            Gson gson = new Gson();
            PropertyData propertyData = gson.fromJson(fileContent , PropertyData.class);

            inputFilesDirectory = propertyData.getInputFilesDirectory();
            outFileSize = propertyData.getOutputFileSize();
            outDirectory = propertyData.getOutputDirectory();
            dictionariesDirectory = propertyData.getDictionariesDirectory();
            enableEnglishText = propertyData.getEnableEnglishText().toLowerCase().equals("true");
            filesForStatisticPaths = propertyData.getFilesForStatisticPaths();
            protectedWordsDir = propertyData.getProtectedWordsDir();
            wordsToDeleteDir = propertyData.getWordsToDeleteDir();
            correctStatisticFilesPaths();

        }catch(IOException e){
            e.printStackTrace();
        }
    }


    private void correctStatisticFilesPaths(){
        for (int i = 0; i < filesForStatisticPaths.length; i++){
            filesForStatisticPaths[i] = filesForStatisticPaths[i].trim();
        }
    }


    public String getInputFilesDirectory() {
        return inputFilesDirectory;
    }
    public Integer getOutFileSize() {
        return outFileSize;
    }
    public String getOutDirectory() {
        return outDirectory;
    }

    public String getDictionariesDirectory() {
        return dictionariesDirectory;
    }

    public String[] getFilesForStatisticPaths() {
        return filesForStatisticPaths;
    }

    public boolean getEnableEnglishText() {
        return enableEnglishText;
    }

    public String getWordsToDeleteDir() {
        return wordsToDeleteDir;
    }

    public String getProtectedWordsDir() {
        return protectedWordsDir;
    }
}
