package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest1_Register {

    WebDriver driver;
    WebDriverWait wait;
    String email;

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
    public void registerUserTest() {

        email = "Amelia123@gmail.com";  // ფიქსირებული მეილი

        // 3️⃣ Verify Home Page
        wait.until(ExpectedConditions.titleContains("Automation"));
        Assert.assertTrue(driver.getTitle().contains("Automation"));

        // 4️⃣ Click Signup/Login
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // 5️⃣ Verify 'New User Signup!' is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='New User Signup!']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//h2[text()='New User Signup!']")).isDisplayed());

        // 6️⃣ Enter name & email
        driver.findElement(By.name("name")).sendKeys("Amelia");
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();

        // 8️⃣ Verify ENTER ACCOUNT INFORMATION
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Enter Account Information']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//b[text()='Enter Account Information']")).isDisplayed());

        // 9️⃣ Fill Account Information
        driver.findElement(By.id("id_gender2")).click();
        driver.findElement(By.id("password")).sendKeys("Amelia1234");

        new Select(driver.findElement(By.id("days"))).selectByValue("10");
        new Select(driver.findElement(By.id("months"))).selectByValue("5");
        new Select(driver.findElement(By.id("years"))).selectByValue("1999");

        // 10️⃣ 11️⃣ Checkboxes
        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();

        // 12️⃣ Address Details
        driver.findElement(By.id("first_name")).sendKeys("Amelia");
        driver.findElement(By.id("last_name")).sendKeys("Smith");
        driver.findElement(By.id("company")).sendKeys("TestCompany");
        driver.findElement(By.id("address1")).sendKeys("Street 1");
        driver.findElement(By.id("address2")).sendKeys("Apartment 10");

        new Select(driver.findElement(By.id("country"))).selectByVisibleText("India");

        driver.findElement(By.id("state")).sendKeys("Bihar");
        driver.findElement(By.id("city")).sendKeys("Patna");
        driver.findElement(By.id("zipcode")).sendKeys("12345");
        driver.findElement(By.id("mobile_number")).sendKeys("595123456");

        // 13️⃣ Click Create Account
        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();

        // 14️⃣ Verify ACCOUNT CREATED
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Account Created!']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//b[text()='Account Created!']")).isDisplayed());


        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Logged in as')]")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//a[contains(text(),'Logged in as')]")).isDisplayed());

        driver.findElement(By.xpath("//button[@data-qa='continue-button']")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Delete Account')]"))).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Account Deleted!']")));

        Assert.assertTrue(driver.findElement(
                By.xpath("//b[text()='Account Deleted!']")).isDisplayed());


        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();

        System.out.println("Account successfully created and deleted.");

    }}