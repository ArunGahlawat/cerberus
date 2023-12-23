package io.github.arungahlawat.automation.api.integrationTests.utils;

import io.github.arungahlawat.automation.api.core.Constants;
import org.apache.commons.configuration2.Configuration;

public class FileUtils extends io.github.arungahlawat.automation.api.core.utils.FileUtils {
    public static Configuration getConfig() {
        return getConfig(Constants.PROPERTIES_FILE_PATH);
    }
}
