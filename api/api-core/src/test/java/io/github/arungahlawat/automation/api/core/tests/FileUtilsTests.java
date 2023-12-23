package io.github.arungahlawat.automation.api.core.tests;

import io.github.arungahlawat.automation.core.utils.Assert;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FileUtilsTests {
    @Test(description = "Verify transforming file path to system paths",
            dataProviderClass = io.github.arungahlawat.automation.api.core.dataProviders.ConstantsDataProvider.class,
            dataProvider = "FilePathTestData")
    public void transformFilePathTests(String filePath, String actualReplacement) {
        Assert.is().assertEquals(FileUtils.transformFilePath(filePath), filePath.replaceAll(actualReplacement, File.separator));
    }

    @Test(description = "Verify creating xml files with Xml Writer instance",
            dataProviderClass = io.github.arungahlawat.automation.api.core.dataProviders.ConstantsDataProvider.class,
            dataProvider = "CreateFileTestData")
    public void verifyWritingXmlFiles(String filePath) {
        Map<String, Object> xmlData = new HashMap<>() {{
            put("key1", 1);
            put("key2", "value2");
            put("key3", true);
            put("key4", 5.21d);
            put("key5", BigDecimal.valueOf(12.21));
            put("key6", 's');
        }};
        filePath = filePath + ".xml";
        filePath = FileUtils.writeXmlFile(xmlData, "root", "properties", "key", "value", filePath);
        Assert.is().assertFileExist(filePath, "File exists");
        try {
            String xmlFile = FileUtils.readXmlFileAsString(filePath);
            Assert.is().assertStringContainsAll(xmlFile, "key1", "key2", "key3", "key4", "key5", "key6", "1", "value2", "true", "5.21", "12.21", "s");
        } finally {
            FileUtils.deleteFile(filePath);
        }
    }
}
