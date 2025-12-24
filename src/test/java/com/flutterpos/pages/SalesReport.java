package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class SalesReport {

    private final AppiumDriver driver;

    // Dashboard Tiles
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
    private final By profitMarginReport =
            AppiumBy.xpath("//*[contains(@text,'Profit & Margin') or contains(@content-desc,'Profit & Margin')]");
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

    public SalesReport(AppiumDriver driver) {
        this.driver = driver;
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
                return;
            }

            System.out.println("[INFO] '" + tileName + "' not visible yet. Scrolling down... (attempt " + (i + 1) + ")");
            scrollDown();
        }

        // Final attempt after scrolling
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            elements.get(0).click();
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

    // Actions with Logs + scroll
    public void openItemDetailsReport() {
        System.out.println("Items Details Report");
        driver.findElement(itemDetailsReport).click();
        driver.navigate().back();

    }

    public void openCustomerDetailsReport() {
        clickTile(customerDetailsReport, "Customer Details Report");
        driver.navigate().back();
    }

    public void openUserDetailsReport() {
        clickTile(userDetailsReport, "User Details Report");
        driver.navigate().back();
    }

    public void openInvoiceListReport() {
        clickTile(invoiceListReport, "Invoice List Report");
        driver.navigate().back();
    }

    public void openRefundBillsReport() {
        clickTile(refundBillsReport, "Refund Bills Report");
        driver.navigate().back();
    }

    public void openCardPayment() {
        clickTile(cardPaymentReport, "Card Payment Report");
        driver.navigate().back();
    }

    public void openCashPayment() {
        clickTile(cashPaymentReport, "Cash Payment Report");
        driver.navigate().back();
    }

    public void openPaymentReport() {
        clickTile(checkPaymentReport, "Payment Report");
        driver.navigate().back();
    }

    public void openDailySalesReport() {
        clickTile(dailySalesReport, "Daily Sales Report");
        driver.navigate().back();
    }

    public void openProfitMarginsReport() {
        clickTile(profitMarginReport, "Profit & Margin Report");
        driver.navigate().back();
    }

    public void openCreditSalesReport() {
        clickTile(CreditsalesReport, "Credit Sales Report");
        driver.navigate().back();
    }

    public void openCashEntryReport() {
        clickTile(startingCashEntryReport, "Starting Cash Entry Report");
        driver.navigate().back();
    }

    public void openDiscountGranted() {
        clickTile(discountGrantedReport, "Discount Granted Report");
        driver.navigate().back();
    }

    public void openUnpaidPurchaseReport() {
        clickTile(unpaidPurchasesReport, "Unpaid Purchase Report");
        driver.navigate().back();
    }

    public void openSupplierListReport() {
        clickTile(supplierListReport, "Supplier List Report");
        driver.navigate().back();
    }

    public void openStockReport() {
        clickTile(stockReport, "Stock Report");
        driver.navigate().back();
    }

    public void openTransactionHistoryReport() {
        clickTile(transactionHistoryReport, "Transaction History Report");
        driver.navigate().back();
    }

    public void openReorderHistoryReport() {
        clickTile(reorderHistoryReport, "Reorder History Report");
        driver.navigate().back();
    }

    public void openLowStockWarningReport() {
        clickTile(lowStockWarningReport, "Low Stock Warning Report");
        driver.navigate().back();
    }
}
