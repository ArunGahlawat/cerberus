package io.github.arungahlawat.automation.core.utils.io.impl.readers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Reader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XlsReader implements Reader<HSSFWorkbook> {
    private static XlsReader instance;

    private XlsReader() {
    }

    public static XlsReader getInstance() {
        if (instance == null)
            instance = new XlsReader();
        return instance;
    }

    @Override
    public HSSFWorkbook read(String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            Log.warn("Could not read file at path {}. Exception:{}. Trying in resources ...", filePath, e.getMessage());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
                if (inputStream == null)
                    throw new IOException("Could not read resource as stream");
                return new HSSFWorkbook(inputStream);
            } catch (IOException ex) {
                Log.error("Error reading file {} in resources. Error: {}", filePath, ex.getMessage());
            }
        }
        return null;
    }

    public Sheet read(String filePath, String sheetName) {
        try (HSSFWorkbook workbook = read(filePath)) {
            return workbook.getSheet(sheetName);
        } catch (IOException e) {
            return null;
        }
    }
}
