package Dictionary;


import java.util.regex.Pattern;

public class UnreadableWordHandler{

    private String[] replacements;
    private Pattern unreadableWordPattern;

    public UnreadableWordHandler(String wordFromDictionary, String[] replacement){
        this.replacements = replacement;
        this.unreadableWordPattern = createPatternForUnreadableWord(wordFromDictionary);
    }

    private Pattern createPatternForUnreadableWord(String wordFromDictionary){
        String unreadableWordRegexp = wordFromDictionary.replaceAll("\\.", "\\\\.");
//        unreadableWordRegexp = "[^\\s]" + unreadableWordRegexp + "[\\s\\n\\W]";
        unreadableWordRegexp = "(?<=\\s|^)" + unreadableWordRegexp + "(?=\\s|\\W|$)";
        Pattern unreadableWordPattern = Pattern.compile(unreadableWordRegexp);
        return unreadableWordPattern;
    }

    public String getReplacement() {
        return replacements[0];
    }

    public Pattern getUnreadableWordPattern() {
        return unreadableWordPattern;
    }
}
