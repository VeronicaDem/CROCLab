package Statistic;

import Handler.Handler;
import InputFile.InputFile;
import InformationFiles.FileWithAbbreviations;
import Properties.PropertyLoader;
import Statistic.GeneralStatistic.GeneralProcessedFilesStatistic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Statistic {

    private ArrayList<InputFile>inputFiles;
    private ArrayList<QuarantineStatisticFile> quarantineStatisticFiles = new ArrayList<>();
    private ArrayList<ProcessedFileStatistic> processedFilesStatistic = new ArrayList<>();
    private ArrayList<RowFileStatistic> rowFilesStatistic = new ArrayList<>();
    ArrayList<FileWithAbbreviations> filesWithAbbreviations = new ArrayList<>();
    private String statisticFilesDir;




    public Statistic(PropertyLoader property, ArrayList<InputFile>rowFiles){
        String outputDirectory = property.getOutDirectory();
        this.inputFiles = rowFiles;
        for (InputFile inputFile : rowFiles){
            this.filesWithAbbreviations.add(inputFile.getFileWithAbbreviations());
            this.quarantineStatisticFiles.add(new QuarantineStatisticFile(inputFile.getQuarantineFile()));
            this.processedFilesStatistic.add(new ProcessedFileStatistic(inputFile));
        }
        this.statisticFilesDir = outputDirectory + "/StatisticFiles";
    }

    public void createStatisticFiles(){
        String generalFilesStatisticDir = statisticFilesDir + "/GeneralProcessedFilesStatistic";
        String eachFileStatisticDir = statisticFilesDir + "/EachProcessedFileStatistic";
        String quarantineStatisticDir = statisticFilesDir + "/QuarantineStatistic";
        String userStatisticDir = statisticFilesDir + "/UserStatistic";
        String filesWithAbbreviationsDir = statisticFilesDir + "/FilesWithAbbreviations";
        String filesWithEnglishTextDir = statisticFilesDir + "/FilesWithEnglish";
        String deletedWordsStatisticDir = statisticFilesDir + "/DeletedWords";
        try {
            Files.createDirectories(Paths.get(statisticFilesDir));
            Files.createDirectories(Paths.get(generalFilesStatisticDir));
            Files.createDirectories(Paths.get(eachFileStatisticDir));
            Files.createDirectories(Paths.get(quarantineStatisticDir));
            Files.createDirectories(Paths.get(userStatisticDir));
            Files.createDirectories(Paths.get(filesWithAbbreviationsDir));
            Files.createDirectories(Paths.get(filesWithEnglishTextDir));
            Files.createDirectories(Paths.get(deletedWordsStatisticDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        generateStatisticEachFile(eachFileStatisticDir);
        //Создание общей статистики по обработанным файлам
        GeneralProcessedFilesStatistic generalProcessedFilesStatistic = new GeneralProcessedFilesStatistic(processedFilesStatistic);
        generalProcessedFilesStatistic.createGeneralStatisticFile(generalFilesStatisticDir);
        //Создание статистики по карантину
        generateQuarantineStatistic(quarantineStatisticDir);
        generateUserStatistic(userStatisticDir);
        generateFilesWithAbbreviations(filesWithAbbreviationsDir);
        generateFilesWithEnglish(filesWithEnglishTextDir);
        generateDeletedWordsStatistic(deletedWordsStatisticDir);
        generateQuarantineWordsStatistic(quarantineStatisticDir);
    }


    private void generateFilesWithAbbreviations(String outDir){
        for(FileWithAbbreviations fileWithAbbreviations : filesWithAbbreviations){
            fileWithAbbreviations.createFile(outDir);
        }
    }

    private void generateQuarantineWordsStatistic(String outDir){
        new QuarantineWordsStatistic(inputFiles).createFile(outDir);
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
        GeneralQuarantineSentencesStatistic generalQuarantineSentencesStatistic =
                new GeneralQuarantineSentencesStatistic(quarantineStatisticFiles);
        generalQuarantineSentencesStatistic.createFile(dir);
    }

    public static void generateRowFilesStatistic(ArrayList<InputFile>inputFiles, PropertyLoader property){
        String statisticFilesDir = property.getOutDirectory() + "/StatisticFiles";
        ArrayList<RowFileStatistic>rowFilesStatistic = new ArrayList<>();
        for (InputFile inputFile : inputFiles){
            rowFilesStatistic.add(new RowFileStatistic(inputFile));
        }
        String rowFilesStatisticDir = statisticFilesDir + "/RowFilesStatistic";
        try{
            Files.createDirectories(Paths.get(rowFilesStatisticDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        for (RowFileStatistic rowFileStatistic : rowFilesStatistic){
            rowFileStatistic.create(rowFilesStatisticDir);
        }
    }

    private void generateUserStatistic(String outDir){
        String[] userFilesForStatistic = Handler.getProperty().getFilesForStatisticPaths();
        for (String filePath : userFilesForStatistic) {
            UserStatisticCreator userStatistic = new UserStatisticCreator(filePath, processedFilesStatistic);
            userStatistic.createStatistic(outDir);
        }
    }

    private void generateFilesWithEnglish(String outDir){
        for (InputFile inputFile : inputFiles){
            inputFile.getFileWithEnglishText().createFile(outDir);
        }
    }

    private void generateDeletedWordsStatistic(String outputDirectory){
        new DeletedWordsStatistic(inputFiles).createStatistic(outputDirectory);
    }

    public void printQuarantineSentencesInfo(){
        int countQuarantineSentences = 0;
        int countProcessedSentences = 0;
        for (QuarantineStatisticFile quarantineStatisticFile : quarantineStatisticFiles){
            countQuarantineSentences += quarantineStatisticFile.getCountQuarantineSentences();
        }
        for (ProcessedFileStatistic processedFileStatistic : processedFilesStatistic){
            countProcessedSentences += processedFileStatistic.getCountSentences();
        }
        System.out.println("В карантин попало " + countQuarantineSentences + " из "
                + (countProcessedSentences + countQuarantineSentences) + " обработанных");
    }

}


