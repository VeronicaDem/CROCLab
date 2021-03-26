package Statistic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class GeneralProcessedFilesStatistic {

    private ArrayList<ProcessedFileStatistic>processedFiles;

    public GeneralProcessedFilesStatistic(ArrayList<ProcessedFileStatistic>processedFiles){
        this.processedFiles = processedFiles;
    }

    public void create(String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/GeneralStatistic.txt"), "UTF-8")){
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
