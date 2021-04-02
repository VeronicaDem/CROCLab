package ProcessingServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncodingService {

    //Принимает путь к файлу с невалидной колировкой. Возвращает путь к файлу, который перезаписан в кодировке UTF-8.
    public static String changeEncoding(String filePath){

        File file = new File(filePath);
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        String newFileName = "ValidEncoding_" + fileName;
        String newFilePath = filePath.replace(fileName, newFileName);
        File newFile = new File(newFilePath);

        FileInputStream inputStream ;
        FileOutputStream outputStream;
        try {
            inputStream = new FileInputStream(file);

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer, 0, buffer.length);
            inputStream.close();

            Charset utf8 = StandardCharsets.UTF_8;

            outputStream = new FileOutputStream(newFile);

            String str = new String(buffer , utf8);

            outputStream.write(str.getBytes(utf8));
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFilePath;
    }
}
