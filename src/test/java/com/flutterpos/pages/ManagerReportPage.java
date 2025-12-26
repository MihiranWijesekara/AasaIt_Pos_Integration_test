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


    //Report Hub page
    private final By itemDetailsReport =
            AppiumBy.xpath("//*[contains(@text,'Items Details') or contains(@content-desc,'Items Details')]");
    private final By customerDetailsReport =
            AppiumBy.xpath("//*[contains(@text,'Customers Details') or contains(@content-desc,'Customers Details')]");
    private final By userDetailsReport =
            AppiumBy.xpath("//*[contains(@text,'Users Details') or contains(@content-desc,'Users Details')]");
    private final By invoiceListReport =
            AppiumBy.xpath("//*[contains(@text,'Invoice List') or contains(@content-desc,'Invoice List')]");
    private final By refundBillsReport =
            AppiumBy.xpath("//*[contains(@text,'Refund Bills') or contains(@content-desc,'Refund Bills')]");
    private final By cardPaymentReport =
            AppiumBy.xpath("//*[contains(@text,'Card Payment') or contains(@content-desc,'Card Payment')]");
    private final By cashPaymentReport =
            AppiumBy.xpath("//*[contains(@text,'Cash Payment') or contains(@content-desc,'Cash Payment')]");
    private final By checkPaymentReport =
            AppiumBy.xpath("//*[contains(@text,'Check Payment') or contains(@content-desc,'Check Payment')]");
    private final By dailySalesReport =
            AppiumBy.xpath("//*[contains(@text,'Daily Sales') or contains(@content-desc,'Daily Sales')]");
//    private final By profitMarginReport =
//            AppiumBy.xpath("//*[contains(@text,'Profit & Margin') or contains(@content-desc,'Profit & Margin')]");
    private final By CreditsalesReport =
            AppiumBy.xpath("//*[contains(@text,'Credit Sales') or contains(@content-desc,'Credit Sales')]");
    private final By startingCashEntryReport =
            AppiumBy.xpath("//*[contains(@text,'Starting Cash Entry') or contains(@content-desc,'Starting Cash Entry')]");
    private final By discountGrantedReport =
            AppiumBy.xpath("//*[contains(@text,'Discount Granted') or contains(@content-desc,'Discount Granted')]");
    private final By unpaidPurchasesReport =
            AppiumBy.xpath("//*[contains(@text,'Unpaid Purchases') or contains(@content-desc,'Unpaid Purchases')]");
    private final By supplierListReport =
            AppiumBy.xpath("//*[contains(@text,'Supplier List') or contains(@content-desc,'Supplier List')]");
    private final By stockReport =
            AppiumBy.xpath("//*[contains(@text,'Stock Report') or contains(@content-desc,'Stock Report')]");
    private final By transactionHistoryReport =
            AppiumBy.xpath("//*[contains(@text,'Transaction History') or contains(@content-desc,'Transaction History')]");
    private final By reorderHistoryReport =
            AppiumBy.xpath("//*[contains(@text,'Reorder History') or contains(@content-desc,'Reorder History')]");
    private final By lowStockWarningReport =
            AppiumBy.xpath("//*[contains(@text,'Low Stock Warning') or contains(@content-desc,'Low Stock Warning')]");


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

//    public void clickReportHubAndStay() {
//        System.out.println("[ACTION] Opening Report Hub Dashboard and staying on page");
//
//        // Wait for the Reports tile to be clickable
//        wait.until(ExpectedConditions.elementToBeClickable(tileReportsHub));
//        driver.findElement(tileReportsHub).click();
//
//        System.out.println("[SUCCESS] Now staying on Registered Users page");
//    }

    public void navigateReportHubAndStay() {
        System.out.println("[ACTION] Clicking Report Hub And Stay");
        driver.findElement(tileReportsHub).click();

    }


    //Report Hub
    public void openItemDetailsReport() {
        System.out.println("Items Details Report");
        driver.findElement(itemDetailsReport).click();
        driver.navigate().back();

    }

    public void openCustomerDetailsReport() {
        System.out.println("Customer Details Report");
        driver.findElement(customerDetailsReport).click();
        driver.navigate().back();
    }

    public void openUserDetailsReport() {
        System.out.println("User Details Report");
        driver.findElement(userDetailsReport).click();
        driver.navigate().back();
    }

    public void openInvoiceListReport() {
        System.out.println("Invoice List Report");
        driver.findElement(invoiceListReport).click();
        driver.navigate().back();
    }

    public void openRefundBillsReport() {
        System.out.println("Refund Bills Report");
        driver.findElement(refundBillsReport).click();
//        driver.navigate().back();
    }

    public void openCardPayment() {
        System.out.println("Card Payment Report");
        driver.findElement(cardPaymentReport).click();
//        driver.navigate().back();
    }

    public void openCashPayment() {
        System.out.println("Cash Payment Report");
        driver.findElement(cashPaymentReport).click();
//        driver.navigate().back();
    }

    public void openPaymentReport() {
        System.out.println("Payment Report");
        driver.findElement(checkPaymentReport).click();
//        driver.navigate().back();
    }

    public void openDailySalesReport() {
        System.out.println("Daily Sales Report");
        driver.findElement(userDetailsReport).click();
//        driver.navigate().back();
    }

//    public void openProfitMarginsReport() {
//        System.out.println("Profit & Margin Report");
//        driver.findElement(profitMarginReport).click();
////        driver.navigate().back();
//    }

    public void openCreditSalesReport() {
        System.out.println("Credit Sales Report");
        driver.findElement(CreditsalesReport).click();
//        driver.navigate().back();
    }

    public void openCashEntryReport() {
        System.out.println("Starting Cash Entry Report");
        driver.findElement(startingCashEntryReport).click();
//        driver.navigate().back();
    }

    public void openDiscountGranted() {
        System.out.println("Discount Granted Report");
        driver.findElement(discountGrantedReport).click();
//        driver.navigate().back();
    }

    public void openUnpaidPurchaseReport() {
        System.out.println("Unpaid Purchase Report");
        driver.findElement(unpaidPurchasesReport).click();
//        driver.navigate().back();
    }

    public void openSupplierListReport() {
        System.out.println("Supplier List Report");
        driver.findElement(supplierListReport).click();
//        driver.navigate().back();
    }

    public void openStockReport() {
        System.out.println("Stock Report");
        driver.findElement(stockReport).click();
//        driver.navigate().back();
    }

    public void openTransactionHistoryReport() {
        System.out.println("Transaction History Report");
        driver.findElement(transactionHistoryReport).click();
//        driver.navigate().back();
    }

    public void openReorderHistoryReport() {
        System.out.println("Reorder History Report");
        driver.findElement(reorderHistoryReport).click();
        driver.navigate().back();
    }

    public void openLowStockWarningReport() {
        System.out.println("Low Stock Warning Report");
        driver.findElement(lowStockWarningReport).click();
//        driver.navigate().back();
    }


}
