package Handler;

import Dictionary.Dictionary;
import FileToProcess.ProcessedFile;
import InputFile.InputFile;
import NumberService.NumberService;
import ProcessingServices.*;
import Properties.PropertyLoader;
import Quarantine.Quarantine;
import ReplacementFile.CreatorReplacementFile;
import Statistic.Statistic;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class Handler {

    private String cleanFilePath;
    private String quarantineFilePath;
    private String statisticsFilePath;
    private String defaultDictionaryPath;
    private ArrayList<InputFile> inputFiles;
    private ArrayList<ProcessedFile> processedFiles = new ArrayList<>();
    private static Dictionary dictionary = new Dictionary("Files/DefaultDictionary.txt");
    private PropertyLoader property;
    private Statistic statistic;

    public Handler(String propertyFile){
        property = new PropertyLoader(propertyFile);
        handle(property.getInputFilePaths(), property.getCharsToDelete(), property.getOutDirectory(), property.getOutFileSize());
    }


    private void handle(String[] inputFilesPaths, String [] charsToDelete, String outDirectory, Integer outFileSize){
        initializeFilesPaths(outDirectory);
        inputFiles = readInputFiles(inputFilesPaths);

        //Обрабатываем аббревиатуры.
        AbbreviationService.handle(inputFiles);
        //Удаляем ссылки из предложений
        LinkService.handle(inputFiles);
        //Обработка мобильных номеров телефонов
        PhoneNumberService.handle(inputFiles);
        //Обработка знаков препинания и спец. символов.
        PunctuationMarkService.handle(inputFiles);
        //Для каждого объекта inputFile создаётся объект processedFile. В processedFile хранится текст разбитый на
        //предложения. Далее вся обработка будет происхожить со списком объектов processedFile.
        processedFiles = SentenceSeparator.getSentences(inputFiles);
        //Раскрываем числа в текст.
        NumberService.handle(processedFiles);
        //Удаляем лишние пробелы из предложения.
        removeWhitespace();
        //Отправляем в карантин предложения содержащие английские буквы.
        EnglishTextService.handle(processedFiles);
        //Отправляем акронимы в карантин
        AcronymService.handle(processedFiles);
        //Удаляем пустые предложения и предложения с одной буквой.
        CleanerSentenceService.handle(processedFiles);
        //Создаём файлы статистики
        statistic = new Statistic(outDirectory, inputFiles, processedFiles);
        //Создаём выходной файл.
        createOutputFiles();


    }



    private void initializeFilesPaths(String outFilesDirectory){
        cleanFilePath = outFilesDirectory + "/HandledFile.txt";
        quarantineFilePath = outFilesDirectory + "/QuarantineFile.txt";
        statisticsFilePath = outFilesDirectory + "/StatisticFile.txt";
        defaultDictionaryPath = "DefaultDictionary.txt";
    }

    //Принимает массив из путей к файлам. Возвращает список с входными файлами.
    private ArrayList<InputFile> readInputFiles(String ... filePaths){
        ArrayList<InputFile>inputFiles = new ArrayList<>();
        for (String filePath : filePaths) {
            InputFile inputFile = new InputFile(filePath);
            inputFiles.add(inputFile);
        }
        return inputFiles;
    }


    //Принимает список входных файлов. Возвращает список текстов прочитанных из файлов.
    private ArrayList<String>getFilesTexts(ArrayList<InputFile>inputFiles){
        ArrayList<String>filesTexts = new ArrayList<>();
        for(InputFile inputFile : inputFiles){
            filesTexts.add(inputFile.getFileText());
        }
        return filesTexts;
    }



    //Удаляем лишние пробелы из предложения.
    private void removeWhitespace(){
        WhitespaceService.handle(processedFiles);
    }


    //Создаёт выходной файл.
    public void createOutputFiles(){
        String processedFilesDir = property.getOutDirectory() + "/ProcessedFiles";
        try{
            Files.createDirectories(Paths.get(processedFilesDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        createProcessedFiles(processedFilesDir);
        Quarantine quarantine = new Quarantine(property.getOutDirectory(), processedFiles);
        quarantine.createQuarantine();
        CreatorReplacementFile.createReplacementFile(property.getOutDirectory(), inputFiles);
        createStatisticFiles();
    }


    public void createProcessedFiles(String outDir){
        for (ProcessedFile currentFile : processedFiles){
            currentFile.createOutputFile(outDir, property.getOutFileSize());
        }
    }

    private void createStatisticFiles(){
        statistic = new Statistic(property.getOutDirectory(), inputFiles, processedFiles);
        statistic.createStatisticFiles();
    }



    public static Dictionary getDictionary() {
        return dictionary;
    }
}
