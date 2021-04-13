package Statistic;

import Quarantine.QuarantineSentencesFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class GeneralQuarantineSentencesStatistic {

    private transient Map<QuarantineSentenceStatistic, Integer> generalSentencesStatistic = new LinkedHashMap<>();
    private List<QuarantineSentenceStatistic> quarantineSentences = new LinkedList();

    public GeneralQuarantineSentencesStatistic(ArrayList<QuarantineStatisticFile>quarantineStatisticFiles){
            generateStatistic(quarantineStatisticFiles);
    }

    private void generateStatistic(ArrayList<QuarantineStatisticFile> quarantineStatisticFiles){
        for (QuarantineStatisticFile quarantineSentencesFile : quarantineStatisticFiles){
            handleFile(quarantineSentencesFile);
            sortStatistic();
        }
    }

    private void handleFile(QuarantineStatisticFile quarantineStatisticFile){
        Map<String, Integer>quarantineFileStatistic = quarantineStatisticFile.getQuarantineSentencesStatistic();
        for (Map.Entry<String, Integer> entries : quarantineFileStatistic.entrySet()){
            String quarantineSentences = entries.getKey();
            Integer countQuarantineSentences = entries.getValue();
            QuarantineSentenceStatistic quarantineSentenceStatistic = new QuarantineSentenceStatistic(quarantineStatisticFile.getFileName(),quarantineSentences);
            if (generalSentencesStatistic.containsKey(quarantineSentenceStatistic)){
                Integer generalCountSentences = generalSentencesStatistic.get(quarantineSentences) + countQuarantineSentences;
                quarantineSentenceStatistic.setCountSentences(generalCountSentences);
                generalSentencesStatistic.replace(quarantineSentenceStatistic, generalCountSentences);
            }else{
                quarantineSentenceStatistic.setCountSentences(countQuarantineSentences);
                generalSentencesStatistic.put(quarantineSentenceStatistic, countQuarantineSentences);
            }
        }
    }

    private void sortStatistic(){
        ArrayList<Map.Entry<QuarantineSentenceStatistic, Integer>>statisticList
                = new ArrayList(generalSentencesStatistic.entrySet());
        statisticList.sort((e1, e2) -> (e2.getKey().getCountSentences() - e1.getKey().getCountSentences()) );
       LinkedList<QuarantineSentenceStatistic>sortedStatisticList = new LinkedList<>();
        for (Map.Entry<QuarantineSentenceStatistic, Integer> entries : statisticList){
            sortedStatisticList.add(entries.getKey());
        }
        quarantineSentences = sortedStatisticList;
    }

    public void createFile(String outDir){
        if (!isFileEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/QuarantineSentencesStatistic.txt"))) {
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

    private boolean isFileEmpty(){
        return quarantineSentences.size() == 0;
    }
}


class QuarantineSentenceStatistic{

    private String fileName;
    private String unreadableSentence;
    private Integer countSentences;


    public QuarantineSentenceStatistic(String fileName, String unreadableSentence){
        this.fileName = fileName;
        this.unreadableSentence = unreadableSentence;
    }

    public String getUnreadableSentence() {
        return unreadableSentence;
    }

    public void setCountSentences(Integer countSentences){
        this.countSentences = countSentences;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + fileName.hashCode();
        result = 31 * result + unreadableSentence.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        QuarantineSentenceStatistic objQuarSentStat = (QuarantineSentenceStatistic)obj;
        return fileName.equals(objQuarSentStat.fileName) && unreadableSentence.equals(objQuarSentStat.unreadableSentence);
    }

    public Integer getCountSentences() {
        return countSentences;
    }
}
