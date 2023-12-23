package io.github.arungahlawat.automation.api.core.utils;

import io.github.arungahlawat.automation.api.core.ApiCoreConstants;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import org.apache.commons.configuration2.Configuration;

public class ApiCoreFileUtils extends FileUtils {
    public static Configuration getConfig() {
        return getConfig(ApiCoreConstants.PROPERTIES_FILE_PATH);
    }
}
