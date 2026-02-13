package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest_VerifyCasesPage {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("http://automationexercise.com");
    }

    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    public void verifyTestCasesPage() {

        // 1️⃣ Verify home page
        WebElement homeSlider = waitForElementVisible(By.id("slider"), 10);
        Assert.assertTrue(homeSlider.isDisplayed(), "Home page is not visible");

        // 2️⃣ Click Test Cases
        WebElement testCasesButton = waitForElementClickable(By.linkText("Test Cases"), 10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", testCasesButton);

        // 3️⃣ Verify navigation
        WebElement testCasesHeader = waitForElementVisible(
                By.xpath("//h2[text()='Test Cases']"), 10);

        Assert.assertTrue(testCasesHeader.isDisplayed(), "Test Cases page is not visible");
        Assert.assertTrue(driver.getCurrentUrl().contains("/test_cases"),
                "URL does not contain '/test_cases'");

        // 4️⃣ Scroll down slightly to show cases clearly
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(0,400);");

        // 5️⃣ Verify test cases list is visible
        WebElement testCasesList = waitForElementVisible(
                By.className("panel-group"), 10);

        Assert.assertTrue(testCasesList.isDisplayed(),
                "Test cases list is not visible");

        System.out.println("Successfully navigated and scrolled to Test Cases page");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}