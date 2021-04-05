package Dictionary;

import ReportLog.ReportLog;
import java.io.File;
import java.util.ArrayList;

public class Dictionaries {

    private ArrayList<DictionaryWhitespaceWords> dictionaryWhitespaceWords = new ArrayList<>();
    private ArrayList<DictionarySingleWords> dictionarySingleWords = new ArrayList<>();

    public Dictionaries(String dictionariesDirectoryPath){
        loadDictionaries(dictionariesDirectoryPath);
    }

    private void loadDictionaries(String dictionariesDirectoryPath){
        ReportLog.logCurrentOperation("Загрузка словарей.");
        File singleWordsFolder = new File(dictionariesDirectoryPath + "/SingleWords");
        File whitespaceWordsFolder = new File(dictionariesDirectoryPath + "/WhitespaceWords");
        loadSingleWordsDictionaries(singleWordsFolder);
        loadWhitespaceWordsDictionaries(whitespaceWordsFolder);
    }

    private void loadSingleWordsDictionaries(File folder){
        for (File file : folder.listFiles()){
            dictionarySingleWords.add(new DictionarySingleWords(file.getPath()));
        }
    }

    private void loadWhitespaceWordsDictionaries(File folder){
        for (File file : folder.listFiles()) {
            dictionaryWhitespaceWords.add(new DictionaryWhitespaceWords(file.getPath()));
        }
    }

    public ArrayList<DictionaryWhitespaceWords> getDictionaryWhitespaceWords(){
        return dictionaryWhitespaceWords;
    }

    public ArrayList<DictionarySingleWords> getDictionarySingleWords() {
        return dictionarySingleWords;
    }
}
