package io.github.arungahlawat.automation.api.fieldOps.enums;

import io.github.arungahlawat.automation.api.core.enums.Url;
import io.github.arungahlawat.automation.api.fieldOps.FieldOpsApiConstants;
import io.github.arungahlawat.automation.core.enums.Env;
import io.restassured.http.Method;

public enum Urls implements Url {
    GET_CITY_LOCATION("/api/locations/{cityName}", Method.GET),
    GET_CITY_AREAS("/api/{cityName}/areas", Method.GET),
    GET_SUPER_AREAS("/api/superareas", Method.GET),
    GET_USER_AREAS("/api/getuserareas/{userId}", Method.GET),
    GET_HUBS_BY_CITY_CODE("/api/cityhubs", Method.POST),
    ;

    final String path;
    final Method method;
    boolean isSensitive;

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
        return FieldOpsApiConstants.URI.getOrDefault(env, FieldOpsApiConstants.URI.getOrDefault(Env.TEST, ""));
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
