package WordsToDelete;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeletedWordsStorage {

    private String processedFileName;
    private Map<String, Integer> deletedWords = new LinkedHashMap<>();

    public DeletedWordsStorage(String fileName){
        this.processedFileName = fileName;
    }

    public void addDeletedWord(String word){
        if (deletedWords.containsKey(word)){
            Integer countWord = deletedWords.get(word);
            deletedWords.replace(word, ++countWord);
        }
        else {
            deletedWords.put(word, 1);
        }
    }

    public String getProcessedFileName() {
        return processedFileName;
    }

    public Map<String, Integer> getDeletedWords() {
        return deletedWords;
    }
}
