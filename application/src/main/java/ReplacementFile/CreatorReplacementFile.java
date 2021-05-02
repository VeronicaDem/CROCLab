package ReplacementFile;

import Handler.Handler;
import InputFile.InputFile;
import ReportLog.LogOperation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CreatorReplacementFile {

    public static void createReplacementFile(String outDir, ArrayList<InputFile> inputFiles) {
        Handler.reportLog.startModule();
        String currentDir = outDir + "/ReplacementFiles";
        try{
            Files.createDirectories(Paths.get(currentDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        for (InputFile inputFile : inputFiles) {
            Handler.reportLog.startCurrentOperation(LogOperation.CREATE_REPLACEMENT_FILE, inputFile.getFileName());
            ReplacementFile replacementFile = inputFile.getReplacementFile();
            replacementFile.createFile(currentDir);
            Handler.reportLog.endOperation();
        }
        Handler.reportLog.endModule("File with replacements module");
    }
}
