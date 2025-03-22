
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PurchaseTest extends TestBase {

    private WebDriver driver;

    private static ExtentTest test;


    @BeforeEach
    public void setUp(){

        ChromeOptions co = new ChromeOptions();

        co.addArguments("--remote-allow-origins=*");

        System.setProperty("webdriver.chrome.driver", "./src/test/resources/driver/chromedriver");

        driver = new ChromeDriver(co);

        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/v1/");

        WebElement userText = driver.findElement(By.id("user-name"));

        userText.sendKeys("standard_user");

        WebElement passText = driver.findElement(By.id("password"));

        passText.sendKeys("secret_sauce");

        WebElement lgBtn = driver.findElement(By.id("login-button"));

        lgBtn.click();

    }

    @Test
    public void addToCartTest(){

        test = ReportManager.getInstance().createTest("Add to cart test");


        try {
            WebElement cart = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/a"));

            List<WebElement> addButtons = driver.findElements(By.cssSelector(".btn_primary.btn_inventory"));

            if (!addButtons.isEmpty()) {
                addButtons.getFirst().click();
            }

            cart.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("cart_item")));

            assertFalse(products.isEmpty());

            test.log(Status.PASS, "First test passed (Purchase)");

        } catch (AssertionError e) {

            test.log(Status.FAIL, "First test failed (Purchase)");

            throw e;
        }

    }

    @Test
    public void removeToCartTest(){

        test = ReportManager.getInstance().createTest("Remove from cart test");


        try {
            WebElement cart = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/a"));

            List<WebElement> addButtons = driver.findElements(By.cssSelector(".btn_primary.btn_inventory"));

            if (!addButtons.isEmpty()) {
                addButtons.getFirst().click();
            }

            cart.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("cart_item")));

            if(!products.isEmpty()){
                products.getFirst().findElement(By.cssSelector(".btn_secondary.cart_button")).click();
            }

            products = driver.findElements(By.className("cart_item"));

            assertTrue(products.isEmpty());

            test.log(Status.PASS, "Second test passed (Purchase)");

        } catch (AssertionError e) {

            test.log(Status.FAIL, "Second test failed (Purchase)");

            throw e;
        }


    }

    @Test
    public void invalidCheckout(){

        test = ReportManager.getInstance().createTest("Invalid checkout test");


        try {
            WebElement cart = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/a"));

            cart.click();

            WebElement checkOutBtn = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div[2]/a[2]"));

            List<WebElement> products = driver.findElements(By.className("cart_item"));

            if(products.isEmpty()){
                checkOutBtn.click();
            }

            String currentURL = driver.getCurrentUrl();

            assertNotEquals( "https://www.saucedemo.com/v1/checkout-step-one.html", currentURL, "invalid_checkout_test");

            test.log(Status.PASS, "Third test passed (Purchase)");

        } catch (AssertionError e) {

            test.log(Status.FAIL, "Third test failed (Purchase)");

            throw e;
        }


    }

    @Test
    public void validCheckout(){

        test = ReportManager.getInstance().createTest("valid checkout test");


        try {
            WebElement cart = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/a"));

            List<WebElement> addButtons = driver.findElements(By.cssSelector(".btn_primary.btn_inventory"));

            if (!addButtons.isEmpty()) {
                addButtons.getFirst().click();
            }

            cart.click();

            WebElement checkOutBtn = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div[2]/a[2]"));

            List<WebElement> products = driver.findElements(By.className("cart_item"));

            if(!products.isEmpty()){
                checkOutBtn.click();
            }

            String currentURL = driver.getCurrentUrl();

            assertEquals( "https://www.saucedemo.com/v1/checkout-step-one.html", currentURL, "valid_checkout_test");

            test.log(Status.PASS, "Fourth test passed (Purchase)");

        } catch (Exception e) {

            test.log(Status.FAIL, "Fourth test failed (Purchase)");

            throw e;
        }


    }



    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
}
