package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected WebDriver driver;
    private static final String SCREENSHOT_DIR = "target/screenshots";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        String browserToUse = System.getProperty("browser", browser);
        DriverManager.initDriver(browserToUse);
        driver = DriverManager.getDriver();

        // Set implicit wait for CI stability
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
    public void tearDown(ITestResult result) {
        // Capture screenshot and page info on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result);
            logPageState(result);
        }
        DriverManager.quitDriver();
    }

    /**
     * Captures a screenshot on test failure and saves it to target/screenshots/
     */
    private void captureScreenshot(ITestResult result) {
        WebDriver currentDriver = DriverManager.getDriver();
        if (currentDriver == null) return;

        try {
            // Create screenshots directory
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(screenshotDir);

            // Generate filename: ClassName_methodName_timestamp.png
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String fileName = result.getTestClass().getRealClass().getSimpleName()
                    + "_" + result.getMethod().getMethodName()
                    + "_" + timestamp + ".png";

            // Take screenshot
            File screenshot = ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.FILE);
            Path destination = screenshotDir.resolve(fileName);
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[SCREENSHOT] Saved: " + destination.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("[SCREENSHOT] Failed to capture: " + e.getMessage());
        }
    }

    /**
     * Logs useful page state on failure for CI debugging
     */
    private void logPageState(ITestResult result) {
        WebDriver currentDriver = DriverManager.getDriver();
        if (currentDriver == null) return;

        try {
            String testName = result.getTestClass().getRealClass().getSimpleName()
                    + "." + result.getMethod().getMethodName();
            System.out.println("=== FAILURE DEBUG INFO: " + testName + " ===");
            System.out.println("[URL]   " + currentDriver.getCurrentUrl());
            System.out.println("[TITLE] " + currentDriver.getTitle());

            // Log page source snippet (first 2000 chars) to help identify page state
            String pageSource = currentDriver.getPageSource();
            if (pageSource != null && pageSource.length() > 2000) {
                pageSource = pageSource.substring(0, 2000) + "\n... [truncated]";
            }
            System.out.println("[PAGE SOURCE]\n" + pageSource);
            System.out.println("=== END DEBUG INFO ===");
        } catch (Exception e) {
            System.out.println("[DEBUG] Failed to log page state: " + e.getMessage());
        }
    }
}
