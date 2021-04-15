package Dictionary;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DictionaryWhitespaceWords {

    private Map<String, UnreadableWordHandler> wordReplacement = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private String fileName;

    public DictionaryWhitespaceWords(ArrayList<WordReplacements>wordReplacements){
        convertWordReplacements(wordReplacements);
    }

    private void convertWordReplacements(ArrayList<WordReplacements>wordReplacements){
        for (WordReplacements wordFromDictionary : wordReplacements){
            String unreadableWord = wordFromDictionary.getWord();
            String[]replacements = wordFromDictionary.getReplacements();
            wordReplacement.put(unreadableWord, new UnreadableWordHandler(unreadableWord, replacements));
        }
    }



    public String getReplacements(String unreadableWord){
        UnreadableWordHandler unreadableWordHandler = wordReplacement.get(unreadableWord);
        if (unreadableWordHandler != null){
            return unreadableWordHandler.getReplacement();
        }
        return null;
    }

    public Map<String, UnreadableWordHandler> getWordReplacement() {
        return wordReplacement;
    }

    public UnreadableWordHandler getUnreadableWordHandler(String unreadableWord){
        return wordReplacement.get(unreadableWord);
    }

    public String getFileName() {
        return fileName;
    }
}
