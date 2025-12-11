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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== LOCATORS =====

    // All EditTexts in this screen
    private final By editTexts =
            AppiumBy.className("android.widget.EditText");

    /**
     * Category "field" – tap the container that has a text like
     * "Select Category" or "Category".
     */
    private final By categoryField =
            AppiumBy.xpath(
                    "//android.view.ViewGroup[" +
                            ".//*[contains(@text,'Select Category') " +
                            "   or contains(@text,'Category') " +
                            "   or contains(@content-desc,'Select Category') " +
                            "   or contains(@content-desc,'Category')" +
                            "]" +
                            "]"
            );

    /**
     * Supplier "field"
     */
    private final By supplierField =
            AppiumBy.xpath(
                    "//android.view.ViewGroup[" +
                            ".//*[contains(@text,'Select Supplier') " +
                            "   or contains(@text,'Supplier') " +
                            "   or contains(@content-desc,'Select Supplier') " +
                            "   or contains(@content-desc,'Supplier')" +
                            "]" +
                            "]"
            );

    /**
     * Generic options inside the bottom sheet / list when a field is opened.
     */
    private final By dropdownOptions = AppiumBy.xpath(
            "//android.widget.ListView//android.widget.TextView" +
                    " | //android.widget.ScrollView//android.widget.TextView" +
                    " | //android.view.ViewGroup//android.widget.TextView"
    );

    // Save button
    private final By btnSaveProduct =
            AppiumBy.xpath("//*[contains(@text,'Save Product') or contains(@content-desc,'Save Product')]");

    // Reset button
    private final By btnReset =
            AppiumBy.xpath("//*[contains(@text,'Reset') or contains(@content-desc,'Reset')]");

    // Snackbar text -> "Item saved (ID: ...)"
    private final By snackBarText =
            AppiumBy.xpath("//*[contains(@text,'Item saved')]");

    // ===== BASIC HELPERS =====

    private List<WebElement> getAllEditTexts() {
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(editTexts)
        );
    }

    private void typeInto(WebElement el, String text) {
        el.click();
        try {
            el.clear();
        } catch (Exception ignore) {}
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

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX, startY
        ));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(400),
                PointerInput.Origin.viewport(),
                startX, endY
        ));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Scrolls up to maxSwipes times until at least one element matching locator
     * becomes present in the UI tree. Returns the first found element or throws.
     */
    private WebElement scrollUntilVisible(By locator, String description, int maxSwipes) {
        for (int i = 0; i < maxSwipes; i++) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                System.out.println("✅ " + description + " visible after " + i + " swipe(s)");
                return elements.get(0);
            }
            swipeUpOnce();
        }
        throw new NoSuchElementException(
                "Could not find " + description + " after " + maxSwipes + " swipes"
        );
    }

    private void selectFirstDropdownOption() {
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions)
        );
        if (options.isEmpty()) {
            throw new IllegalStateException("No options found in dropdown");
        }

        for (WebElement opt : options) {
            try {
                String txt = opt.getText();
                if (txt != null && !txt.trim().isEmpty()) {
                    opt.click();
                    System.out.println("✅ Selected dropdown option: " + txt);
                    return;
                }
            } catch (Exception ignore) {}
        }
        options.get(0).click();
        System.out.println("⚠️ Fallback: clicked first dropdown TextView (text may be empty).");
    }

    // ===== FIELD GETTERS =====

    private WebElement getProductNameField() {
        List<WebElement> fields = getAllEditTexts();
        if (fields.size() < 1) {
            throw new IllegalStateException("No EditText fields found for Product Name");
        }
        return fields.get(0);
    }

    private WebElement getInitialQuantityField() {
        List<WebElement> fields = getAllEditTexts();
        if (fields.size() < 3) {
            throw new IllegalStateException("Not enough EditText fields for Initial Quantity");
        }
        return fields.get(2);
    }

    private WebElement getUnitCostField() {
        List<WebElement> fields = getAllEditTexts();
        if (fields.size() < 4) {
            throw new IllegalStateException("Not enough EditText fields for Unit Cost");
        }
        return fields.get(3);
    }

    private WebElement getSalesPriceField() {
        List<WebElement> fields = getAllEditTexts();
        if (fields.size() < 5) {
            throw new IllegalStateException("Not enough EditText fields for Sales Price");
        }
        return fields.get(4);
    }

    private WebElement getLowStockField() {
        List<WebElement> fields = getAllEditTexts();
        if (fields.size() < 6) {
            throw new IllegalStateException("Not enough EditText fields for Low-stock Warning");
        }
        return fields.get(5);
    }

    // ===== FIELD-LEVEL ACTIONS =====

    public void fillBasicInfo(String productName) {
        WebElement nameField = getProductNameField();
        typeInto(nameField, productName);
        System.out.println("✅ Product name entered: " + productName);
    }

    public void enterInitialQuantity(String quantity) {
        WebElement field = getInitialQuantityField();
        typeInto(field, quantity);
        System.out.println("✅ Initial quantity entered: " + quantity);
    }

    public void enterUnitCost(String unitCost) {
        WebElement field = getUnitCostField();
        typeInto(field, unitCost);
        System.out.println("✅ Unit cost entered: " + unitCost);
    }

    public void enterSalesPrice(String salesPrice) {
        WebElement field = getSalesPriceField();
        typeInto(field, salesPrice);
        System.out.println("✅ Sales price entered: " + salesPrice);
    }

    public void enterLowStock(String lowStockThreshold) {
        WebElement field = getLowStockField();
        typeInto(field, lowStockThreshold);
        System.out.println("✅ Low-stock warning entered: " + lowStockThreshold);
    }

    // ===== RELATIONS & ACTION BUTTONS =====

    public void selectCategoryAndSupplier() {
        // --- Category ---
        try {
            WebElement cat = scrollUntilVisible(categoryField, "Category field", 6);
            wait.until(ExpectedConditions.elementToBeClickable(cat)).click();
            System.out.println("✅ Category field tapped");
            selectFirstDropdownOption();
        } catch (Exception e) {
            System.out.println("❌ Failed to tap/select Category: " + e.getMessage());
        }

        // --- Supplier ---
        try {
            WebElement sup = scrollUntilVisible(supplierField, "Supplier field", 6);
            wait.until(ExpectedConditions.elementToBeClickable(sup)).click();
            System.out.println("✅ Supplier field tapped");
            selectFirstDropdownOption();
        } catch (Exception e) {
            System.out.println("❌ Failed to tap/select Supplier: " + e.getMessage());
        }
    }

    public void tapSaveProduct() {
        WebElement btn = scrollUntilVisible(btnSaveProduct, "'Save Product' button", 8);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        System.out.println("✅ Save Product tapped");
    }

    public void tapReset() {
        WebElement btn = scrollUntilVisible(btnReset, "'Reset' button", 6);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        System.out.println("✅ Reset tapped");
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
}
