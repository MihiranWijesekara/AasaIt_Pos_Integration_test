package com.flutterpos.pages.stockKeeper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class AddItem {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public AddItem(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ===== LOCATORS =====

    // All EditTexts in this screen
    private final By editTexts = AppiumBy.className("android.widget.EditText");

    // Category container (Relations section)
    private final By categoryField = AppiumBy.xpath(
            "//android.view.ViewGroup[" +
                    ".//*[contains(@text,'Select Category') " +
                    "or contains(@text,'Category') " +
                    "or contains(@content-desc,'Select Category') " +
                    "or contains(@content-desc,'Category')]" +
                    "]"
    );

    // Supplier container (Relations section)
    private final By supplierField = AppiumBy.xpath(
            "//android.view.ViewGroup[" +
                    ".//*[contains(@text,'Select Supplier') " +
                    "or contains(@text,'Supplier') " +
                    "or contains(@content-desc,'Select Supplier') " +
                    "or contains(@content-desc,'Supplier')]" +
                    "]"
    );

    /**
     * ✅ FIXED: Dropdown options only from actual list containers.
     * Flutter bottom sheets often use RecyclerView.
     * (REMOVED the dangerous ViewGroup//TextView wildcard)
     */
    private final By dropdownOptions = AppiumBy.xpath(
            "//androidx.recyclerview.widget.RecyclerView//android.widget.TextView" +
                    " | //android.widget.ListView//android.widget.TextView"
    );

    // Buttons (Save Product / Reset)
    private final By btnSaveProduct =
            AppiumBy.xpath("//*[contains(@text,'Save Product') or contains(@content-desc,'Save Product')]");

    private final By btnReset =
            AppiumBy.xpath("//*[contains(@text,'Reset') or contains(@content-desc,'Reset')]");

    // Snackbar text -> "Item saved (ID: ...)"
    private final By snackBarText =
            AppiumBy.xpath("//*[contains(@text,'Item saved')]");

    // (Optional) Edit mode button text
    private final By btnUpdateProduct =
            AppiumBy.xpath("//*[contains(@text,'Update Product') or contains(@content-desc,'Update Product')]");

    // ===== BASIC HELPERS =====

    private List<WebElement> getAllEditTexts() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(editTexts));
    }

    private void typeInto(WebElement el, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        try { el.clear(); } catch (Exception ignore) {}
        el.sendKeys(text);
    }

    /**
     * Perform a single swipe up gesture (W3C actions).
     */
    private void swipeUpOnce() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.80);
        int endY   = (int) (size.height * 0.25);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(450), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Scrolls up to maxSwipes times until at least one element matching locator
     * becomes present in the UI tree.
     */
    private WebElement scrollUntilVisible(By locator, String description, int maxSwipes) {
        for (int i = 0; i <= maxSwipes; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                System.out.println("✅ " + description + " visible after " + i + " swipe(s)");
                return elements.get(0);
            }
            swipeUpOnce();
        }
        throw new NoSuchElementException("Could not find " + description + " after " + maxSwipes + " swipes");
    }

    /**
     * ✅ NEW: Wait until dropdown list container appears after tapping category/supplier.
     */
    private void waitForDropdownToOpen() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(AppiumBy.className("androidx.recyclerview.widget.RecyclerView")),
                ExpectedConditions.presenceOfElementLocated(AppiumBy.className("android.widget.ListView"))
        ));
    }

    /**
     * ✅ FIXED: Select first REAL option (skip headers like "Select", skip Cancel/Close)
     */
    private void selectFirstDropdownOption() {
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions)
        );

        for (WebElement opt : options) {
            try {
                String txt = opt.getText();
                if (txt == null) txt = "";
                txt = txt.trim();

                if (!txt.isEmpty()
                        && !txt.toLowerCase().contains("select")
                        && !txt.equalsIgnoreCase("cancel")
                        && !txt.equalsIgnoreCase("close")) {
                    opt.click();
                    System.out.println("✅ Selected dropdown option: " + txt);
                    return;
                }
            } catch (Exception ignore) {}
        }

        throw new IllegalStateException("No valid dropdown option found to select.");
    }

    private boolean isEditMode() {
        return !driver.findElements(btnUpdateProduct).isEmpty();
    }

    // ===== FIELD GETTERS (index-based) =====

    private WebElement getProductNameField() {
        List<WebElement> fields = getAllEditTexts();
        return fields.get(0);
    }

    private WebElement getBarcodeField() {
        List<WebElement> fields = getAllEditTexts();
        return fields.get(1);
    }

    private WebElement getInitialQuantityField() {
        // Add mode only
        return getAllEditTexts().get(2);
    }

    private WebElement getUnitCostField() {
        // Add mode only
        return getAllEditTexts().get(3);
    }

    private WebElement getSalesPriceField() {
        // Add mode only
        return getAllEditTexts().get(4);
    }

    private WebElement getLowStockField() {
        // ✅ Fix: low stock index changes in Edit mode
        List<WebElement> fields = getAllEditTexts();
        if (isEditMode()) {
            // Edit mode: name(0), barcode(1), lowStock(2)
            return fields.get(2);
        }
        // Add mode: name(0), barcode(1), qty(2), cost(3), price(4), lowStock(5)
        return fields.get(5);
    }

    // ===== FIELD-LEVEL ACTIONS =====

    public void fillProductName(String productName) {
        typeInto(getProductNameField(), productName);
        System.out.println("✅ Product name entered: " + productName);
    }

    public void fillBarcode(String barcode) {
        typeInto(getBarcodeField(), barcode);
        System.out.println("✅ Barcode entered: " + barcode);
    }

    public void fillPricesAndStockIfAddMode(String qty, String unitCost, String salesPrice) {
        if (!isEditMode()) {
            typeInto(getInitialQuantityField(), qty);
            System.out.println("✅ Initial quantity entered: " + qty);

            typeInto(getUnitCostField(), unitCost);
            System.out.println("✅ Unit cost entered: " + unitCost);

            typeInto(getSalesPriceField(), salesPrice);
            System.out.println("✅ Sales price entered: " + salesPrice);
        } else {
            System.out.println("⚠️ Edit mode detected → skipping qty/unitCost/salesPrice (not shown in UI)");
        }
    }

    public void fillLowStock(String lowStockThreshold) {
        typeInto(getLowStockField(), lowStockThreshold);
        System.out.println("✅ Low-stock warning entered: " + lowStockThreshold);
    }

    // ===== RELATIONS (CATEGORY + SUPPLIER) =====

    public void selectCategory() {
        WebElement cat = scrollUntilVisible(categoryField, "Category field", 8);
        wait.until(ExpectedConditions.elementToBeClickable(cat)).click();
        System.out.println("✅ Category field tapped");
        waitForDropdownToOpen();
        selectFirstDropdownOption();
    }

    public void selectSupplier() {
        WebElement sup = scrollUntilVisible(supplierField, "Supplier field", 8);
        wait.until(ExpectedConditions.elementToBeClickable(sup)).click();
        System.out.println("✅ Supplier field tapped");
        waitForDropdownToOpen();
        selectFirstDropdownOption();
    }

    public void selectCategoryAndSupplier() {
        try { selectCategory(); }
        catch (Exception e) { System.out.println("❌ Category failed: " + e.getMessage()); }

        try { selectSupplier(); }
        catch (Exception e) { System.out.println("❌ Supplier failed: " + e.getMessage()); }
    }

    // ===== BUTTONS =====

    public void tapSaveProduct() {
        WebElement btn = scrollUntilVisible(btnSaveProduct, "'Save Product' button", 10);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        System.out.println("✅ Save Product tapped");
    }

    public void tapReset() {
        WebElement btn = scrollUntilVisible(btnReset, "'Reset' button", 8);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        System.out.println("✅ Reset tapped");
    }

    public boolean waitForSuccessSnackBar() {
        try {
            WebElement snack = wait.until(ExpectedConditions.visibilityOfElementLocated(snackBarText));
            String text = snack.getText();
            System.out.println("✅ AddItem snackbar: " + text);
            return text != null && text.contains("Item saved");
        } catch (TimeoutException e) {
            System.out.println("❌ Snackbar not found within timeout");
            return false;
        }
    }

    // ===== FULL FLOW METHOD (EASY FOR TEST) =====

    public boolean addItemFullFlow(
            String name,
            String barcode,
            String qty,
            String unitCost,
            String salesPrice,
            String lowStock
    ) {
        fillProductName(name);
        fillBarcode(barcode);

        fillPricesAndStockIfAddMode(qty, unitCost, salesPrice);
        fillLowStock(lowStock);

        selectCategoryAndSupplier();
        tapSaveProduct();

        return waitForSuccessSnackBar();
    }
}
