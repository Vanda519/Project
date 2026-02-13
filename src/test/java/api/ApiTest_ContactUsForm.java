package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ApiTest_ContactUsForm {

    @Test
    public void contactUsFormAPI() {


        RestAssured.baseURI = "http://automationexercise.com";


        File file = new File("src/main/resources/textfile.txt");
        System.out.println("File exists: " + file.exists() + ", length: " + file.length());

        Assert.assertTrue(file.exists(), "Test file does not exist!");


        Response response = given()
                .multiPart("name", "Amelia")
                .multiPart("email", "amelia123@gmail.com")
                .multiPart("subject", "Product")
                .multiPart("message", "Hello, I have one problem...")
                .multiPart("upload_file", file)
                .when()
                .post("/contact_us");


        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.asString());


        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code!");


        Assert.assertTrue(response.asString().contains("Success! Your details have been submitted successfully."),
                "Success message not found in response!");
    }
}