package tests;

import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setupCartAndCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        checkoutPage = new CheckoutPage(driver);
    }

    @Test(description = "Complete checkout with valid information")
    public void testSuccessfulCheckout() {
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete(), "Order should be complete");
        Assert.assertEquals(checkoutPage.getConfirmationHeader(), "Thank you for your order!");
    }

    @Test(description = "Checkout with missing first name should show error")
    public void testCheckoutMissingFirstName() {
        checkoutPage.fillCheckoutInfo("", "Doe", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(), "Error should be shown when first name is missing");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "Checkout with missing postal code should show error")
    public void testCheckoutMissingPostalCode() {
        checkoutPage.fillCheckoutInfo("John", "Doe", "");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(), "Error should be shown when postal code is missing");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
    }

    @Test(description = "Cancel checkout should return to cart")
    public void testCancelCheckout() {
        checkoutPage.clickCancel();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isOnCartPage(), "Cancelling checkout should return to cart");
    }
}
