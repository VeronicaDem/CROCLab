package Statistic;

import FileToProcess.ProcessedFile;
import Handler.Handler;
import InputFile.InputFile;
import InformationFiles.FileWithAbbreviations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Statistic {

    private ArrayList<ProcessedFile>processedFiles;
    private ArrayList<QuarantineStatisticFiles> quarantineStatisticFiles = new ArrayList<>();
    private ArrayList<ProcessedFileStatistic> processedFilesStatistic = new ArrayList<>();
    private ArrayList<RowFileStatistic> rowFilesStatistic = new ArrayList<>();
    ArrayList<FileWithAbbreviations> filesWithAbbreviations = new ArrayList<>();
    private String statisticFilesDir;




    public Statistic(String outputDirectory, ArrayList<InputFile>rowFiles, ArrayList<ProcessedFile>processedFiles){
        this.processedFiles = processedFiles;
        for (ProcessedFile processedFile : processedFiles){
         quarantineStatisticFiles.add(new QuarantineStatisticFiles(processedFile.getQuarantineFile()));
        }
        for (ProcessedFile processedFile : processedFiles){
            this.processedFilesStatistic.add(new ProcessedFileStatistic(processedFile));
        }
        for (InputFile inputFile : rowFiles){
            this.rowFilesStatistic.add(new RowFileStatistic(inputFile));
            this.filesWithAbbreviations.add(inputFile.getFileWithAbbreviations());
        }
        this.statisticFilesDir = outputDirectory + "/StatisticFiles";
    }

    public void createStatisticFiles(){
        String generalFilesStatisticDir = statisticFilesDir + "/GeneralProcessedFilesStatistic";
        String eachFileStatisticDir = statisticFilesDir + "/EachProcessedFileStatistic";
        String rowFilesStatisticDir = statisticFilesDir + "/RowFilesStatistic";
        String quarantineStatisticDir = statisticFilesDir + "/QuarantineStatistic";
        String userStatisticDir = statisticFilesDir + "/UserStatistic";
        String filesWithAbbreviationsDir = statisticFilesDir + "/FilesWithAbbreviations";
        String filesWithEnglishTextDir = statisticFilesDir + "/FilesWithEnglish";
        try {
            Files.createDirectories(Paths.get(statisticFilesDir));
            Files.createDirectories(Paths.get(generalFilesStatisticDir));
            Files.createDirectories(Paths.get(eachFileStatisticDir));
            Files.createDirectories(Paths.get(quarantineStatisticDir));
            Files.createDirectories(Paths.get(rowFilesStatisticDir));
            Files.createDirectories(Paths.get(userStatisticDir));
            Files.createDirectories(Paths.get(filesWithAbbreviationsDir));
            Files.createDirectories(Paths.get(filesWithEnglishTextDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        generateStatisticEachFile(eachFileStatisticDir);
        GeneralProcessedFilesStatistic generalProcessedStatistic = new GeneralProcessedFilesStatistic(processedFilesStatistic);
        generalProcessedStatistic.create(generalFilesStatisticDir);
        generateQuarantineStatistic(quarantineStatisticDir);
        generateRowFilesStatistic(rowFilesStatisticDir);
        generateUserStatistic(userStatisticDir);
        generateFilesWithAbbreviations(filesWithAbbreviationsDir);
        generateFilesWithEnglish(filesWithEnglishTextDir);
    }


    private void generateFilesWithAbbreviations(String outDir){
        for(FileWithAbbreviations fileWithAbbreviations : filesWithAbbreviations){
            fileWithAbbreviations.createFile(outDir);
        }
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

    private void generateUserStatistic(String outDir){
        String[] userFilesForStatistic = Handler.getProperty().getFilesForStatisticPaths();
        for (String filePath : userFilesForStatistic) {
            UserStatisticFile userStatisticFile = new UserStatisticFile(filePath, processedFilesStatistic);
            userStatisticFile.generateStatistic(outDir);
        }
    }

    private void generateFilesWithEnglish(String outDir){

        for (ProcessedFile processedFile : processedFiles){
            processedFile.getFileWithEnglishText().createFile(outDir);
        }
    }



}


