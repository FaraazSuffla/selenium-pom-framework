package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    // Locators
    private final By cartItems       = By.cssSelector(".cart_item");
    private final By checkoutButton  = By.id("checkout");
    private final By continueButton  = By.id("continue-shopping");
    private final By removeButtons   = By.cssSelector(".cart_button");
    private final By itemNames       = By.cssSelector(".inventory_item_name");

    public CartPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("cart");
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart");
    }

    public List<WebElement> getCartItems() {
        return findElements(cartItems);
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }

    public List<String> getCartItemNames() {
        List<WebElement> names = findElements(itemNames);
        return names.stream().map(WebElement::getText).toList();
    }

    public void removeItemByIndex(int index) {
        List<WebElement> buttons = findElements(removeButtons);
        if (index < buttons.size()) {
            clickElementWithJs(buttons.get(index));
        }
    }

    public void clickCheckout() {
        WebElement btn = waitForClickability(checkoutButton);
        clickElementWithJs(btn);
        waitForUrlContains("checkout-step-one");
    }

    public void continueShopping() {
        WebElement btn = waitForClickability(continueButton);
        clickElementWithJs(btn);
        waitForUrlContains("inventory");
    }
}
