package ProcessingServices;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceSeparator {


    //Принимает текст файла. Возвращает текст разбитый на список предложений.
    public static ArrayList<String>getSentences(String fileText){
        ArrayList<String>fileSentences = new ArrayList<>();
        //Регулярное выражение для поиска предложений в строке.
        Pattern pattern = Pattern.compile("[^\\!\\?\\.].*?[\\!\\?\\.]");
        Matcher matcher = pattern.matcher(fileText);
        while(matcher.find()){
            String foundSentence = fileText.substring(matcher.start() , matcher.end());
            fileSentences.add(foundSentence);
        }
        return fileSentences;
    }


}
