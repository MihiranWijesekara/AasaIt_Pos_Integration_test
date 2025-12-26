package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ManagerDashboard {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    // Page title
    private final By titleManager =
            AppiumBy.xpath("//*[contains(@text,'Manager') or contains(@content-desc,'Manager')]");

    // Dashboard Tiles
    private final By tileRegisteredUser =
            AppiumBy.xpath("//*[contains(@text,'Registered Users') or contains(@content-desc,'Registered Users')]");
    private final By tileUserSettings =
            AppiumBy.xpath("//*[contains(@text,'User Settings') or contains(@content-desc,'User Settings')]");
    private final By tileAuditLogs =
            AppiumBy.xpath("//*[contains(@text,'Audit Logs') or contains(@content-desc,'Audit Logs')]");
//    private final By tilePromotions =
//            AppiumBy.xpath("//*[contains(@text,'Promotions') or contains(@content-desc,'Promotions')]");
    private final By tileCreditors =
            AppiumBy.xpath("//*[contains(@text,'Creditors') or contains(@content-desc,'Creditors')]");
    private final By tileProfitDetail =
            AppiumBy.xpath("//*[contains(@text,'Profit Detail') or contains(@content-desc,'Profit Detail')]");

    public ManagerDashboard(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Check Dashboard visible with scrolling support
    // Check Dashboard visible with scrolling support
    public boolean isDashboardVisible() {
        try {
            boolean visible = !driver.findElements(titleManager).isEmpty();
            if (!visible) {
                // Try scrolling to find it
                int maxScrolls = 5;
                for (int i = 0; i < maxScrolls; i++) {
                    List<WebElement> elements = driver.findElements(titleManager);
                    if (!elements.isEmpty()) {
                        visible = true;
                        break;
                    }
                    System.out.println("[INFO] Manager title not visible yet. Scrolling down... (attempt " + (i + 1) + ")");
                    scrollDown();
                }
            }
            System.out.println("[INFO] Manager Dashboard visible: " + visible);
            return visible;
        } catch (Exception e) {
            System.out.println("[ERROR] Error checking dashboard visibility: " + e.getMessage());
            return false;
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



    // --- Tile actions updated with scrolling ---
    public void openRegisteredUser() {
        clickTile(tileRegisteredUser, "Registered User");
        driver.navigate().back();
    }

    public void openUserSettings() {
        clickTile(tileUserSettings, "User Settings");
        driver.navigate().back();
    }

    public void openAuditLogs() {
        clickTile(tileAuditLogs, "Audit Logs");
        driver.navigate().back();
    }

//    public void openPromotions() {
//        clickTile(tilePromotions, "Promotions");
//        driver.navigate().back();
//    }

    public void openCreditors() {
        clickTile(tileCreditors, "Creditors");
        driver.navigate().back();
    }

    public void openProfitDetail() {
        clickTile(tileProfitDetail, "Profit Detail");
        driver.navigate().back();
    }


    // Check if element is on screen (without scrolling)
    public boolean isElementVisible(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // Get visible tiles for debugging
    public void printVisibleTiles() {
        System.out.println("[INFO] === Checking visible tiles ===");
        System.out.println("Registered Users visible: " + isElementVisible(tileRegisteredUser));
        System.out.println("User Settings visible: " + isElementVisible(tileUserSettings));
        System.out.println("Audit Logs visible: " + isElementVisible(tileAuditLogs));
//        System.out.println("Promotions visible: " + isElementVisible(tilePromotions));
        System.out.println("Creditors visible: " + isElementVisible(tileCreditors));
        System.out.println("Profit Detail visible: " + isElementVisible(tileProfitDetail));
        System.out.println("[INFO] === End tile visibility check ===");
    }
}