package Statistic;

import Quarantine.QuarantineSentencesFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuarantineStatisticFiles {

    private String processedFileName;
    private transient String fileName;
    private transient QuarantineSentencesFile quarantineSentencesFile;
    private Map<String, Integer>quarantineSentencesStatistic = new LinkedHashMap<>();

    public QuarantineStatisticFiles(QuarantineSentencesFile quarantineSentencesFile){
        this.processedFileName = quarantineSentencesFile.getFileName();
        this.quarantineSentencesFile = quarantineSentencesFile;
        generateQuarantineSentencesStatistic();
        fileName = "Statistic" + quarantineSentencesFile.getFileName();
    }

    private void generateQuarantineSentencesStatistic(){
        ArrayList<String>quarantineSentences = quarantineSentencesFile.getQuarantineSentences();
        for (String quarantineSentence : quarantineSentences){
            if (quarantineSentencesStatistic.containsKey(quarantineSentence)){
                int countQuarantineSentence = quarantineSentencesStatistic.get(quarantineSentence);
                quarantineSentencesStatistic.replace(quarantineSentence, ++countQuarantineSentence);
            }else{
                quarantineSentencesStatistic.put(quarantineSentence, 1);
            }
        }
        sortStatistic();
    }

    private void sortStatistic(){
        ArrayList<Map.Entry<String, Integer>>quarantineSentencesPairs
                = new ArrayList<>(quarantineSentencesStatistic.entrySet());
        quarantineSentencesPairs.sort((s1, s2) -> (s2.getValue() - s1.getValue()) );
        Map<String, Integer>sortedStatisticMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entries : quarantineSentencesPairs){
            sortedStatisticMap.put(entries.getKey(), entries.getValue());
        }
        quarantineSentencesStatistic = sortedStatisticMap;
    }

    public void createFile(String outDir){
        if (!fileIsEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter
                    (new FileOutputStream(outDir + "/" + fileName), "UTF-8")) {
                os.write(getJsonFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getFileName(){
        return fileName;
    }

    public String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }

    private boolean fileIsEmpty(){
        return quarantineSentencesStatistic.size() == 0;
    }
}
