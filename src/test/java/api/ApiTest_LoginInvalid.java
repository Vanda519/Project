package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest_LoginInvalid {

    @Test
    public void verifyLoginWithInvalidCredentials() {

        // 🔹 Generate random invalid credentials
        String invalidEmail = "user" + System.currentTimeMillis() + "@test.com";
        String invalidPassword = "InvalidPass" + System.currentTimeMillis();

        // 🔹 Set Base URI
        RestAssured.baseURI = "https://automationexercise.com/api";

        // 🔹 Send POST request to /verifyLogin
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{ \"email\": \"" + invalidEmail + "\", " +
                        "\"password\": \"" + invalidPassword + "\" }")
                .post("/verifyLogin");

        // 🔹 Print info
        System.out.println("Request Email: " + invalidEmail);
        System.out.println("Request Password: " + invalidPassword);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // 🔹 Assertions
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 for invalid login");
        Assert.assertTrue(response.getBody().asString().contains("User not found!"),
                "Response should contain 'User not found!'");
    }
}