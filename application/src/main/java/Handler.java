import FileToProcess.ProcessedFile;
import NumberService.NumberService;


import java.util.ArrayList;
import java.util.Iterator;

public class Handler {

    private String cleanFilePath;
    private String quarantineFilePath;
    private String statisticsFilePath;
    private String defaultDictionaryPath;
    private ArrayList<InputFile> inputFiles;
    private ArrayList<ProcessedFile> processedFiles = new ArrayList<>();
    public static Dictionary dictionary = new Dictionary("Files/DefaultDictionary.txt");
    PropertyLoader pl;


    public Handler(String propertyFile){
         pl = new PropertyLoader(propertyFile);
        handle(pl.getInputFilePaths(), pl.getCharsToDelete(), pl.getOutDirectory(), pl.getOutFileSize());
        System.out.println();
    }


    private void handle(String[] inputFilesPaths, String [] charsToDelete, String outDirectory, Integer outFileSize){
        initializeFilesPaths(outDirectory);
        inputFiles = readInputFiles(inputFilesPaths);

        //Считываем каждый файл в отдельную строку.
        ArrayList<String>filesTexts = getFilesTexts(inputFiles);
        //Обрабатываем аббревиатуры.
        ArrayList<String>removeAbbreviations = replaceAbbreviation(filesTexts);
        //Удаляем ссылки из предложений
        ArrayList<String>removeLinks = handleLinks(removeAbbreviations);
        //Обработка знаков препинания и спец. символов.
        ArrayList<String>correctPunctuationTexts = PunctuationMarkService.handle(removeLinks);
        //Разбиваем текст на предложения
        separateFilesOnSentences(correctPunctuationTexts);
        //Раскрываем числа в текст.
        handleNumbers();
        //Удаляем лишние пробелы из предложения.
        removeWhitespace();
        //Удаляем пустые предложения и предложения с одной буквой.
        CleanerSentenceService.handle(processedFiles);
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
        Iterator iterator = processedFiles.iterator();
        for (String file : files){
            //передаём в метод файл записанный в строку, получаем файл разбитый на список предложений.
            ArrayList<String>fileSentences = SentenceSeparator.getSentences(file);
            ProcessedFile handledFile = (ProcessedFile)iterator.next();
            handledFile.setSentences(fileSentences);
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

    //Обработка строк. Предложения, содержащие строки помещаются в карантин.
    private ArrayList<String> handleLinks(ArrayList<String>filesTexts){
        return LinkService.handleText(filesTexts);
    }

    //Создаёт выходной файл.
    public void createOutputFiles(){
        createProcessedFiles();
        createQuarantineFile();
    }

    public void createQuarantineFile(){
        ProcessedFile.createQuarantineFile(pl.getOutDirectory());
    }

    public void createProcessedFiles(){
        for (ProcessedFile currentFile : processedFiles){
            currentFile.createOutputFile(pl.getOutDirectory());
        }
    }

}
