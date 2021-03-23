package Quarantine;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class QuarantineSentencesFile {

    private ArrayList<String>quarantineSentences = new ArrayList<>();
    private String fileName;
    private String processedFileName;


    public QuarantineSentencesFile(String processedFileName){
        this.processedFileName = processedFileName;
        this.fileName = "QuarantineSentences" + processedFileName;
    }

    public void addQuarantineSentence(String fileName, String sentence){
        quarantineSentences.add(fileName + " " + sentence);
    }

    public ArrayList<String> getQuarantineSentences() {
        removeUselessSentences();
        return quarantineSentences;
    }

    private void removeUselessSentences(){
        String uselessSentences = "^[А-ЯёЁA-Za-z](?=[!?.])$";
        quarantineSentences.removeIf(sentence -> Pattern.matches(uselessSentences, sentence));
    }

    //создается выходной файл с карантинными предложениями для определённого входного файла.
    public void create(String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + fileName), "UTF-8")){
            for (String quarantineSentence : quarantineSentences){
                os.write(quarantineSentence + "\n");
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }
}

class QuarantineSentence{
    private String fileName;
    private String unreadableSentence;
    private String description;

    public QuarantineSentence(String fileName, String unreadableSentence, String description){
        this.unreadableSentence = unreadableSentence;
        this.description = description;
    }

    public String getUnreadableSentence() {
        return unreadableSentence;
    }

    public String getDescription() {
        return description;
    }
}
