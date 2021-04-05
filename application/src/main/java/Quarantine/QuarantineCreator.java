package Quarantine;

import InputFile.InputFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class QuarantineCreator {

    private String outputDirectory;

    public static void createQuarantine(String outDirectory, ArrayList<InputFile> inputFiles){
        String quarantineSentencesFilesDir = outDirectory + "/QuarantineSentences";
        String failedValidationFiles = outDirectory + "/FailedValidationFile";
        try{
            Files.createDirectories(Paths.get(quarantineSentencesFilesDir));
            Files.createDirectories((Paths.get(failedValidationFiles)));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        createQuarantineSentencesFiles(quarantineSentencesFilesDir, inputFiles);
    }


    private static void createQuarantineSentencesFiles(String outDir, ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            inputFile.getQuarantineFile().create(outDir);
        }
    }

}
