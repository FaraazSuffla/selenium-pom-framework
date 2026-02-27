package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {

    private final WebDriver driver;

    // Locators
    private final By cartItems       = By.cssSelector(".cart_item");
    private final By checkoutButton  = By.id("checkout");
    private final By continueButton  = By.id("continue-shopping");
    private final By removeButtons   = By.cssSelector(".cart_button");
    private final By itemNames       = By.cssSelector(".inventory_item_name");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart");
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItems);
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }

    public List<String> getCartItemNames() {
        List<WebElement> names = driver.findElements(itemNames);
        return names.stream().map(WebElement::getText).toList();
    }

    public void removeItemByIndex(int index) {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public void continueShopping() {
        driver.findElement(continueButton).click();
    }
}
