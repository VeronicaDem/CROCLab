package Statistic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserStatisticFile {

    private String filePath;
    private ArrayList<String> wordsForUserStatistic;
    private String userStatisticFileName;
    private ArrayList<ProcessedFileUserStatistic>processedFileUserStatistics = new ArrayList<>();
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

    public String getUserStatisticFileName() {
        return userStatisticFileName;
    }




    class ProcessedFileUserStatistic{

        private String processedFileName;
        private Map<String, Integer>userStatistic;

        public ProcessedFileUserStatistic(ProcessedFileStatistic processedFileStatistic){
            this.processedFileName = processedFileStatistic.getProcessedFileName();
            this.userStatistic = createStatistic(processedFileStatistic);
        }

        private Map<String, Integer>createStatistic(ProcessedFileStatistic processedFileStatistic){
            Map<String, Integer>processedWordsStatistic = processedFileStatistic.getWordsStatistic();
            Map<String, Integer>userStatistic = new HashMap<>();
            for (String wordForStatistic : wordsForUserStatistic){
                if (processedWordsStatistic.containsKey(wordForStatistic)){
                    Integer countWordInFile = processedWordsStatistic.get(wordForStatistic);
                    userStatistic.put(wordForStatistic, countWordInFile);
                }
            }
            return userStatistic;
        }

        private String getJsonFormat(){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(this);
        }

        public void createFile(String outDir){
            try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + userStatisticFileName + processedFileName))){
                os.write(getJsonFormat());
            }catch(IOException ex){
                ex.printStackTrace();
            }

        }
    }
}
