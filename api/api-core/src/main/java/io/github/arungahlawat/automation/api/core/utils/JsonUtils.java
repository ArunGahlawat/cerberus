package io.github.arungahlawat.automation.api.core.utils;

import io.github.arungahlawat.automation.api.core.helpers.AnnotationExclusionStrategyHelper;
import io.github.arungahlawat.automation.api.core.helpers.JacksonFieldNamingStrategyHelper;

import java.util.Map;

public class JsonUtils extends io.github.arungahlawat.automation.core.utils.JsonUtils {
    public static String toJson(Object object) {
        return toJson(object, new JacksonFieldNamingStrategyHelper(), new AnnotationExclusionStrategyHelper());
    }

    public static Map<String, Object> convertFromObjectToMap(Object object) {
        return convertFromJsonToMap(toJson(object), "", new Object());
    }
}
