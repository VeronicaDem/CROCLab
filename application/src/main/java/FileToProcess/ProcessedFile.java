package FileToProcess;

import Quarantine.QuarantineFile;

import java.io.*;
import java.util.ArrayList;

public class ProcessedFile {

    private String fileName;
    private static QuarantineFile quarantineFile = new QuarantineFile();
    private ArrayList<String>sentences = new ArrayList<>();


    public ProcessedFile(String fileName){
        this.fileName = fileName;
    }


    public ArrayList<String> getSentences() {
        return sentences;
    }

    public void moveToQuarantine(String sentence){
        quarantineFile.addQuarantineSentence(fileName, sentence);
        sentences.remove(sentence);
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    public QuarantineFile getQuarantineFile() {
        return quarantineFile;
    }

    public void removeSentence(String sentence){
        sentences.remove(sentence);
    }

    public void createOutputFile(String outputDirectory){
        try(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed" + fileName))){
            for (String sentence : sentences){
                osw.write(sentence + "\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void createQuarantineFile(String outputDirectory){
        try(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/QuarantineFile.txt"))){
            ArrayList<String>quarantineSentences = quarantineFile.getQuarantineSentences();
            for (String quarantineSentence : quarantineSentences){
                osw.write(quarantineSentence + "\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
