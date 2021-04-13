package InputFile;

import Handler.Handler;
import InformationFiles.FileWithAbbreviations;
import InformationFiles.FileWithEnglishText;
import Properties.PropertyLoader;
import Quarantine.QuarantineSentencesFile;
import ReplacementFile.ReplacementFile;
import ReportLog.LogOperation;
import WordsToDelete.DeletedWordsStorage;
import org.apache.any23.encoding.TikaEncodingDetector;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class InputFile {

    private boolean validEncoding;
    private String filePath;
    private String fileName;
    private String fileText;
    private ReplacementFile replacementFile;
    private FileWithAbbreviations fileWithAbbreviations;
    private QuarantineSentencesFile quarantineFile;
    private FileWithEnglishText fileWithEnglishText;
    private ArrayList<String> sentences = new ArrayList<>();
    private DeletedWordsStorage deletedWordsStorage;


    public InputFile(String filePath) {
        this.filePath = filePath;
        this.fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
//        validEncoding = checkEncoding();
//        if (!validEncoding) {
//            this.filePath = EncodingService.changeEncoding(filePath);
//        }
        readFile();
        this.replacementFile = new ReplacementFile(fileName);
        this.fileWithAbbreviations = new FileWithAbbreviations(fileName);
        this.quarantineFile = new QuarantineSentencesFile(fileName);
        this.fileWithEnglishText = new FileWithEnglishText(fileName);
        this.deletedWordsStorage = new DeletedWordsStorage(fileName);
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    //Считывает данные из файла в одну строку.
    private void readFile() {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String readData = "";
            while ((readData = br.readLine()) != null) {
                fileData.append(readData.trim()).append(" . ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileText = fileData.toString();
    }

    public void createOutputFile(String outputDirectory, PropertyLoader property){
        Handler.reportLog.startCurrentOperation(LogOperation.CREATE_PROCESSED_FILES, fileName);
        int countByte = 0;
        int countSentence = 0;
        int countFiles = 1;
        int outputFileSize = property.getOutFileSize();
        OutputStreamWriter os = null;
        try{
            os = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed_" +
                    fileName.replace(".txt", "_" + countFiles + ".txt")));
        }catch(IOException ex){
            ex.printStackTrace();
        }

        for (String sentence : sentences){
            byte[]sentenceBytes = sentence.getBytes();
            countByte += sentenceBytes.length;
            countSentence++;
            if (countByte >= (outputFileSize * 1024 * 1024) || countSentence >= 100000){
                countByte = 0;
                countSentence = 0;
                try{
                    os.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                countFiles++;
                try{
                    os = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed_" +
                            fileName.replace(".txt", "_" + countFiles + ".txt")));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            try {
                os.write(sentence.toLowerCase() + "\n");
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        try{
            os.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        Handler.reportLog.endOperation();
    }


    //Проверка кодировки файла. Возвращает true, если кодировка файла UTF-8.
    private boolean checkEncoding() {
        String fileEncoding = "";
        try (InputStream is = new FileInputStream(filePath);) {
            fileEncoding = Charset.forName(new TikaEncodingDetector().guessEncoding(is)).toString();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return fileEncoding.equals("UTF-8");
    }

    public DeletedWordsStorage getDeletedWordsStorage() {
        return deletedWordsStorage;
    }

    public void moveToQuarantine(String sentence){
        quarantineFile.addQuarantineSentence(sentence);
        sentences.remove(sentence);
    }

    public String getFileName() {
        return fileName;
    }

    public ReplacementFile getReplacementFile() {
        return replacementFile;
    }

    public FileWithAbbreviations getFileWithAbbreviations() {
        return fileWithAbbreviations;
    }

    public QuarantineSentencesFile getQuarantineFile() {
        return quarantineFile;
    }

    public FileWithEnglishText getFileWithEnglishText() {
        return fileWithEnglishText;
    }

    public ArrayList<String> getSentences() {
        return sentences;
    }
    public void setSentences(ArrayList<String>sentences){
        this.sentences = sentences;
    }
}
