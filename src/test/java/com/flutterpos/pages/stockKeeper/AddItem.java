package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddItem {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public AddItem(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== LOCATORS =====

    // All EditTexts in this screen
    // Expected order for NEW item:
    // 0: Product Name
    // 1: Barcode (Optional)   <-- we will IGNORE this
    // 2: Initial Quantity (Optional)
    // 3: Unit Cost (Optional)
    // 4: Sales Price (Optional)
    // 5: Low-stock Warning (Required)
    // 6: Gradient (Optional)
    // 7: Notes (Optional)
    private final By editTexts = AppiumBy.className("android.widget.EditText");

    // Category dropdown (from Relations section)
    private final By categoryDropdown =
            AppiumBy.xpath("//*[contains(@text,'Select Category') or contains(@text,'Category')]");

    // Supplier dropdown
    private final By supplierDropdown =
            AppiumBy.xpath("//*[contains(@text,'Select Supplier') or contains(@text,'Supplier')]");

    // Options in dropdown list (generic)
    private final By dropdownOptions =
            AppiumBy.xpath("//android.widget.ListView//android.widget.TextView | //android.view.ViewGroup//android.widget.TextView");

    // Save button
    private final By btnSaveProduct =
            AppiumBy.xpath("//*[contains(@text,'Save Product')]");

    // Reset button
    private final By btnReset =
            AppiumBy.xpath("//*[contains(@text,'Reset')]");

    // Snackbar text -> "Item saved (ID: ...)"
    private final By snackBarText =
            AppiumBy.xpath("//*[contains(@text,'Item saved')]");

    // ===== BASIC HELPERS =====

    private List<WebElement> getAllEditTexts() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(editTexts));
    }

    private void typeInto(WebElement el, String text) {
        el.click();
        try {
            el.clear();
        } catch (Exception ignore) {
        }
        el.sendKeys(text);
        hideKeyboardIfVisible();
    }

    private void hideKeyboardIfVisible() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignore) {
            // ignore if no keyboard
        }
    }

    private void scrollToText(String text) {
        try {
            String script = "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollTextIntoView(\"" + text + "\")";
            driver.findElement(AppiumBy.androidUIAutomator(script));
        } catch (Exception ignore) {
            // maybe already visible
        }
    }

    private void openDropdown(By dropdown) {
        scrollToElement(dropdown);
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
    }

    private void scrollToElement(By locator) {
        try {
            driver.findElement(locator);
        } catch (NoSuchElementException e) {
            // best effort; not critical
        }
    }

    private void selectFirstDropdownOption() {
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions)
        );
        if (options.isEmpty()) {
            throw new IllegalStateException("No options found in dropdown");
        }
        options.get(0).click();
    }

    // ===== FIELD-LEVEL ACTIONS =====

    /**
     * Only fills Product Name.
     * Barcode field (index 1) is NOT touched (remains empty → auto generated).
     */
    public void fillBasicInfo(String productName) {
        List<WebElement> fields = getAllEditTexts();
        if (fields.isEmpty()) {
            throw new IllegalStateException("No text fields found in AddItem page");
        }

        // 0: Product Name
        typeInto(fields.get(0), productName);
        // 1: Barcode (optional) -> intentionally ignored
    }

    public void fillImagePriceStockSection(
            String initialQty,
            String unitCost,
            String salesPrice,
            String lowStockWarning
    ) {
        // Scroll to section labels to ensure fields are visible
        scrollToText("Low-stock Warning");

        List<WebElement> fields = getAllEditTexts();
        if (fields.size() < 6) {
            throw new IllegalStateException(
                    "Expected at least 6 EditTexts (name, barcode, qty, cost, price, low-stock). Found: " + fields.size()
            );
        }

        // 2: Initial Quantity (optional)
        if (initialQty != null && !initialQty.isEmpty()) {
            typeInto(fields.get(2), initialQty);
        }

        // 3: Unit Cost (optional)
        if (unitCost != null && !unitCost.isEmpty()) {
            typeInto(fields.get(3), unitCost);
        }

        // 4: Sales Price (optional)
        if (salesPrice != null && !salesPrice.isEmpty()) {
            typeInto(fields.get(4), salesPrice);
        }

        // 5: Low-stock Warning (required)
        typeInto(fields.get(5), lowStockWarning);
    }

    public void selectCategoryAndSupplier() {
        scrollToText("Relations");

        // Category
        openDropdown(categoryDropdown);
        selectFirstDropdownOption();

        // Supplier
        openDropdown(supplierDropdown);
        selectFirstDropdownOption();
    }

    public void tapSaveProduct() {
        scrollToText("Save Product");
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveProduct)).click();
    }

    public void tapReset() {
        scrollToText("Reset");
        wait.until(ExpectedConditions.elementToBeClickable(btnReset)).click();
    }

    public boolean waitForSuccessSnackBar() {
        try {
            WebElement snack = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(snackBarText)
            );
            String text = snack.getText();
            System.out.println("✅ AddItem snackbar: " + text);
            return text.contains("Item saved");
        } catch (TimeoutException e) {
            System.out.println("❌ Snackbar not found for AddItem within timeout");
            return false;
        }
    }

    // ===== HIGH-LEVEL FLOW =====

    /**
     * Full flow to create a simple item and wait for the success snackbar.
     * NOTE: No barcode input – it is left empty so the app will auto-generate it.
     */
    public boolean createItem(
            String productName,
            String initialQty,
            String unitCost,
            String salesPrice,
            String lowStockWarning
    ) {
        fillBasicInfo(productName); // no barcode
        fillImagePriceStockSection(initialQty, unitCost, salesPrice, lowStockWarning);
        selectCategoryAndSupplier();
        tapSaveProduct();
        return waitForSuccessSnackBar();
    }
}
