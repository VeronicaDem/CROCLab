package Dictionary;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DictionarySingleWords {

    private ArrayList<WordReplacements> dictionaryWords;
    private HashMap<String, String[]>unreadableWordReplacements = new HashMap<>();

    public DictionarySingleWords(String filePath){
        loadDictionary(filePath);
    }

    private void loadDictionary(String filePath){
        StringBuilder fileData = new StringBuilder();
        String readLine;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while ((readLine = br.readLine()) != null){
                fileData.append(readLine);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        Gson gson = new Gson();
        JsonDictionary jsonDictionary = gson.fromJson(fileData.toString(), JsonDictionary.class);
        dictionaryWords = jsonDictionary.getDictionaryWords();
        convertInMap(dictionaryWords);
    }

    private void convertInMap(ArrayList<WordReplacements>wordReplacements){
        for (WordReplacements wordReplacement : wordReplacements){
            String dictionaryWord = wordReplacement.getWord();
            String[] replacements = wordReplacement.getReplacements();
            unreadableWordReplacements.put(dictionaryWord, replacements);
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
