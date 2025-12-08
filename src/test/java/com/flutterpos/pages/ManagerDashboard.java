package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ManagerDashboard {

    private AppiumDriver driver;

    // Page title
    private final By titleManager =
            AppiumBy.xpath("//*[contains(@text,'Owner') or contains(@content-desc,'Owner')]");
    // Dashboard Tiles
    private final By tileAddUser =
            AppiumBy.xpath("//*[contains(@text,'Add User') or contains(@content-desc,'Add User')]");
    private final By tileUserManagement =
            AppiumBy.xpath("//*[contains(@text,'User Management') or contains(@content-desc,'User Management')]");
    private final By tileCreditors =
            AppiumBy.xpath("//*[contains(@text,'Creditors') or contains(@content-desc,'Creditors')]");
    private final By tileSalesReports =
            AppiumBy.xpath("//*[contains(@text,'Sales Reports') or contains(@content-desc,'Sales Reports')]");
    private final By tileInsights =
            AppiumBy.xpath("//*[contains(@text,'Insights') or contains(@content-desc,'Insights')]");
    private final By tileProfitMargins =
            AppiumBy.xpath("//*[contains(@text,'Profit Margins') or contains(@content-desc,'Profit Margins')]");
    private final By tileAuditLogs =
            AppiumBy.xpath("//*[contains(@text,'Audit Logs') or contains(@content-desc,'Audit Logs')]");
    private final By tilePromotions =
            AppiumBy.xpath("//*[contains(@text,'Promotions') or contains(@content-desc,'Promotions')]");

    public ManagerDashboard(AppiumDriver driver) {  // <-- constructor
        this.driver = driver;
    }


    // Check Dashboard visible
    public boolean isDashboardVisible() {
        boolean visible = !driver.findElements(titleManager).isEmpty();
        System.out.println("[INFO] Manager Dashboard visible: " + visible);
        return visible;
    }

    // Actions with Logs
    public void openAddUser() {
        System.out.println("[ACTION] Clicking Add User tile");
        driver.findElement(tileAddUser).click();
        driver.navigate().back();
    }

    public void openUserManagement() {
        System.out.println("[ACTION] Clicking User Management tile");
        driver.findElement(tileUserManagement).click();
        driver.navigate().back();
    }

    public void openCreditors() {
        System.out.println("[ACTION] Clicking Creditors tile");
        driver.findElement(tileCreditors).click();
        driver.navigate().back();
    }

    public void openSalesReports() {
        System.out.println("[ACTION] Clicking Sales Reports tile");
        driver.findElement(tileSalesReports).click();
        driver.navigate().back();
    }

    public void openInsights() {
        System.out.println("[ACTION] Clicking Insights tile");
        driver.findElement(tileInsights).click();
        driver.navigate().back();
    }

    public void openProfitMargins() {
        System.out.println("[ACTION] Clicking Profit Margins tile");
        driver.findElement(tileProfitMargins).click();
        driver.navigate().back();
    }

    public void openAuditLogs() {
        System.out.println("[ACTION] Clicking Audit Logs tile");
        driver.findElement(tileAuditLogs).click();
        driver.navigate().back();
    }

    public void openPromotions() {
        System.out.println("[ACTION] Clicking Promotions tile");
        driver.findElement(tilePromotions).click();
        driver.navigate().back();
    }

    public void openAddUserAndStay() {
        System.out.println("[ACTION] Clicking Add User tile (stay on page)");
        driver.findElement(tileAddUser).click();
    }

    public void openSalesReportAndStay() {
        System.out.println("[ACTION] Clicking Sales Report tile (stay on page)");
        driver.findElement(tileSalesReports).click();
    }

}
