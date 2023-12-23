package io.github.arungahlawat.automation.api.userManagement.controllers;

import io.github.arungahlawat.automation.api.core.utils.Api;
import io.github.arungahlawat.automation.api.userManagement.UserManagementApiConstants;
import io.github.arungahlawat.automation.api.userManagement.enums.Urls;
import io.github.arungahlawat.automation.api.userManagement.requests.LoginDetailsRequest;
import io.github.arungahlawat.automation.api.userManagement.requests.LoginRequest;
import io.github.arungahlawat.automation.api.userManagement.responses.StaffDetailsResponse;
import io.github.arungahlawat.automation.core.utils.Log;
import io.restassured.response.Response;
import lombok.Synchronized;

import static io.github.arungahlawat.automation.api.userManagement.utils.UserManagementApiFileUtils.getConfig;

public class AuthController {
    public static final Object getTokenLock = new Object();

    public static String getAuthKey() {
        String env = System.getProperty("env", "test").toUpperCase();
        return getConfig().getString("AUTH_KEY_" + env);
    }

    @Synchronized("getTokenLock")
    public static String getAuthToken() {
        String env = System.getProperty("env", "test").toUpperCase();
        String username = getConfig().getString("AUTH_USERNAME_" + env);
        Log.info("Generating token : {}", username);
        LoginRequest loginRequest = LoginRequest.builder()
                .username(getConfig().getString("AUTH_USERNAME_" + env))
                .password(getConfig().getString("AUTH_PASSWORD_" + env))
                .build();
        Response response = new AuthController().login(loginRequest, getAuthKey());
        response.then().assertThat().statusCode(200);
        StaffDetailsResponse staffDetailsResponse = response.then().extract().as(StaffDetailsResponse.class);
        return staffDetailsResponse.getToken();
    }

    public Response login(LoginRequest loginRequest, String key) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_SOURCE, "supply_app")
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_DEVICE_ID, "undefined")
                .setJsonBody(loginRequest)
                .send(Urls.LOGIN);
    }

    public Response getLoginDetails(LoginDetailsRequest loginDetailsRequest, String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .setJsonBody(loginDetailsRequest)
                .send(Urls.VALIDATE_OTP);
    }

    public Response logout(String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .send(Urls.LOGOUT);
    }
}
