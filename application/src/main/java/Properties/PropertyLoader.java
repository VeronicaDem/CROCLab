package Properties;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PropertyLoader {
    String[] inputFilePaths = null;
    String[] charsToDelete = null;
    Integer outFileSize = null;
    String outDirectory = null;
    String[] dictionaries = null;

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

            inputFilePaths = propertyData.getInputFiles();
            correctInFilePaths();
            charsToDelete = propertyData.getCharacterToDelete();
            outFileSize = propertyData.getOutputFileSize();
            outDirectory = propertyData.getOutputDirectory();
            dictionaries = propertyData.getDictionaries();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void correctInFilePaths(){
        for (int i = 0; i < inputFilePaths.length; i++){
            inputFilePaths[i] = inputFilePaths[i].trim();
        }
    }


    public String[] getInputFilePaths() {
        return inputFilePaths;
    }
    public String[] getCharsToDelete() {
        return charsToDelete;
    }
    public Integer getOutFileSize() {
        return outFileSize;
    }
    public String getOutDirectory() {
        return outDirectory;
    }

}
