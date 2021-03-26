package Statistic;

import FileToProcess.ProcessedFile;
import InputFile.InputFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Statistic {

    private ArrayList<QuarantineStatisticFiles> quarantineStatisticFiles = new ArrayList<>();
    private ArrayList<ProcessedFileStatistic> processedFilesStatistic = new ArrayList<>();
    private ArrayList<RowFileStatistic> rowFilesStatistic = new ArrayList<>();
    private String statisticFilesDir;




    public Statistic(String outputDirectory, ArrayList<InputFile>rowFiles, ArrayList<ProcessedFile>processedFiles){
        for (ProcessedFile processedFile : processedFiles){
         quarantineStatisticFiles.add(new QuarantineStatisticFiles(processedFile.getQuarantineFile()));
        }
        for (ProcessedFile processedFile : processedFiles){
            this.processedFilesStatistic.add(new ProcessedFileStatistic(processedFile));
        }
        for (InputFile inputFile : rowFiles){
            this.rowFilesStatistic.add(new RowFileStatistic(inputFile));
        }
        this.statisticFilesDir = outputDirectory + "/StatisticFiles";
    }

    public void createStatisticFiles(){
        String generalFilesStatisticDir = statisticFilesDir + "/GeneralProcessedFilesStatistic";
        String eachFileStatisticDir = statisticFilesDir + "/EachProcessedFileStatistic";
        String rowFilesStatisticDir = statisticFilesDir + "/RowFilesStatistic";
        String quarantineStatisticDir = statisticFilesDir + "/QuarantineStatistic";
        try {
            Files.createDirectories(Paths.get(statisticFilesDir));
            Files.createDirectories(Paths.get(generalFilesStatisticDir));
            Files.createDirectories(Paths.get(eachFileStatisticDir));
            Files.createDirectories(Paths.get(quarantineStatisticDir));
            Files.createDirectories(Paths.get(rowFilesStatisticDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        generateStatisticEachFile(eachFileStatisticDir);
        GeneralProcessedFilesStatistic generalProcessedStatistic = new GeneralProcessedFilesStatistic(processedFilesStatistic);
        generalProcessedStatistic.create(generalFilesStatisticDir);
        generateQuarantineStatistic(quarantineStatisticDir);
        generateRowFilesStatistic(rowFilesStatisticDir);
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

    private void generateRowFilesStatistic(String dir){
        for (RowFileStatistic rowFileStatistic : rowFilesStatistic){
            rowFileStatistic.create(dir);
        }
    }

}


