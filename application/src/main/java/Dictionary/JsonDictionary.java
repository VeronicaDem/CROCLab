package Dictionary;

import java.util.ArrayList;

public class JsonDictionary {

    public ArrayList<WordReplacements> dictionaryWords;

    public ArrayList<WordReplacements> getDictionaryWords() {
        return dictionaryWords;
    }

    public void setDictionaryWords(ArrayList<WordReplacements> dictionaryWords) {
        this.dictionaryWords = dictionaryWords;
    }

}

class WordReplacements {

    public String word;
    public String[] replacements;

    public String[] getReplacements() {
        return replacements;
    }

    public void setReplacements(String[] replacements) {
        this.replacements = replacements;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

