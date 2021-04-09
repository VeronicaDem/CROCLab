package Properties;

public class PropertyData {
    private String inputFilesDirectory;
    private Integer outputFileSize;
    private String outputDirectory;
    private String dictionariesDirectory;
    private String[] filesForStatisticPaths;
    private String enableEnglishText;
    private String protectedWordsDir;
    private String wordsToDeleteDir;

    public String getWordsToDeleteDir() {
        return wordsToDeleteDir;
    }

    public void setWordsToDeleteDir(String wordsToDeleteDir) {
        this.wordsToDeleteDir = wordsToDeleteDir;
    }

    public String getProtectedWordsDir() {
        return protectedWordsDir;
    }

    public void setProtectedWordsDir(String protectedWordsDir) {
        this.protectedWordsDir = protectedWordsDir;
    }

    public String getEnableEnglishText() {
        return enableEnglishText;
    }
    public void setEnableEnglishText(String enableEnglishText) {
        this.enableEnglishText = enableEnglishText;
    }

    public String getInputFilesDirectory() {
        return inputFilesDirectory;
    }
    public void setInputFilesDirectory(String inputFilesDirectory) {
        this.inputFilesDirectory = inputFilesDirectory;
    }

    public Integer getOutputFileSize() {
        return outputFileSize;
    }
    public void setOutputFileSize(Integer outputFileSize) {
        this.outputFileSize = outputFileSize;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }
    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getDictionariesDirectory() {
        return dictionariesDirectory;
    }
    public void setDictionariesDirectory(String dictionariesDirectory) {
        this.dictionariesDirectory = dictionariesDirectory;
    }

    public String[] getFilesForStatisticPaths() {
        return filesForStatisticPaths;
    }
    public void setFilesForStatisticPaths(String[] filesForStatisticPaths) {
        this.filesForStatisticPaths = filesForStatisticPaths;
    }
}
