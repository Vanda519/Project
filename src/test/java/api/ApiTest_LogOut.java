package api;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest_LogOut {
    @Test
    @Description("Login with correct email and password then logout via API")
    public void loginAndLogoutAPI() {
        // Step 6-7: Login
        Map<String, String> loginData = new HashMap<>();
        loginData.put("email", "testuser123@test.com");
        loginData.put("password", "123456");

        Response loginResponse = RestAssured
                .given()
                .formParams(loginData)
                .post("https://automationexercise.com/api/login");

        Allure.addAttachment("Login Response", loginResponse.asPrettyString());
        Assert.assertEquals(loginResponse.getStatusCode(), 200);

        // Get session/token from login if needed
        String token = loginResponse.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");

        // Step 9-10: Logout
        Response logoutResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .post("https://automationexercise.com/api/logout");

        Allure.addAttachment("Logout Response", logoutResponse.asPrettyString());
        Assert.assertEquals(logoutResponse.getStatusCode(), 200);
    }
}
