package WordsToDelete;

import Properties.PropertyLoader;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WordsToDeleteStorage {

    private ArrayList<FileWordToDelete> filesWordsToDelete = new ArrayList<>();
    private ArrayList<String>wordsToDelete = new ArrayList<>();

    public WordsToDeleteStorage(PropertyLoader property){
        loadFiles(property.getWordsToDeleteDir());
        extractWordsToDelete();
    }

    private void loadFiles(String dir){
        File currentDirectory = new File(dir);
        for (File file : currentDirectory.listFiles()){
            Gson gson = new Gson();
            String fileData = readFile(file.getPath());
            filesWordsToDelete.add(gson.fromJson(fileData, FileWordToDelete.class));
        }
    }

    private String readFile(String filePath){
        StringBuilder fileData = new StringBuilder();
        String readLine;
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while((readLine = br.readLine()) != null){
                fileData.append(readLine);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return fileData.toString();
    }

    private void extractWordsToDelete(){
        for (FileWordToDelete fileWordToDelete : filesWordsToDelete){
            wordsToDelete.addAll(fileWordToDelete.getWordsToDelete());
        }
    }

    public ArrayList<String> getWordsToDelete() {
        return wordsToDelete;
    }
}
