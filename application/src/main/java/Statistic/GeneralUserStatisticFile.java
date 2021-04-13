package Statistic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeneralUserStatisticFile {

    private String userStatisticFileName;
    private Map<String, Integer>generalUserStatistic;

    public GeneralUserStatisticFile(String userStatisticFileName, ArrayList<UserStatisticFile>userStatisticFiles){
        this.userStatisticFileName = userStatisticFileName;
        fillInStatistic(userStatisticFiles);
        sortGeneralStatistic();
        sortGeneralStatistic();
    }

    public void createGeneralStatistic(String outDir){
        if (!isFileEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/GeneralUserStatistic_" + userStatisticFileName + ".txt"))) {
                os.write(getJsonFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void fillInStatistic(ArrayList<UserStatisticFile> userStatisticFiles){
        generalUserStatistic = new LinkedHashMap<>();
        for (UserStatisticFile userStatisticFile : userStatisticFiles){
            Map<String, Integer>eachFileUserStatistic = userStatisticFile.getUserStatistic();
            for (Map.Entry<String, Integer> entries : eachFileUserStatistic.entrySet()){
                String wordForStatistic = entries.getKey();
                Integer countWord = entries.getValue();
                if (generalUserStatistic.containsKey(wordForStatistic)){
                    Integer generalCountWord = generalUserStatistic.get(wordForStatistic);
                    generalUserStatistic.replace(wordForStatistic, countWord + generalCountWord);
                }else{
                    generalUserStatistic.put(wordForStatistic, countWord);
                }
            }
        }
    }

    private void sortGeneralStatistic(){
        ArrayList<Map.Entry<String, Integer>>statisticPairs = new ArrayList<>(generalUserStatistic.entrySet());
        statisticPairs.sort((p1, p2) -> (p2.getValue() - p1.getValue()) );
        Map<String, Integer>sortedGeneralStatistic = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entries : statisticPairs){
            sortedGeneralStatistic.put(entries.getKey(), entries.getValue());
        }
        generalUserStatistic = sortedGeneralStatistic;
    }

    private String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    private boolean isFileEmpty(){
        return generalUserStatistic.size() == 0;
    }
}
