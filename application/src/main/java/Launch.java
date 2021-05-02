import Handler.Handler;
import NumberService.NumberHandler;
import ProcessingServices.DateServices.DateHandler;
import ProcessingServices.DateServices.StringToData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launch {


    public static void main(String[] args) throws IOException {

        Long startTime = System.currentTimeMillis();
       // Handler analyzer = new Handler("C:/Users/Nica/CROCLab/application/Property.json");
       // analyzer.createOutputFiles();
//        ExcelParser excelParser = new ExcelParser("C:\\Users\\ivan\\Desktop\\CROCLab\\Files\\txt");
//        excelParser.createFilesForProcess("C:\\Users\\ivan\\Desktop\\CROCLab\\Files\\AllMonth");
        List<String> permutations = new ArrayList<>();
        GenerateSequence.generates(6, 0, permutations, "");
        for(String elem : permutations) {
            System.out.println(elem);
        }
        System.out.println(permutations.size());
        Long endTime = System.currentTimeMillis();
        System.out.println("Время на обработку файла = " + (float)(endTime - startTime)/1000 + " с.");



    }

}
