package ReplacementFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;


public class ReplacementFile {

    private String processedFileName;
    private int countReplacement = 0;
    private transient Map<Replacement, Integer> replacementsCount = new HashMap<>();
    private transient String fileName;
    @SerializedName("replacements")
    private ArrayList<Replacement>replacementForOut = new ArrayList<>();


    public ReplacementFile(String processedFileName){
        this.processedFileName = processedFileName;
        this.fileName = "Replacements" + processedFileName;
    }

    public void addReplacement(String unreadableWord, String replacement, String description){
        Replacement newReplacement = new Replacement(unreadableWord, replacement, description);
        if (replacementsCount.containsKey(newReplacement)) {
            Integer countReplacement = replacementsCount.get(newReplacement);
            replacementsCount.replace(newReplacement, ++countReplacement);
        }else{
            replacementsCount.put(newReplacement, 1);
        }
        countReplacement++;
    }

    public String getJsonFormat() {
        if (replacementForOut.size() == 0) {
            initializeOutList();
            sortOutList();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(this);
        return result;
    }

    public String getFileName() {
        return fileName;
    }

    private void initializeOutList(){
        for (Map.Entry entries : replacementsCount.entrySet()) {
            Replacement replacement = (Replacement) entries.getKey();
            Integer countReplacement = (Integer) entries.getValue();
            replacement.countIdenticalReplacements = countReplacement;
            replacementForOut.add(replacement);
        }
    }

    private void sortOutList(){
        replacementForOut.sort(Comparator.comparing(r -> r.countIdenticalReplacements));
        Collections.reverse(replacementForOut);
    }

    public void createFile(String outDir) {
        if (!fileIsEmpty()) {
            try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + fileName))) {
                os.write(getJsonFormat());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean fileIsEmpty(){
        return replacementsCount.size() == 0;
    }



    class Replacement{
        private String unreadableWord;
        private String replacement;
        private String description;
        @SerializedName("count")
        private Integer countIdenticalReplacements;

        public Replacement(String unreadableWord, String replacement, String description) {
            this.unreadableWord = unreadableWord;
            this.replacement = replacement;
            this.description = description;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = result * prime + (unreadableWord == null ? 0 : unreadableWord.hashCode());
            result = result * prime + (replacement == null ? 0 : replacement.hashCode());
            result = result * prime + (description == null ? 0 : description.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj){
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()){
                return false;
            }
            Replacement objReplacement = (Replacement)obj;
            return (this.unreadableWord.equals(objReplacement.unreadableWord))
                    && (this.description.equals(objReplacement.description))
                    && (this.replacement.equals(objReplacement.replacement));
        }
    }


}
