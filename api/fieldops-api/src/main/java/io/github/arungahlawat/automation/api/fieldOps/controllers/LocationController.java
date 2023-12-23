package io.github.arungahlawat.automation.api.fieldOps.controllers;

import io.github.arungahlawat.automation.api.core.utils.Api;
import io.github.arungahlawat.automation.api.fieldOps.enums.Urls;
import io.restassured.response.Response;

public class LocationController {
    public Response getCityAreas(String cityName, String key, String token) {
        Response response = new Api()
                .addHeaders("UserManagementConstants.HEADER_AUTH_KEY", key)
                .addHeaders("UserManagementConstants.HEADER_AUTH_TOKEN", token)
                .addPathParams("cityName", cityName)
                .send(Urls.GET_CITY_AREAS);
        return response;
    }
}
