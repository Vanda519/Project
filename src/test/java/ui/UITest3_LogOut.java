package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest3_LogOut {

    WebDriver driver;
    WebDriverWait wait;
    String email = "Amelia123@gmail.com";
    String password = "Amelia1234";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://automationexercise.com");
    }

    @Test
    public void loginUserOnce() {


        wait.until(ExpectedConditions.titleContains("Automation"));
        Assert.assertTrue(driver.getTitle().contains("Automation"));


        driver.findElement(By.cssSelector("a[href='/login']")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='Login to your account']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//h2[text()='Login to your account']")).isDisplayed());


        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);


        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Logged in as')]")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//a[contains(text(),'Logged in as')]")).isDisplayed());


         driver.findElement(By.linkText("Logout")).click();
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Login to your account']")));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}