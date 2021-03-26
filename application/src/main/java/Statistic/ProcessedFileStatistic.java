package Statistic;

import FileToProcess.ProcessedFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;


public class ProcessedFileStatistic {

    private transient String outFileName;
    private String processedFileName;
    private int countWords = 0;
    private int countSentences = 0;
    private Map<String, Integer> wordsStatistic = new LinkedHashMap<>();
    private Map <String, Integer> sentencesStatistic = new LinkedHashMap<>();


    public ProcessedFileStatistic(ProcessedFile processedFile){
        this.outFileName = "StatisticProcessed" + processedFile.getFileName();
        this.processedFileName = processedFile.getFileName();
        generateWordsStatistic(processedFile);
        generateSentencesStatistic(processedFile);
    }


    private void generateWordsStatistic(ProcessedFile processedFile){
        ArrayList<String>sentences = processedFile.getSentences();
        for (String sentence : sentences){
            String[]words = sentence.split(" ");
                countWords += words.length;
                words[words.length-1] = words[words.length-1].replaceAll("\\.", "");
                for (String word : words) {
                if (wordsStatistic.containsKey(word)){
                    int countWord = wordsStatistic.get(word);
                    wordsStatistic.replace(word, ++countWord);
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
        sortMap();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String statistic = gson.toJson(this);
        return statistic;
    }

    private void sortMap(){
        StatisticComparator wordComparator = new StatisticComparator(wordsStatistic);
        StatisticComparator sentenceComparator = new StatisticComparator(sentencesStatistic);
        TreeMap<String, Integer>sortedWordsStatistic = new TreeMap<>(wordComparator);
        TreeMap<String, Integer>sortedSentencesStatistic = new TreeMap<>(sentenceComparator);
        sortedSentencesStatistic.putAll(sentencesStatistic);
        sortedWordsStatistic.putAll(wordsStatistic);
        sentencesStatistic = sortedSentencesStatistic;
        wordsStatistic = sortedWordsStatistic;
    }

    public String getOutFileName(){
        return  outFileName;
    }

    public String getProcessedFileName(){
        return processedFileName;
    }



    class StatisticComparator implements Comparator<String>{

        private Map<String, Integer>statisticMap;

        public StatisticComparator(Map<String, Integer>statisticMap){
            this.statisticMap = statisticMap;
        }

        @Override
        public int compare(String a, String b){
            return statisticMap.get(b) - statisticMap.get(a);
        }
    }

}
