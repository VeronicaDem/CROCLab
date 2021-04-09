package Statistic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserStatisticFile {

    private String processedFileName;
    private Map<String, Integer> userStatistic = new LinkedHashMap<>();
    private transient ArrayList<String>wordsForUserStatistic;
    private transient String userStatisticFileName;

    public UserStatisticFile(ArrayList<String>wordsForUserStatistic, ProcessedFileStatistic processedFileStatistic,
                             String userStatisticFileName) {
        this.userStatisticFileName = userStatisticFileName;
        this.wordsForUserStatistic = wordsForUserStatistic;
        this.processedFileName = processedFileStatistic.getProcessedFileName();
        createStatistic(processedFileStatistic);
    }

    private void createStatistic(ProcessedFileStatistic processedFileStatistic) {
        Map<String, Integer> processedWordsStatistic = processedFileStatistic.getWordsStatistic();
        for (String wordForStatistic : wordsForUserStatistic) {
            if (processedWordsStatistic.containsKey(wordForStatistic)) {
                Integer countWordInFile = processedWordsStatistic.get(wordForStatistic);
                userStatistic.put(wordForStatistic, countWordInFile);
            }
        }
        sortStatistic();
    }

    private void sortStatistic() {
        ArrayList<Map.Entry<String, Integer>> sortedStatisticEntries = new ArrayList<>(userStatistic.entrySet());
        sortedStatisticEntries.sort((w1, w2) -> (w2.getValue() - w1.getValue()));
        Map<String, Integer> sortedStatisticMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entries : sortedStatisticEntries) {
            sortedStatisticMap.put(entries.getKey(), entries.getValue());
        }
        userStatistic = sortedStatisticMap;
    }

    private String getJsonFormat() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void createFile(String outDir) {
        if (!fileIsEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + userStatisticFileName + processedFileName))) {
                os.write(getJsonFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private boolean fileIsEmpty() {
        return userStatistic.size() == 0;
    }

    public Map<String, Integer> getUserStatistic() {
        return userStatistic;
    }
}

