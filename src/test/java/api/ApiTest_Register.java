package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest_Register {

    @Test
    public void createUserAPI() {

        RestAssured.baseURI = "https://automationexercise.com";

        String email = "vanda" + System.currentTimeMillis() + "@test.com";

        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("name", "Amelia")
                        .formParam("email", email)
                        .formParam("password", "Amelia1234")
                        .formParam("title", "Mrs")
                        .formParam("birth_date", "10")
                        .formParam("birth_month", "5")
                        .formParam("birth_year", "2000")
                        .formParam("firstname", "Amelia")
                        .formParam("lastname", "Smith")
                        .formParam("company", "TestCompany")
                        .formParam("address1", "Street 1")
                        .formParam("address2", "Apartment 10")
                        .formParam("country", "India")
                        .formParam("zipcode", "12345")
                        .formParam("state", "Bihar")
                        .formParam("city", "Patna")
                        .formParam("mobile_number", "595123456")
                        .when()
                        .post("/api/createAccount")
                        .then()
                        .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("User created!"));
    }
}