package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest_LoginInvalid {

    @Test
    public void verifyLoginWithInvalidCredentials() {


        String invalidEmail = "user" + System.currentTimeMillis() + "@test.com";
        String invalidPassword = "InvalidPass" + System.currentTimeMillis();


        RestAssured.baseURI = "https://automationexercise.com/api";


        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{ \"email\": \"" + invalidEmail + "\", " +
                        "\"password\": \"" + invalidPassword + "\" }")
                .post("/verifyLogin");


        System.out.println("Request Email: " + invalidEmail);
        System.out.println("Request Password: " + invalidPassword);
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());


        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 for invalid login");
        Assert.assertTrue(response.getBody().asString().contains("User not found!"),
                "Response should contain 'User not found!'");
    }
}