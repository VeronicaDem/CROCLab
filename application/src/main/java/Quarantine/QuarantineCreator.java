package Quarantine;

import InputFile.InputFile;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class QuarantineCreator {

    private String outputDirectory;
    public static int countQuarantineSentences = 0;

    public static void createQuarantine(String outDirectory, ArrayList<InputFile> inputFiles){
        String quarantineSentencesFilesDir = outDirectory + "/QuarantineSentences";
        String failedValidationFiles = outDirectory + "/FailedValidationFile";
        try{
            Files.createDirectories(Paths.get(quarantineSentencesFilesDir));
            Files.createDirectories((Paths.get(failedValidationFiles)));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        generateQuarantineSentencesFiles(quarantineSentencesFilesDir, inputFiles);
    }


    private static void generateQuarantineSentencesFiles(String outDir, ArrayList<InputFile>inputFiles){
        ArrayList<String>quarantineSentencesFiles = new ArrayList<>();
        for (InputFile inputFile : inputFiles){
            ArrayList<String>fileQuarantineSentences = inputFile.getQuarantineFile().getQuarantineSentences();
            quarantineSentencesFiles.addAll(fileQuarantineSentences);
            countQuarantineSentences += fileQuarantineSentences.size();
        }
        if (quarantineSentencesFiles.size() != 0){
            createFile(quarantineSentencesFiles, outDir);
        }
    }

    private static void createFile(ArrayList<String>allQuarantineSentences, String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/QuarantineSentences.txt"))){
            os.write("В карантин попало " + countQuarantineSentences + " предложений\n");
            for (String sentence : allQuarantineSentences){
                os.write(sentence + "\n");
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }



}
