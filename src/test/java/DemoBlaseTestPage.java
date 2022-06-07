import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DemoBlaseTestPage {

    /*
     7 Actions of a Selenium Script:
      1. Instantiate a WebDriver object to drive the browser you want to use in your tests (System.setProperty("webdriver.chrome.driver", "path") or as a WebDriverManager).
      2. Navigate to the web page you want to test (make sure to include "http").
      4. Ensure the browser is in the correct state to interact with that element (e.g. presence of a specific element found by an Id, after WebDriverWait)
      3. Locate the element on the web page you need to interact with
      5. Perform the action on the element
      6. Record the test results
      7. Quit the test
     */

    // Setting up a webdriver variable available to all tests
    static WebDriver driver;
    static FluentWait wait;
    final static String DOMAIN = "https://www.demoblaze.com/";
    final static String SEND_KEYS = "ThisIsTheTest";
    final static String CITY = "Praha";
    final static String MONTH = "1";
    final static String YEAR = "1900";
    final static Integer DELAY = 3;

    @BeforeEach
    public void setClass() {
        // Telling the system where to find chromedriver
        WebDriverManager.chromedriver().setup();

        // 1. Instantiate a webdriver object, set up a wait period
        driver = new ChromeDriver();
        wait = new FluentWait(driver);
        wait.withTimeout(Duration.ofMillis(5000));
        wait.pollingEvery(Duration.ofMillis(10));
        wait.ignoring(NoSuchElementException.class);

        driver.get(DOMAIN);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        // 7. Quit the driver
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void RegistrationTest() throws InterruptedException {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("signin2")));
            driver.findElement(By.id("signin2")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-username")));
            driver.findElement(By.id("sign-username")).click();
            driver.findElement(By.id("sign-username")).sendKeys(SEND_KEYS);
            driver.findElement(By.id("sign-password")).click();
            driver.findElement(By.id("sign-password")).sendKeys(SEND_KEYS);
            driver.findElement(By.cssSelector("#signInModal .btn-primary")).click();
        } catch (Exception ex) {
            throw ex;
        }

        wait.until(ExpectedConditions.alertIsPresent());
//        assertThat(driver.switchTo().alert().getText(), is("Sign up successful."));
        assertThat(driver.switchTo().alert().getText(), is("This user already exist."));
    }

    @Test
    public void LoginTest() throws InterruptedException {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
            driver.findElement(By.id("login2")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));
            driver.findElement(By.id("loginusername")).click();
            driver.findElement(By.id("loginusername")).sendKeys(SEND_KEYS);
            driver.findElement(By.id("loginpassword")).click();
            driver.findElement(By.id("loginpassword")).sendKeys(SEND_KEYS);
            driver.findElement(By.cssSelector("#logInModal .btn-primary")).click();
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (Exception ex) {
            throw ex;
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nameofuser")));
        assertThat(driver.findElement(By.id("nameofuser")).getText(), is("Welcome " + SEND_KEYS));

    }

    @Test
    public void LogoutTest() throws InterruptedException {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
            driver.findElement(By.id("login2")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));
            driver.findElement(By.id("loginusername")).click();
            driver.findElement(By.id("loginusername")).sendKeys(SEND_KEYS);
            driver.findElement(By.id("loginpassword")).click();
            driver.findElement(By.id("loginpassword")).sendKeys(SEND_KEYS);
            driver.findElement(By.cssSelector("#logInModal .btn-primary")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout2")));
            driver.findElement(By.id("logout2")).click();
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (Exception ex) {
            throw ex;
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
        assertThat(driver.findElement(By.id("login2")).getText(), is("Log in"));

    }

    @Test
    public void SendMessageTest() throws InterruptedException {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Contact")));
            driver.findElement(By.linkText("Contact")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("recipient-email")));
            driver.findElement(By.id("recipient-email")).click();
            driver.findElement(By.id("recipient-email")).sendKeys(SEND_KEYS + "@" + SEND_KEYS + ".com");
            driver.findElement(By.id("recipient-name")).click();
            driver.findElement(By.id("recipient-name")).sendKeys(SEND_KEYS);
            driver.findElement(By.id("message-text")).click();
            driver.findElement(By.id("message-text")).sendKeys(SEND_KEYS);
            driver.findElement(By.cssSelector("#exampleModal .btn-primary")).click();
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (Exception ex) {
            throw ex;
        }

        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(driver.switchTo().alert().getText(), is("Thanks for the message!!"));

    }

    @Test
    public void PlayVideoTest() throws InterruptedException {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("About us")));
            driver.findElement(By.linkText("About us")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='vjs-big-play-button']")));
            driver.findElement(By.xpath("//button[@class='vjs-big-play-button']")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-picture-in-picture-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-play-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-picture-in-picture-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-play-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-fullscreen-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-volume-control")).click();
            driver.findElement(By.cssSelector(".vjs-mute-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-mute-control")).click();
            driver.findElement(By.cssSelector(".vjs-fullscreen-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-play-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.cssSelector(".vjs-play-control")).click();
            TimeUnit.SECONDS.sleep(DELAY);
            driver.findElement(By.xpath("//div[@id='videoModal']//button[text()='Close']")).click();
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @ParameterizedTest
    @CsvSource({"Praha,1,1900", "Praha,12,2022", "Londýn,1,2022", "Londýn,12,1900", "Brasília,1,1900", "Brasília,12,2022"})
    public void MakeAnOrderTest(String city, Integer month, Integer year) throws InterruptedException {

        try {

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Samsung galaxy s6')]")));
            driver.findElement(By.xpath("//a[contains(text(), 'Samsung galaxy s6')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-success btn-lg']")));
            driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg']")).click();
            wait.until(ExpectedConditions.alertIsPresent());
            assertThat(driver.switchTo().alert().getText(), containsString("Product added"));
            driver.switchTo().alert().accept();

            driver.findElement(By.xpath("//li[@class='nav-item active']/a")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Sony vaio i5')]")));
            driver.findElement(By.xpath("//a[contains(text(), 'Sony vaio i5')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-success btn-lg']")));
            driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg']")).click();
            wait.until(ExpectedConditions.alertIsPresent());
            assertThat(driver.switchTo().alert().getText(), containsString("Product added"));
            driver.switchTo().alert().accept();

            driver.findElement(By.xpath("//li[@class='nav-item active']/a")).click();
            driver.findElement(By.xpath("//a[contains(text(),'Monitors')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'ASUS Full HD')]")));
            driver.findElement(By.xpath("//a[contains(text(), 'ASUS Full HD')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-success btn-lg']")));
            driver.findElement(By.xpath("//a[@class='btn btn-success btn-lg']")).click();
            wait.until(ExpectedConditions.alertIsPresent());
            assertThat(driver.switchTo().alert().getText(), containsString("Product added"));
            driver.switchTo().alert().accept();

            driver.findElement(By.id("cartur")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Delete")));
            driver.findElement(By.linkText("Delete")).click();
            TimeUnit.SECONDS.sleep(DELAY);

            driver.findElement(By.cssSelector(".btn-success")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
            driver.findElement(By.id("name")).click();
            driver.findElement(By.id("name")).sendKeys(SEND_KEYS);
            driver.findElement(By.cssSelector("#orderModal form")).click();
            driver.findElement(By.id("country")).click();
            driver.findElement(By.id("country")).sendKeys(SEND_KEYS);
            driver.findElement(By.id("city")).click();
            driver.findElement(By.id("city")).sendKeys(city);
            driver.findElement(By.id("card")).sendKeys(SEND_KEYS);
            driver.findElement(By.id("month")).click();
            driver.findElement(By.id("month")).sendKeys(Integer.toString(month));
            driver.findElement(By.id("year")).sendKeys(Integer.toString(year));
            driver.findElement(By.cssSelector("#orderModal .btn-primary")).click();


        } catch (Exception ex) {
            throw ex;
        }

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'sweet-alert')]//h2")));
            assertThat(driver.findElement(By.xpath("//div[contains(@class,'sweet-alert')]//h2")).getText(), is("Thank you for your purchase!"));

            driver.findElement(By.cssSelector(".confirm")).click(); // be aware that this may cause exception

    }

}