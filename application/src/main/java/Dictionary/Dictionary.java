package Dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {

    private LinkedHashMap<String, String> abbreviationsMap;


    public Dictionary(String filePath){
        initializationHashMap(filePath);
    }



    public void initializationHashMap(String dictionaryFilePath) {
        abbreviationsMap = new LinkedHashMap <String,String> ();
        String line;
        BufferedReader reader = null;
        try {
             reader = new BufferedReader(new FileReader(dictionaryFilePath));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length >= 2) {
                    String key = parts[0];
                    String value = parts[1];
                    abbreviationsMap.put(key, value);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        try {
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String abbreviationExplanation(String abbreviation){
        return abbreviationsMap.get(abbreviation);
    }
}
