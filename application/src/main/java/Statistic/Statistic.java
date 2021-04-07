package Statistic;

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

    private ArrayList<InputFile>inputFiles;
    private ArrayList<QuarantineStatisticFiles> quarantineStatisticFiles = new ArrayList<>();
    private ArrayList<ProcessedFileStatistic> processedFilesStatistic = new ArrayList<>();
    private ArrayList<RowFileStatistic> rowFilesStatistic = new ArrayList<>();
    ArrayList<FileWithAbbreviations> filesWithAbbreviations = new ArrayList<>();
    private String statisticFilesDir;




    public Statistic(String outputDirectory, ArrayList<InputFile>rowFiles){
        this.inputFiles = rowFiles;
        for (InputFile inputFile : rowFiles){
            this.rowFilesStatistic.add(new RowFileStatistic(inputFile));
            this.filesWithAbbreviations.add(inputFile.getFileWithAbbreviations());
            this.quarantineStatisticFiles.add(new QuarantineStatisticFiles(inputFile.getQuarantineFile()));
            this.processedFilesStatistic.add(new ProcessedFileStatistic(inputFile));
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
        //Создание общей статистики по обработанным файлам
        GeneralProcessedFilesStatistic generalProcessedStatistic = new GeneralProcessedFilesStatistic(processedFilesStatistic);
        generalProcessedStatistic.createGeneralStatisticFile(generalFilesStatisticDir);
        //Создание статистики по карантину
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
            quarantineStatisticFile.createFile(dir);
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
        for (InputFile inputFile : inputFiles){
            inputFile.getFileWithEnglishText().createFile(outDir);
        }
    }



}


