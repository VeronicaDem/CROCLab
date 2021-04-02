package Handler;

import Dictionary.Dictionaries;
import FileToProcess.ProcessedFile;
import InputFile.InputFilesLoader;
import InputFile.InputFile;
import NumberService.NumberService;
import ProcessingServices.*;
import Properties.PropertyLoader;
import Quarantine.Quarantine;
import ReplacementFile.CreatorReplacementFile;
import ReportLog.ReportLog;
import Statistic.Statistic;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Handler {

    private ArrayList<InputFile> inputFiles;
    private ArrayList<ProcessedFile> processedFiles = new ArrayList<>();
    private static Dictionaries dictionaries;
    private static PropertyLoader property;
    private Statistic statistic;

    public Handler(String propertyFile){
        property = new PropertyLoader(propertyFile);
        handle();
    }


    private void handle(){

        inputFiles = InputFilesLoader.loadInputFiles(property.getInputFilesDirectory());
        System.out.println(Calendar.getInstance().getTime().toString());
        dictionaries = new Dictionaries(property.getDictionariesDirectory());

        ReplacementFromDictionaryService.handle(dictionaries.getDictionaries(), inputFiles);

        //Обработка дней недели
        DaysOfWeekHandler.handleDaysOfWeek(inputFiles);

        //Обработка сокращений( вида - гор./м./г./...)
        AbbreviationFinder.processAbbreviations(inputFiles);

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
        //Находим все предложения содержащие английский текст
        EnglishTextFinder.findEnglishText(processedFiles);
        if (property.getEnableEnglishText()) {
            //Отправляем в карантин предложения содержащие английские буквы.
            EnglishTextRemover.handle(processedFiles);
        }

        //Отправляем акронимы в карантин
        AcronymService.handle(processedFiles);

        //Предложения содержащие CamelCase отправляются в карантин.
        CamelCaseRemover.removeCamelCase(processedFiles);
        //Удаляем пустые предложения и предложения с одной буквой.
        CleanerSentenceService.handle(processedFiles);
        //Создаём файлы статистики
        statistic = new Statistic(property.getOutDirectory(), inputFiles, processedFiles);
        //Создаём выходной файл.
        createOutputFiles();


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
        ReportLog.logCurrentOperation("Создание выходных файлов.");
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

    public static PropertyLoader getProperty(){
        return property;
    }

    public static Dictionaries getDictionaries() {
        return dictionaries;
    }
}
