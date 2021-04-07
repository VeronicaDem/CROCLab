package InformationFiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileWithAbbreviations {


    private String inputFileName;
    private ArrayList<String>sentencesWithAbbreviation = new ArrayList<>();

    public FileWithAbbreviations(String inputFileName){
        this.inputFileName = inputFileName;
    }

    public void addAbbreviationSentences(String sentence){
        sentencesWithAbbreviation.add(sentence);
    }

    public void createFile(String outDir){
        if (!fileIsEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/AbbreviationSentences" + inputFileName))) {
                os.write(getJsonFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    private boolean fileIsEmpty(){
        return sentencesWithAbbreviation.size() == 0;
    }

}
