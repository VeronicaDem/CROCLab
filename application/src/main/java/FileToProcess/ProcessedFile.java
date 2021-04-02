package FileToProcess;

import InformationFiles.FileWithEnglishText;
import Properties.PropertyLoader;
import Quarantine.QuarantineSentencesFile;
import ReplacementFile.ReplacementFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ProcessedFile {

    private String fileName;
    private QuarantineSentencesFile quarantineFile;
    private ArrayList<String>sentences = new ArrayList<>();
    private ReplacementFile replacementFile;
    private FileWithEnglishText fileWithEnglishText;


    public ProcessedFile(String fileName){
        this.fileName = fileName;
        this.quarantineFile = new QuarantineSentencesFile(fileName);
        this.replacementFile = new ReplacementFile(fileName);
        this.fileWithEnglishText = new FileWithEnglishText(fileName);
    }


    public ArrayList<String> getSentences() {
        return sentences;
    }


    public String getFileName() {
        return fileName;
    }

    public void moveToQuarantine(String sentence){
        quarantineFile.addQuarantineSentence(sentence);
        sentences.remove(sentence);
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    public void removeSentence(String sentence){
        sentences.remove(sentence);
    }

    public void createOutputFile(String outputDirectory, int outputFileSize){
//        String pathForFile = outputDirectory + "/Processed_" + fileName.replace(".txt", "");
//        try{
//            Files.createDirectories(Paths.get(pathForFile));
//        }catch(IOException ex){
//            ex.printStackTrace();
//        }
        //Проверка размера выходного файла. Выходной файл делится на куски по 5мб или 10к строк(какое условие выполнится
        // быстрее)
        int countByte = 0;
        int countSentence = 0;
        int countFiles = 1;
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
    }


    public QuarantineSentencesFile getQuarantineFile(){
        return quarantineFile;
    }

    public ReplacementFile getReplacementFile() {
        return replacementFile;
    }

    public FileWithEnglishText getFileWithEnglishText() {
        return fileWithEnglishText;
    }
}
