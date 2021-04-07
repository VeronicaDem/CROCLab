package FileWordsToExclude;

import InformationFiles.FileWithEnglishText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileWithExcludeWords {

    private ArrayList<String>excludeWords = new ArrayList<>();

    FileWithExcludeWords(String filePath){
        loadFile(filePath);
    }

    private void loadFile(String filePath){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String readLine;
            while((readLine = br.readLine()) != null){
                excludeWords.add(readLine);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public boolean isWordExclusion(String wordToCheck){
        return excludeWords.contains(wordToCheck);
    }
}
