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

        // Base URI
        RestAssured.baseURI = "http://automationexercise.com";

        // ფაილი, რომელიც უნდა ატვირთო
        File file = new File("src/main/resources/textfile.txt");
        System.out.println("File exists: " + file.exists() + ", length: " + file.length());

        Assert.assertTrue(file.exists(), "Test file does not exist!");

        // API Request
        Response response = given()
                .multiPart("name", "Amelia")
                .multiPart("email", "amelia123@gmail.com")
                .multiPart("subject", "Product")
                .multiPart("message", "Hello, I have one problem...")
                .multiPart("upload_file", file)
                .when()
                .post("/contact_us");

        // Debugging: ნახე response
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.asString());

        // Assert Status Code 200 (სწორი წარმატებული პასუხისთვის)
        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code!");

        // Assert Response Body contains success message
        Assert.assertTrue(response.asString().contains("Success! Your details have been submitted successfully."),
                "Success message not found in response!");
    }
}