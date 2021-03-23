package ReplacementFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;



public class ReplacementFile {

    private ArrayList<Replacement> replacements = new ArrayList<>();
    private int countReplacement = 0;
    private String filePath;


    public void addReplacement(String unreadableWord, String replacement, String description){
        Replacement newReplacement = new Replacement(unreadableWord, replacement, description);
        replacements.add(newReplacement);
        countReplacement++;
    }

    public void createReplacementFile(){
        try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");) {
            for (Replacement replacement : replacements) {
                os.write(replacement.toString());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Replacement>getReplacements(){
        return replacements;
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
