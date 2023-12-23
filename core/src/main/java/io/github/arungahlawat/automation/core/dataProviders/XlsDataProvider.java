package io.github.arungahlawat.automation.core.dataProviders;

import io.github.arungahlawat.automation.core.utils.io.impl.readers.XlsReader;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;

public class XlsDataProvider {
    public Object[][] getProvider(String filePath, String sheetName) {
        HSSFWorkbook workbook = XlsReader.getInstance().read(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        Iterator<Row> rowIterator = sheet.rowIterator();
        Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(sheet.getFirstRowNum()).getLastCellNum()];
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                data[cell.getRowIndex()][cell.getColumnIndex()] = new DataFormatter().formatCellValue(cell, new HSSFFormulaEvaluator(workbook));
            }
        }
        return data;
    }
}
