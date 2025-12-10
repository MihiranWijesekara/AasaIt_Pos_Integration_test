package com.flutterpos.pages.stockKeeper;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    // Page title
    private final By titleInventoryManagement =
            AppiumBy.xpath("//*[contains(@text,'Inventory Management') or contains(@content-desc,'Inventory Management')]");

    private final By tileTotalItems =
            AppiumBy.xpath("//*[contains(@text,'Total Items') or contains(@content-desc,'Total Items')]");
    private final By tileLowStock =
            AppiumBy.xpath("//*[contains(@text,'Low Stock') or contains(@content-desc,'Low Stock')]");
    private final By tileReStock =
            AppiumBy.xpath("//*[contains(@text,'Re-Stock') or contains(@content-desc,'Re-Stock')]");



    public InventoryPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    // Check Dashboard visible
    public boolean isDashboardVisible() {
        boolean visible = !driver.findElements(titleInventoryManagement).isEmpty();
        System.out.println("[INFO] Stock Keeper Dashboard visible: " + visible);
        return visible;
    }

    public void openTotalItems() {
        System.out.println("[ACTION] Clicking Total Items");
        driver.findElement(tileTotalItems).click();
        driver.navigate().back();
    }
    public void openLowStock() {
        System.out.println("[ACTION] Clicking Low stock");
        driver.findElement(tileLowStock).click();
        driver.navigate().back();
    }
    public void openReStock() {
        System.out.println("[ACTION] Clicking Re Stock");
        driver.findElement(tileReStock).click();
        driver.navigate().back();
    }

}
