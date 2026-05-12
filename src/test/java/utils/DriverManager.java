package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Manages WebDriver lifecycle with thread-safe storage.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initializes a WebDriver instance for the specified browser.
     */
    public static void initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;

        switch (browser.toLowerCase()) {
            case "firefox":
                driver.set(createFirefoxDriver(headless));
                break;
            case "chrome":
            default:
                driver.set(createChromeDriver(headless));
                break;
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        
        String chromeBinary = System.getenv("SE_CHROME");
        if (chromeBinary != null && !chromeBinary.isEmpty()) {
            chromeOptions.setBinary(chromeBinary);
        }
        
        if (headless) {
            configureHeadlessChrome(chromeOptions);
        } else {
            chromeOptions.addArguments("--start-maximized");
        }
        
        return new ChromeDriver(chromeOptions);
    }

    private static void configureHeadlessChrome(ChromeOptions options) {
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-search-engine-choice-screen");
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        
        if (headless) {
            firefoxOptions.addArguments("-headless");
            firefoxOptions.addArguments("--width=1920");
            firefoxOptions.addArguments("--height=1080");
        }
        
        return new FirefoxDriver(firefoxOptions);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
