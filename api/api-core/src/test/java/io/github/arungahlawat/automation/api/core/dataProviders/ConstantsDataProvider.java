package io.github.arungahlawat.automation.api.core.dataProviders;

import org.testng.annotations.DataProvider;

public class ConstantsDataProvider {
    @DataProvider(name = "FilePathTestData")
    public Object[][] stringFilePathTestData() {
        return new String[][]{
                {"parent/child/child/child", "/"},
                {"parent\\child\\child\\child", "\\\\"},
                {".", "/"},
                {"./../../../abc/def", "/"},
                {"../../../../abc/def", "/"},
                {"c:\\abc\\def\\hig", "\\\\"},
                {"abc.txt", "\\\\"},
                {"./abc.txt", "/"},
                {".\\abc.txt", "\\\\"},
                {"..\\abc.txt", "\\\\"},
                {"/Users/abc", "/"}
        };
    }

    @DataProvider(name = "CreateFileTestData")
    public Object[] createFileTestData() {
        return new String[]{
                "target/test-data/test",
                "target/test",
                "test",
                "src/main/resources/test",
                "src/main/resources/test-data/test",
                "src/test/resources/test",
                "src/test/resources/test-data/test"
        };
    }
}
