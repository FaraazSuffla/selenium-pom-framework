package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators - Step One
    private final By firstNameField  = By.id("first-name");
    private final By lastNameField   = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton  = By.id("continue");
    private final By cancelButton    = By.id("cancel");
    private final By errorMessage    = By.cssSelector("[data-test='error']");

    // Locators - Step Two (Overview)
    private final By finishButton    = By.id("finish");
    private final By summaryTotal    = By.cssSelector(".summary_total_label");

    // Locators - Complete
    private final By confirmationHeader = By.cssSelector(".complete-header");
    private final By confirmationText   = By.cssSelector(".complete-text");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        driver.findElement(postalCodeField).clear();
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void clickCancel() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    public void clickFinish() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public boolean isErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSummaryTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(summaryTotal)).getText();
    }

    public String getConfirmationHeader() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeader)).getText();
    }

    public String getConfirmationText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationText)).getText();
    }

    public boolean isOrderComplete() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}
