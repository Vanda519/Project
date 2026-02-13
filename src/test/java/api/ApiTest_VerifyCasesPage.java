package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_VerifyCasesPage {

    @Test
    public void verifyTestCasesListAPI() {

        // Base URI
        RestAssured.baseURI = "https://automationexercise.com";

        // Send GET request
        Response response =
                given()
                        .when()
                        .get("/api/testCasesList")
                        .then()
                        .extract()
                        .response();

        // 1️⃣ Verify Status Code
        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected status code 200");

        // 2️⃣ Print response
        System.out.println("Response Body:\n" + response.getBody().asString());

        // 3️⃣ Verify response contains test cases
        Assert.assertTrue(response.getBody().asString().contains("test_cases"),
                "Response does not contain test cases");

        // 4️⃣ Optional: Verify count > 0
        int testCaseCount = response.jsonPath().getList("test_cases").size();
        Assert.assertTrue(testCaseCount > 0,
                "Test cases list is empty");

        System.out.println("Total Test Cases: " + testCaseCount);
        System.out.println("Test Cases API Test Passed");
    }
}