package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StockFooter {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private final By tileHome =
            AppiumBy.xpath("//*[contains(@text,'Home') or contains(@content-desc,'Home')]");
    private final By tileReports =
            AppiumBy.xpath("//*[contains(@text,'Reports') or contains(@content-desc,'Reports')]");
    private final By titleNotification =
            AppiumBy.xpath("//*[contains(@text,'Notifications') or contains(@content-desc,'Notifications')]");
    private final By tileSettings =
            AppiumBy.xpath("//*[contains(@text,'Settings') or contains(@content-desc,'Settings')]");

    private final By tileDailyStockReport =
            AppiumBy.xpath("//*[contains(@text,'Daily Stock Report') or contains(@content-desc,'Daily Stock Report')]");
    private final By tileAppearance =
            AppiumBy.xpath("//*[contains(@text,'Appearance') or contains(@content-desc,'Appearance')]");

    public StockFooter(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    public void navigateReports() {
        System.out.println("[ACTION] Clicking Reports");
        driver.findElement(tileReports).click();
    }
    public void navigateNotification() {
        System.out.println("[ACTION] Clicking notifications");
        driver.findElement(titleNotification).click();

    }
    public void navigateSetting() {
        System.out.println("[ACTION] Clicking Settings");
        driver.findElement(tileSettings).click();

    }
    public void navigateHome() {
        System.out.println("[ACTION] Clicking Home");
        driver.findElement(tileHome).click();

    }
    public void navigateDailyStockReport() {
        System.out.println("[ACTION] Clicking Daily Stock Report");
        driver.findElement(tileDailyStockReport).click();
        driver.navigate().back();

    }
    public void navigateTileAppearance() {
        System.out.println("[ACTION] Clicking Appearance");
        driver.findElement(tileAppearance).click();
        driver.navigate().back();

    }

}
