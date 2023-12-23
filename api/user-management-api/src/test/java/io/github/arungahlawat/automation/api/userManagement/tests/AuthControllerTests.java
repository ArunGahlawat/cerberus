package io.github.arungahlawat.automation.api.userManagement.tests;

import io.github.arungahlawat.automation.api.userManagement.controllers.AuthController;
import io.github.arungahlawat.automation.api.userManagement.requests.LoginRequest;
import io.github.arungahlawat.automation.api.userManagement.responses.StaffDetailsResponse;
import io.restassured.response.Response;
import org.apache.commons.configuration2.Configuration;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.github.arungahlawat.automation.api.userManagement.utils.UserManagementApiFileUtils.getConfig;

public class AuthControllerTests {
    private static final Configuration userManagementConfig = getConfig();

    @Test(description = "Verify logging in using username and password", groups = {"SMOKE"})
    public void verifyLoginUsingUsernameAndPassword() {
        AuthController authController = new AuthController();
        String env = System.getProperty("env", "test").toUpperCase();
        LoginRequest loginRequest = LoginRequest.builder()
                .username(userManagementConfig.getString("AUTH_USERNAME_" + env))
                .password(userManagementConfig.getString("AUTH_PASSWORD_" + env))
                .build();
        Response response = authController.login(loginRequest, AuthController.getAuthKey());
        response.then().assertThat().statusCode(200);
        StaffDetailsResponse staffDetailsResponse = response.then().extract().as(StaffDetailsResponse.class);
        Assert.assertNotNull(staffDetailsResponse.getToken(), "Token");
        response = authController.logout(AuthController.getAuthKey(), staffDetailsResponse.getToken());
        response.then().assertThat().statusCode(200);
        response.then().body("message", equalTo("Logged out"));
    }
}
