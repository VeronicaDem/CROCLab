package InputFile;

import java.io.File;
import java.util.ArrayList;

public class InputFilesLoader {

    private static ArrayList<InputFile> inputFiles = new ArrayList<>();


    public static ArrayList<InputFile> loadInputFiles(String inputFilesDirectoryPath){
        File inputFilesDirectory = new File(inputFilesDirectoryPath);
        for(File inputFile : inputFilesDirectory.listFiles()){
            if (inputFile.isDirectory()){
                loadInputFiles(inputFile.getPath());
            }else{
                inputFiles.add(new InputFile(inputFile.getPath()));
            }
        }
        return inputFiles;
    }

}
