package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManagerReportPage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private final By tileReports =
            AppiumBy.xpath("//*[contains(@text,'Reports') or contains(@content-desc,'Reports')]");
    private final By tileDailySummary =
            AppiumBy.xpath("//*[contains(@text,'Daily Summary') or contains(@content-desc,'Daily Summary')]");
    private final By tileReportsHub =
            AppiumBy.xpath("//*[contains(@text,'Reports Hub') or contains(@content-desc,'Reports Hub')]");
    private final By titleTopProducts =
            AppiumBy.xpath("//*[contains(@text,'Top Products') or contains(@content-desc,'Top Products')]");
    private final By tileProfitSplit =
            AppiumBy.xpath("//*[contains(@text,'Profit Split') or contains(@content-desc,'Profit Split')]");


    public ManagerReportPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickReportFooterAndStay() {
        System.out.println("[ACTION] Opening Report Dashboard and staying on page");

        // Wait for the Reports tile to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(tileReports));
        driver.findElement(tileReports).click();

        // Wait for the Reports page to load before staying on it
        try {
            Thread.sleep(1500);  // Wait a bit for the page to load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[SUCCESS] Now staying on Registered Users page");
    }


    public void navigateDailySummary() {
        System.out.println("[ACTION] Clicking Daily Summary");
        driver.findElement(tileDailySummary).click();
        driver.navigate().back();

    }
    public void navigateReportHub() {
        System.out.println("[ACTION] Clicking Report Hub");
        driver.findElement(tileReportsHub).click();
        driver.navigate().back();

    }
    public void navigateTopProducts() {
        System.out.println("[ACTION] Clicking Top Products");
        driver.findElement(titleTopProducts).click();
        driver.navigate().back();

    }
    public void navigateProfitSplit() {
        System.out.println("[ACTION] Clicking Profit Split");
        driver.findElement(tileProfitSplit).click();
        driver.navigate().back();

    }

    public void clickReporHubAndStay() {
        System.out.println("[ACTION] Opening Report Hub Dashboard and staying on page");

        // Wait for the Reports tile to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(tileReportsHub));
        driver.findElement(tileReportsHub).click();

        // Wait for the Reports page to load before staying on it
        try {
            Thread.sleep(1500);  // Wait a bit for the page to load
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[SUCCESS] Now staying on Registered Users page");
    }


}
