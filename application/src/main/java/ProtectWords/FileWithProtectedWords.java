package ProtectWords;

import DataStructure.CaseInsensitiveList;

import java.util.ArrayList;

public class FileWithProtectedWords {

    public ArrayList<String> protectedWords = new ArrayList<>();


    public ArrayList<String> getProtectedWords() {
        return protectedWords;
    }

    public void setProtectedWords(ArrayList<String> protectedWords) {
        protectedWords = protectedWords;
    }

    public boolean isWordProtected(String wordToCheck){
        return protectedWords.contains(wordToCheck);
    }
}
