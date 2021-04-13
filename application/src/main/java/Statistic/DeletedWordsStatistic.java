package Statistic;

import InputFile.InputFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeletedWordsStatistic {

    Map<String, Integer>deletedWordsStatistic = new LinkedHashMap<>();

    public DeletedWordsStatistic(ArrayList<InputFile>inputFiles){
        generateStatistic(inputFiles);
        sortStatistic();
    }

    private void generateStatistic(ArrayList<InputFile>inputFiles){
        for (InputFile inputFile : inputFiles){
            handleFile(inputFile);
        }
    }

    private void handleFile(InputFile inputFile){
       Map<String, Integer>deletedWords = inputFile.getDeletedWordsStorage().getDeletedWords();
        for (Map.Entry<String, Integer> entries : deletedWords.entrySet()){
            String deletedWord = entries.getKey();
            Integer countDeletedWords = entries.getValue();
            if (deletedWordsStatistic.containsKey(deletedWord)){
                Integer statisticCountDeletedWord = deletedWordsStatistic.get(deletedWord);
                deletedWordsStatistic.replace(deletedWord, countDeletedWords + statisticCountDeletedWord);
            }else{
                deletedWordsStatistic.put(deletedWord, countDeletedWords);
            }
        }
    }

    private void sortStatistic(){
        Map<String, Integer>sortedStatisticMap = new LinkedHashMap<>();
        ArrayList<Map.Entry<String, Integer>>sortedStatisticList = new ArrayList<>(deletedWordsStatistic.entrySet());
        sortedStatisticList.sort((e1, e2) -> (e2.getValue() - e1.getValue()));
        for (Map.Entry entries : sortedStatisticList){
            sortedStatisticMap.put((String)entries.getKey(), (Integer)entries.getValue());
        }
        deletedWordsStatistic = sortedStatisticMap;
    }

    public void createStatistic(String outDir){
        if (!isFileEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/DeletedWords.txt"))) {
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
        return deletedWordsStatistic.size() == 0;
    }

}
