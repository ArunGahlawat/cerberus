package io.github.arungahlawat.automation.api.userManagement.utils;

import io.github.arungahlawat.automation.api.userManagement.UserManagementApiConstants;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import org.apache.commons.configuration2.Configuration;

public class UserManagementApiFileUtils extends FileUtils {
    public static Configuration getConfig() {
        return getConfig(UserManagementApiConstants.USER_MANAGEMENT_API_PROPERTIES_FILE_PATH);
    }
}
