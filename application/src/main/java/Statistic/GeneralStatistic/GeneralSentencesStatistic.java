package Statistic.GeneralStatistic;

import Statistic.ProcessedFileStatistic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeneralSentencesStatistic {

    private int countSentences = 0;
    private Map<String, Integer>generalSentencesStatistic = new LinkedHashMap<>();

    public GeneralSentencesStatistic(ArrayList<ProcessedFileStatistic> processedFileStatistics){
        generateGeneralSentencesStatistic(processedFileStatistics);
        sortSentenceStatistic();
    }

    private void generateGeneralSentencesStatistic(ArrayList<ProcessedFileStatistic>processedFileStatistics){
        for (ProcessedFileStatistic processedFileStatistic : processedFileStatistics){
            countSentences += processedFileStatistic.getCountSentences();
            Map<String, Integer> fileWordsStatistic = processedFileStatistic.getSentencesStatistic();
            for (Map.Entry entries : fileWordsStatistic.entrySet()){
                String sentence = (String)entries.getKey();
                Integer countSentences = (Integer)entries.getValue();
                if (generalSentencesStatistic.containsKey(sentence)){
                    Integer countWordInGenStat = generalSentencesStatistic.get(sentence);
                    generalSentencesStatistic.replace(sentence, countWordInGenStat + countSentences);
                }else{
                    generalSentencesStatistic.put(sentence, countSentences);
                }
            }
        }
    }

    private void sortSentenceStatistic(){
        Map<String, Integer>sortedSentencesStatistic = new LinkedHashMap<>();
        ArrayList<Map.Entry<String, Integer>>listSentencesStatistic = new ArrayList<>(generalSentencesStatistic.entrySet());
        listSentencesStatistic.sort((e1, e2) -> e2.getValue() - e1.getValue());
        for (Map.Entry entries : listSentencesStatistic){
            String sentence = (String)entries.getKey();
            Integer countSentences = (Integer)entries.getValue();
            sortedSentencesStatistic.put(sentence, countSentences);
        }
        this.generalSentencesStatistic = sortedSentencesStatistic;
    }

    public void createFile(String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/GeneralSentencesStatistic.txt"))){
            os.write(getJsonFormat());
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public int getCountSentences(){
        return countSentences;
    }
}
