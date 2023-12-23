package io.github.arungahlawat.automation.api.core.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;

import java.lang.reflect.Type;

public class Jackson2ObjectMapperFactoryHelper implements Jackson2ObjectMapperFactory {

    @Override
    public ObjectMapper create(Type type, String s) {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.parseBoolean(System.getProperty("FAIL_ON_UNKNOWN_PROPERTIES", "false")));
        return objectMapper;
    }
}
