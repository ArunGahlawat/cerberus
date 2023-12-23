package io.github.arungahlawat.automation.core.utils.io.impl.writers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Writer;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsxWriter implements Writer<Object[][]> {
    private static XlsxWriter instance;

    private XlsxWriter() {
    }

    public static XlsxWriter getInstance() {
        if (instance == null)
            instance = new XlsxWriter();
        return instance;
    }

    @Override
    public String write(Object[][] data, String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet();
            for (int row = 0; row < data.length; row++) {
                XSSFRow sheetRow = sheet.createRow(row);
                for (int column = 0; column < data[row].length; column++) {
                    XSSFCell cell = sheetRow.createCell(column);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(String.valueOf(data[row][column]));
                }
            }
            workbook.write(new BufferedOutputStream(new FileOutputStream(filePath)));
            return filePath;
        } catch (IOException e) {
            Log.error("Exception in writing file. {}", e.getMessage());
        }
        return "";
    }

    public String write(List<List<Object>> data, String filePath) {
        Object[][] dataArray = new Object[][]{data.toArray()};
        return write(dataArray, filePath);
    }
}
