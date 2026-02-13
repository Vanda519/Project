package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest2_RegisterWithExistingEmail {

    WebDriver driver;
    WebDriverWait wait;

    String name = "Amelia";
    String existingEmail = "Amelia123@gmail.com";

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
    public void registerWithExistingEmail() {


        wait.until(ExpectedConditions.titleContains("Automation"));
        Assert.assertTrue(driver.getTitle().contains("Automation"));


        driver.findElement(By.cssSelector("a[href='/login']")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='New User Signup!']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//h2[text()='New User Signup!']")).isDisplayed());


        driver.findElement(By.name("name")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']"))
                .sendKeys(existingEmail);


        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[text()='Email Address already exist!']")));

        Assert.assertTrue(driver.findElement(
                        By.xpath("//p[text()='Email Address already exist!']"))
                .isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}