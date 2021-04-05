package Dictionary;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DictionaryWhitespaceWords {

    private LinkedHashMap<String, UnreadableWordHandler> wordReplacement = new LinkedHashMap<>();

    private String fileName;

    public DictionaryWhitespaceWords(String filePath){
        this.fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        loadDictionary(filePath);
    }



    public void loadDictionary(String filePath) {
        String readLine;
        StringBuilder dictionaryData = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while((readLine = br.readLine()) != null){
                dictionaryData.append(readLine);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        Gson gson = new Gson();
        JsonDictionary dictionary = gson.fromJson(dictionaryData.toString(), JsonDictionary.class);
        ArrayList<WordReplacements>dictionaryWords = dictionary.getDictionaryWords();
        for (WordReplacements word : dictionaryWords){
            String unreadableWord = word.getWord();
            String[] replacements = word.getReplacements();
            this.wordReplacement.put(unreadableWord, new UnreadableWordHandler(unreadableWord, replacements));
        }

    }

    public String getReplacements(String unreadableWord){
        UnreadableWordHandler unreadableWordHandler = wordReplacement.get(unreadableWord);
        if (unreadableWordHandler != null){
            return unreadableWordHandler.getReplacement();
        }
        return null;
    }

    public LinkedHashMap<String, UnreadableWordHandler> getWordReplacement() {
        return wordReplacement;
    }

    public UnreadableWordHandler getUnreadableWordHandler(String unreadableWord){
        return wordReplacement.get(unreadableWord);
    }

    public String getFileName() {
        return fileName;
    }
}
