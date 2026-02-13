package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UITest5_LoginInvalid {

    WebDriver driver;
    WebDriverWait wait;

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
    public void loginWithInvalidCredentials() {

        // 🔹 Generate truly random invalid credentials
        String invalidEmail = "user" + System.currentTimeMillis() + "@test.com";
        String invalidPassword = "InvalidPass" + System.currentTimeMillis();

        // 3️⃣ Verify Home Page
        wait.until(ExpectedConditions.titleContains("Automation"));
        Assert.assertTrue(driver.getTitle().contains("Automation"));

        // 4️⃣ Click Signup/Login
        driver.findElement(By.cssSelector("a[href='/login']")).click();

        // 5️⃣ Verify 'Login to your account' is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='Login to your account']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//h2[text()='Login to your account']")).isDisplayed());

        // 6️⃣ Enter incorrect email & password
        driver.findElement(By.xpath("//input[@data-qa='login-email']")).sendKeys(invalidEmail);
        driver.findElement(By.xpath("//input[@data-qa='login-password']")).sendKeys(invalidPassword);

        // 7️⃣ Click login
        driver.findElement(By.xpath("//button[@data-qa='login-button']")).click();

        // 8️⃣ Verify error message
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='Your email or password is incorrect!']")));
        Assert.assertTrue(driver.findElement(
                By.xpath("//*[text()='Your email or password is incorrect!']")).isDisplayed());

        System.out.println("Tested invalid login with:");
        System.out.println("Email: " + invalidEmail);
        System.out.println("Password: " + invalidPassword);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}