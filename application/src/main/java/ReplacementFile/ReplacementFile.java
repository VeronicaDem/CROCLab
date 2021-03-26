package ReplacementFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;



public class ReplacementFile {

    private String processedFileName;
    private int countReplacement = 0;
    private ArrayList<Replacement> replacements = new ArrayList<>();
    private transient String fileName;


    public ReplacementFile(String processedFileName){
        this.processedFileName = processedFileName;
        this.fileName = "Replacements" + processedFileName;
    }

    public void addReplacement(String unreadableWord, String replacement, String description){
        Replacement newReplacement = new Replacement(unreadableWord, replacement, description);
        replacements.add(newReplacement);
        countReplacement++;
    }



    public ArrayList<Replacement>getReplacements(){
        return replacements;
    }

    public String getJsonFormat(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }

    public String getFileName() {
        return fileName;
    }

    class Replacement{
        private String unreadableWord;
        private String replacement;
        private String description;

        public Replacement(String unreadableWord, String replacement, String description) {
            this.unreadableWord = unreadableWord;
            this.replacement = replacement;
            this.description = description;
        }

        public String toString(){
            return "Нечитаемое слово-" + unreadableWord + " заменено на-" + replacement + "; Описание замены-" + description;
        }
    }


}
