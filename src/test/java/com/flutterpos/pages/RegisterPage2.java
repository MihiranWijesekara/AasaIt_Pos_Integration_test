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

public class RegisterPage2 {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public RegisterPage2(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    //First Page Locators
    private final By fieldDisplayName =
            AppiumBy.xpath("//android.widget.EditText[@text='Display name' or @hint='Display name' or @content-desc='Display name']");
    private final By fieldLegalName =
            AppiumBy.xpath("//android.widget.EditText[@text='Legal name' or @hint='Legal name' or @content-desc='Legal name']");
    private final By fieldPhone =
            AppiumBy.xpath("//android.widget.EditText[@text='Phone' or @hint='Phone' or @content-desc='Phone']");
    private final By fieldEmail =
            AppiumBy.xpath("//android.widget.EditText[@text='Email' or @hint='Email' or @content-desc='Email']");
    private final By fieldAddress =
            AppiumBy.xpath("//android.widget.EditText[@text='Address' or @hint='Address' or @content-desc='Address']");
    private final By fieldLKR =
            AppiumBy.xpath("//android.widget.EditText[@text='LKR' or @hint='LKR' or @content-desc='LKR']");
    private final By fieldVAT =
            AppiumBy.xpath("//android.widget.EditText[@text='VAT' or @hint='VAT' or @content-desc='VAT']");
    private final By fieldTaxID =
            AppiumBy.xpath("//android.widget.EditText[@text='Tax ID' or @hint='Tax ID' or @content-desc='Tax ID']");

    //Second Page Locators
    private final By fieldFullName =
            AppiumBy.xpath("//android.widget.EditText[@text='Full name' or @hint='Full name' or @content-desc='Full name']");
    private final By fieldEmail2 =
            AppiumBy.xpath("//android.widget.EditText[@text='Email' or @hint='Email' or @content-desc='Email']");
    private final By fieldContactNumber =
            AppiumBy.xpath("//android.widget.EditText[@text='Contact number' or @hint='Contact number' or @content-desc='Contact number']");
    private final By fieldNIC =
            AppiumBy.xpath("//android.widget.EditText[@text='NIC' or @hint='NIC' or @content-desc='NIC']");
    private final By fieldPassword =
            AppiumBy.xpath("//android.widget.EditText[@text='Password' or @hint='Password' or @content-desc='Password']");
    private final By fieldConfirmPassword =
            AppiumBy.xpath("//android.widget.EditText[@text='Confirm password' or @hint='Confirm password' or @content-desc='Confirm password']");


    private final By tapNext =
            AppiumBy.xpath("//*[contains(@text,'Next') or contains(@content-desc,'Next')]");
    private final By tapFinish =
            AppiumBy.xpath("//*[contains(@text,'Finish') or contains(@content-desc,'Finish')]");


    public void enterDisplayName(String name) {
        enterText(fieldDisplayName, name);
    }
    public void enterLegalName(String name) {
        enterText(fieldLegalName, name);
    }
    public void enterPhone(String name) {
        enterText(fieldPhone, name);
    }
    public void enterEmail(String name) {
        enterText(fieldEmail, name);
    }
    public void enterAddress(String name) {
        enterText(fieldAddress, name);
    }
    public void enterLKR(String name) {
        enterText(fieldLKR, name);
    }
    public void enterVAT(String name) {
        enterText(fieldVAT, name);
    }
    public void enterTaxID(String name) {
        enterText(fieldTaxID, name);
    }

    // Second Page Methods
    public void enterFullNameField(String name) {
        enterText(fieldFullName, name);
    }
    public void enterEmailSecField(String name) {
        enterText(fieldEmail2, name);
    }
    public void enterContactNumberField(String name) {
        enterText(fieldContactNumber, name);
    }
    public void getNICField(String name) {
        enterText(fieldNIC, name);
    }
    public void enterPasswordField(String name) {
        enterText(fieldPassword, name);
    }
    public void enterConfirmPasswordField(String name) {
        enterText(fieldConfirmPassword, name);
    }

    public void tapNext() {
        clickTile(tapNext, "Tap Next");

    }
    public void tapFinish() {
        clickTile(tapFinish, "Tap Finish");

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
