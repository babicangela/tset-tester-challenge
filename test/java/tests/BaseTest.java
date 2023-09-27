package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.logging.Logger;

public class BaseTest {

    protected WebDriver driver;
    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    private static final String BASE_URL = "http://localhost:3000/";

    @BeforeMethod
    public void setUp() {
        initializeDriver();
        navigateToBaseUrl();
    }

    private void initializeDriver() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--incognito");
            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));

            driver = new ChromeDriver(options);
        } catch (Exception e) {
            logger.severe("Error initializing the driver: " + e.getMessage());
            throw e;
        }
    }

    private void navigateToBaseUrl() {
        try {
            driver.get(BASE_URL);
        } catch (Exception e) {
            logger.severe("Error navigating to URL: " + BASE_URL + ". Error: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        closeDriver();
    }

    private void closeDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.severe("Error closing the driver: " + e.getMessage());
            }
        }
    }
}
