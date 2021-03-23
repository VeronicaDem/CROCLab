package Statistic;

import FileToProcess.ProcessedFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class ProcessedFileStatistic {

    private transient String outFileName;
    private String processedFileName;
    private int countWords = 0;
    private int countSentences = 0;
    private LinkedHashMap<String, Integer> wordsStatistic = new LinkedHashMap<>();
    private LinkedHashMap <String, Integer> sentencesStatistic = new LinkedHashMap<>();


    public ProcessedFileStatistic(ProcessedFile processedFile){
        this.outFileName = "Statistic" + processedFile.getFileName();
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
            if (sentencesStatistic.containsKey(sentence)){
                int countSentence = sentencesStatistic.get(sentence);
                sentencesStatistic.replace(sentence, ++countSentence);
            }else{
                sentencesStatistic.put(sentence, 1);
            }
        }
    }

    public String getJsonStatistic(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String statistic = gson.toJson(this);
        return statistic;
    }

    public String getOutFileName(){
        return  outFileName;
    }

    public String getProcessedFileName(){
        return processedFileName;
    }

}
