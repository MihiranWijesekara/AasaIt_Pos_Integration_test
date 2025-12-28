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

public class AddProduct {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public AddProduct(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By fieldProductName =
            AppiumBy.xpath("//android.widget.EditText[@text='Product Name' or @hint='Product Name' or @content-desc='Product Name']");
    private final By fieldInitialQuantity =
            AppiumBy.xpath("//android.widget.EditText[@text='Initial Quantity (Optional)' or @hint='Initial Quantity (Optional)' or @content-desc='Initial Quantity (Optional)']");
    private final By fieldUnitCost =
            AppiumBy.xpath("//android.widget.EditText[@text='Unit Cost (Optional)' or @hint='Unit Cost (Optional)' or @content-desc='Unit Cost (Optional)']");
    private final By fieldSalesPriceOptional =
            AppiumBy.xpath("//android.widget.EditText[@text='Sales Price (Optional)' or @hint='Sales Price (Optional)' or @content-desc='Sales Price (Optional)']");
    private final By fieldLowStock =
            AppiumBy.xpath("//android.widget.EditText[@text='Low Stock Alert' or @hint='Low Stock Alert' or @content-desc='Low Stock Alert']");
    private final By fieldReorderLevel =
            AppiumBy.xpath("//android.widget.EditText[@text='Reorder Level' or @hint='0' or @content-desc='Reorder Level']");
    private final By tapCategory =
            AppiumBy.xpath("//android.widget.EditText[@text='Category' or @hint='Category' or @content-desc='Category']");
    private final By tapSupplier =
            AppiumBy.xpath("//android.widget.EditText[@text='Supplier' or @hint='Supplier' or @content-desc='Supplier");
    private final By tapBeverages =
            AppiumBy.xpath("//*[contains(@text,'Beverages') or contains(@content-desc,'Beverages')]");

    public void enterProductName(String name) {
        enterText(fieldProductName, name);
    }
    public void enterInitialQuantity(String name) {
        enterText(fieldInitialQuantity, name);
    }
    public void enterUnitCost(String name) {
        enterText(fieldUnitCost, name);
    }
    public void enterSalesPrice(String name) {
        enterText(fieldSalesPriceOptional, name);
    }

    public void enterLowStock(String name) {
        enterText(fieldLowStock, name);
    }
    public void enterReorderLevel(String name) {
        enterText(fieldReorderLevel, name);
    }
    public void tapCategory() {
        clickTile(tapCategory, "Tap Category");

    }
    public void tapBeverages() {
        clickTile(tapBeverages, "Tap books");

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


    // ---------- Reusable Method ----------
    private void enterText(By locator, String value) {
        System.out.println("[ACTION] Entering text: " + value);

        // Step 1: Click label / hint
        WebElement label = wait.until(ExpectedConditions.elementToBeClickable(locator));
        label.click();

        // Step 2: Send keys to focused input
        driver.switchTo().activeElement().sendKeys(value);
    }

}
