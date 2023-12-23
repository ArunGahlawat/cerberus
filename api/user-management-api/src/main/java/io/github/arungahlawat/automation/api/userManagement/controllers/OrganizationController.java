package io.github.arungahlawat.automation.api.userManagement.controllers;

import io.github.arungahlawat.automation.api.core.utils.Api;
import io.github.arungahlawat.automation.api.userManagement.UserManagementApiConstants;
import io.github.arungahlawat.automation.api.userManagement.enums.Urls;
import io.github.arungahlawat.automation.api.userManagement.requests.MultiIdSearchRequest;
import io.restassured.response.Response;

public class OrganizationController {
    public Response getStaffDetails(MultiIdSearchRequest multiIdSearchRequest, String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .setJsonBody(multiIdSearchRequest)
                .send(Urls.GET_STAFF_DETAILS);
    }

    public Response getIndustries(String key, String token) {
        return new Api()
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_KEY, key)
                .addHeaders(UserManagementApiConstants.HEADER_AUTH_TOKEN, token)
                .send(Urls.GET_INDUSTRIES);
    }
}
