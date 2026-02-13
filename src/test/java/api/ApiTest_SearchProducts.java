package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_SearchProducts {
    @Test
    public void verifySearchProductAPI() {


        RestAssured.baseURI = "https://automationexercise.com";

        String searchValue = "shirt";


        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", searchValue)
                .when()
                .post("/api/searchProduct")
                .then()
                .extract()
                .response();


        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected 200 but got " + response.getStatusCode());


        System.out.println("Response JSON:\n" + response.getBody().asString());


        Assert.assertTrue(response.getBody().asString().contains("products"),
                "Response does not contain products");

        
        Assert.assertTrue(response.getBody().asString().toLowerCase().contains(searchValue),
                "Response does not contain searched keyword");

        System.out.println("Search Product API Test Passed");
    }
}