package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {

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
        super(driver);
        waitForUrlContains("checkout-step-one");
    }

    /**
     * Sets a React controlled input value correctly in headless Chrome 147+.
     *
     * Plain WebDriver sendKeys() fires keyboard events but does not trigger React's
     * synthetic onChange/onInput delegation in newer headless Chrome, so React's
     * internal state never updates — the form sees every field as empty when submitted.
     * The fix: use the native HTMLInputElement value setter (bypassing React's overridden
     * property) then dispatch a bubbling 'input' event, which React's event delegation
     * does catch and uses to re-sync its internal state.
     */
    private void setInputValue(WebElement element, String value) {
        executeJs(
            "var setter = Object.getOwnPropertyDescriptor(" +
                "window.HTMLInputElement.prototype, 'value').set;" +
            "setter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
            element, value);
    }

    public void enterFirstName(String firstName) {
        setInputValue(waitForVisibility(firstNameField), firstName);
    }

    public void enterLastName(String lastName) {
        setInputValue(findElement(lastNameField), lastName);
    }

    public void enterPostalCode(String postalCode) {
        setInputValue(findElement(postalCodeField), postalCode);
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        WebElement btn = waitForClickability(continueButton);
        clickElementWithJs(btn);
    }

    public void clickCancel() {
        WebElement btn = waitForClickability(cancelButton);
        clickElementWithJs(btn);
        waitForUrlContains("cart");
    }

    public void clickFinish() {
        WebElement btn = waitForClickability(finishButton);
        clickElementWithJs(btn);
        waitForUrlContains("checkout-complete");
    }

    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getSummaryTotal() {
        return getElementText(summaryTotal);
    }

    public String getConfirmationHeader() {
        return getElementText(confirmationHeader);
    }

    public String getConfirmationText() {
        return getElementText(confirmationText);
    }

    public boolean isOrderComplete() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}
