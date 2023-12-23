package io.github.arungahlawat.automation.api.fieldOps.utils;

import io.github.arungahlawat.automation.api.fieldOps.FieldOpsApiConstants;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import org.apache.commons.configuration2.Configuration;

public class FieldOpsApiFileUtils extends FileUtils {
    public static Configuration getConfig() {
        return getConfig(FieldOpsApiConstants.FIELD_OPS_API_PROPERTIES_FILE_PATH);
    }
}
