package io.github.arungahlawat.automation.core.utils.io.impl.writers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Writer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsWriter implements Writer<Object[][]> {
    private static XlsWriter instance;

    private XlsWriter() {
    }

    public static XlsWriter getInstance() {
        if (instance == null)
            instance = new XlsWriter();
        return instance;
    }

    @Override
    public String write(Object[][] data, String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet sheet = workbook.createSheet();
            for (int row = 0; row < data.length; row++) {
                HSSFRow sheetRow = sheet.createRow(row);
                for (int column = 0; column < data[row].length; column++) {
                    HSSFCell cell = sheetRow.createCell(column);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(String.valueOf(data[row][column]));
                }
            }
            workbook.write(new BufferedOutputStream(new FileOutputStream(filePath)));
            return new File(filePath).getAbsolutePath();
        } catch (IOException e) {
            Log.error("Exception in writing file. {}", e.getMessage());
        }
        return new File(filePath).getAbsolutePath();
    }

    public String write(List<List<Object>> data, String filePath) {
        Object[][] dataArray = new Object[][]{data.toArray()};
        return write(dataArray, filePath);
    }
}
