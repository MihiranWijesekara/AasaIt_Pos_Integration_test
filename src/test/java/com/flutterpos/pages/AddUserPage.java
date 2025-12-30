package com.flutterpos.pages;

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

public class AddUserPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public AddUserPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // -------------------- Locators (Label -> Input in same section) --------------------

//    private final By fieldFullName =
//            AppiumBy.xpath("//*[@text='Full Name' or @content-desc='Full Name']/parent::*/parent::*//android.widget.EditText");
private final By fieldFullName =
        AppiumBy.xpath("//*[(@text='Full Name' or @content-desc='Full Name')]/following::android.widget.EditText[1]");


    private final By fieldEmail =
            AppiumBy.xpath("//*[@text='Email Address' or @content-desc='Email Address']/parent::*/parent::*//android.widget.EditText");

    private final By fieldContactNumber =
            AppiumBy.xpath("//*[@text='Contact Number' or @content-desc='Contact Number']/parent::*/parent::*//android.widget.EditText");

    private final By fieldNic =
            AppiumBy.xpath("//*[@text='NIC' or @content-desc='NIC']/parent::*/parent::*//android.widget.EditText");

    private final By fieldPassword =
            AppiumBy.xpath("//*[@text='Password' or @content-desc='Password']/parent::*/parent::*//android.widget.EditText");

    private final By fieldConfirmPassword =
            AppiumBy.xpath("//*[@text='Confirm Password' or @content-desc='Confirm Password']/parent::*/parent::*//android.widget.EditText");

    private final By tapCreateUser =
            AppiumBy.xpath("//*[contains(@text,'Create User') or contains(@content-desc,'Create User')]");

    private final By tapAddUser =
            AppiumBy.xpath("//*[contains(@text,'Add User') or contains(@content-desc,'Add User')]");

    // -------------------- Public Actions --------------------

    public void tapAddUser() {
        clickTile(tapAddUser, "Tap Add User");
    }

    public void enterDisplayName(String name) {
        enterText(fieldFullName, name);
    }

    public void enterEmail(String email) {
        enterText(fieldEmail, email);
    }

    public void enterContactNumberField(String contact) {
        enterText(fieldContactNumber, contact);
    }

    public void enterNICField(String nic) {
        enterText(fieldNic, nic);
    }

    public void enterPasswordField(String password) {
        enterText(fieldPassword, password);
    }

    public void enterConfirmPasswordField(String confirm) {
        enterText(fieldConfirmPassword, confirm);
    }

    public void tapCrateUser() {
        clickTile(tapCreateUser, "Tap Create User");
    }

    // -------------------- Robust Helpers --------------------

    /**
     * Enter text into a Flutter field reliably:
     * - Ensures field is visible (scrolls if required)
     * - Center tap (Flutter-safe)
     * - Clears existing text (best-effort)
     * - Types directly into the element (NOT activeElement)
     * - Optionally hides keyboard
     */
    private void enterText(By locator, String value) {
        System.out.println("[ACTION] Entering text: " + value);

        // Step 1: Click label / hint
        WebElement label = wait.until(ExpectedConditions.elementToBeClickable(locator));
        label.click();

        // Step 2: Send keys to focused input
        driver.switchTo().activeElement().sendKeys(value);
    }

    /**
     * Scroll until the element appears (up to maxScrolls).
     * This avoids failures when fields are below the fold.
     */
    private void ensureVisible(By locator) {
        int maxScrolls = 6;
        for (int i = 0; i < maxScrolls; i++) {
            if (!driver.findElements(locator).isEmpty()) return;
            scrollDown();
        }
        // do nothing here; wait() will throw a good error if still not found
    }

    /**
     * Generic helper to scroll down and click a tile using Flutter-safe tap.
     */
    private void clickTile(By locator, String tileName) {
        System.out.println("[ACTION] Clicking " + tileName);
        int maxScrolls = 6;

        for (int i = 0; i < maxScrolls; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                tapCenter(elements.get(0));
                System.out.println("[SUCCESS] " + tileName + " tapped.");
                return;
            }

            System.out.println("[INFO] '" + tileName + "' not visible yet. Scrolling... (attempt " + (i + 1) + ")");
            scrollDown();
        }

        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            tapCenter(elements.get(0));
            System.out.println("[SUCCESS] " + tileName + " tapped after scrolling.");
        } else {
            throw new RuntimeException("Could not find: " + tileName + " after scrolling.");
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
        int startY = (int) (height * 0.80);
        int endY   = (int) (height * 0.30);

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
                Duration.ofMillis(650),
                PointerInput.Origin.viewport(),
                startX,
                endY
        ));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Exact center tap (recommended for Flutter).
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
        tap.addAction(new Pause(finger, Duration.ofMillis(60)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }
}
