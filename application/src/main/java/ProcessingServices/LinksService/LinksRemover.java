package ProcessingServices.LinksService;

public class LinksRemover {

    private static final String[] linksRegex = {
            "(https?:\\/\\/)?([\\w-]{1,32}\\.[\\w-]{1,32})[^\\s@]*",
            "(http|https|ftp|ftps)://(.*?)(\\s|\\n|[,.?!](\\s|\\n)|$)"};

    public static String removeLinks(String fileText){
        fileText = fileText.replaceAll("e mail", "email")
                .replaceAll("e-mail", "email").replaceAll("E-mail", "email");
        for (String linkRegexp : linksRegex) {
            fileText = fileText.replaceAll(linkRegexp, " ");
        }
        return fileText;
    }
}
