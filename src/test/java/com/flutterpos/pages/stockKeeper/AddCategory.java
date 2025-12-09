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

public class AddCategory {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public AddCategory(AndroidDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ---------- LOCATORS ----------

    // FAB text: "+  Add Category" (bottom right)
    // IMPORTANT: use 'or' – not '||'
    private final By btnFabAddCategory =
            AppiumBy.xpath("//*[contains(@text,'Add Category') or contains(@content-desc,'Add Category')]");

    // All EditTexts on this screen (search + category name field)
    private final By editTextFields =
            AppiumBy.className("android.widget.EditText");

    // "Save Category" button inside the form
    private final By btnSaveCategory =
            AppiumBy.xpath("//*[contains(@text,'Save Category') or contains(@content-desc,'Save Category')]");

    // Snackbar text: Category "X" added successfully!
    private final By snackBarText =
            AppiumBy.xpath("//*[contains(@text,'Category') and contains(@text,'added successfully')]");

    // Category list row by text
    private By categoryRowByName(String name) {
        return AppiumBy.xpath(
                "//*[contains(@text,'" + name + "') or contains(@content-desc,'" + name + "')]"
        );
    }

    // ---------- HELPER: SCROLL ----------

    private void swipeUpOnce() {
        Dimension size = driver.manage().window().getSize();
        int width = size.width;
        int height = size.height;

        int startX = width / 2;
        int startY = (int) (height * 0.75);
        int endY   = (int) (height * 0.25);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(400),
                PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    private WebElement scrollToElement(By locator, int maxSwipes) {
        for (int i = 0; i < maxSwipes; i++) {
            try {
                return driver.findElement(locator);
            } catch (WebDriverException e) {
                swipeUpOnce();
            }
        }
        // last try with explicit wait (if still fails, throw timeout)
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ---------- PUBLIC ACTIONS ----------

    /** Make sure the Add Category form is open */
    public void openAddCategoryForm() {
        System.out.println("[AddCategory] Ensuring Add Category form is open...");

        // If form is already open (we see "Save Category" button), just return
        List<WebElement> existing = driver.findElements(btnSaveCategory);
        if (!existing.isEmpty()) {
            System.out.println("[AddCategory] Form already open.");
            return;
        }

        // Wait for FAB and tap it
        System.out.println("[AddCategory] Waiting for FAB Add Category...");
        WebElement fab = wait.until(
                ExpectedConditions.elementToBeClickable(btnFabAddCategory)
        );
        System.out.println("[AddCategory] Tapping FAB Add Category...");
        fab.click();

        // Wait until we have at least 2 EditTexts: search + category name
        System.out.println("[AddCategory] Waiting for EditTexts after FAB click...");
        wait.until(d -> {
            List<WebElement> fields = d.findElements(editTextFields);
            return fields.size() >= 2;
        });
        System.out.println("[AddCategory] EditTexts are visible, form should be open.");
    }

    /** Fill category name (image optional) */
    public void enterCategoryName(String name) {
        // Scroll down a bit so the bottom form is visible
        swipeUpOnce();
        swipeUpOnce();

        List<WebElement> fields = driver.findElements(editTextFields);
        if (fields.isEmpty()) {
            throw new NoSuchElementException("No EditText fields found on Add Category screen");
        }

        // Last EditText on the screen is the "Category Name" field
        WebElement nameField = fields.get(fields.size() - 1);
        System.out.println("[AddCategory] Typing into category name field...");
        nameField.click();
        nameField.clear();
        nameField.sendKeys(name);
    }

    /** Press "Save Category" button */
    public void tapSaveCategory() {
        WebElement btn = scrollToElement(btnSaveCategory, 5);
        System.out.println("[AddCategory] Tapping Save Category button...");
        btn.click();
    }

    /** Wait for success snackbar */
    public boolean waitForSuccessSnackBar() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(snackBarText));
            System.out.println("[AddCategory] Success snackbar detected.");
            return true;
        } catch (TimeoutException e) {
            System.out.println("[AddCategory] No success snackbar within timeout.");
            return false;
        }
    }

    /** Verify new category appears in list */
    public boolean isCategoryInList(String name) {
        try {
            Thread.sleep(1000);  // small wait
        } catch (InterruptedException ignored) {}

        try {
            scrollToElement(categoryRowByName(name), 6);
            System.out.println("[AddCategory] Category found in list: " + name);
            return true;
        } catch (Exception e) {
            System.out.println("[AddCategory] Category NOT found in list: " + name);
            return false;
        }
    }

    /** Full flow: open form, type name, save */
    public void createCategory(String name) {
        openAddCategoryForm();
        enterCategoryName(name);
        tapSaveCategory();
    }
}
