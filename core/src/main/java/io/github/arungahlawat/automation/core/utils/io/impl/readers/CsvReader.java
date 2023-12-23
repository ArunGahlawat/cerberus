package io.github.arungahlawat.automation.core.utils.io.impl.readers;

import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Reader;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.arungahlawat.automation.core.Constants.CSV_DELIMITER;

public class CsvReader implements Reader<Iterator<Object[]>> {
    private static CsvReader instance;

    private CsvReader() {
    }

    public static CsvReader getInstance() {
        if (instance == null)
            instance = new CsvReader();
        return instance;
    }

    @Override
    public Iterator<Object[]> read(String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return read(reader).iterator();
        } catch (IOException e) {
            Log.warn("Could not read file at path {}. Exception:{}. Trying in resources ...", filePath, e.getMessage());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
                if (inputStream == null)
                    throw new IOException("Could not read resource as stream");
                return read(new BufferedReader(new InputStreamReader(inputStream))).iterator();
            } catch (IOException ex) {
                Log.error("Error reading file {} in resources. Error: {}", filePath, ex.getMessage());
            }
        }
        return null;
    }

    private List<Object[]> read(BufferedReader reader) {
        List<Object[]> csvRows = new ArrayList<>();
        reader.lines().forEach((line -> csvRows.add(line.split(CSV_DELIMITER))));
        return csvRows;
    }
}
