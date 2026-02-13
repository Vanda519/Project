package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class UITest_AllProducts {

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    public void verifyAllProductsAndDetailPage() {

        WebElement homeSlider = waitForElementVisible(By.id("slider"), 10);
        Assert.assertTrue(homeSlider.isDisplayed(), "Home page is not visible");


        driver.findElement(By.cssSelector("a[href='/products']")).click();


        WebElement allProductsHeader = waitForElementVisible(By.xpath("//h2[text()='All Products']"), 10);
        Assert.assertTrue(allProductsHeader.isDisplayed(), "All Products page is not visible");
        Assert.assertTrue(driver.getCurrentUrl().contains("/products"), "URL does not contain '/products'");
        System.out.println("Navigated to All Products page successfully");


        List<WebElement> productList = driver.findElements(By.cssSelector(".product-image-wrapper"));
        Assert.assertTrue(productList.size() > 0, "Products list is not visible");
        System.out.println("Products list is visible, count: " + productList.size());


        WebElement firstViewProduct = productList.get(0).findElement(By.linkText("View Product"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstViewProduct);


        WebElement productDetail = waitForElementVisible(By.cssSelector(".product-information"), 10);
        Assert.assertTrue(productDetail.isDisplayed(), "Product detail page is not visible");
        System.out.println("Product detail page is visible");


        Assert.assertTrue(waitForElementVisible(By.xpath("//h2"), 5).isDisplayed(), "Product name not visible");
        Assert.assertTrue(waitForElementVisible(By.xpath("//p[contains(text(),'Category')]"), 5).isDisplayed(), "Category not visible");
        Assert.assertTrue(waitForElementVisible(By.xpath("//span[@class='price']"), 5).isDisplayed(), "Price not visible");
        Assert.assertTrue(waitForElementVisible(By.xpath("//p[contains(text(),'Availability')]"), 5).isDisplayed(), "Availability not visible");
        Assert.assertTrue(waitForElementVisible(By.xpath("//p[contains(text(),'Condition')]"), 5).isDisplayed(), "Condition not visible");
        Assert.assertTrue(waitForElementVisible(By.xpath("//p[contains(text(),'Brand')]"), 5).isDisplayed(), "Brand not visible");

        System.out.println("All product details verified successfully");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}