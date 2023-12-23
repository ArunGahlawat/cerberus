package io.github.arungahlawat.automation.core.utils.io.impl.writers;

import com.google.gson.*;
import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.Writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class JsonWriter extends DefaultWriter implements Writer<Object> {
    private static JsonWriter instance;
    private FieldNamingStrategy fieldNamingStrategy = FieldNamingPolicy.IDENTITY;
    private ExclusionStrategy exclusionStrategy;

    private JsonWriter() {
    }

    public static JsonWriter getInstance() {
        if (instance == null)
            instance = new JsonWriter();
        return instance;
    }

    public static JsonWriter getInstance(FieldNamingStrategy fieldNamingStrategy, ExclusionStrategy exclusionStrategy) {
        JsonWriter instance = getInstance();
        instance.exclusionStrategy = exclusionStrategy;
        instance.fieldNamingStrategy = fieldNamingStrategy;
        return instance;
    }

    public String write(Object dataObject, GsonBuilder gsonBuilder, String filePath, boolean pretty, boolean append) {
        if (pretty) {
            gsonBuilder.setPrettyPrinting();
            gsonBuilder.disableHtmlEscaping();
        }
        Gson gson = gsonBuilder.create();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        com.google.gson.stream.JsonWriter writer = new com.google.gson.stream.JsonWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.setIndent("  ");
        try {
            gson.toJson(dataObject, dataObject.getClass(), writer);
            writer.close();
        } catch (IOException e) {
            Log.error("Error writing json file", e);
        }
        return super.write(out.toByteArray(), filePath, append);
    }

    public String write(Object data, String filePath, boolean pretty) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (this.fieldNamingStrategy != null)
            gsonBuilder.setFieldNamingStrategy(fieldNamingStrategy);
        if (this.exclusionStrategy != null)
            gsonBuilder.addDeserializationExclusionStrategy(exclusionStrategy);
        return this.write(data, gsonBuilder, filePath, pretty, false);
    }
}
