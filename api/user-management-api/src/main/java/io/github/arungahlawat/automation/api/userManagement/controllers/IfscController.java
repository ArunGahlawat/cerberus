package io.github.arungahlawat.automation.api.userManagement.controllers;

import io.github.arungahlawat.automation.api.core.utils.Api;
import io.github.arungahlawat.automation.api.userManagement.UserManagementApiConstants;
import io.github.arungahlawat.automation.api.userManagement.enums.Urls;
import io.restassured.response.Response;

public class IfscController {
    public Response getIfscDetails(String ifscCode, String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .addPathParams("ifscCode", ifscCode)
                .send(Urls.GET_IFSC_DETAILS);
    }
}