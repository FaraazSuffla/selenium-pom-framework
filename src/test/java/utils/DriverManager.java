package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        // Detect headless mode — enabled via -Dheadless=true or in CI environment
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                // If SE_CHROME is set (e.g. by browser-actions/setup-chrome in CI), point
                // ChromeDriver at that binary so Selenium Manager downloads the matching driver
                // version rather than searching for Chrome itself.
                String chromeBinary = System.getenv("SE_CHROME");
                if (chromeBinary != null && !chromeBinary.isEmpty()) {
                    chromeOptions.setBinary(chromeBinary);
                }
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-renderer-backgrounding");
                    chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
                    chromeOptions.addArguments("--disable-search-engine-choice-screen");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                driver.set(new ChromeDriver(chromeOptions));
                break;
        }
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
