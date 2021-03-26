package Statistic;

import InputFile.InputFile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RowFileStatistic {

    private String processedFileName;
    private int countSentences = 0;
    private int countWords = 0;
    private LinkedHashMap<String, Integer> wordsStatistic = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> sentencesStatistic = new LinkedHashMap<>();
    private transient String fileName;
    private transient String rowText;

    public RowFileStatistic(InputFile inputFile) {
        this.fileName = "StatisticRow" + inputFile.getFileName();
        this.processedFileName = inputFile.getFileName();
        this.rowText = inputFile.getFileText();
        generateWordsStatistic();
        generateSentencesStatistic();
    }


    private void generateWordsStatistic() {
        Pattern wordsPattern = Pattern.compile("(?<=[\\s.])([A-Za-zА-Яа-я_]+?)(?=[\\s!?.$])");
        Matcher matcher = wordsPattern.matcher(rowText);
        while (matcher.find()) {
            countWords++;
            String foundWord = rowText.substring(matcher.start(), matcher.end());
            if (wordsStatistic.containsKey(foundWord)) {
                Integer countWord = wordsStatistic.get(foundWord);
                wordsStatistic.replace(foundWord, ++countWord);
            } else {
                wordsStatistic.put(foundWord, 1);
            }
        }
    }

    private void generateSentencesStatistic() {
        Pattern wordsPattern = Pattern.compile("([^.!?]+)");
        Matcher matcher = wordsPattern.matcher(rowText);
        while (matcher.find()) {
            countSentences++;
            String foundWord = rowText.substring(matcher.start(), matcher.end());
            if (sentencesStatistic.containsKey(foundWord)) {
                Integer countWord = sentencesStatistic.get(foundWord);
                sentencesStatistic.replace(foundWord, ++countWord);
            } else {
                sentencesStatistic.put(foundWord, 1);
            }
        }
    }

    public void create(String outDir){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + fileName))){
            os.write(getJsonFormat());
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }

}
