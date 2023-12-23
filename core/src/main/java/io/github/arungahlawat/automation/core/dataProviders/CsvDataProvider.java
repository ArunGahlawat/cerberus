package io.github.arungahlawat.automation.core.dataProviders;

import io.github.arungahlawat.automation.core.utils.io.impl.readers.CsvReader;

import java.util.Iterator;

import static io.github.arungahlawat.automation.core.Constants.DATA_PROVIDER_FIRST_DATA_ROW_INDEX;

public class CsvDataProvider {
    public Iterator<Object[]> getProvider(String filePath) {
        return getProvider(filePath, DATA_PROVIDER_FIRST_DATA_ROW_INDEX);
    }

    public Iterator<Object[]> getProvider(String filePath, int firstDataRowIndex) {
        Iterator<Object[]> dataRows = CsvReader.getInstance().read(filePath);
        int skipCount = 0;
        while (skipCount++ < firstDataRowIndex && dataRows.hasNext())
            dataRows.next();
        return dataRows;
    }
}
