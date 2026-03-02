package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        String browserToUse = System.getProperty("browser", browser);
        DriverManager.initDriver(browserToUse);
        driver = DriverManager.getDriver();

        // Set implicit wait for CI stability — avoids NoSuchElement on slow runners
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Only maximize when not running headless (headless window size is set in DriverManager)
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;
        if (!headless) {
            driver.manage().window().maximize();
        }

        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
