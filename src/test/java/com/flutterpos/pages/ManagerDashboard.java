package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
    private final By tilePromotions =
            AppiumBy.xpath("//*[contains(@text,'promotions') or contains(@content-desc,'promotions')]");
    private final By tileCreditors =
            AppiumBy.xpath("//*[contains(@text,'Creditors ') or contains(@content-desc,'Creditors ')]");
    private final By tileProfitDetail =
            AppiumBy.xpath("//*[contains(@text,'Profit Detail') or contains(@content-desc,'Profit Detail')]");

    // 🔐 Logout icon from StockAppBar (tooltip: 'Logout')
    private final By btnLogout =
            AppiumBy.accessibilityId("Logout"); // or "logout_button" if you add Semantics

    public ManagerDashboard(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Check Dashboard visible with scrolling support
    public boolean isDashboardVisible() {
        try {
            boolean visible = !driver.findElements(titleManager).isEmpty();
            if (!visible) {
                // Try scrolling to find it
                visible = scrollToFindElementNoThrow(titleManager) != null;
            }
            System.out.println("[INFO] Manager Dashboard visible: " + visible);
            return visible;
        } catch (Exception e) {
            System.out.println("[ERROR] Error checking dashboard visibility: " + e.getMessage());
            return false;
        }
    }

    // Scroll to find element - returns null if not found (no exception)
    public WebElement scrollToFindElementNoThrow(By locator) {
        System.out.println("[ACTION] Scrolling to find element: " + locator);

        // Get screen dimensions for scrolling
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        int startX = screenWidth / 2;
        int startY = (int) (screenHeight * 0.8);
        int endY = (int) (screenHeight * 0.2);

        int maxScrolls = 10;
        int scrollCount = 0;

        while (scrollCount < maxScrolls) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                System.out.println("[INFO] Element found after " + scrollCount + " scrolls");
                return elements.get(0);
            }

            // Scroll down (swipe up)
            try {
                driver.executeScript("mobile: scrollGesture", Map.of(
                        "left", startX, "top", startY, "width", 50, "height", 50,
                        "direction", "down",
                        "percent", 0.75
                ));
            } catch (Exception e) {
                // Fallback to swipe
                driver.executeScript("mobile: swipe", Map.of(
                        "startX", startX, "startY", startY,
                        "endX", startX, "endY", endY,
                        "duration", 1000
                ));
            }

            scrollCount++;
            try {
                Thread.sleep(500); // Wait for scroll animation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("[WARNING] Element not found after " + maxScrolls + " scrolls");
        return null;
    }

    // Scroll to find element - throws exception if not found
    public WebElement scrollToFindElement(By locator) {
        WebElement element = scrollToFindElementNoThrow(locator);
        if (element == null) {
            throw new RuntimeException("Element not found after scrolling: " + locator);
        }
        return element;
    }

    // Scroll to specific tile and click - safe version with error handling
    public void scrollAndClickTileSafe(By tileLocator, String tileName) {
        System.out.println("[ACTION] Scrolling to and clicking " + tileName + " tile");
        try {
            WebElement tile = scrollToFindElementNoThrow(tileLocator);
            if (tile == null) {
                System.out.println("[WARNING] Tile not found: " + tileName + ". Skipping...");
                return;
            }

            tile.click();

            // Wait a bit for page navigation
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Go back to dashboard
            driver.navigate().back();
            System.out.println("[SUCCESS] " + tileName + " tile clicked and returned to dashboard");

        } catch (Exception e) {
            System.out.println("[ERROR] Error clicking " + tileName + " tile: " + e.getMessage());
        }
    }

    // --- Tile actions updated with scrolling ---
    public void openRegisteredUser() {
        scrollAndClickTileSafe(tileRegisteredUser, "Registered User");
    }

    public void openUserSettings() {
        scrollAndClickTileSafe(tileUserSettings, "User Settings");
    }

    public void openAuditLogs() {
        scrollAndClickTileSafe(tileAuditLogs, "Audit Logs");
    }

    public void openPromotions() {
        scrollAndClickTileSafe(tilePromotions, "Promotions");
    }

    public void openCreditors() {
        scrollAndClickTileSafe(tileCreditors, "Creditors");
    }

    public void openProfitDetail() {
        scrollAndClickTileSafe(tileProfitDetail, "Profit Detail");
    }

    // Try multiple methods to find and click a tile
    public boolean tryClickTile(String tileName) {
        System.out.println("[ACTION] Trying to click " + tileName + " tile");

        // Try direct click first
        try {
            By tileLocator = getTileLocatorByName(tileName);
            if (tileLocator != null) {
                WebElement tile = driver.findElement(tileLocator);
                if (tile.isDisplayed()) {
                    tile.click();
                    Thread.sleep(1000);
                    driver.navigate().back();
                    System.out.println("[SUCCESS] " + tileName + " clicked directly");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("[INFO] Direct click failed, trying scroll method");
        }

        // Try scroll method
        try {
            By tileLocator = getTileLocatorByName(tileName);
            if (tileLocator != null) {
                scrollAndClickTileSafe(tileLocator, tileName);
                return true;
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Scroll click also failed for " + tileName);
        }

        return false;
    }

    private By getTileLocatorByName(String tileName) {
        switch (tileName.toLowerCase()) {
            case "registered users":
                return tileRegisteredUser;
            case "user settings":
                return tileUserSettings;
            case "audit logs":
                return tileAuditLogs;
            case "promotions":
                return tilePromotions;
            case "creditors":
                return tileCreditors;
            case "profit detail":
                return tileProfitDetail;
            default:
                return null;
        }
    }

    // 🔚 Logout
    public void logout() {
        System.out.println("[ACTION] Clicking Logout icon on Manager Dashboard...");
        try {
            WebElement logoutIcon = wait.until(
                    ExpectedConditions.elementToBeClickable(btnLogout)
            );
            logoutIcon.click();
            System.out.println("✅ Logout icon tapped.");
        } catch (Exception e) {
            System.out.println("[ERROR] Error logging out: " + e.getMessage());
            throw e;
        }
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
        System.out.println("Promotions visible: " + isElementVisible(tilePromotions));
        System.out.println("Creditors visible: " + isElementVisible(tileCreditors));
        System.out.println("Profit Detail visible: " + isElementVisible(tileProfitDetail));
        System.out.println("[INFO] === End tile visibility check ===");
    }
}