package com.flutterpos.pages.cashier;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class CashierDashboard {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public CashierDashboard(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By tapEnter =
            AppiumBy.xpath("//*[contains(@text,'Enter') or contains(@content-desc,'Enter')]");
    private final By tapBakery =
            AppiumBy.xpath("//*[contains(@text,'Bakery') or contains(@content-desc,'Bakery')]");
    private final By tapProduct =
            AppiumBy.xpath("//*[contains(@text,'cb') or contains(@content-desc,'cb')]");
    private final By tapAdd =
            AppiumBy.xpath("//*[contains(@text,'Add') or contains(@content-desc,'Add')]");
    private final By tapHome =
            AppiumBy.xpath("//*[contains(@text,'Home') or contains(@content-desc,'Home')]");
    private final By tapBook =
            AppiumBy.xpath("//*[contains(@text,'Books') or contains(@content-desc,'Books')]");
    private final By tapTomato =
            AppiumBy.xpath("//*[contains(@text,'tomato') or contains(@content-desc,'tomato')]");
    private final By tapFantaResult =
            AppiumBy.xpath(
                    "//*[(" +
                            "contains(@text,'Fanta') or contains(@content-desc,'Fanta')" +
                            ") and not(self::android.widget.EditText)]"
            );


    private final By fieldQuantity =
            AppiumBy.xpath("//android.widget.EditText[@text='Quantity' or @hint='Quantity' or @content-desc='Quantity']");
    private final By fieldSearch =
            AppiumBy.xpath("//android.widget.EditText[@text='Search item or scan barcode...' or @hint='Search item or scan barcode...' or @content-desc='Search item or scan barcode...']");



    public void tapEnter() {
        clickTile(tapEnter, "Tap Enter Button");
    }
    public void tapBakery() {
        clickTile(tapBakery, "Tap Bakery Button");

    }
    public void tapProduct() {
        clickTile(tapProduct, "Tap Product Button");
    }
    public void tapAdd() {
        clickTile(tapAdd, "Tap Add Button");
    }
    public void tapHome() {
        clickTile(tapHome, "Tap Home Button");
    }
    public void tapBook() {
        clickTile(tapBook, "Tap Book Button");
    }
    public void tapTomato() {
        clickTile(tapTomato, "Tap Tomato Button");
    }

    public void tapFanta() {
        System.out.println("[ACTION] Tap Fanta (search result)");

        // Wait until at least one result appears (NOT the EditText)
        wait.until(ExpectedConditions.presenceOfElementLocated(tapFantaResult));

        // Sometimes multiple matches → choose the best visible one (lower on screen, not inside appbar)
        List<WebElement> items = driver.findElements(tapFantaResult);
        if (items.isEmpty()) throw new RuntimeException("Fanta result not found");

        WebElement best = items.get(0);
        int bestY = -1;

        for (WebElement el : items) {
            Rectangle r = el.getRect();
            // ignore very top items (appbar/search)
            if (r.getY() > 250 && r.getY() > bestY) {
                best = el;
                bestY = r.getY();
            }
        }

        tapCenter(best);
        System.out.println("[SUCCESS] Fanta tapped.");
    }


    public void enterQuantity(String name) {
        enterText(fieldQuantity, name);
    }
    public void enterSearch(String name) {
        enterText(fieldSearch, name);
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
