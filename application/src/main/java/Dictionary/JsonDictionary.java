package Dictionary;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

//public class DictionaryDeserializer implements JsonDeserializer<JsonDictionary> {
//    @Override
//    public JsonDictionary deserialize(JsonElement jsonElement, Type type,
//                                      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonDictionary dictionary = new JsonDictionary();
//        JsonObject ob = jsonElement.getAsJsonObject();
//        Map<String, ArrayList<String>> map = new HashMap<>();
//        JsonArray arr = ob.getAsJsonArray("dictionaryWords");
//        for (JsonElement e: arr) {
//            map.put(e.getAsJsonObject().getAsJsonPrimitive("type").getAsString(),
//                    e.getAsJsonArray("replacements"));
//        }
//        user.setOperations(map);
//        return user;
