package FileToProcess;

import Quarantine.QuarantineSentencesFile;
import ReplacementFile.ReplacementFile;

import java.io.*;
import java.util.ArrayList;

public class ProcessedFile {

    private String fileName;
    private QuarantineSentencesFile quarantineFile;
    private ArrayList<String>sentences = new ArrayList<>();
    private ReplacementFile replacementFile;


    public ProcessedFile(String fileName){
        this.fileName = fileName;
        this.quarantineFile = new QuarantineSentencesFile(fileName);
        this.replacementFile = new ReplacementFile(fileName);
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
//        try(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed" + fileName), "UTF-8")){
//            for (String sentence : sentences){
//                osw.write(sentence + "\n");
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }

        int countByte = 0;
        int countSentence = 0;
        int countFiles = 1;
        OutputStreamWriter os = null;
        try{
            os = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed" + fileName + countFiles));
        }catch(IOException ex){
            ex.printStackTrace();
        }

        for (String sentence : sentences){
            byte[]sentenceBytes = sentence.getBytes();
            countByte += sentenceBytes.length;
            countSentence++;
            if (countByte >= 5242880 || countSentence >= 10000){
                countByte = 0;
                countSentence = 0;
                try{
                    os.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                countFiles++;
                try{
                    os = new OutputStreamWriter(new FileOutputStream(outputDirectory + "/Processed" + fileName +countFiles));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            try {
                os.write(sentence + "\n");
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }


    public QuarantineSentencesFile getQuarantineFile(){
        return quarantineFile;
    }

    public ReplacementFile getReplacementFile() {
        return replacementFile;
    }
}
