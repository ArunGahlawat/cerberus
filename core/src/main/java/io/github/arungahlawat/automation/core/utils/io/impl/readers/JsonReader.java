package io.github.arungahlawat.automation.core.utils.io.impl.readers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.github.arungahlawat.automation.core.utils.Log;
import io.github.arungahlawat.automation.core.utils.io.FileUtils;
import io.github.arungahlawat.automation.core.utils.io.Reader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonReader implements Reader<JsonElement> {
    private static JsonReader instance;

    private JsonReader() {
    }

    public static JsonReader getInstance() {
        if (instance == null)
            instance = new JsonReader();
        return instance;
    }

    @Override
    public JsonElement read(String filePath) {
        filePath = FileUtils.transformFilePath(filePath);
        try (com.google.gson.stream.JsonReader reader = new Gson().newJsonReader(new FileReader(filePath))) {
            return JsonParser.parseReader(reader);
        } catch (IOException e) {
            Log.warn("Could not read file at path {}. Exception:{}. Trying in resources ...", filePath, e.getMessage());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
                if (inputStream == null)
                    throw new IOException("Could not read resource as stream");
                JsonParser.parseReader(new Gson().newJsonReader(new InputStreamReader(inputStream)));
            } catch (IOException ex) {
                Log.error("Error reading file {} in resources. Error: {}", filePath, ex.getMessage());
                return null;
            }
        }
        return null;
    }

    public String readAsString(String filePath) {
        return read(filePath).toString();
    }
}
