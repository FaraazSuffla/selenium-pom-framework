package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    // Locators
    private final By pageTitle        = By.cssSelector(".title");
    private final By inventoryItems   = By.cssSelector(".inventory_item");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon         = By.cssSelector(".shopping_cart_link");
    private final By sortDropdown     = By.cssSelector("[data-test='product_sort_container']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getElementText(pageTitle);
    }

    public boolean isOnInventoryPage() {
        return waitForUrlContains("inventory");
    }

    public List<WebElement> getInventoryItems() {
        return findElements(inventoryItems);
    }

    public int getInventoryItemCount() {
        waitForVisibility(inventoryItems);
        return getInventoryItems().size();
    }

    /**
     * Clicks the "Add to Cart" button on a specific inventory item by index.
     * Uses the .btn_inventory button within each .inventory_item container,
     * and checks the button text to ensure it is an "Add to Cart" button.
     */
    private void clickAddToCartOnItem(int itemIndex) {
        List<WebElement> items = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItems));
        if (itemIndex < items.size()) {
            WebElement button = items.get(itemIndex).findElement(By.cssSelector(".btn_inventory"));
            // Only click if it's an "Add to Cart" button (not "Remove")
            if (button.getText().toUpperCase().contains("ADD TO CART")) {
                clickElementWithJs(button);
                // Re-find the button each poll to avoid StaleElementReferenceException
                final int index = itemIndex;
                wait.until(d -> {
                    try {
                        List<WebElement> current = d.findElements(inventoryItems);
                        if (index >= current.size()) return false;
                        WebElement btn = current.get(index).findElement(By.cssSelector(".btn_inventory"));
                        return btn.getText().equalsIgnoreCase("Remove");
                    } catch (Exception e) {
                        return false;
                    }
                });
            }
        }
    }

    public void addFirstItemToCart() {
        clickAddToCartOnItem(0);
    }

    public void addItemToCartByIndex(int index) {
        clickAddToCartOnItem(index);
    }

    public String getCartBadgeCount() {
        try {
            List<WebElement> badges = findElements(cartBadge);
            if (!badges.isEmpty()) {
                return badges.get(0).getText();
            }
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }

    public void goToCart() {
        WebElement cart = waitForClickability(cartIcon);
        clickElementWithJs(cart);
        waitForUrlContains("cart");
    }

    public void sortBy(String visibleText) {
        Select select = new Select(waitForClickability(sortDropdown));
        select.selectByVisibleText(visibleText);
    }
}
