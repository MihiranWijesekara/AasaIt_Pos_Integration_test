package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RegisterPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public RegisterPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ---------- Common Locators ----------
    private final By titleShopStep =
            AppiumBy.xpath("//*[contains(@text,'Welcome to POS Setup') or contains(@text,'Shop Details')]");
    private final By titleManagerStep =
            AppiumBy.xpath("//*[contains(@text,'Create Manager Account') or contains(@text,'Manager Registration')]");

    private final By btnNext = AppiumBy.androidUIAutomator("new UiSelector().text(\"Next\")");
    private final By btnBack = AppiumBy.androidUIAutomator("new UiSelector().text(\"Back\")");
    private final By btnFinish = AppiumBy.androidUIAutomator("new UiSelector().text(\"Finish & Go To Login\")");

    private final By editTexts = AppiumBy.className("android.widget.EditText");

    // ---------- Public Flow Methods ----------

    public void waitForInstallationPage() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(titleShopStep),
                ExpectedConditions.presenceOfElementLocated(titleManagerStep)
        ));
    }

    public void fillShopDetails(
            String displayName,
            String legalName,
            String phone,
            String email,
            String address,
            String currency,
            String taxRegime,
            String taxId
    ) {
        ensureShopStep();

        typeIntoField("Display name", displayName, 0);
        typeIntoField("Legal name", legalName, 1);
        typeIntoField("Phone", phone, 2);
        typeIntoField("Email", email, 3);
        typeIntoField("Address", address, 4);
        typeIntoField("Currency (e.g., LKR, USD)", currency, 5);
        typeIntoField("Tax regime (e.g., VAT)", taxRegime, 6);
        typeIntoField("Tax ID", taxId, 7);
    }

    public void goNextToManager() {
        tap(btnNext);
        ensureManagerStep();
    }

    public void fillManagerDetails(String fullName, String email, String contact, String password) {
        ensureManagerStep();

        typeIntoField("Full name", fullName, 0);
        typeIntoField("Email", email, 1);
        typeIntoField("Contact number", contact, 2);
        typeIntoField("Password", password, 3);
    }

    public void finishAndGoToLogin() {
        ensureManagerStep();
        tap(btnFinish);
    }

    public void goBackToShop() {
        tap(btnBack);
        ensureShopStep();
    }

    // ---------- Core: Your fallback method style ----------

    private void typeIntoField(String labelText, String value, int fallbackIndex) {
        if (value == null || value.trim().isEmpty()) return;

        WebElement field = getFieldByLabelOrIndex(labelText, fallbackIndex);

        clearAndType(field, value);
        hideKeyboardSafe();
    }

    /**
     * This is your requested pattern:
     * 1) Try “nice” semantic locator first (text/content-desc contains label)
     * 2) Fallback to EditText[index]
     * 3) Dump page source if nothing found
     */
    private WebElement getFieldByLabelOrIndex(String labelText, int fallbackIndex) {
        String primaryXpath =
                "//*[contains(@content-desc,'" + escapeXpath(labelText) + "') or contains(@text,'" + escapeXpath(labelText) + "')]";

        try {
            // (Optional) scroll to label area first (helps on long forms)
            scrollToTextContains(labelText);

            // Try primary semantic locator
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));

        } catch (TimeoutException e) {
            System.out.println("⚠️ Primary locator failed for field: " + labelText +
                    " | Trying fallback EditText index=" + fallbackIndex);

            // Fallback: use EditText list index (try scroll once if not enough)
            WebElement byIndex = getEditTextByIndexWithScroll(fallbackIndex);
            if (byIndex != null) {
                System.out.println("✅ Using EditText[" + fallbackIndex + "] for: " + labelText);
                return byIndex;
            }

            // Last fallback: dump page source for debugging
            String src = driver.getPageSource();
            System.out.println("❌ Could not locate field: " + labelText);
            System.out.println("Page source (first 3000 chars):");
            System.out.println(src.substring(0, Math.min(3000, src.length())));

            throw new NoSuchElementException("Cannot locate field: " + labelText + " (fallback index " + fallbackIndex + ")");
        }
    }

    private WebElement getEditTextByIndexWithScroll(int index) {
        for (int attempt = 0; attempt < 2; attempt++) {
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > index) return inputs.get(index);
            scrollDownSmall();
        }
        return null;
    }

    // ---------- Internal Helpers ----------

    private void ensureShopStep() {
        wait.until(ExpectedConditions.presenceOfElementLocated(titleShopStep));
    }

    private void ensureManagerStep() {
        wait.until(ExpectedConditions.presenceOfElementLocated(titleManagerStep));
    }

    private void tap(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void clearAndType(WebElement el, String text) {
        wait.until(ExpectedConditions.visibilityOf(el));
        el.click();
        try {
            el.clear();
        } catch (Exception ignored) {
            el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            el.sendKeys(Keys.DELETE);
        }
        el.sendKeys(text);
    }

    private void hideKeyboardSafe() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
        }
    }

    private void scrollToTextContains(String text) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
            ));
        } catch (Exception ignored) {
        }
    }

    private void scrollDownSmall() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.70);
        int endY = (int) (size.height * 0.35);

        new TouchAction<>(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    private String escapeXpath(String s) {
        return s.replace("'", "\\'");
    }
}
