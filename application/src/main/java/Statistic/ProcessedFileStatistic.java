package Statistic;

import FileToProcess.ProcessedFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;


public class ProcessedFileStatistic {

    private transient String outFileName;

    private String processedFileName;
    private int countWords = 0;
    private int countSentences = 0;
    private transient Map<String, Integer> wordsStatistic = new LinkedHashMap<>();
    private transient Map <String, Integer> sentencesStatistic = new LinkedHashMap<>();
    private ArrayList<String>sortedWordsStatistic = new ArrayList<>();
    private ArrayList<String>sortedSentencesStatistic = new ArrayList<>();


    public ProcessedFileStatistic(ProcessedFile processedFile){
        this.outFileName = "StatisticProcessed" + processedFile.getFileName();
        this.processedFileName = processedFile.getFileName();
        generateWordsStatistic(processedFile);
        generateSentencesStatistic(processedFile);
        sortMap();
    }


    private void generateWordsStatistic(ProcessedFile processedFile){
        ArrayList<String>sentences = processedFile.getSentences();
        for (String sentence : sentences){
            String[]words = sentence.split(" ");
                countWords += words.length;
                words[words.length-1] = words[words.length-1].replaceAll("[.!?]", "");
                for (String word : words) {
                if (wordsStatistic.containsKey(word)){
                    int countWord = wordsStatistic.get(word);
                    wordsStatistic.put(word, ++countWord);
                }else{
                    wordsStatistic.put(word, 1);
                }
            }
        }
    }

    private void generateSentencesStatistic(ProcessedFile processedFile){
        ArrayList<String>sentences = processedFile.getSentences();
        countSentences = sentences.size();
        for (String sentence : sentences){
            String foundSentence = sentence.replaceAll("[.!?]", "");
            if (sentencesStatistic.containsKey(foundSentence)){
                int countSentence = sentencesStatistic.get(foundSentence);
                sentencesStatistic.replace(foundSentence, ++countSentence);
            }else{
                sentencesStatistic.put(foundSentence, 1);
            }
        }
    }

    public String getJsonStatistic(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String statistic = gson.toJson(this);
        return statistic;
    }

    private void sortMap(){
        List<Map.Entry<String, Integer>> sortedWStatistic = new ArrayList(wordsStatistic.entrySet());
       sortedWStatistic.sort((o1, o2) -> o2.getValue() - o1.getValue());
       for (Map.Entry entry : sortedWStatistic){
           String key = (String)entry.getKey();
           Integer value = (Integer)entry.getValue();
           sortedWordsStatistic.add(key + "-" + value.toString());
       }

        List<Map.Entry<String, Integer>> sortedSStatistic = new ArrayList(sentencesStatistic.entrySet());
        sortedSStatistic.sort((o1, o2) -> o2.getValue() - o1.getValue());
        for (Map.Entry entry : sortedSStatistic){
            String key = (String)entry.getKey();
            Integer value = (Integer)entry.getValue();
            sortedSentencesStatistic.add(key + "-" + value.toString());
        }

    }

    public String getOutFileName(){
        return  outFileName;
    }

    public String getProcessedFileName(){
        return processedFileName;
    }

    public Map<String, Integer> getWordsStatistic() {
        return wordsStatistic;
    }


    class StatisticComparator implements Comparator<Map.Entry<String, Integer>>{

        private Map.Entry<String, Integer>statisticMap;

        public StatisticComparator(Map.Entry<String, Integer>statisticMap){
            this.statisticMap = statisticMap;
        }

        @Override
        public int compare(Map.Entry<String, Integer>s1, Map.Entry<String, Integer> s2){
            return s1.getValue() - s2.getValue();
        }
    }




}
