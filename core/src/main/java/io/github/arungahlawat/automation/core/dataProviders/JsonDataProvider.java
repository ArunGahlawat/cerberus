package io.github.arungahlawat.automation.core.dataProviders;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.github.arungahlawat.automation.core.utils.io.impl.readers.JsonReader;

import java.util.Iterator;
import java.util.List;

public class JsonDataProvider {
    public <T> Iterator<T[]> getProvider(String filePath, int dateFormat, int timeFormat,
                                         ExclusionStrategy serializationExclusionStrategy,
                                         ExclusionStrategy deSerializationExclusionStrategy,
                                         FieldNamingStrategy fieldNamingStrategy,
                                         boolean disableHtmlEscaping) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat(dateFormat, timeFormat)
                .addSerializationExclusionStrategy(serializationExclusionStrategy)
                .addDeserializationExclusionStrategy(deSerializationExclusionStrategy)
                .setFieldNamingStrategy(fieldNamingStrategy);
        if (disableHtmlEscaping)
            gsonBuilder.disableHtmlEscaping();
        return getProvider(gsonBuilder, filePath);
    }

    public <T> Iterator<T> getProvider(GsonBuilder gsonBuilder, String filePath) {
        JsonElement jsonElement = JsonReader.getInstance().read(filePath);
        Gson gson = gsonBuilder.create();
        List<T> rowData = gson.fromJson(jsonElement, new TypeToken<List<T>>() {
        }.getType());
        return rowData.iterator();
    }
}
