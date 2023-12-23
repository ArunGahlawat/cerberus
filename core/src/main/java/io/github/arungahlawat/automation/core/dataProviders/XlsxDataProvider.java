package io.github.arungahlawat.automation.core.dataProviders;

import io.github.arungahlawat.automation.core.utils.io.impl.readers.XlsxReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;

public class XlsxDataProvider {
    public Object[][] getProvider(String filePath, String sheetName) {
        XSSFWorkbook workbook = XlsxReader.getInstance().read(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> rowIterator = sheet.rowIterator();
        Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(sheet.getFirstRowNum()).getLastCellNum()];
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                data[cell.getRowIndex()][cell.getColumnIndex()] = new DataFormatter().formatCellValue(cell, new XSSFFormulaEvaluator(workbook));
            }
        }
        return data;
    }
}
