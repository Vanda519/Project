package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest_Subscription {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("http://automationexercise.com");
    }

    public WebElement waitVisible(By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    public void verifySubscriptionInHomePage() {

        // 1️⃣ Verify Home Page is visible
        Assert.assertTrue(waitVisible(By.id("slider"), 10).isDisplayed(),
                "Home page is not visible");

        // 2️⃣ Scroll to Footer
        WebElement footer = waitVisible(By.id("footer"), 10);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", footer);

        // 3️⃣ Verify 'SUBSCRIPTION' text
        WebElement subscriptionHeader = waitVisible(
                By.xpath("//h2[contains(text(),'Subscription')]"), 10);

        Assert.assertTrue(subscriptionHeader.isDisplayed(),
                "Subscription header not visible");

        // 4️⃣ Enter Specific Email
        String email = "Amelia123@gmail.com";

        WebElement emailInput = waitVisible(By.id("susbscribe_email"), 10);
        emailInput.clear();
        emailInput.sendKeys(email);

        driver.findElement(By.id("subscribe")).click();

        // 5️⃣ Verify Success OR Already Exist message
        WebElement message = waitVisible(
                By.xpath("//*[contains(text(),'successfully subscribed') or contains(text(),'already exist')]"),
                10);

        Assert.assertTrue(message.isDisplayed(),
                "Subscription message not displayed");

        System.out.println("Subscription message: " + message.getText());
        System.out.println("Subscription Test Passed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}