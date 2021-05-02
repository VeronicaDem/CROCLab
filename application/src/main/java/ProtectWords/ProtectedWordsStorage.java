package ProtectWords;

import Properties.PropertyLoader;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class ProtectedWordsStorage {

    private static ArrayList<FileWithProtectedWords> fileWithProtectedWords = new ArrayList<>();

    public ProtectedWordsStorage(PropertyLoader property){
        loadFiles(property.getProtectedWordsDir());
    }

    private void loadFiles(String directoryPath){
            File directory = new File(directoryPath);
        for (File file : directory.listFiles()){
            Gson gson = new Gson();
            FileWithProtectedWords fileWithExcludeWords = gson.fromJson(readFileData(file.getPath()), FileWithProtectedWords.class);
            fileWithProtectedWords.add(fileWithExcludeWords);
        }
    }

    private String readFileData(String filePath){
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

    public static boolean isWordProtected(String wordToCheck){
        for (FileWithProtectedWords file : fileWithProtectedWords){
            if (file.isWordProtected(wordToCheck)) {
                return true;
            }
        }
        return false;
    }

}
