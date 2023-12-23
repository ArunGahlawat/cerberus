package io.github.arungahlawat.automation.api.core.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;
import java.util.Locale;

public class JacksonFieldNamingStrategyHelper implements FieldNamingStrategy {
    static String separateCamelCase(String name, String separator) {
        StringBuilder translation = new StringBuilder();
        int i = 0;
        for (int length = name.length(); i < length; ++i) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && !translation.isEmpty()) {
                translation.append(separator);
            }
            translation.append(character);
        }

        return translation.toString();
    }

    static String upperCaseFirstLetter(String name) {
        int firstLetterIndex = 0;
        int limit = name.length() - 1;
        while (!Character.isLetter(name.charAt(firstLetterIndex)) && firstLetterIndex < limit) {
            ++firstLetterIndex;
        }
        char firstLetter = name.charAt(firstLetterIndex);
        if (Character.isUpperCase(firstLetter)) {
            return name;
        } else {
            char uppercased = Character.toUpperCase(firstLetter);
            return firstLetterIndex == 0 ? uppercased + name.substring(1) : name.substring(0, firstLetterIndex) + uppercased + name.substring(firstLetterIndex + 1);
        }
    }

    @Override
    public String translateName(Field f) {
        if (f.isAnnotationPresent(JsonProperty.class))
            return f.getAnnotation(JsonProperty.class).value();
        try {
            String jsonNamingAnnotation = f.getDeclaringClass().getAnnotation(JsonNaming.class).value().getSimpleName();
            switch (jsonNamingAnnotation) {
                case "SnakeCaseStrategy":
                    return separateCamelCase(f.getName(), "_").toLowerCase(Locale.ENGLISH);
                case "UpperCamelCaseStrategy":
                    return upperCaseFirstLetter(f.getName());
                case "KebabCaseStrategy":
                    return separateCamelCase(f.getName(), "-").toLowerCase(Locale.ENGLISH);
                default:
                    return f.getName();
            }
        } catch (Exception e) {
            return f.getName();
        }
    }
}
