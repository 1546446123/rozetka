import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ExcelWriter {



    public  void saveToFile(Map<String, String> items, String fname) throws IOException,
            InvalidFormatException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Create Other rows and cells with contacts data
        int rowNum = 1;
        for (Map.Entry<String, String> entry : items.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fname);
        workbook.write(fileOut);
        fileOut.close();
    }

    public  void saveToFileEx(Map<String, String> items, Map<String, String> items2, String fname) throws IOException,
            InvalidFormatException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Create Other rows and cells with contacts data
        int rowNum = 1;
        for (Map.Entry<String, String> entry : items.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        Sheet popular  = workbook.createSheet("Popular");
        int rowNum2 = 1;
        for (Map.Entry<String, String> entry : items2.entrySet()) {
            Row row = popular.createRow(rowNum2++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }


        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fname);
        workbook.write(fileOut);
        fileOut.close();
    }
}
