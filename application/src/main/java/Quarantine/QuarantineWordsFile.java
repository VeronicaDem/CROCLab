package Quarantine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuarantineWordsFile {

    private Map<String, Integer> quarantineWords = new HashMap<>();

    public  void addQuarantineWord(String word){
        if (quarantineWords.containsKey(word)){
            Integer countWord = quarantineWords.get(word);
            quarantineWords.replace(word, ++countWord);
        }else{
            quarantineWords.put(word, 1);
        }
    }

    public Map<String, Integer> getQuarantineWords() {
        return quarantineWords;
    }
}
