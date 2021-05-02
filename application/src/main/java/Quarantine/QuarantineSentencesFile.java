package Quarantine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
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
        return quarantineSentences;
    }

    public String getFileName() {
        return fileName;
    }



    private boolean fileIsEmpty(){
        return quarantineSentences.size() == 0;
    }
}


