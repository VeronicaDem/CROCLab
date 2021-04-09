package ExcelParser;

import InputFile.InputFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelParser {

    Map<String, String> filesTexts = new LinkedHashMap<>();

    public ExcelParser(String directoryWithExcelFiles){
        File excelFilesDirectory = new File(directoryWithExcelFiles);
        for (File excelFile : excelFilesDirectory.listFiles()){
            parseFile(excelFile);
        }
    }

    private void parseFile(File excelFile){
        StringBuffer excelFileText =  new StringBuffer();
        FileInputStream fis = null;
        HSSFWorkbook excelFileWorkbook = null;
        try{
            fis = new FileInputStream(excelFile);
            excelFileWorkbook = new HSSFWorkbook(fis);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        HSSFSheet excelSheet = excelFileWorkbook.getSheet("Имя переменной");
        Iterator rowBIterator = excelSheet.rowIterator();
        while(rowBIterator.hasNext()){
            HSSFRow currentRow = (HSSFRow) rowBIterator.next();
            HSSFCell cellB = currentRow.getCell(1);
            if (cellB != null && cellB.getCellType() == Cell.CELL_TYPE_STRING){

                Pattern phoneNumberPattern = Pattern.compile("-->");
                Matcher matcher = phoneNumberPattern.matcher(cellB.getStringCellValue());

                if (matcher.find()) {
                    int index = matcher.start();
                    String subStr = cellB.getStringCellValue().substring(0, index);
                    excelFileText.append(subStr).append("\n");
                }else {
                    excelFileText.append(cellB.getStringCellValue()).append("\n");
                }

            }
        }
        filesTexts.put(excelFile.getName() ,excelFileText.toString());
    }

    public void createFilesForProcess(String outDir){
        for (Map.Entry fileText : filesTexts.entrySet()){
            String fileName = (String)fileText.getKey();
            String textFromFile = (String)fileText.getValue();
            try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(outDir + "/" + fileName.replace(".xls", ".txt")), "UTF-8")){
                os.write(textFromFile);
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}