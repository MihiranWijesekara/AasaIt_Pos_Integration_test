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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class StockKeeperLogoutPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public StockKeeperLogoutPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Menu item: Logout (after tapping 3 dots)
    private final By menuLogout =
            AppiumBy.xpath("//*[contains(@text,'Logout') or contains(@content-desc,'Logout')]");

    // Modal title: Logout
    private final By modalTitleLogout =
            AppiumBy.xpath("//*[contains(@text,'Logout') or contains(@content-desc,'Logout')]");

    // Modal buttons
    private final By btnLogoutConfirm =
            AppiumBy.xpath("//*[contains(@text,'Logout') or contains(@content-desc,'Logout')]");
    private final By btnCancel =
            AppiumBy.xpath("//*[contains(@text,'Cancel') or contains(@content-desc,'Cancel')]");

    /** Full logout flow */
    public void logout() {
        openMoreOptions();       // ✅ coordinate tap
        tapLogoutFromMenu();     // ✅ click Logout from menu
        waitForLogoutModal();    // ✅ wait for modal title
        confirmLogout();         // ✅ click Logout confirm
    }

    /** ✅ Tap 3-dots (More options) using coordinates (Flutter-safe) */
    public void openMoreOptions() {
        System.out.println("[ACTION] Tap 3-dots (More options) - coordinate tap");

        // small wait to ensure toolbar is stable
        try { Thread.sleep(400); } catch (Exception ignored) {}

        tapMoreOptionsByCoordinates();

        System.out.println("[SUCCESS] 3-dots tapped.");
    }

    public void tapLogoutFromMenu() {
        System.out.println("[ACTION] Tap Logout from menu");

        // wait a bit until menu appears
        try { Thread.sleep(400); } catch (Exception ignored) {}

        clickTile(menuLogout, "Logout (menu)");
    }

    public void waitForLogoutModal() {
        System.out.println("[WAIT] Waiting for Logout modal title");
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitleLogout));
        System.out.println("[SUCCESS] Logout modal is visible.");
    }

    public void confirmLogout() {
        System.out.println("[ACTION] Confirm Logout button");

        // Wait until confirm button is clickable (modal opened)
        wait.until(ExpectedConditions.elementToBeClickable(btnLogoutConfirm));

        // Tap "Logout" button in modal
        clickTile(btnLogoutConfirm, "Logout (confirm button)");

        System.out.println("[SUCCESS] Logout confirmed.");
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Tap the top-right (3 dots) area */
    private void tapMoreOptionsByCoordinates() {
        Dimension size = driver.manage().window().getSize();

        // Adjust if needed (but these values work in most emulators)
        int x = (int) (size.width * 0.95);   // near right edge
        int y = (int) (size.height * 0.07);  // appbar area (top)

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(80)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }

    /** Generic click helper (tries normal find; no need scroll for menu/dialog but kept safe) */
    private void clickTile(By locator, String tileName) {
        System.out.println("[ACTION] Clicking " + tileName);
        int retries = 4;

        for (int i = 0; i < retries; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                tapCenter(elements.get(0));
                System.out.println("[SUCCESS] " + tileName + " tapped.");
                return;
            }
            try { Thread.sleep(300); } catch (Exception ignored) {}
        }

        throw new RuntimeException("Could not find: " + tileName);
    }

    /** Exact center tap (REQUIRED for Flutter sometimes) */
    private void tapCenter(WebElement element) {
        Rectangle rect = element.getRect();
        int centerX = rect.getX() + rect.getWidth() / 2;
        int centerY = rect.getY() + rect.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(60)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }
}
