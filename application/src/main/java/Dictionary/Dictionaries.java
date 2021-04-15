package Dictionary;

import Handler.Handler;
import ReportLog.*;
import com.google.gson.Gson;


import java.io.*;
import java.util.ArrayList;

public class Dictionaries {

    private ArrayList<JsonDictionary>jsonDictionaries = new ArrayList<>();
    private DictionarySingleWords dictionarySingleWords;
    private DictionaryWhitespaceWords dictionaryWhitespaceWords;

    public Dictionaries(String dictionariesDirectoryPath){
        Handler.reportLog.startCurrentOperation(LogOperation.DICTIONARIES_LOAD);
        loadDictionaries(dictionariesDirectoryPath);
        separateDictionary();
    }

    private void loadDictionaries(String dictionariesDirectoryPath){
        File currentFolder = new File(dictionariesDirectoryPath);
        for (File file : currentFolder.listFiles()){
            if (file.isDirectory()){
             loadDictionaries(file.getPath());
            }else {
                readDictionary(file.getPath());
            }
        }
    }

    private void readDictionary(String dictionaryPath){
        StringBuilder fileData = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))){
            String readLine = "";
            while((readLine = br.readLine()) != null){
                fileData.append(readLine);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        Gson gson = new Gson();
        jsonDictionaries.add(gson.fromJson(fileData.toString(), JsonDictionary.class));
    }

    private void separateDictionary(){
        ArrayList<WordReplacements>whitespaceDictionaryWords = new ArrayList<>();
        ArrayList<WordReplacements>singleDictionaryWords = new ArrayList<>();
        for (JsonDictionary jsonDictionary : jsonDictionaries){
            ArrayList<WordReplacements>replacementsFromDictionary = jsonDictionary.getDictionaryWords();
            for (WordReplacements wordReplacement : replacementsFromDictionary){
                String unreadableWord = wordReplacement.getWord();
                if (unreadableWord.contains(" ")){
                    whitespaceDictionaryWords.add(wordReplacement);
                }else{
                    singleDictionaryWords.add(wordReplacement);
                }
            }
        }
        dictionarySingleWords = new DictionarySingleWords(singleDictionaryWords);
        dictionaryWhitespaceWords = new DictionaryWhitespaceWords(whitespaceDictionaryWords);
    }

    public DictionarySingleWords getDictionarySingleWords() {
        return dictionarySingleWords;
    }

    public DictionaryWhitespaceWords getDictionaryWhitespaceWords() {
        return dictionaryWhitespaceWords;
    }
}
