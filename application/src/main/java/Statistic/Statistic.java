package Statistic;

import FileToProcess.ProcessedFile;
import InputFile.InputFile;
import Quarantine.QuarantineSentencesFile;
import ReplacementFile.ReplacementFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Statistic {

    private String outputDirectory;

    private int totalNumberSentences;
    private int numberIdenticalSentences;
    private Map<String, Integer> wordsStatistic;
    private static Map<String, Integer>totalWordsStatistic;
    private int replacementsStatistic;

    private QuarantineSentencesFile quarantineSentencesFile;
    private ArrayList<QuarantineStatisticFiles> quarantineStatisticFiles = new ArrayList<>();
    private ArrayList<ProcessedFileStatistic> processedFilesStatistic = new ArrayList<>();
    private ArrayList<RowFileStatistic> rowFileStatistic = new ArrayList<>();
    private ReplacementFile replacementFile;
    private String statisticFilesDir;




    public Statistic(String outputDirectory, ArrayList<InputFile>rowFiles, ArrayList<ProcessedFile>processedFiles,
                      ReplacementFile replacementFile){
        this.outputDirectory = outputDirectory;
        for (ProcessedFile processedFile : processedFiles){
         quarantineStatisticFiles.add(new QuarantineStatisticFiles(processedFile.getQuarantineFile()));
        }
        for (ProcessedFile processedFile : processedFiles){
            this.processedFilesStatistic.add(new ProcessedFileStatistic(processedFile));
        }
        for (InputFile inputFile : rowFiles){
            this.rowFileStatistic.add(new RowFileStatistic(inputFile));
        }
        this.replacementFile = replacementFile;
        this.statisticFilesDir = outputDirectory + "/StatisticFiles";
    }

    public void createStatisticFiles(){
        String allFilesStatisticDir = statisticFilesDir + "/AllFilesStatistic";
        String eachFileStatisticDir = statisticFilesDir + "/EachProcessedFileStatistic";
        String quarantineStatisticDir = statisticFilesDir + "/QuarantineStatistic";
        try {
            Files.createDirectories(Paths.get(statisticFilesDir));
            Files.createDirectories(Paths.get(allFilesStatisticDir));
            Files.createDirectories(Paths.get(eachFileStatisticDir));
            Files.createDirectories(Paths.get(quarantineStatisticDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        generateStatisticEachFile(eachFileStatisticDir);
        generateAllFilesStatistic(allFilesStatisticDir);
        generateQuarantineStatistic(quarantineStatisticDir);
    }

    private void generateStatisticEachFile(String dir){
        for (ProcessedFileStatistic processedFileStatistic : processedFilesStatistic){
            try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(dir + "/" + processedFileStatistic.getOutFileName()), "UTF-8")){
                os.write(processedFileStatistic.getJsonStatistic());
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private void generateAllFilesStatistic(String dir){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(dir + "/GeneralStatistic.txt"), "UTF-8")){
            for (ProcessedFileStatistic processedFileStatistic : processedFilesStatistic){
                os.write(processedFileStatistic.getJsonStatistic() + "\n\n\n");
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    private void generateQuarantineStatistic(String dir){
        for (QuarantineStatisticFiles quarantineStatisticFile : quarantineStatisticFiles){
            try(OutputStreamWriter os = new OutputStreamWriter
                    (new FileOutputStream(dir + "/" +  quarantineStatisticFile.getFileName()), "UTF-8")){
                os.write(quarantineStatisticFile.getJsonStatistic());
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

}


