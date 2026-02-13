package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class UITest_SearchProducts {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://automationexercise.com");
    }

    public WebElement waitVisible(By locator, int time) {
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitClickable(By locator, int time) {
        return new WebDriverWait(driver, Duration.ofSeconds(time))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Test
    public void verifySearchProduct() {

        // 3. Verify Home page
        Assert.assertTrue(waitVisible(By.id("slider"), 10).isDisplayed());

        // 4. Click Products
        driver.findElement(By.cssSelector("a[href='/products']")).click();

        // 5. Verify ALL PRODUCTS page
        Assert.assertTrue(waitVisible(By.xpath("//h2[text()='All Products']"), 10).isDisplayed());

        // 6. Enter product name and search
        String productName = "Colour Blocked Shirt – Sky Blue";

        waitVisible(By.id("search_product"), 10).sendKeys(productName);
        waitClickable(By.id("submit_search"), 10).click();

        // 7. Verify SEARCHED PRODUCTS visible
        WebElement searchedHeader = waitVisible(
                By.xpath("//h2[text()='Searched Products']"), 10);

        Assert.assertTrue(searchedHeader.isDisplayed());

// Scroll down to results
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", searchedHeader);

// 8. Verify related products visible
        List<WebElement> searchedProducts =
                waitVisible(By.cssSelector(".features_items"), 10)
                        .findElements(By.cssSelector(".product-image-wrapper"));

        Assert.assertTrue(searchedProducts.size() > 0,
                "No searched products displayed");

        for (WebElement product : searchedProducts) {
            Assert.assertTrue(product.getText().toLowerCase().contains("shirt"),
                    "Product not related to search");
        }

        System.out.println("Search Product UI test passed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}