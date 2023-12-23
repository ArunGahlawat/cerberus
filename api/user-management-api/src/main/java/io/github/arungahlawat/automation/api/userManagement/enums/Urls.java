package io.github.arungahlawat.automation.api.userManagement.enums;

import io.github.arungahlawat.automation.api.core.enums.Url;
import io.github.arungahlawat.automation.api.userManagement.UserManagementApiConstants;
import io.github.arungahlawat.automation.core.enums.Env;
import io.restassured.http.Method;

public enum Urls implements Url {
    GET_IFSC_DETAILS("/{ifscCode}", Method.GET),
    GET_ACTIVE_CITIES("/cities/active", Method.GET),
    GET_ALL_CITIES("/cities", Method.GET),
    LOGIN("/login", Method.POST,true),
    VALIDATE_OTP("/otp/validate", Method.POST,true),
    GET_INDUSTRIES("/industries", Method.GET),
    GET_STAFF_DETAILS("/staff/multi", Method.POST,true),
    LOGOUT("/logout", Method.GET),
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
        return UserManagementApiConstants.URI.getOrDefault(env, UserManagementApiConstants.URI.getOrDefault(Env.TEST, ""));
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
