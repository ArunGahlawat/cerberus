package io.github.arungahlawat.automation.core.utils.io.impl.readers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Reader;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XlsxReader implements Reader<XSSFWorkbook> {
    private static XlsxReader instance;

    private XlsxReader() {
    }

    public static XlsxReader getInstance() {
        if (instance == null)
            instance = new XlsxReader();
        return instance;
    }

    @Override
    public XSSFWorkbook read(String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            Log.warn("Could not read file at path {}. Exception:{}. Trying in resources ...", filePath, e.getMessage());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
                if (inputStream == null)
                    throw new IOException("Could not read resource as stream");
                return new XSSFWorkbook(inputStream);
            } catch (IOException ex) {
                Log.error("Error reading file {} in resources. Error: {}", filePath, ex.getMessage());
            }
        }
        return null;
    }

    public Sheet read(String filePath, String sheetName) {
        try (XSSFWorkbook workbook = read(filePath)) {
            return workbook.getSheet(sheetName);
        } catch (IOException e) {
            return null;
        }
    }
}
