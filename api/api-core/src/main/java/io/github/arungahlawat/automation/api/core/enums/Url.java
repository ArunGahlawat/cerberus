package io.github.arungahlawat.automation.api.core.enums;

import io.github.arungahlawat.automation.core.enums.Env;
import io.restassured.http.Method;

public interface Url {
    String getPath();
    Method getMethod();
    String getUri(Env env);
    String getUrl();
    boolean isSensitive();

    default String getUri() {
        Env currentEnv;
        try {
            currentEnv = Env.valueOf(System.getProperty("env", "test").toUpperCase());
        } catch (IllegalArgumentException exception) {
            currentEnv = Env.TEST;
        }
        return getUri(currentEnv);
    }
}
