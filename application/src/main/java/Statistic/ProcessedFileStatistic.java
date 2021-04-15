package Statistic;

import InputFile.InputFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;


public class ProcessedFileStatistic {

    private transient String outFileName;

    private String processedFileName;
    private int countWords = 0;
    private int countSentences = 0;
    private  Map<String, Integer> wordsStatistic = new LinkedHashMap<>();
    private  Map <String, Integer> sentencesStatistic = new LinkedHashMap<>();



    public ProcessedFileStatistic(InputFile inputFile){
        this.outFileName = "StatisticProcessed" + inputFile.getFileName();
        this.processedFileName = inputFile.getFileName();
        generateWordsStatistic(inputFile);
        generateSentencesStatistic(inputFile);
        sortMap();
    }


    private void generateWordsStatistic(InputFile inputFile){
        ArrayList<String>sentences = inputFile.getSentences();
        for (String sentence : sentences){
            String[]words = sentence.split(" ");
                countWords += words.length;
                words[words.length-1] = words[words.length-1].replaceAll("[.!?,]", "");
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

    public Map<String, Integer> getSentencesStatistic() {
        return sentencesStatistic;
    }

    private void generateSentencesStatistic(InputFile inputFile){
        ArrayList<String>sentences = inputFile.getSentences();
        countSentences = sentences.size();
        for (String sentence : sentences){
//            String foundSentence = sentence.replaceAll("[.!?]", "");
            String foundSentence = sentence;
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
        Map<String, Integer>sortedWordsMap = new LinkedHashMap<>();
       sortedWStatistic.sort((o1, o2) -> o2.getValue() - o1.getValue());
       for (Map.Entry entry : sortedWStatistic){
           String key = (String)entry.getKey();
           Integer value = (Integer)entry.getValue();
           sortedWordsMap.put(key, value);
       }
       wordsStatistic = sortedWordsMap;

        List<Map.Entry<String, Integer>> sortedSStatistic = new ArrayList(sentencesStatistic.entrySet());
       Map<String, Integer>sortedSentencesMap = new LinkedHashMap<>();
        sortedSStatistic.sort((o1, o2) -> o2.getValue() - o1.getValue());
        for (Map.Entry entry : sortedSStatistic){
            String key = (String)entry.getKey();
            Integer value = (Integer)entry.getValue();
            sortedSentencesMap.put(key, value);
        }
        sentencesStatistic = sortedSentencesMap;

    }

    public int getCountWords() {
        return countWords;
    }

    public int getCountSentences() {
        return countSentences;
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

}
