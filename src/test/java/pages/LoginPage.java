package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        waitForVisibility(usernameField).clear();
        findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        findElement(passwordField).clear();
        findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        WebElement btn = waitForClickability(loginButton);
        clickElementWithJs(btn);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}
