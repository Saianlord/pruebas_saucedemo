import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest extends TestBase{

    private WebDriver driver;
//    private static ExtentReports extent;
//    private static ExtentSparkReporter sparkReporter;
    private static ExtentTest test;


//    @BeforeAll
//    public static void beforeAll(){
//        extent = new ExtentReports();
//        sparkReporter = new ExtentSparkReporter("./test-output/ExtentReport.html");
//        extent.attachReporter(sparkReporter);
//    }

    @BeforeEach
    public void setUp() {

        ChromeOptions co = new ChromeOptions();

        co.addArguments("--remote-allow-origins=*");

        System.setProperty("webdriver.chrome.driver", "./src/test/resources/driver/chromedriver");


        driver = new ChromeDriver(co);

        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/v1/");

    }

    @Test
    public void validUserLoginTest() {

        test = ReportManager.getInstance().createTest("Valid User Login Test");

        try {
            WebElement userText = driver.findElement(By.id("user-name"));

            userText.sendKeys("standard_user");

            WebElement passText = driver.findElement(By.id("password"));

            passText.sendKeys("secret_sauce");

            WebElement lgBtn = driver.findElement(By.id("login-button"));

            lgBtn.click();

            String currentURL = driver.getCurrentUrl();

            assertEquals( "https://www.saucedemo.com/v1/inventory.html", currentURL, "valid_user_test");

            test.log(Status.PASS, "First test passed (Login)");

        } catch (AssertionError e) {

            test.log(Status.FAIL, "First test failed (Login)");

            throw e;

        }


    }

    @Test
    public void invalidUserLoginTest() {

        test = ReportManager.getInstance().createTest("Invalid User Login Test");

        try {
            WebElement userText = driver.findElement(By.id("user-name"));

            userText.sendKeys("locked_out_user");

            WebElement passText = driver.findElement(By.id("password"));

            passText.sendKeys("secret_sauce");

            WebElement lgBtn = driver.findElement(By.id("login-button"));

            lgBtn.click();

            WebElement errorMsg = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/form/h3"));

            assertEquals( "Epic sadface: Sorry, this user has been locked out.", errorMsg.getText(), "invalid_user_test");

            test.log(Status.PASS, "Second test passed (Login)");

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Second test failed (Login)");

            throw e;

        }

    }

    @Test
    public void invalidLoginTest() {
        try {
            test = ReportManager.getInstance().createTest("Invalid Login Test");

            WebElement userText = driver.findElement(By.id("user-name"));

            userText.sendKeys("standard_user");

            WebElement passText = driver.findElement(By.id("password"));

            passText.sendKeys("wrong_password");

            WebElement lgBtn = driver.findElement(By.id("login-button"));

            lgBtn.click();

            WebElement errorMsg = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/form/h3"));

            assertEquals( "Epic sadface: Username and password do not match any user in this service", errorMsg.getText(), "invalid_login_test");
            test.log(Status.PASS, "Third test passed (Login)");

        } catch (AssertionError e) {

            test.log(Status.FAIL, "Third test failed (Login)");

            throw e;

        }

    }

    @Test
    public void logoutTest() {

        try {
            test = ReportManager.getInstance().createTest("Logout Test");


            WebElement userText = driver.findElement(By.id("user-name"));

            userText.sendKeys("standard_user");

            WebElement passText = driver.findElement(By.id("password"));

            passText.sendKeys("secret_sauce");

            WebElement lgBtn = driver.findElement(By.id("login-button"));

            lgBtn.click();

            WebElement menuBtn = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[3]/div/button"));

            menuBtn.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement logOutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));

            logOutBtn.click();

            String currentURL = driver.getCurrentUrl();

            assertEquals("https://www.saucedemo.com/v1/index.html", currentURL, "valid_user_test");

            test.log(Status.PASS, "Fourth test passed (Login)");

        } catch (AssertionError e) {

            test.log(Status.FAIL, "Fourth test failed (Login)");

            throw e;
        }


    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }

//    @AfterAll
//    public static void afterAll(){
//        extent.flush();
//    }

}
