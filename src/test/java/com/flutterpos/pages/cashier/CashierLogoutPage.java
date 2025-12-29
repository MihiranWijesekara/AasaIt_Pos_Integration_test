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

public class CashierLogoutPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public CashierLogoutPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Drawer/Menu icon (☰). In Flutter it may not be visible to XPath, so we also provide coordinate tap.
    private final By btnMenu =
            AppiumBy.xpath("//*[contains(@content-desc,'menu') or contains(@content-desc,'Menu') " +
                    "or contains(@text,'menu') or contains(@text,'Menu')]");

    // Bills title (to confirm Bills screen opened)
    private final By titleBills =
            AppiumBy.xpath("//*[contains(@text,'Bills') or contains(@content-desc,'Bills')]");

    // Logout modal title/text
    private final By modalTitleLogout =
            AppiumBy.xpath("//*[contains(@text,'Logout') or contains(@content-desc,'Logout')]");

    // Confirm Logout button (dialog)
    private final By btnLogoutConfirm =
            AppiumBy.xpath("//*[contains(@text,'Logout') or contains(@content-desc,'Logout')]");

    /** ✅ Full Flow: Menu -> Bills -> top-right logout icon -> confirm */
    public void logoutFromCashier() {
        openMenu();
        waitForBillsScreen();
        tapTopRightLogoutIcon();   // coordinate tap (Flutter-safe)
//        waitForLogoutDialog();
//        confirmLogout();
    }

    /** Try normal locator; if not found, use coordinate tap */
    public void openMenu() {
        System.out.println("[ACTION] Tap menu icon (☰)");

        List<WebElement> els = driver.findElements(btnMenu);
        if (!els.isEmpty()) {
            tapCenter(els.get(0));
            System.out.println("[SUCCESS] Menu tapped by locator.");
            return;
        }

        // Flutter fallback (menu icon area)
        tapMenuByCoordinates();
        System.out.println("[SUCCESS] Menu tapped by coordinates.");
    }

    public void waitForBillsScreen() {
        System.out.println("[WAIT] Waiting for Bills screen");
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleBills));
        System.out.println("[SUCCESS] Bills screen visible.");
    }

    /** Top-right logout icon (arrow-out). Flutter often hides it -> coordinate tap is best */
    public void tapTopRightLogoutIcon() {
        System.out.println("[ACTION] Tap top-right logout icon (Bills appbar)");

        // small pause so appbar icons are stable
        try { Thread.sleep(300); } catch (Exception ignored) {}

        tapLogoutIconByCoordinates();
        System.out.println("[SUCCESS] Logout icon tapped.");
    }

//    public void waitForLogoutDialog() {
//        System.out.println("[WAIT] Waiting for Logout dialog");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitleLogout));
//        System.out.println("[SUCCESS] Logout dialog visible.");
//    }
//
//    public void confirmLogout() {
//        System.out.println("[ACTION] Confirm Logout");
//        // dialog has Cancel + Logout
//        clickTile(btnLogoutConfirm, "Logout (confirm)");
//        System.out.println("[SUCCESS] Logout confirmed.");
//    }

    // -------------------------------------------------------------------------
    // Coordinate taps (stable for Flutter)
    // -------------------------------------------------------------------------

    private void tapMenuByCoordinates() {
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width * 0.94); // right side where menu icon is (near Achintha)
        int y = (int) (size.height * 0.08); // appbar top area
        tapXY(x, y);
    }

    private void tapLogoutIconByCoordinates() {
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width * 0.94); // top-right icon
        int y = (int) (size.height * 0.08); // appbar top area
        tapXY(x, y);
    }

    private void tapXY(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(80)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private void clickTile(By locator, String name) {
        System.out.println("[ACTION] Clicking " + name);

        int retries = 5;
        for (int i = 0; i < retries; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                tapCenter(elements.get(0));
                return;
            }
            try { Thread.sleep(250); } catch (Exception ignored) {}
        }
        throw new RuntimeException("Could not find: " + name);
    }

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
