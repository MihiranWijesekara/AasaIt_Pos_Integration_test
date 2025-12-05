package com.flutterpos.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ManagerDashboard {

    private AppiumDriver driver;

    // Page title
    private By titleManager = By.xpath("//*[@text='Owner / Manager']");

    // Dashboard Tiles
    private By tileAddUser = By.xpath("//*[@text='Add User']");
    private By tileUserManagement = By.xpath("//*[@text='User Management']");
    private By tileCreditors = By.xpath("//*[@text='Creditors']");
    private By tileSalesReports = By.xpath("//*[@text='Sales Reports']");
    private By tileInsights = By.xpath("//*[@text='Insights']");
    private By tileProfitMargins = By.xpath("//*[@text='Profit Margins']");
    private By tileAuditLogs = By.xpath("//*[@text='Audit Logs']");
    private By tilePromotions = By.xpath("//*[@text='Promotions']");

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
    }

    public void openUserManagement() {
        System.out.println("[ACTION] Clicking User Management tile");
        driver.findElement(tileUserManagement).click();
    }

    public void openCreditors() {
        System.out.println("[ACTION] Clicking Creditors tile");
        driver.findElement(tileCreditors).click();
    }

    public void openSalesReports() {
        System.out.println("[ACTION] Clicking Sales Reports tile");
        driver.findElement(tileSalesReports).click();
    }

    public void openInsights() {
        System.out.println("[ACTION] Clicking Insights tile");
        driver.findElement(tileInsights).click();
    }

    public void openProfitMargins() {
        System.out.println("[ACTION] Clicking Profit Margins tile");
        driver.findElement(tileProfitMargins).click();
    }

    public void openAuditLogs() {
        System.out.println("[ACTION] Clicking Audit Logs tile");
        driver.findElement(tileAuditLogs).click();
    }

    public void openPromotions() {
        System.out.println("[ACTION] Clicking Promotions tile");
        driver.findElement(tilePromotions).click();
    }
}
