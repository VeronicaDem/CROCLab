import Handler.Handler;

import java.io.IOException;

public class Launch {


    public static void main(String[] args) throws IOException {

        Long startTime = System.currentTimeMillis();
        Handler analyzer = new Handler("C:/Users/ivan/Desktop/CROCLab/Files/Property.json");
        analyzer.createOutputFiles();
//        ExcelParser excelParser = new ExcelParser("C:\\Users\\ivan\\Desktop\\CROCLab\\Files\\txt");
//        excelParser.createFilesForProcess("C:\\Users\\ivan\\Desktop\\CROCLab\\Files\\AllMonth");
        Long endTime = System.currentTimeMillis();
        System.out.println("Время на обработку файла = " + (endTime - startTime));


    }

}
