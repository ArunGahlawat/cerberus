package io.github.arungahlawat.automation.api.userManagement;

import io.github.arungahlawat.automation.core.enums.Env;

import java.util.HashMap;
import java.util.Map;

public class UserManagementApiConstants {
    public static final String USER_MANAGEMENT_API_PROPERTIES_FILE_PATH = "properties/user-management-api.properties";
    public static final Map<Env,String > URI = new HashMap<>(){{
        put(Env.TEST, "https://test-userapis.letstransport.in");
        put(Env.STAGE, "https://stg-userapis.letstransport.in");
    }};
    public static final String HEADER_AUTH_KEY = "key";
    public static final String HEADER_AUTH_TOKEN = "token";
    public static final String HEADER_AUTH_SOURCE = "source";
    public static final String HEADER_AUTH_DEVICE_ID = "unique_device_id";
}
