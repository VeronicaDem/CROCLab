import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(filePath)){
            properties.load(fis);
            inputFilePaths = properties.getProperty("inputFiles").split(",");
            correctInFilePaths();
            charsToDelete = properties.getProperty("characterToDelete").split(",");
            outFileSize = Integer.parseInt(properties.getProperty("outputFileSize"));
            outDirectory = properties.getProperty("outputDirectory");
            dictionaries = properties.getProperty("dictionaries").split(",");
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
