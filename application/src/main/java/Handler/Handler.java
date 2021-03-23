package Handler;

import Dictionary.Dictionary;
import FileToProcess.ProcessedFile;
import InputFile.InputFile;
import NumberService.NumberService;
import ProcessingServices.*;
import Properties.PropertyLoader;
import ReplacementFile.ReplacementFile;
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
    public static ReplacementFile replacementFile;

    public Handler(String propertyFile){
        property = new PropertyLoader(propertyFile);
        replacementFile = new ReplacementFile();
        handle(property.getInputFilePaths(), property.getCharsToDelete(), property.getOutDirectory(), property.getOutFileSize());
    }


    private void handle(String[] inputFilesPaths, String [] charsToDelete, String outDirectory, Integer outFileSize){
        initializeFilesPaths(outDirectory);
        inputFiles = readInputFiles(inputFilesPaths);

        //Считываем каждый файл в отдельную строку.
        ArrayList<String>filesTexts = getFilesTexts(inputFiles);
        //Обрабатываем аббревиатуры.
        ArrayList<String>removeAbbreviationsTexts = replaceAbbreviation(filesTexts);
        //Удаляем ссылки из предложений
        ArrayList<String>removeLinksTexts = LinkService.handle(removeAbbreviationsTexts);
        //Обработка мобильных номеров телефонов
        ArrayList<String>processedPhoneNumbers = PhoneNumberService.handle(removeLinksTexts);
        //Обработка знаков препинания и спец. символов.
        ArrayList<String>correctPunctuationTexts = PunctuationMarkService.handle(processedPhoneNumbers);
        //Разбиваем текст на предложения
        separateFilesOnSentences(correctPunctuationTexts);
        //Раскрываем числа в текст.
        handleNumbers();
        //Удаляем лишние пробелы из предложения.
        removeWhitespace();
        //Отправляем в карантин предложения содержащие английские буквы.
        EnglishTextService.handle(processedFiles);
        //Отправляем акронимы в карантин
        AcronymService.handle(processedFiles);
        //Удаляем пустые предложения и предложения с одной буквой.
        CleanerSentenceService.handle(processedFiles);
        //Создаём фалы статистики
        statistic = new Statistic(outDirectory, inputFiles, processedFiles, replacementFile);
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
            InputFile inFile = new InputFile(filePath);
            inputFiles.add(inFile);
            processedFiles.add(new ProcessedFile(inFile.getFileName()));
        }
        return inputFiles;
    }

    private ArrayList<String>replaceAbbreviation(ArrayList<String>filesTexts){
        ArrayList<String>cleanTexts = new ArrayList<>();
        for (String fileText : filesTexts){
            //получаем текст, в котором заменены все сокращения, для которых была найдена замена в словаре
            //сокращений.
           String handledAbbreviation = AbbreviationService.handle(fileText);
           cleanTexts.add(handledAbbreviation);
        }
        return cleanTexts;
    }

    //Принимает список входных файлов. Возвращает список текстов прочитанных из файлов.
    private ArrayList<String>getFilesTexts(ArrayList<InputFile>inputFiles){
        ArrayList<String>filesTexts = new ArrayList<>();
        for(InputFile inputFile : inputFiles){
            filesTexts.add(inputFile.getFileData());
        }
        return filesTexts;
    }

    //Принимает список файлов записанных в строку. Заполняет список обрабатываемых файлов актуальными данными.
    private void separateFilesOnSentences(ArrayList<String>files){
        Iterator<ProcessedFile> iterator = processedFiles.iterator();
        for (String file : files){
            //передаём в метод файл записанный в строку, получаем файл разбитый на список предложений.
            ArrayList<String>fileSentences = SentenceSeparator.getSentences(file);
            ProcessedFile processedFile = iterator.next();
            processedFile.setSentences(fileSentences);
        }
    }

    //Раскрывает числа в текст.
    private void handleNumbers(){
        NumberService.handle(processedFiles);
    }


    //Удаляем лишние пробелы из предложения.
    private void removeWhitespace(){
        WhitespaceService.handle(processedFiles);
    }


    //Создаёт выходной файл.
    public void createOutputFiles(){
        String processedFilesDir = property.getOutDirectory() + "/ProcessedFiles";
        String quarantineFilesDir = property.getOutDirectory() + "/QuarantineFiles";
        try{
            Files.createDirectories(Paths.get(processedFilesDir));
            Files.createDirectories(Paths.get(quarantineFilesDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        createProcessedFiles(processedFilesDir);
        createQuarantineFiles(quarantineFilesDir);
        statistic.createStatisticFiles();
    }

    public void createQuarantineFiles(String outDir){
        for (ProcessedFile processedFile : processedFiles){
            processedFile.createQuarantineFile(outDir);
        }
    }

    public void createProcessedFiles(String outDir){
        for (ProcessedFile currentFile : processedFiles){
            currentFile.createOutputFile(outDir, property.getOutFileSize());
        }
    }

    public static Dictionary getDictionary() {
        return dictionary;
    }
}
