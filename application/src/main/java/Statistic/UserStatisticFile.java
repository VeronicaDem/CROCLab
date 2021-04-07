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

    private String filePath;
    private ArrayList<String> wordsForUserStatistic;
    private String userStatisticFileName;
    private ArrayList<ProcessedFileStatistic>processedFileStatistics;


    public UserStatisticFile(String filePath, ArrayList<ProcessedFileStatistic> processedFileStatistics){
        this.filePath = filePath;
        readFile();
        this.userStatisticFileName = createFileName();
        this.processedFileStatistics = processedFileStatistics;
    }

    private String createFileName(){
        return filePath.substring(filePath.lastIndexOf("/"), filePath.lastIndexOf("."));
    }

    private void readFile(){
        ArrayList<String>fileData = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "windows-1251") )){
            String readString;
            while((readString = br.readLine()) != null){
                fileData.add(readString.trim());
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        wordsForUserStatistic = fileData;
    }


    public void generateStatistic(String outDir){
        String fileDir = outDir + "/" + userStatisticFileName;
        try {
            Files.createDirectories(Paths.get(fileDir));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        for (ProcessedFileStatistic processedFileStatistic : processedFileStatistics){
            ProcessedFileUserStatistic processedFileUserStatistic = new ProcessedFileUserStatistic(processedFileStatistic);
            processedFileUserStatistic.createFile(fileDir);
        }
    }




    class ProcessedFileUserStatistic{

        private String processedFileName;
        private Map<String, Integer>userStatistic = new LinkedHashMap<>();

        public ProcessedFileUserStatistic(ProcessedFileStatistic processedFileStatistic){
            this.processedFileName = processedFileStatistic.getProcessedFileName();
            createStatistic(processedFileStatistic);
        }

        private void createStatistic(ProcessedFileStatistic processedFileStatistic){
            Map<String, Integer>processedWordsStatistic = processedFileStatistic.getWordsStatistic();
            for (String wordForStatistic : wordsForUserStatistic){
                if (processedWordsStatistic.containsKey(wordForStatistic)){
                    Integer countWordInFile = processedWordsStatistic.get(wordForStatistic);
                    userStatistic.put(wordForStatistic, countWordInFile);
                }
            }
            sortStatistic();
        }

        private void sortStatistic(){
            ArrayList<Map.Entry<String, Integer>>sortedStatisticEntries = new ArrayList<>(userStatistic.entrySet());
            sortedStatisticEntries.sort((w1, w2) -> (w2.getValue() - w1.getValue()) );
            Map<String, Integer>sortedStatisticMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entries : sortedStatisticEntries){
                sortedStatisticMap.put(entries.getKey(), entries.getValue());
            }
            userStatistic = sortedStatisticMap;
        }

        private String getJsonFormat(){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(this);
        }

        public void createFile(String outDir){
            if (!fileIsEmpty()) {
                try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + userStatisticFileName + processedFileName))) {
                    os.write(getJsonFormat());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

        private boolean fileIsEmpty(){
            return userStatistic.size() == 0;
        }
    }
}
