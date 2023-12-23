package io.github.arungahlawat.automation.core.utils;


import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.asserts.IAssertLifecycle;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Assert extends Assertion implements IAssertLifecycle {
    private static Assert is;

    private Assert() {
    }

    public static Assert is() {
        if (is == null)
            is = new Assert();
        return is;
    }

    @Override
    public void onBeforeAssert(IAssert<?> assertCommand) {
        String message = "Asserting";
        message += StringUtils.isBlank(assertCommand.getMessage()) ? "" : " | " + assertCommand.getMessage();
        message += " | Actual: {} | Expected {}";
        Log.report(message, assertCommand.getActual(), assertCommand.getExpected());
    }

    @Step("Asserting file exist")
    public void assertFileExist(String filePath, String message) {
        assertTrue(FileUtils.isFileExists(filePath), message);
    }

    @Step("Asserting file exist")
    public void assertFileExist(String filePath) {
        assertTrue(FileUtils.isFileExists(filePath));
    }

    @Step("Asserting directory exist")
    public void assertDirectoryExist(String filePath, String message) {
        assertTrue(FileUtils.isFileExists(filePath), message);
    }

    @Step("Asserting directory exist")
    public void assertDirectoryExist(String filePath) {
        assertTrue(FileUtils.isFileExists(filePath));
    }

    @Step("Asserting string contains all words")
    public void assertStringContainsAll(String inputString, String... searchWords) {
        Set<String> missingWords = Stream.of(searchWords)
                .filter(searchWord -> !inputString.contains(searchWord))
                .collect(Collectors.toSet());
        String message = "Input string contains all search words ?"
                + (missingWords.isEmpty()
                ? ""
                : " Missing words: [" + StringUtils.join(missingWords, ", ") + "]");
        assertTrue(missingWords.isEmpty(), message);
    }
}
