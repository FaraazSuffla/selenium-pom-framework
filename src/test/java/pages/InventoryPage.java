package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By pageTitle        = By.cssSelector(".title");
    private final By inventoryItems   = By.cssSelector(".inventory_item");
    private final By addToCartButtons = By.cssSelector(".btn_inventory");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon         = By.cssSelector(".shopping_cart_link");
    private final By sortDropdown     = By.cssSelector("[data-test='product_sort_container']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItems);
    }

    public int getInventoryItemCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItems));
        return getInventoryItems().size();
    }

    public void addFirstItemToCart() {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
            // Wait for cart badge to appear/update after click
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
        }
    }

    public void addItemToCartByIndex(int index) {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        if (index < buttons.size()) {
            buttons.get(index).click();
            // Wait for cart badge to update after click
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
        }
    }

    public String getCartBadgeCount() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public void sortBy(String visibleText) {
        Select select = new Select(wait.until(
                ExpectedConditions.elementToBeClickable(sortDropdown)));
        select.selectByVisibleText(visibleText);
    }
}
