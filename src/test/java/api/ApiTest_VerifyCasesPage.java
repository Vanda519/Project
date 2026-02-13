package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_VerifyCasesPage {

    @Test
    public void verifyTestCasesListAPI() {


        RestAssured.baseURI = "https://automationexercise.com";


        Response response =
                given()
                        .when()
                        .get("/api/testCasesList")
                        .then()
                        .extract()
                        .response();


        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected status code 200");


        System.out.println("Response Body:\n" + response.getBody().asString());


        Assert.assertTrue(response.getBody().asString().contains("test_cases"),
                "Response does not contain test cases");


        int testCaseCount = response.jsonPath().getList("test_cases").size();
        Assert.assertTrue(testCaseCount > 0,
                "Test cases list is empty");

        System.out.println("Total Test Cases: " + testCaseCount);
        System.out.println("Test Cases API Test Passed");
    }
}