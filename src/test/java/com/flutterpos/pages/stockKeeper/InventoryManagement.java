package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class InventoryManagement {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public InventoryManagement(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private final By tileInventory =
            AppiumBy.xpath("//*[contains(@text,'Inventory') or contains(@content-desc,'Inventory')]");
    private final By tapTotalItems =
            AppiumBy.xpath("//*[contains(@text,'Total Items') or contains(@content-desc,'Total Items')]");
    private final By tapLowStock =
            AppiumBy.xpath("//*[contains(@text,'Low Stock') or contains(@content-desc,'Low Stock')]");
    private final By tapReStock =
            AppiumBy.xpath("//*[contains(@text,'Re-Stock') or contains(@content-desc,'Re-Stock')]");


    public void openInventoryNotBack() {
        clickTile(tileInventory, "Inventory Stayed ");
    }

    public void tapTotalItems() {
        clickTile(tapTotalItems, "Tap total items");
//        driver.navigate().back();

    }
    public void tapLowStock() {
        clickTile(tapLowStock, "Tap low stock");
//        driver.navigate().back();

    }
    public void tapReStock() {
        clickTile(tapReStock, "Tap Re Stock");
        driver.navigate().back();

    }


    /**
     * Generic helper to scroll down and click a tile using Flutter-safe tap.
     * - Tries to find the element.
     * - If not found, performs a swipe down and retries (maxScrolls times).
     */
    private void clickTile(By locator, String tileName) {
        System.out.println("[ACTION] Clicking " + tileName);
        int maxScrolls = 5;

        for (int i = 0; i < maxScrolls; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                tapCenter(elements.get(0)); // Use Flutter-safe tap instead of click()
                System.out.println("[SUCCESS] " + tileName + " tapped.");
                return;
            }

            System.out.println("[INFO] '" + tileName + "' not visible yet. Scrolling down... (attempt " + (i + 1) + ")");
            scrollDown();
        }

        // Final attempt after scrolling
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            tapCenter(elements.get(0)); // Use Flutter-safe tap instead of click()
            System.out.println("[SUCCESS] " + tileName + " tapped after scrolling.");
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

    /**
     * Exact center tap (REQUIRED for Flutter)
     */
    private void tapCenter(WebElement element) {
        Rectangle rect = element.getRect();
        int centerX = rect.getX() + rect.getWidth() / 2;
        int centerY = rect.getY() + rect.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                centerX,
                centerY
        ));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(50))); // Small pause for better tap detection
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }

}
