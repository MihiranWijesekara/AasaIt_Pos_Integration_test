package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class stockKeeperDashboard {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    // Page title
    private final By titleStock =
            AppiumBy.xpath("//*[contains(@text,'Stock Keeper') or contains(@content-desc,'Stock Keeper')]");

    // Dashboard Tiles
    private final By tileAddCategory =
            AppiumBy.xpath("//*[contains(@text,'Add Category') or contains(@content-desc,'Add Category')]");
    private final By tileAddItem =
            AppiumBy.xpath("//*[contains(@text,'Add Item') or contains(@content-desc,'Add Item')]");
    private final By tileSuppliers =
            AppiumBy.xpath("//*[contains(@text,'Suppliers') or contains(@content-desc,'Suppliers')]");
    private final By tileInsights =
            AppiumBy.xpath("//*[contains(@text,'Insights') or contains(@content-desc,'Insights')]");
    private final By tileSalesReports =
            AppiumBy.xpath("//*[contains(@text,'Inventory') or contains(@content-desc,'Inventory')]");
    private final By tileProfitMargins =
            AppiumBy.xpath("//*[contains(@text,'Supplier Request') or contains(@content-desc,'Supplier Request')]");
    private final By btnLogout =
            AppiumBy.accessibilityId("Logout");

    public stockKeeperDashboard(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private void tapBackButton() {
        try {
            WebElement back = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.xpath("//*[contains(@content-desc,'Back') or contains(@text,'Back')]")
            ));
            back.click();
            System.out.println("⬅️ Back button tapped.");
        } catch (Exception e) {
            System.out.println("❌ Back button not found. Trying Android back...");
            driver.navigate().back();
        }
    }

    // Check Dashboard visible
    public boolean isDashboardVisible() {
        boolean visible = !driver.findElements(titleStock).isEmpty();
        System.out.println("[INFO] Stock Keeper Dashboard visible: " + visible);
        return visible;
    }

    public void openAddCategory() {
        System.out.println("[ACTION] Clicking Add Category");
        driver.findElement(tileAddCategory).click();
        tapBackButton();
    }

    public void openAddItem() {
        System.out.println("[ACTION] Clicking Add Item");
        driver.findElement(tileAddItem).click();
        tapBackButton();
    }

    public void openSuppliers() {
        System.out.println("[ACTION] Clicking Suppliers");
        driver.findElement(tileSuppliers).click();
        tapBackButton();
    }

    public void openInsights() {
        System.out.println("[ACTION] Clicking Insights tile");
        driver.findElement(tileInsights).click();
        tapBackButton();
    }

    public void openSalesReport() {
        System.out.println("[ACTION] Clicking Sales Report tile");
        driver.findElement(tileSalesReports).click();
        tapBackButton();
    }

    public void openProfitMargins() {
        System.out.println("[ACTION] Clicking Profit Margins tile");
        driver.findElement(tileProfitMargins).click();
        tapBackButton();
    }

    public void openAddCategoryAndStay() {
        System.out.println("[ACTION] Clicking Add Category tile (stay on page)");
        driver.findElement(tileAddCategory).click();
    }

    public void logout() {
        System.out.println("[ACTION] Clicking Logout icon on Manager Dashboard...");
        WebElement logoutIcon = wait.until(
                ExpectedConditions.elementToBeClickable(btnLogout)
        );
        logoutIcon.click();
        System.out.println("✅ Logout icon tapped.");
    }

}
