package ReplacementFile;

import InputFile.InputFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CreatorReplacementFile {

    public static void createReplacementFile(String outDir, ArrayList<InputFile> inputFiles) {
        String currentDir = outDir + "/ReplacementFiles";
        try{
            Files.createDirectories(Paths.get(currentDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        for (InputFile inputFile : inputFiles) {
            ReplacementFile replacementFile = inputFile.getReplacementFile();
            replacementFile.createFile(currentDir);
        }
    }
}
