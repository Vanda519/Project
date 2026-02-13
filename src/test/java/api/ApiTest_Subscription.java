package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_Subscription {

    @Test
    public void verifySubscriptionAPI() {

        RestAssured.baseURI = "https://automationexercise.com";

        String email = "test" + System.currentTimeMillis() + "@mail.com";

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .when()
                .post("/api/subscribe")
                .then()
                .extract()
                .response();

        // Verify 200 status
        Assert.assertEquals(response.getStatusCode(), 200);

        System.out.println("Response: " + response.getBody().asString());

        Assert.assertTrue(response.getBody().asString()
                        .contains("success"),
                "Subscription API failed");

        System.out.println("Subscription API Test Passed");
    }
}