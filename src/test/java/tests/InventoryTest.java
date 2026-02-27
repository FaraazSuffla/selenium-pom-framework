package tests;

import pages.InventoryPage;
import pages.LoginPage;
import utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginFirst() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(driver);
    }

    @Test(description = "Inventory page should display 6 products")
    public void testInventoryItemCount() {
        Assert.assertEquals(inventoryPage.getInventoryItemCount(), 6, "There should be 6 products on inventory page");
    }

    @Test(description = "Page title should be Products")
    public void testInventoryPageTitle() {
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");
    }

    @Test(description = "Adding item to cart should update cart badge")
    public void testAddItemToCart() {
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1", "Cart badge should show 1 after adding an item");
    }

    @Test(description = "Adding multiple items should reflect correct badge count")
    public void testAddMultipleItemsToCart() {
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        inventoryPage.addItemToCartByIndex(2);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "3", "Cart badge should show 3 after adding 3 items");
    }
}
