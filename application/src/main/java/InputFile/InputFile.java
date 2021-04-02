package InputFile;

import InformationFiles.FileWithAbbreviations;
import ProcessingServices.EncodingService;
import ReplacementFile.ReplacementFile;
import org.apache.any23.encoding.TikaEncodingDetector;

import java.io.*;
import java.nio.charset.Charset;


public class InputFile {

    private boolean validEncoding;
    private boolean validFormat;
    private String filePath;
    private String fileName;
    private String fileText;
    private ReplacementFile replacementFile;
    private FileWithAbbreviations fileWithAbbreviations;


    public InputFile(String filePath) {
        this.filePath = filePath;
        this.fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        validEncoding = checkEncoding();
//        if (!validEncoding) {
//            this.filePath = EncodingService.changeEncoding(filePath);
//        }
        readData();
        this.replacementFile = new ReplacementFile(fileName);
        this.fileWithAbbreviations = new FileWithAbbreviations(fileName);
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    //Считывает данные из файла в одну строку.
    private void readData() {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String readData = "";
            while ((readData = br.readLine()) != null) {
                fileData.append(readData.trim()).append(". ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileText = fileData.toString();
    }


    //Проверка кодировки файла. Возвращает true, если кодировка файла UTF-8.
    private boolean checkEncoding() {
        String fileEncoding = "";
        try (InputStream is = new FileInputStream(filePath);) {
            fileEncoding = Charset.forName(new TikaEncodingDetector().guessEncoding(is)).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileEncoding.equals("UTF-8");
    }

    public String getFileName() {
        return fileName;
    }

    public ReplacementFile getReplacementFile() {
        return replacementFile;
    }

    public FileWithAbbreviations getFileWithAbbreviations() {
        return fileWithAbbreviations;
    }
}
