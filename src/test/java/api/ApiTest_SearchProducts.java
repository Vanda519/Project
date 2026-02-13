package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_SearchProducts {

    @Test
    public void verifySearchProductAPI() {

        // Base URI
        RestAssured.baseURI = "https://automationexercise.com";

        String searchValue = "shirt";

        // Send POST request
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", searchValue)
                .when()
                .post("/api/searchProduct")
                .then()
                .extract()
                .response();

        // 1️⃣ Verify Status Code
        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected 200 but got " + response.getStatusCode());

        // 2️⃣ Print JSON Response
        System.out.println("Response JSON:\n" + response.getBody().asString());

        // 3️⃣ Verify JSON contains products
        Assert.assertTrue(response.getBody().asString().contains("products"),
                "Response does not contain products");

        // 4️⃣ Optional: verify searched keyword exists
        Assert.assertTrue(response.getBody().asString().toLowerCase().contains(searchValue),
                "Response does not contain searched keyword");

        System.out.println("Search Product API Test Passed");
    }
}