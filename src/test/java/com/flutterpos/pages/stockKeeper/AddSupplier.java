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

public class AddSupplier {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public AddSupplier(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ---------- Locators ----------
    private final By tileAddSupplier =
            AppiumBy.xpath("//*[contains(@text,'Add Supplier') or contains(@content-desc,'Add Supplier')]");

    // Update your locators in AddSupplier class:
    private final By fieldSupplierName =
            AppiumBy.xpath("//android.widget.EditText[@text='Supplier Name' or @hint='Supplier Name' or @content-desc='Supplier Name']");

    private final By fieldContactNumber =
            AppiumBy.xpath("//android.widget.EditText[@text='Contact Number' or @hint='Contact Number' or @content-desc='Contact Number']");

    private final By fieldEmail =
            AppiumBy.xpath("//android.widget.EditText[@text='Email (Optional)' or @hint='Email (Optional)' or @content-desc='Email (Optional)']");

    private final By fieldBrandCompany =
            AppiumBy.xpath("//android.widget.EditText[@text='Brand / Company' or @hint='Brand / Company' or @content-desc='Brand / Company']");

    private final By btnSaveSupplier =
            AppiumBy.xpath("//*[contains(@text,'Save Supplier') or contains(@content-desc,'Save Supplier')]");

    // ---------- Actions ----------

    public void navigateAddSupplier() {
        System.out.println("[ACTION] Clicking Add Supplier");
        wait.until(ExpectedConditions.elementToBeClickable(tileAddSupplier)).click();
    }

    public void enterSupplierName(String name) {
        enterText(fieldSupplierName, name);
    }

    public void enterContactNumber(String number) {
        enterText(fieldContactNumber, number);
    }

    public void enterEmail(String email) {
        enterText(fieldEmail, email);
    }

    public void enterBrandCompany(String company) {
        enterText(fieldBrandCompany, company);
    }

    public void clickSaveSupplier() {
        clickTile(btnSaveSupplier, "Save Supplier");
        driver.navigate().back();
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
