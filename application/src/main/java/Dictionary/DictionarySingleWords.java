package Dictionary;


import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DictionarySingleWords {

    private Map<String, String[]> unreadableWordReplacements = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public DictionarySingleWords(ArrayList<WordReplacements>wordReplacements){
        convertUnreadableWords(wordReplacements);
    }

    private void convertUnreadableWords(ArrayList<WordReplacements>wordReplacements){
        for (WordReplacements wordReplacement : wordReplacements){
            unreadableWordReplacements.put(wordReplacement.getWord(), wordReplacement.getReplacements());
        }
    }

    public String getSingleWordReplacement(String unreadableWord){
        String replacement[] = unreadableWordReplacements.get(unreadableWord);
        if (replacement != null){
            return replacement[0];
        }else {
            return null;
        }
    }


}
