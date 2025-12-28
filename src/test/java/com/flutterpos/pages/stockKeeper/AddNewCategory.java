package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class AddNewCategory {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public AddNewCategory(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===================== LOCATORS =====================

    // STRICT Add Category button locator (Flutter-safe)
    private final By btnAddCategory =
            AppiumBy.xpath("//android.widget.Button[@text='Add Category' or @content-desc='Add Category']");

    private final By btnSaveCategory =
            AppiumBy.xpath("//android.widget.Button[@text='Save Category' or @content-desc='Save Category']");

    // Flutter TextField – hint may disappear, so keep generic
    private final By fieldCategoryName =
            AppiumBy.xpath("//android.widget.EditText[" +
                    "@text='e.g., Electronics, Food & Bev...' or " +
                    "@hint='e.g., Electronics, Food & Bev...' or " +
                    "@content-desc='e.g., Electronics, Food & Bev...' or " +
                    "@label='e.g., Electronics, Food & Bev...' or " +
                    "contains(@hint, 'Electronics') or " +
                    "contains(@text, 'Electronics')]");

    // ===================== PUBLIC ACTIONS =====================

    public void navigateAddCategory() {
        System.out.println("[ACTION] Opening Add Category screen...");
        tapElement(btnAddCategory);

        // 🔥 VERY IMPORTANT: wait until Add Category screen really opens
        wait.until(ExpectedConditions.presenceOfElementLocated(fieldCategoryName));
        System.out.println("[SUCCESS] Add Category screen opened.");
    }

    public void enterCategoryName(String name) {
        enterText(fieldCategoryName, name);
    }

    public void saveNewCategory() {
        clickTile(btnSaveCategory, "Save Supplier");
        driver.navigate().back();
    }

    // ===================== CORE HELPERS =====================

    /**
     * Flutter-safe tap using W3C Actions
     */
    private void tapElement(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        tapCenter(element);
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
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }

    private void enterText(By locator, String value) {
        System.out.println("[ACTION] Entering text: " + value);

        // Step 1: Click label / hint
        WebElement label = wait.until(ExpectedConditions.elementToBeClickable(locator));
        label.click();

        // Step 2: Send keys to focused input
        driver.switchTo().activeElement().sendKeys(value);
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
}
