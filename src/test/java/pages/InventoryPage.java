package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage {

    private final WebDriver driver;

    // Locators
    private final By pageTitle        = By.cssSelector(".title");
    private final By inventoryItems   = By.cssSelector(".inventory_item");
    private final By addToCartButtons = By.cssSelector(".btn_inventory");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon         = By.cssSelector(".shopping_cart_link");
    private final By sortDropdown     = By.cssSelector("[data-test='product_sort_container']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItems);
    }

    public int getInventoryItemCount() {
        return getInventoryItems().size();
    }

    public void addFirstItemToCart() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public void addItemToCartByIndex(int index) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public String getCartBadgeCount() {
        try {
            return driver.findElement(cartBadge).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public void sortBy(String visibleText) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText(visibleText);
    }
}
