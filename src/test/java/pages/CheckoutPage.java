package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    private final WebDriver driver;

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
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).clear();
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
        driver.findElement(continueButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isErrorDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSummaryTotal() {
        return driver.findElement(summaryTotal).getText();
    }

    public String getConfirmationHeader() {
        return driver.findElement(confirmationHeader).getText();
    }

    public String getConfirmationText() {
        return driver.findElement(confirmationText).getText();
    }

    public boolean isOrderComplete() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}
