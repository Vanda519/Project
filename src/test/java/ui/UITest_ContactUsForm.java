package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

public class UITest_ContactUsForm {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("http://automationexercise.com");
    }

    // Explicit wait for element to be clickable
    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Explicit wait for element to be visible
    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    public void contactUsTestStable() throws InterruptedException {
        // Verify Home Page
        Assert.assertTrue(driver.getTitle().contains("Automation"));

        // Click 'Contact Us'
        WebElement contactUsLink = waitForElementClickable(By.linkText("Contact us"), 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", contactUsLink);

        // Verify 'GET IN TOUCH'
        waitForElementVisible(By.xpath("//h2[text()='Get In Touch']"), 10);

        // Fill details
        waitForElementVisible(By.name("name"), 10).sendKeys("Amelia");
        waitForElementVisible(By.name("email"), 10).sendKeys("amelia123@gmail.com");
        waitForElementVisible(By.name("subject"), 10).sendKeys("Product");
        waitForElementVisible(By.name("message"), 10).sendKeys("Hello, I have one problem...");

        // Upload file
        File file = new File("src/main/resources/textfile.txt");
        System.out.println("File exists: " + file.exists() + ", path: " + file.getAbsolutePath());
        Assert.assertTrue(file.exists(), "File not found!");

        WebElement uploadElement = waitForElementVisible(By.name("upload_file"), 10);
        uploadElement.sendKeys(file.getAbsolutePath());
        Thread.sleep(500); // tiny wait for attachment

        // Click Submit using JavaScript
        WebElement submitButton = waitForElementClickable(By.name("submit"), 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", submitButton);

        // Handle Alert (Click OK)
        try {
            WebDriverWait waitAlert = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = waitAlert.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert text: " + alert.getText());
            alert.accept(); // Click OK
        } catch (TimeoutException e) {
            System.out.println("No alert appeared.");
        }

        // Wait for success message
        WebElement successMessage = waitForElementVisible(By.xpath("//div[contains(@class,'status alert-success')]"), 15);
        Assert.assertTrue(successMessage.isDisplayed(), "Success message is not visible!");
        Assert.assertTrue(successMessage.getText().contains("Success! Your details have been submitted successfully."),
                "Success text does not match!");
        System.out.println("Success message verified: " + successMessage.getText());

        // Click Home button by link text using JS
        // Click Home button directly by link text
        WebElement homeButton = waitForElementClickable(By.linkText("Home"), 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", homeButton);

// Verify Home Page loaded
        waitForElementVisible(By.id("slider"), 10);
        Assert.assertTrue(driver.getTitle().contains("Automation"));
        System.out.println("Returned to Home Page successfully");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}