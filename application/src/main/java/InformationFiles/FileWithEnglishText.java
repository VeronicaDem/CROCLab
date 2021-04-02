package InformationFiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class FileWithEnglishText {

    private String processedFileName;
    private ArrayList<String>sentencesWithEnglish = new ArrayList<>();

    public FileWithEnglishText(String processedFileName){
        this.processedFileName = processedFileName;
    }

    public void addEnglishSentences(String englishSentences){
        this.sentencesWithEnglish.add(englishSentences);
    }

    public void createFile(String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/EnglishText_" + processedFileName))){
            os.write(getJsonFormat());
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
