package Statistic;

import InputFile.InputFile;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowFileStatistic {

        private String rowText;
        private int countSentences = 0;
        private int countWords = 0;
        private LinkedHashMap<String, Integer> wordsStatistic = new LinkedHashMap<>();
        private LinkedHashMap <String, Integer> sentencesStatistic = new LinkedHashMap<>();

        public RowFileStatistic(InputFile inputFile){
            this.rowText = inputFile.getFileData();
            generateWordsStatistic();
            generateSentencesStatistic();
        }


        private void generateWordsStatistic(){
            Pattern wordsPattern = Pattern.compile("\\b\\w+?\\b");
            Matcher matcher = wordsPattern.matcher(rowText);
            while(matcher.find()){
                countWords++;
                String foundWord = rowText.substring(matcher.start(), matcher.end());
                if (wordsStatistic.containsKey(foundWord)){
                    Integer countWord = wordsStatistic.get(foundWord);
                    wordsStatistic.replace(foundWord, ++countWord);
                }else{
                    wordsStatistic.put(foundWord, 1);
                }
            }
        }

        private void generateSentencesStatistic(){
            Pattern wordsPattern = Pattern.compile("[!?;.].*?[!?;.]");
            Matcher matcher = wordsPattern.matcher(rowText);
            while(matcher.find()){
                countSentences++;
                String foundWord = rowText.substring(matcher.start(), matcher.end());
                if (sentencesStatistic.containsKey(foundWord)){
                    Integer countWord = sentencesStatistic.get(foundWord);
                    sentencesStatistic.replace(foundWord, ++countWord);
                }else{
                    sentencesStatistic.put(foundWord, 1);
                }
            }
        }

        public int getCountSentences() {
            return countSentences;
        }

        public int getCountWords() {
            return countWords;
        }

        public LinkedHashMap<String, Integer> getWordsStatistic() {
            return wordsStatistic;
        }

        public LinkedHashMap<String, Integer> getSentencesStatistic() {
            return sentencesStatistic;
        }

}
