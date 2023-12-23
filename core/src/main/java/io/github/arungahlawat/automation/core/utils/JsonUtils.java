package io.github.arungahlawat.automation.core.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    public static String toJson(Object object, FieldNamingStrategy fieldNamingStrategy, ExclusionStrategy exclusionStrategy) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingStrategy(fieldNamingStrategy)
                .setExclusionStrategies(exclusionStrategy)
                .disableHtmlEscaping();
        try {
            return gsonBuilder
                    .create()
                    .toJson(object);
        } catch (Exception ex) {
            return new Gson().toJson(object);
        }
    }
    public static <T, K> Map<T, K> convertFromJsonToMap(String jsonString, T keyObject, K valueObject) {
        if (StringUtils.isEmpty(jsonString))
            return null;
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type;
        type = objectMapper.getTypeFactory().constructMapType(HashMap.class, keyObject.getClass(), valueObject.getClass());
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (Exception e) {
            Log.warn("Error converting to Map", e);
            return null;
        }
    }

    public static Map<String, Object> convertFromObjectToMap(Object object,FieldNamingStrategy fieldNamingStrategy, ExclusionStrategy exclusionStrategy) {
        return convertFromJsonToMap(toJson(object, fieldNamingStrategy, exclusionStrategy), "", new Object());
    }
}
