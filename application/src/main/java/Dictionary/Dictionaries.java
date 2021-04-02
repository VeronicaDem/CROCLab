package Dictionary;

import ReportLog.ReportLog;

import java.io.File;
import java.util.ArrayList;

public class Dictionaries {

    private ArrayList<Dictionary> dictionaries = new ArrayList<>();

    public Dictionaries(String dictionariesDirectoryPath){
        loadDictionaries(dictionariesDirectoryPath);
    }

    private void loadDictionaries(String dictionariesDirectoryPath){
        ReportLog.logCurrentOperation("Загрузка словарей.");
        File folder = new File(dictionariesDirectoryPath);
        for (File file : folder.listFiles()){
            if (!file.isDirectory()){
                dictionaries.add(new Dictionary(file.getPath()));
            }
        }
    }

    public ArrayList<Dictionary>getDictionaries(){
        return dictionaries;
    }

    public String getReplacement(String unreadableWord){
        String replacement = null;
        for (Dictionary dictionary : dictionaries){
            replacement = dictionary.getReplacements(unreadableWord);
        }
        return replacement;
    }
}
