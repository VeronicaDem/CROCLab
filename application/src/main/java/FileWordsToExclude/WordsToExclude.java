package FileWordsToExclude;

import java.io.File;
import java.util.ArrayList;

public class WordsToExclude {

    private ArrayList<FileWithExcludeWords> filesExcludeWords = new ArrayList<>();

    public WordsToExclude(String directoryPath){
        loadFiles(directoryPath);
    }

    private void loadFiles(String directoryPaths){
        File directory = new File(directoryPaths);
        for (File file : directory.listFiles()){
            filesExcludeWords.add(new FileWithExcludeWords(file.getPath()));
        }
    }

    public boolean isWordExclusion(String wordToCheck){
        for (FileWithExcludeWords file : filesExcludeWords){
            if (file.isWordExclusion(wordToCheck)) {
                return true;
            }
        }
        return false;
    }

}
