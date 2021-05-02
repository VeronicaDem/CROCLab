package Handler;

import Dictionary.Dictionaries;
import ProcessingServices.DateServices.DateHandler;
import ProcessingServices.LinksService.LinkService;
import ProtectWords.ProtectedWordsStorage;
import InputFile.InputFilesLoader;
import InputFile.InputFile;
import NumberService.NumberService;
import ProcessingServices.*;
import Properties.PropertyLoader;
import Quarantine.QuarantineCreator;
import ReplacementFile.CreatorReplacementFile;
import ReplacingWords.ReplacerSingleWords;
import ReplacingWords.ReplacerSpaceWords;
import ReportLog.*;
import Statistic.*;
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
    private ProtectedWordsStorage protectedWordsStorage;
    public static ReportLog reportLog;

    public Handler(String propertyFile) {
        property = new PropertyLoader(propertyFile);
        protectedWordsStorage = new ProtectedWordsStorage(property);
        handle();
    }


    private void handle() {
        Cleaner.deleteOldOutDirectory(property);
        inputFiles = InputFilesLoader.loadInputFiles(property.getInputFilesDirectory());
        Statistic.generateRowFilesStatistic(inputFiles, property);
        reportLog = new ReportLog(inputFiles.size());
        System.out.println(Calendar.getInstance().getTime().toString());
        dictionaries = new Dictionaries(property.getDictionariesDirectory());
        //Обработка мобильных номеров телефонов
        PhoneNumberService.handle(inputFiles);
        //Обработка дат
        DateHandler.processDate(inputFiles);
        //Обработка времени
        TimeService.handle(inputFiles);
        //Обработка денежных сумм
        MoneyService.processMoney(inputFiles);
        //Обработка дробей
        FractionService.handle(inputFiles);
        //Раскрываем числа в текст.
        NumberService.handleNumbers(inputFiles);
        //Удаляем ссылки из предложений
        LinkService.handle(inputFiles);
        //Обработка знаков препинания и спец. символов.
        PunctuationMarkService.handle(inputFiles);
        //Удаление слов, которые пользователь добавил в файл
        WordsRemover.removeWords(property, inputFiles);
        //Замена слов которые содержат пробелы(# в. ч. -> войсковая часть)
        ReplacerSpaceWords.handleWhitespaceWords(dictionaries.getDictionaryWhitespaceWords(), inputFiles);
        System.gc();
        //Замена слов которые не содержат пробелы(# гор. -> город)
        ReplacerSingleWords.handleSingleWords(dictionaries.getDictionarySingleWords(), inputFiles);
        dictionaries = null;
        System.gc();
        //Обработка дней недели
        DaysOfWeekHandler.handleDaysOfWeek(inputFiles);
        System.gc();
        //Удаление инициалов
        InitialsRemover.removeInitials(inputFiles);
        //Поиск сокращений( вида - гор./м./г./...)
        AbbreviationFinder.processAbbreviations(inputFiles);
        //Предложения содержащие CamelCase отправляются в карантин.
        CamelCaseRemover.removeCamelCase(inputFiles);
        //Обработка месяцев записанных в сокращенном виде(янв, фев, ...)
        MonthHandler.processMonths(inputFiles);
        //Разбиваем файлы на предложения.
        SentenceSeparator.splitOnSentences(inputFiles);
        System.gc();
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

    }


    //Создаёт выходной файл.
    public void createOutputFiles() {
        String processedFilesDir = property.getOutDirectory() + "/ProcessedFiles";
        reportLog.createReportFile(property);
        try {
            Files.createDirectories(Paths.get(processedFilesDir));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        QuarantineCreator.createQuarantine(property.getOutDirectory(), inputFiles);
        CreatorReplacementFile.createReplacementFile(property.getOutDirectory(), inputFiles);
        Statistic statistic = new Statistic(property, inputFiles);
        statistic.createStatisticFiles();
        for (InputFile inputFile : inputFiles) {
            Handler.reportLog.startCurrentOperation(LogOperation.CREATE_PROCESSED_FILES, inputFile.getFileName());
            inputFile.createOutputFile(processedFilesDir, property);
            Handler.reportLog.endOperation();
        }
        statistic.printQuarantineSentencesInfo();
    }

    public static PropertyLoader getProperty() {
        return property;
    }
}
