package api;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest_RegisterWithExistingEmail {
    @Test
    @Description("Attempt to register user with existing email")
    public void registerExistingEmailAPI() {

        Map<String, String> data = new HashMap<>();
        data.put("name", "Test User");
        data.put("email", "testuser123@test.com"); // already registered
        data.put("password", "123456");

        Response response = RestAssured
                .given()
                .formParams(data)
                .post("https://automationexercise.com/api/createAccount");

        Allure.addAttachment("Register Existing Email Response", response.asPrettyString());


        Assert.assertEquals(response.getStatusCode(), 409, "Status code for existing email");
        Assert.assertTrue(response.asString().contains("Email Address already exist!"), "Error message visible");
    }
}
