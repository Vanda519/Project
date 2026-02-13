package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_AllProducts {

    @Test
    public void getAllProductsList() {
        // Base URI
        RestAssured.baseURI = "https://automationexercise.com";

        // Send GET request
        Response response = given()
                .when()
                .get("/api/productsList")
                .then()
                .extract()
                .response();

        // Verify Response Code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Expected status code 200, but got " + statusCode);

        // Print response body
        String responseBody = response.getBody().asString();
        System.out.println("Response JSON:\n" + responseBody);

        // Optional: check that JSON contains 'products'
        Assert.assertTrue(responseBody.contains("products"), "Response JSON does not contain 'products'");
    }
}