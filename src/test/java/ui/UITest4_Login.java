package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest4_Login {

    WebDriver driver;
    WebDriverWait wait;
    String email = "Amelia123@gmail.com";   // ფიქსირებული სწორი Email
    String password = "Amelia1234";         // ფიქსირებული სწორი Password

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

        // Verify Home Page
        wait.until(ExpectedConditions.titleContains("Automation"));
        Assert.assertTrue(driver.getTitle().contains("Automation"));

        // Click Signup/Login
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // Verify 'Login to your account' is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='Login to your account']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//h2[text()='Login to your account']")).isDisplayed());

        // Enter Email & Password
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(password);

        // Click Login
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        // Verify 'Logged in as username'
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Logged in as')]")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//a[contains(text(),'Logged in as')]")).isDisplayed());

        // Delete Account after login
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Delete Account')]"))).click();

// Verify Account Deleted
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Account Deleted!']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//b[text()='Account Deleted!']")).isDisplayed());

// Click Continue after delete
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();
        System.out.println("Account successfully deleted after login.");

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}