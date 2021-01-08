/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv_parser;

import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Eug
 */
public class ExcelSheetCreator {
    public static void create (List<TimeTable> timeTables, String inputFilePath) {
        try {
            //init code
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Report");
            Row headerRow = sheet.createRow(0);  
            String[] colNames = {"Time", "Name","Category","QTY","Item"};
            createColumnNames(colNames, headerRow);
            
            fillSheet(sheet, timeTables);
            
            autoSizeAllCol(colNames.length, sheet); //so that columns wrap around text

            String fileLocation = inputFilePath.substring(0,inputFilePath.length()-4) + "-REVISED" + ".xlsx"; //-4 to delete ".csv"

            FileOutputStream outputStream = new FileOutputStream(fileLocation, false); //false bool overwrites if already exists
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createColumnNames (String[] names, Row headerRow) {
        for(int i = 0; i < names.length; i++) {
            Cell c = headerRow.createCell(i);
            c.setCellValue(names[i]);
        }
    }
    
    private static void autoSizeAllCol(int numCols, Sheet sheet) {
        for(int i = 0; i < numCols; i++) sheet.autoSizeColumn(i);
    }
    
    //currently ignoring totals table at timeblock level, just using totals at person level
    private static void fillSheet(Sheet sheet, List<TimeTable> timeTables) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a"); //NOTE: only use this for printing, if use for parsing, will fail b/c no seconds info
        
        int currRow = 1; //0th row for header
        
        for (TimeTable timeTable : timeTables) {

            String timeString = timeTable.time.format(formatter);
            
            for (Person p: timeTable.people) {
                
                String name = p.name;
                
                for (String category : p.totals.totalsTable.keySet()) {
                    
                    if (category.equals("Other/Ignore")) continue;
                    //NOTE: don't have to worry about side category b/c totals.updateOldOrderWithSide() already called in MainWindow, 
                    //which directly updates the main food (i.e. doesn't update the quantity for the side)
                    
                    HashMap<String, Integer> foodQuantityMap = p.totals.totalsTable.get(category);
                    
                    //create row for each order
                    for (String food : foodQuantityMap.keySet()) {
                        Row row = sheet.createRow(currRow++);
                        //populate cols
                        row.createCell(0).setCellValue(timeString);
                        row.createCell(1).setCellValue(name);
                        row.createCell(2).setCellValue(category);
                        row.createCell(3).setCellValue(foodQuantityMap.get(food));
                        row.createCell(4).setCellValue(food);
                    }
                }
            }
            
        }
    }
}
