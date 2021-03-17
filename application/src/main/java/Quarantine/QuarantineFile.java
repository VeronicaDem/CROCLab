package Quarantine;

import java.util.ArrayList;

public class QuarantineFile {

    private ArrayList<String>quarantineSentences = new ArrayList<>();

    public void addQuarantineSentence(String fileName, String sentence){
        quarantineSentences.add(sentence);
    }

    public ArrayList<String> getQuarantineSentences() {
        return quarantineSentences;
    }
}
