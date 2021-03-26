package Quarantine;

import FileToProcess.ProcessedFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Quarantine {

    private String outputDirectory;
    private ArrayList<ProcessedFile>processedFiles;

    public Quarantine(String outDirectory, ArrayList<ProcessedFile> processedFile){
        this.outputDirectory = outDirectory + "/Quarantine";
        this.processedFiles = processedFile;
    }

    public void createQuarantine(){
        String quarantineSentencesFilesDir = outputDirectory + "/QuarantineSentences";
        String failedValidationFiles = outputDirectory + "/FailedValidationFile";
        try{
            Files.createDirectories(Paths.get(quarantineSentencesFilesDir));
            Files.createDirectories((Paths.get(failedValidationFiles)));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        createQuarantineSentencesFiles(quarantineSentencesFilesDir);
    }

    private void createQuarantineSentencesFiles(String outDir){
        for (ProcessedFile processedFile : processedFiles){
            processedFile.getQuarantineFile().create(outDir);
        }
    }

}
