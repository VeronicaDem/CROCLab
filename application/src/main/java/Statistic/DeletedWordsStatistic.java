package Statistic;

import InputFile.InputFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeletedWordsStatistic {

    Map<String, Integer>deletedWordsStatistic = new LinkedHashMap<>();

    public DeletedWordsStatistic(ArrayList<InputFile>inputFiles){

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


}
