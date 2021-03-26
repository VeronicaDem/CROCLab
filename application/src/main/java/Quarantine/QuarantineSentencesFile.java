package Quarantine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class QuarantineSentencesFile {

    private String processedFileName;
    private ArrayList<String>quarantineSentences = new ArrayList<>();
    private transient String fileName;



    public QuarantineSentencesFile(String processedFileName){
        this.processedFileName = processedFileName;
        this.fileName = "QuarantineSentences" + processedFileName;
    }

    public void addQuarantineSentence(String sentence){
        quarantineSentences.add(sentence);
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
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + fileName), "UTF-8")){
            String result = getJsonFormat();
            os.write(result);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }
}

class QuarantineSentence{
    private String unreadableSentence;
    private String description;

    public QuarantineSentence(String unreadableSentence, String description){
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
