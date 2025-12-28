package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class stockKeeperDashboard {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    // Page title
    private final By titleStock =
            AppiumBy.xpath("//*[contains(@text,'Stock Keeper') or contains(@content-desc,'Stock Keeper')]");

    // Dashboard Tiles
    private final By tileAddCategory =
            AppiumBy.xpath("//*[contains(@text,'Add Category') or contains(@content-desc,'Add Category')]");
    private final By tileAddItem =
            AppiumBy.xpath("//*[contains(@text,'Add Item') or contains(@content-desc,'Add Item')]");
    private final By tileSuppliers =
            AppiumBy.xpath("//*[contains(@text,'Suppliers') or contains(@content-desc,'Suppliers')]");
    private final By tileInventory =
            AppiumBy.xpath("//*[contains(@text,'Inventory') or contains(@content-desc,'Inventory')]");
    private final By tileTotalStockItems =
            AppiumBy.xpath("//*[contains(@text,'Total Stock Items') or contains(@content-desc,'Total Stock Items')]");
    private final By tileRestockRequests =
            AppiumBy.xpath("//*[contains(@text,'Restock Requests') or contains(@content-desc,'Restock Requests')]");
    private final By tileInsights =
            AppiumBy.xpath("//*[contains(@text,'Insights') or contains(@content-desc,'Insights')]");
    private final By tileSupplierReviews =
            AppiumBy.xpath("//*[contains(@text,'Supplier Reviews') or contains(@content-desc,'Supplier Reviews')]");


    public stockKeeperDashboard(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private void tapBackButton() {
        try {
            WebElement back = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.xpath("//*[contains(@content-desc,'Back') or contains(@text,'Back')]")
            ));
            back.click();
            System.out.println("⬅️ Back button tapped.");
        } catch (Exception e) {
            System.out.println("❌ Back button not found. Trying Android back...");
            driver.navigate().back();
        }
    }

    /**
     * Generic helper to scroll down and click a tile.
     * - Tries to find the element.
     * - If not found, performs a swipe down and retries (maxScrolls times).
     */
    private void clickTile(By locator, String tileName) {
        System.out.println("[ACTION] Clicking " + tileName);
        int maxScrolls = 5;

        for (int i = 0; i < maxScrolls; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                elements.get(0).click();
                System.out.println("[SUCCESS] " + tileName + " clicked.");
                return;
            }

            System.out.println("[INFO] '" + tileName + "' not visible yet. Scrolling down... (attempt " + (i + 1) + ")");
            scrollDown();
        }

        // Final attempt after scrolling
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            elements.get(0).click();
            System.out.println("[SUCCESS] " + tileName + " clicked after scrolling.");
        } else {
            throw new RuntimeException("Could not find tile: " + tileName + " after scrolling.");
        }
    }


    /**
     * Simple swipe down using W3C actions.
     */
    private void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int width = size.width;
        int height = size.height;

        int startX = width / 2;
        int startY = (int) (height * 0.8); // near bottom
        int endY = (int) (height * 0.3);   // near top

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX,
                startY
        ));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(600),
                PointerInput.Origin.viewport(),
                startX,
                endY
        ));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }



    // Check Dashboard visible
    public boolean isDashboardVisible() {
        boolean visible = !driver.findElements(titleStock).isEmpty();
        System.out.println("[INFO] Stock Keeper Dashboard visible: " + visible);
        return visible;
    }

    public void openAddCategory() {
        clickTile(tileAddCategory, "Add Category");
        tapBackButton();
    }

    public void openAddItem() {
        clickTile(tileAddItem, "Add Item");
        tapBackButton();
    }

    public void openSuppliers() {
        clickTile(tileSuppliers, "Suppliers");
        tapBackButton();
    }
    public void openInventory() {
        clickTile(tileInventory, "Sales Report");
        tapBackButton();
    }

    public void openTotalStockItem() {
        clickTile(tileTotalStockItems, "Total Stock Item ");
        tapBackButton();
    }

    public void openRestockRequests() {
        clickTile(tileTotalStockItems, "Restock Requests ");
        tapBackButton();
    }
    public void openInsights() {
        clickTile(tileInsights, "Insights");
        tapBackButton();
    }
    public void openSupplierReviews() {
        clickTile(tileSupplierReviews, "Supplier Reviews");
        tapBackButton();
    }

    public void openAddCategoryNotBack() {
        clickTile(tileAddCategory, "Add Category");
    }
    public void openSuppliersNotBack() {
        clickTile(tileSuppliers, "Suppliers");
    }
    public void openAddItemNotBack() {
        clickTile(tileAddItem, "Add Item");
    }


}
