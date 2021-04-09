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

public class GeneralWordsStatistic {

    private int countWords = 0;
    private Map<String, Integer> generalWordsStatistic = new LinkedHashMap<>();

    public GeneralWordsStatistic(ArrayList<ProcessedFileStatistic> processedFileStatistic){
        generateGeneralWordsStatistic(processedFileStatistic);
        sortWordsStatistic();
    }

    private void generateGeneralWordsStatistic(ArrayList<ProcessedFileStatistic> processedFileStatistics){
        for (ProcessedFileStatistic processedFileStatistic : processedFileStatistics){
            countWords += processedFileStatistic.getCountWords();
            Map<String, Integer> fileWordsStatistic = processedFileStatistic.getWordsStatistic();
            for (Map.Entry entries : fileWordsStatistic.entrySet()){
                String word = (String)entries.getKey();
                Integer countWordInFile = (Integer)entries.getValue();
                if (generalWordsStatistic.containsKey(word)){
                    Integer countWordInGenStat = generalWordsStatistic.get(word);
                    generalWordsStatistic.replace(word, countWordInGenStat + countWordInFile);
                }else{
                    generalWordsStatistic.put(word, countWordInFile);
                }
            }
        }
    }

    private void sortWordsStatistic(){
        Map<String, Integer>sortedWordsStatistic = new LinkedHashMap<>();
        ArrayList<Map.Entry<String, Integer>>listWordsStatistic = new ArrayList<>(generalWordsStatistic.entrySet());
        listWordsStatistic.sort((e1, e2) -> e2.getValue() - e1.getValue());
        for (Map.Entry entries : listWordsStatistic){
            String word = (String)entries.getKey();
            Integer countWords = (Integer)entries.getValue();
            sortedWordsStatistic.put(word, countWords);
        }
        this.generalWordsStatistic = sortedWordsStatistic;
    }

    public void createFile(String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/GeneralWordsStatistic.txt"))){
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
