import org.apache.any23.encoding.TikaEncodingDetector;
import java.io.*;
import java.nio.charset.Charset;


public class InputFile {

    private boolean validEncoding;
    private boolean validFormat;
    private String filePath;
    private String fileName;
    private String fileData;


    public InputFile(String filePath){
    this.filePath = filePath;
    this.fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
    fileData = readData(filePath);
    validEncoding = checkEncoding();
    }

    public String getFileData() {
        return fileData;
    }



    //Считывает данные из файла в одну строку.
    private String readData(String filePath){
        StringBuilder fileData = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))){
            String readData = "";
            while ((readData = br.readLine()) != null){
                fileData.append(readData.trim()).append(".");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return fileData.toString();
    }


    //Проверка кодировки файла. Возвращает true, если кодировка файла UTF-8.
    private boolean checkEncoding(){
        boolean result = false;
        String fileEncoding = "";
        try(InputStream is = new FileInputStream(filePath);){
           fileEncoding = Charset.forName(new TikaEncodingDetector().guessEncoding(is)).toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return fileEncoding.equals("UTF-8");
    }

    public String getFileName() {
        return fileName;
    }
}
