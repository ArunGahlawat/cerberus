package io.github.arungahlawat.automation.api.integrationTests.enums;

import io.github.arungahlawat.automation.api.core.enums.Url;
import io.github.arungahlawat.automation.api.integrationTests.Constants;
import io.github.arungahlawat.automation.core.enums.Env;
import io.restassured.http.Method;

public enum Urls implements Url {
    ;

    final String path;
    final Method method;
    boolean isSensitive = false;

    Urls(String path, Method method) {
        this.path = path;
        this.method = method;
    }

    Urls(String path, Method method, boolean isSensitive) {
        this.path = path;
        this.method = method;
        this.isSensitive = isSensitive;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public String getUri(Env env) {
        return Constants.URI.getOrDefault(env, Constants.URI.getOrDefault(Env.TEST, ""));
    }

    @Override
    public String getUrl() {
        return getUri() + getPath();
    }

    @Override
    public boolean isSensitive() {
        return this.isSensitive;
    }
}
