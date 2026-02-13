package api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest_VerifyLogin {

    @Test
    public void verifyLogin() {
        String email = "Amelia123@gmail.com";
        String password = "Amelia1234";

        // Request body
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        // POST request to /verifyLogin
        Response response = RestAssured.given()
                .baseUri("https://automationexercise.com/api")
                .contentType(ContentType.JSON)
                .body(body)
                .post("/verifyLogin");

        // Assertions
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), "User exists!");

        System.out.println("API Response: " + response.asString());
    }
}
