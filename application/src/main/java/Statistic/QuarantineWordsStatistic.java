package Statistic;

import InputFile.InputFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuarantineWordsStatistic {

    private  Map<String, Integer> quarantineWordsStatistic = new HashMap<>();

    public QuarantineWordsStatistic(ArrayList<InputFile> inputFiles){
        generateStatistic(inputFiles);
        sortStatistic();
    }

    private void generateStatistic(ArrayList<InputFile> inputFiles){
        for (InputFile inputFile : inputFiles){
            processFile(inputFile);
        }
    }

    private void processFile(InputFile inputFile){
        Map<String, Integer>fileWordsStatistic = inputFile.getQuarantineWordsFile().getQuarantineWords();
        for (Map.Entry<String, Integer> entries : fileWordsStatistic.entrySet()){
            String quarantineWord = entries.getKey();
            Integer countWordInFile = entries.getValue();
            if (quarantineWordsStatistic.containsKey(quarantineWord)){
                Integer generalCountWord = quarantineWordsStatistic.get(quarantineWord);
                quarantineWordsStatistic.replace(quarantineWord, countWordInFile + generalCountWord);
            }else{
                quarantineWordsStatistic.put(quarantineWord, countWordInFile);
            }
        }
    }

    private void sortStatistic(){
        LinkedHashMap<String, Integer> sortedStatistic = new LinkedHashMap<>();
        ArrayList<Map.Entry<String, Integer>>statisticList = new ArrayList<>(quarantineWordsStatistic.entrySet());
        statisticList.sort( (s1, s2) -> (s2.getValue() - s1.getValue()));
        for (Map.Entry<String, Integer> entries : statisticList){
            sortedStatistic.put(entries.getKey(), entries.getValue());
        }
        quarantineWordsStatistic = sortedStatistic;
    }

    private String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    private boolean isFileEmpty(){
        return quarantineWordsStatistic.size() == 0;
    }

    public void createFile(String filePath){
        if (!isFileEmpty()){
            try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(filePath + "/QuarantineWordsStatistic.txt"))){
                os.write(getJsonFormat());
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
