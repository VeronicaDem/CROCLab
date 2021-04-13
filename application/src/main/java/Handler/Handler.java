package Handler;

import Dictionary.Dictionaries;
import FileWordsToExclude.ProtectedWordsStorage;
import InputFile.InputFilesLoader;
import InputFile.InputFile;
import NumberService.NumberService;
import ProcessingServices.*;
import Properties.PropertyLoader;
import Quarantine.QuarantineCreator;
import ReplacementFile.CreatorReplacementFile;
import ReplacingWords.ReplacerSingleWords;
import ReplacingWords.ReplacerSpaceWords;
import ReportLog.ReportLog;
import Statistic.Statistic;
import WordsToDelete.WordsRemover;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;

public class Handler {

    private ArrayList<InputFile> inputFiles;
    private static Dictionaries dictionaries;
    private static PropertyLoader property;
    private Statistic statistic;
    private ProtectedWordsStorage protectedWordsStorage;

    public Handler(String propertyFile){
        property = new PropertyLoader(propertyFile);
        protectedWordsStorage = new ProtectedWordsStorage(property);
        handle();
    }


    private void handle(){

        inputFiles = InputFilesLoader.loadInputFiles(property.getInputFilesDirectory());
        System.out.println(Calendar.getInstance().getTime().toString());
        dictionaries = new Dictionaries(property.getDictionariesDirectory());
        //Удаление слов, которые пользователь добавил в файл
        WordsRemover.WordsRemover(property, inputFiles);
        //Обработка слов которые содержат пробелы(# в. ч. -> войсковая часть)
        ReplacerSpaceWords.handleWhitespaceWords(dictionaries.getDictionaryWhitespaceWords(), inputFiles);
        //Обработка слов которые не содержат пробелы(# гор. -> город)
        ReplacerSingleWords.handleSingleWords(dictionaries.getDictionarySingleWords(), inputFiles);
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
        //ДРазбиваем файлы на предложения.
        SentenceSeparator.splitOnSentences(inputFiles);
        //Раскрываем числа в текст.
        NumberService.handleNumbers(inputFiles);
        //Удаляем лишние пробелы из предложения.
        WhitespaceService.removeExtraWhitespace(inputFiles);
        //Находим все предложения содержащие английский текст
        EnglishTextFinder.findEnglishText(inputFiles);
        if (property.getEnableEnglishText()) {
            //Отправляем в карантин предложения содержащие английские буквы.
            EnglishTextRemover.removeEnglishText(inputFiles);
        }
        //Отправляем акронимы в карантин
        AcronymService.acronymsInQuarantine(inputFiles);
        //Предложения содержащие CamelCase отправляются в карантин.
        CamelCaseService.removeCamelCase(inputFiles);
        //Создаём файлы статистики
        statistic = new Statistic(property.getOutDirectory(), inputFiles);
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
        QuarantineCreator.createQuarantine(property.getOutDirectory(), inputFiles);
        CreatorReplacementFile.createReplacementFile(property.getOutDirectory(), inputFiles);
        createStatisticFiles();
    }


    public void createProcessedFiles(String outDir){
        for (InputFile inputFile : inputFiles){
            inputFile.createOutputFile(outDir, property.getOutFileSize());
        }
    }

    private void createStatisticFiles(){
        statistic = new Statistic(property.getOutDirectory(), inputFiles);
        statistic.createStatisticFiles();
    }

    public static PropertyLoader getProperty(){
        return property;
    }

    public static Dictionaries getDictionaries() {
        return dictionaries;
    }
}
