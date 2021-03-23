package FileToProcess;

import Quarantine.QuarantineSentencesFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class ProcessedFile {

    private String fileName;
    private QuarantineSentencesFile quarantineFile;
    private ArrayList<String>sentences = new ArrayList<>();


    public ProcessedFile(String fileName){
        this.fileName = fileName;
        this.quarantineFile = new QuarantineSentencesFile(fileName);
    }


    public ArrayList<String> getSentences() {
        return sentences;
    }


    public String getFileName() {
        return fileName;
    }

    public void moveToQuarantine(String sentence){
        quarantineFile.addQuarantineSentence(fileName, sentence);
        sentences.remove(sentence);
    }

    public void makeReplacement(String unreadableWord, String replacement, String descriptionReplacement){

    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    public void removeSentence(String sentence){
        sentences.remove(sentence);
    }

    public void createOutputFile(String outputDirectory, int outputFileSize){
        try(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed" + fileName), "UTF-8")){
            for (String sentence : sentences){
                osw.write(sentence + "\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void createQuarantineFile(String outputDirectory){
        quarantineFile.create(outputDirectory);
    }

    public QuarantineSentencesFile getQuarantineFile(){
        return quarantineFile;
    }

}


class FileStatistic{

    private int totalNumberSentences;
    private int numberIdenticalSentences;
    private Map<String, Integer>wordsStatistic;
    private static Map<String, Integer>totalWordsStatistic;
    private int replacementsStatistic;
    private int quarantineSentencesStatistic;


}