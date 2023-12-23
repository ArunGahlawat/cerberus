package io.github.arungahlawat.automation.api.userManagement.controllers;

import io.github.arungahlawat.automation.api.core.utils.Api;
import io.github.arungahlawat.automation.api.userManagement.UserManagementApiConstants;
import io.github.arungahlawat.automation.api.userManagement.enums.Urls;
import io.restassured.response.Response;

public class LocationController {
    public Response getActiveCities(String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .send(Urls.GET_ACTIVE_CITIES);
    }

    public Response getAllCities(String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .send(Urls.GET_ALL_CITIES);
    }
}
