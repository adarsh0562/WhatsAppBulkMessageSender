package utils;

import config.ConfigReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private static final String FILE_PATH = System.getProperty("user.dir")+ ConfigReader.get("EXCEL_PATH");
    private static Workbook workbook;
    private static Sheet sheet;

    static {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0); // Assuming first sheet
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getContacts() {
        List<String[]> contacts = new ArrayList<>();

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // Start from row 1 to skip headers
            Row row = sheet.getRow(rowIndex);
            if (row == null) continue;

            try {
                int rowNum = (int) row.getCell(0).getNumericCellValue(); // Handle number column
                String name = getCellValueAsString(row.getCell(1));
                String number = getCellValueAsString(row.getCell(2));

                contacts.add(new String[]{String.valueOf(rowNum), name, number});
            } catch (Exception e) {
                System.out.println("⚠️ Error reading row " + rowIndex + ": " + e.getMessage());
            }
        }
        return contacts;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue()); // Convert numeric to String
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    public static void updateStatus(int rowNum, String status, String color) {
        Row row = sheet.getRow(rowNum);

        if (row == null) return;

        Cell cell = row.createCell(3); // Change index to 3 for "Message Status" column
        cell.setCellValue(status);

        CellStyle style = workbook.createCellStyle();
        if (color.equalsIgnoreCase("green")) {
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        } else {
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
        }
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
    }


    public static void saveChanges() {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
