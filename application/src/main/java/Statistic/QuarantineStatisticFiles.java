package Statistic;

import Quarantine.QuarantineSentencesFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class QuarantineStatisticFiles {

    private String processedFileName;
    private transient String fileName;
    private LinkedHashMap<String, Integer>quarantineSentencesStatistic = new LinkedHashMap<>();

    public QuarantineStatisticFiles(QuarantineSentencesFile quarantineSentencesFile){
        this.processedFileName = quarantineSentencesFile.getFileName();
        generateQuarantineSentencesStatistic(quarantineSentencesFile);
        fileName = "Statistic" + quarantineSentencesFile.getFileName();
    }

    private void generateQuarantineSentencesStatistic(QuarantineSentencesFile quarantineSentencesFile){
        ArrayList<String>quarantineSentences = quarantineSentencesFile.getQuarantineSentences();
        for (String quarantineSentence : quarantineSentences){
            if (quarantineSentencesStatistic.containsKey(quarantineSentence)){
                int countQuarantineSentence = quarantineSentencesStatistic.get(quarantineSentence);
                quarantineSentencesStatistic.replace(quarantineSentence, ++countQuarantineSentence);
            }else{
                quarantineSentencesStatistic.put(quarantineSentence, 1);
            }
        }
    }

    public String getFileName(){
        return fileName;
    }

    public String getJsonStatistic(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }
}
