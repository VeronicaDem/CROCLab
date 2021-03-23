package Properties;

public class PropertyData {
    private String[] inputFiles ;
    private String[] characterToDelete ;
    private Integer outputFileSize ;
    private String outputDirectory ;
    private String[] dictionaries ;

    public String[] getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(String[] inputFiles) {
        this.inputFiles = inputFiles;
    }

    public String[] getCharacterToDelete() {
        return characterToDelete;
    }

    public void setCharacterToDelete(String[] characterToDelete) {
        this.characterToDelete = characterToDelete;
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

    public String[] getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(String[] dictionaries) {
        this.dictionaries = dictionaries;
    }
}
