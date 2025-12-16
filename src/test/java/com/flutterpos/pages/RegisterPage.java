package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class RegisterPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final String APP_PACKAGE = "com.example.pos";

    public RegisterPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ---------- Common Locators ----------
    private final By titleShopStep =
            AppiumBy.xpath("//*[contains(@text,'Welcome to POS Setup') or contains(@text,'Shop Details')]");
    private final By titleManagerStep =
            AppiumBy.xpath("//*[contains(@text,'Create Manager Account') or contains(@text,'Manager Registration')]");

    private final By editTexts = AppiumBy.className("android.widget.EditText");
    private final By btnFinish = AppiumBy.androidUIAutomator("new UiSelector().text(\"Finish & Go To Login\")");
    private final By btnBack = AppiumBy.androidUIAutomator("new UiSelector().text(\"Back\")");

    // =========================================================
    //  Field Getters
    // =========================================================

    private WebElement getDisplayField() {
        String primaryXpath = "//*[contains(@content-desc,'Display name') or contains(@text,'Display name')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Display failed. Trying EditText[0]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 0) return inputs.get(0);
            throw e;
        }
    }

    private WebElement getLegalNameField() {
        String primaryXpath = "//*[contains(@content-desc,'Legal name') or contains(@text,'Legal name')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Legal name failed. Trying EditText[1]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 1) return inputs.get(1);
            dumpSource("Legal name");
            throw e;
        }
    }

    private WebElement getPhoneField() {
        String primaryXpath = "//*[contains(@content-desc,'Phone') or contains(@text,'Phone')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Phone failed. Trying EditText[2]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 2) return inputs.get(2);
            dumpSource("Phone");
            throw e;
        }
    }

    private WebElement getEmailField() {
        String primaryXpath = "//*[contains(@content-desc,'Email') or contains(@text,'Email')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Email failed. Trying EditText[3]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 3) return inputs.get(3);
            dumpSource("Email");
            throw e;
        }
    }

    private WebElement getAddressField() {
        String primaryXpath =
                "//*[contains(@content-desc,'Address') or contains(@text,'Address') " +
                        "or contains(@content-desc,'address') or contains(@text,'address')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Address failed. Trying EditText[4]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 4) return inputs.get(4);
            dumpSource("Address");
            throw e;
        }
    }

    private WebElement getCurrencyField() {
        String primaryXpath = "//*[contains(@content-desc,'Currency') or contains(@text,'Currency')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Currency failed. Trying EditText[5]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 5) return inputs.get(5);
            dumpSource("Currency");
            throw e;
        }
    }

    private WebElement getTaxField() {
        String primaryXpath =
                "//*[contains(@content-desc,'Tax regime') or contains(@text,'Tax regime') " +
                        "or contains(@content-desc,'VAT') or contains(@text,'VAT')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Tax regime failed. Trying EditText[6]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 6) return inputs.get(6);
            dumpSource("Tax regime");
            throw e;
        }
    }

    private WebElement getTaxIDField() {
        String primaryXpath = "//*[contains(@content-desc,'Tax ID') or contains(@text,'Tax ID')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Tax ID failed. Trying EditText[7]...");
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > 7) return inputs.get(7);
            dumpSource("Tax ID");
            throw e;
        }
    }

    // =========================================================
    //  Second page (Manager)
    // =========================================================

    private WebElement getFullNameField() {
        String primaryXpath =
                "//*[contains(@content-desc,'Full name') or contains(@text,'Full name') or " +
                        "contains(@content-desc,'Full Name') or contains(@text,'Full Name')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Full name failed. Trying EditText[0]...");
            WebElement el = getEditTextByIndexWithSmallScroll(0);
            if (el != null) return el;
            dumpSource("Full Name");
            throw e;
        }
    }

    private WebElement getEmailSecField() {
        String primaryXpath = "//*[contains(@content-desc,'Email') or contains(@text,'Email')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Email (manager) failed. Trying EditText[1]...");
            WebElement el = getEditTextByIndexWithSmallScroll(1);
            if (el != null) return el;
            dumpSource("Email (Manager)");
            throw e;
        }
    }

    private WebElement getContactNumberField() {
        String primaryXpath =
                "//*[contains(@content-desc,'Contact Number') or contains(@text,'Contact Number') or " +
                        "contains(@content-desc,'Contact number') or contains(@text,'Contact number')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Contact Number failed. Trying EditText[2]...");
            WebElement el = getEditTextByIndexWithSmallScroll(2);
            if (el != null) return el;
            dumpSource("Contact Number");
            throw e;
        }
    }

    private WebElement getPasswordField() {
        String primaryXpath = "//*[contains(@content-desc,'Password') or contains(@text,'Password')]";
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));
        } catch (TimeoutException e) {
            System.out.println("⚠️ Password failed. Trying EditText[3]...");
            WebElement el = getEditTextByIndexWithSmallScroll(3);
            if (el != null) return el;
            dumpSource("Password");
            throw e;
        }
    }

    // =========================================================
    //  Public typing helpers
    // =========================================================

    public void enterDisplayField(String displayName) {
        WebElement field = getDisplayField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(displayName);
        System.out.println("✅ Display Field entered.");
    }

    public void enterLegalNameField(String legalName) {
        WebElement field = getLegalNameField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(legalName);
        System.out.println("✅ Legal Name entered.");
    }

    public void enterPhoneField(String phone) {
        WebElement field = getPhoneField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(phone);
        System.out.println("✅ Phone entered.");
    }

    public void enterEmailField(String email) {
        WebElement field = getEmailField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(email);
        System.out.println("✅ Email entered.");
    }

    public void enterAddressField(String address) {
        WebElement field = getAddressField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(address);
        System.out.println("✅ Address entered.");
    }

    public void enterCurrencyField(String currency) {
        WebElement field = getCurrencyField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(currency);
        System.out.println("✅ Currency entered.");
    }

    public void enterTaxField(String taxRegime) {
        WebElement field = getTaxField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(taxRegime);
        System.out.println("✅ taxRegime entered.");
    }

    public void enterTaxIDField(String taxId) {
        WebElement field = getTaxIDField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(taxId);
        System.out.println("✅ TaxId entered.");
    }

    // Second page
    public void enterFullNameField(String fullName) {
        WebElement field = getFullNameField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(fullName);
        System.out.println("✅ Full Name entered.");
    }

    public void enterEmailSecField(String EmailSecond) {
        WebElement field = getEmailSecField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(EmailSecond);
        System.out.println("✅ Email entered.");
    }

    public void enterContactNumberField(String ContactNumber) {
        WebElement field = getContactNumberField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(ContactNumber);
        System.out.println("✅ Contact Number entered.");
    }

    public void enterPasswordField(String password) {
        WebElement field = getPasswordField();
        field.click();
        try { field.clear(); } catch (Exception ignored) {}
        field.sendKeys(password);
        System.out.println("✅ Password entered.");
    }

    // =========================================================
    //  Step waiting & step detection
    // =========================================================

    public void waitForInstallationPage() {
        wait.until(d -> d.findElements(editTexts).size() >= 1);
    }

    public boolean isManagerStep() {
        return driver.findElements(editTexts).size() <= 4;
    }

    // =========================================================
    //  Flow methods
    // =========================================================

    public void nextStep(String displayName, String legalName, String phone, String email,
                         String address, String currency, String taxRegime, String taxId) {

        System.out.println("next step...");
        enterDisplayField(displayName);
        enterLegalNameField(legalName);
        enterPhoneField(phone);
        enterEmailField(email);
        enterAddressField(address);
        enterCurrencyField(currency);
        enterTaxField(taxRegime);
        enterTaxIDField(taxId);

        scrollShopFormToBottom();

        tapNext();

        ensureManagerStep();
    }

    public void fillManagerDetails(String fullName, String EmailSecond, String ContactNumber, String password) {
        ensureManagerStep();

        enterFullNameField(fullName);
        enterEmailSecField(EmailSecond);
        enterContactNumberField(ContactNumber);
        enterPasswordField(password);

        // Scroll down to make sure button is visible
//        scrollDownSmall();
//        sleep(500);

        // Use the improved finish method
        finishAndGoToLogin();
    }

    // =========================================================
    //  UPDATED: Improved finishAndGoToLogin method
    // =========================================================

    public void finishAndGoToLogin() {
        ensureManagerStep();

        System.out.println("🔍 Looking for Finish & Go To Login button...");

        // Try multiple strategies
        if (clickFinishByText()) {
            System.out.println("✅ Finish button clicked successfully!");
            return;
        }

        if (clickFinishByContentDesc()) {
            System.out.println("✅ Finish button clicked via content-desc!");
            return;
        }

        if (clickFinishByXpath()) {
            System.out.println("✅ Finish button clicked via XPath!");
            return;
        }

        if (clickFinishByCoordinates()) {
            System.out.println("✅ Finish button clicked via coordinates!");
            return;
        }

        // Last resort: dump page source for debugging
        dumpSource("Finish button");
        throw new NoSuchElementException("Could not find or click Finish & Go To Login button");
    }

    private boolean clickFinishByText() {
        try {
            String[] finishTexts = {
                    "Finish & Go To Login",
                    "Finish and Go To Login",
                    "Finish",
                    "FINISH",
                    "Complete",
                    "Complete Setup",
                    "Go To Login"
            };

            for (String text : finishTexts) {
                try {
                    By locator = AppiumBy.xpath("//*[@text='" + text + "']");
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    System.out.println("✅ Finish clicked by exact text: " + text);
                    return true;
                } catch (Exception e) {
                    // Try with contains
                    By locator = AppiumBy.xpath("//*[contains(@text, '" + text + "')]");
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            element.click();
                            System.out.println("✅ Finish clicked by contains text: " + text);
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ clickFinishByText failed: " + e.getMessage());
        }
        return false;
    }

    private boolean clickFinishByContentDesc() {
        try {
            String[] descs = {
                    "Finish & Go To Login",
                    "Finish_and_Go_To_Login",
                    "finish_button",
                    "complete_button",
                    "btnFinish",
                    "finish",
                    "go_to_login"
            };

            for (String desc : descs) {
                try {
                    By locator = AppiumBy.xpath("//*[@content-desc='" + desc + "']");
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    System.out.println("✅ Finish clicked by exact content-desc: " + desc);
                    return true;
                } catch (Exception e) {
                    // Try with contains
                    By locator = AppiumBy.xpath("//*[contains(@content-desc, '" + desc + "')]");
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            element.click();
                            System.out.println("✅ Finish clicked by contains content-desc: " + desc);
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ clickFinishByContentDesc failed: " + e.getMessage());
        }
        return false;
    }

    private boolean clickFinishByXpath() {
        try {
            // Try various XPath patterns
            String[] xpaths = {
                    "//android.widget.Button[contains(@text, 'Finish')]",
                    "//android.widget.Button[contains(@text, 'Complete')]",
                    "//*[contains(@text, 'Login') and contains(@text, 'Finish')]",
                    "//*[@clickable='true' and contains(@text, 'Finish')]",
                    "//*[@enabled='true' and contains(@text, 'Finish')]",
                    "//*[contains(@text, 'Go To Login')]",
                    "//android.view.ViewGroup[.//*[contains(@text, 'Finish')]]",
                    "//android.widget.TextView[contains(@text, 'Finish')]/.."
            };

            for (String xpath : xpaths) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(xpath));
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            try {
                                element.click();
                                System.out.println("✅ Finish clicked by XPath: " + xpath);
                                return true;
                            } catch (Exception e) {
                                // Try JavaScript click as fallback
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                                System.out.println("✅ Finish clicked via JavaScript with XPath: " + xpath);
                                return true;
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception e) {
            System.out.println("❌ clickFinishByXpath failed: " + e.getMessage());
        }
        return false;
    }

    private boolean clickFinishByCoordinates() {
        try {
            Dimension size = driver.manage().window().getSize();
            System.out.println("📱 Screen size: " + size.width + "x" + size.height);

            // Try different positions (prioritize bottom area)
            int[][] positions = {
                    {(int) (size.width * 0.50), (int) (size.height * 0.92)},  // Very bottom center
                    {(int) (size.width * 0.50), (int) (size.height * 0.88)},  // Bottom center
                    {(int) (size.width * 0.50), (int) (size.height * 0.85)},  // Slightly higher
                    {(int) (size.width * 0.85), (int) (size.height * 0.92)},  // Bottom right
                    {(int) (size.width * 0.15), (int) (size.height * 0.92)},  // Bottom left
            };

            for (int i = 0; i < positions.length; i++) {
                int[] pos = positions[i];
                try {
                    System.out.println("📍 Attempt " + (i+1) + ": Tapping coordinates x=" + pos[0] + " y=" + pos[1]);

                    new TouchAction<>(driver)
                            .tap(PointOption.point(pos[0], pos[1]))
                            .perform();

                    sleep(2000); // Wait to see if screen changes

                    // Check if we moved away from manager step
                    if (!isOnManagerStep()) {
                        System.out.println("✅ Successfully clicked finish button via coordinates");
                        return true;
                    }

                    // Check if we see login screen elements
                    if (isLoginScreenVisible()) {
                        System.out.println("✅ Successfully moved to login screen");
                        return true;
                    }

                } catch (Exception e) {
                    System.out.println("❌ Coordinate tap attempt " + (i+1) + " failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Coordinate click failed: " + e.getMessage());
        }
        return false;
    }

    private boolean isOnManagerStep() {
        try {
            return driver.findElements(titleManagerStep).size() > 0 ||
                    driver.findElements(editTexts).size() >= 1;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLoginScreenVisible() {
        try {
            By loginLocators = AppiumBy.xpath(
                    "//*[contains(@text, 'Login') or contains(@text, 'Sign In') or " +
                            "contains(@text, 'Email') or contains(@text, 'Password')]"
            );
            return driver.findElements(loginLocators).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================================================
    //  Alternative simple method for finish button
    // =========================================================

    public void clickFinishButtonSimple() {
        try {
            // Try the most common ways
            String finishText = "Finish & Go To Login";

            // Method 1: Direct XPath
            try {
                By locator = AppiumBy.xpath("//*[@text='" + finishText + "']");
                WebElement element = driver.findElement(locator);
                element.click();
                System.out.println("✅ Finish button clicked directly");
                return;
            } catch (Exception e1) {
                // Method 2: Case-insensitive search
                List<WebElement> allElements = driver.findElements(AppiumBy.xpath("//*[@text or @content-desc]"));
                for (WebElement element : allElements) {
                    try {
                        String text = element.getAttribute("text");
                        String contentDesc = element.getAttribute("content-desc");

                        if ((text != null && text.toLowerCase().contains("finish")) ||
                                (contentDesc != null && contentDesc.toLowerCase().contains("finish"))) {
                            if (element.isDisplayed() && element.isEnabled()) {
                                element.click();
                                System.out.println("✅ Finish button clicked via text search");
                                return;
                            }
                        }
                    } catch (Exception ignored) {}
                }

                // Method 3: Screenshot for debugging
                System.out.println("📸 Taking screenshot for debugging...");
                try {
                    byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
                    // You could save this to a file here if needed
                } catch (Exception e) {
                    System.out.println("Could not take screenshot: " + e.getMessage());
                }

                throw new NoSuchElementException("Finish button not found with text: " + finishText);
            }
        } catch (Exception e) {
            System.out.println("❌ clickFinishButtonSimple failed: " + e.getMessage());
            throw e;
        }
    }

    public void goBackToShop() {
        tap(btnBack);
        ensureShopStep();
    }

    // =========================================================
    //  Next button logic
    // =========================================================

    public void tapNext() {
        sleep(500);

        System.out.println("🔍 Looking for Next button...");

        if (clickNextByText()) return;
        if (clickNextByContentDesc()) return;
        if (clickNextByFlutterSemantics()) return;
        if (clickNextByButtonType()) return;
        if (clickNextByStepIndicator()) return;

        clickNextByCoordinates();
    }

    private boolean clickNextByText() {
        try {
            String[] nextTexts = {"Next", "NEXT", "Next >", "Continue", "CONTINUE", "Proceed"};

            for (String text : nextTexts) {
                By locator = AppiumBy.xpath("//*[@text='" + text + "' or @label='" + text + "']");
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        element.click();
                        System.out.println("✅ Next clicked by text: " + text);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ clickNextByText failed: " + e.getMessage());
        }
        return false;
    }

    private boolean clickNextByContentDesc() {
        try {
            String[] descs = {"Next", "next", "btnNext", "next_button", "continue_button"};

            for (String desc : descs) {
                By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + desc + "')]");
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        element.click();
                        System.out.println("✅ Next clicked by content-desc: " + desc);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ clickNextByContentDesc failed: " + e.getMessage());
        }
        return false;
    }

    private boolean clickNextByFlutterSemantics() {
        try {
            By locator = AppiumBy.xpath("//*[@semantics-label='Next' or @aria-label='Next']");
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("✅ Next clicked by Flutter semantics");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean clickNextByButtonType() {
        try {
            By locator = AppiumBy.xpath(
                    "//android.widget.Button[contains(@text,'Next') or contains(@text,'Continue') or " +
                            "contains(@content-desc,'Next') or contains(@content-desc,'Continue')]"
            );
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("✅ Next clicked by button type");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean clickNextByStepIndicator() {
        try {
            By locator = AppiumBy.xpath(
                    "//*[contains(@text,'Manager Registration') or " +
                            "contains(@text,'Step 2') or " +
                            "contains(@text,'Create Manager')]"
            );
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("✅ Clicked on step indicator as fallback");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void clickNextByCoordinates() {
        try {
            Dimension size = driver.manage().window().getSize();

            int[][] positions = {
                    {(int) (size.width * 0.85), (int) (size.height * 0.90)},
                    {(int) (size.width * 0.85), (int) (size.height * 0.85)},
                    {(int) (size.width * 0.50), (int) (size.height * 0.90)},
                    {(int) (size.width * 0.85), (int) (size.height * 0.70)},
            };

            for (int[] pos : positions) {
                try {
                    new TouchAction<>(driver)
                            .tap(PointOption.point(pos[0], pos[1]))
                            .perform();

                    System.out.println("⚠️ Next tapped by coordinates x=" + pos[0] + " y=" + pos[1]);

                    sleep(1000);
                    if (isManagerStep()) {
                        System.out.println("✅ Successfully moved to manager step");
                        return;
                    }
                } catch (Exception ignored) {}
            }

            System.out.println("❌ All coordinate attempts failed");

        } catch (Exception e) {
            System.out.println("❌ Coordinate click failed: " + e.getMessage());
        }
    }

    // =========================================================
    //  Core fallback methods
    // =========================================================

    private void typeIntoField(String labelText, String value, int fallbackIndex) {
        if (value == null || value.trim().isEmpty()) return;

        WebElement field = getFieldByLabelOrIndex(labelText, fallbackIndex);
        clearAndType(field, value);
        hideKeyboardSafe();
    }

    private WebElement getFieldByLabelOrIndex(String labelText, int fallbackIndex) {
        String primaryXpath =
                "//*[contains(@content-desc,'" + escapeXpath(labelText) + "') or contains(@text,'" + escapeXpath(labelText) + "')]";

        try {
            scrollToTextContains(labelText);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath)));

        } catch (TimeoutException e) {
            System.out.println("⚠️ Primary locator failed for: " + labelText +
                    " | Trying fallback EditText index=" + fallbackIndex);

            WebElement byIndex = getEditTextByIndexWithScroll(fallbackIndex);
            if (byIndex != null) return byIndex;

            dumpSource(labelText);
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

    private WebElement getEditTextByIndexWithSmallScroll(int index) {
        for (int attempt = 0; attempt < 4; attempt++) {
            List<WebElement> inputs = driver.findElements(editTexts);
            if (inputs.size() > index) return inputs.get(index);

            scrollDownSmall();
            sleep(400);
        }
        return null;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    // =========================================================
    //  Internal Helpers
    // =========================================================

    private void ensureShopStep() {
        wait.until(d -> d.findElements(editTexts).size() >= 5);
    }

    private void ensureManagerStep() {
        By managerTitle = AppiumBy.xpath(
                "//*[contains(@content-desc,'Create Manager Account') " +
                        "or contains(@text,'Create Manager Account') " +
                        "or contains(@content-desc,'Manager Registration') " +
                        "or contains(@text,'Manager Registration')]"
        );

        wait.until(d ->
                d.findElements(managerTitle).size() > 0
                        || d.findElements(editTexts).size() >= 1
        );

        wait.until(d -> d.findElements(editTexts).size() >= 1);

        System.out.println("✅ Manager step confirmed.");
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
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
    }

    private void scrollToTextContains(String text) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"
            ));
        } catch (Exception ignored) {}
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

    private void dumpSource(String fieldName) {
        String src = driver.getPageSource();
        System.out.println("❌ No element found for: " + fieldName);
        System.out.println("Page source (first 3000 chars):");
        System.out.println(src.substring(0, Math.min(3000, src.length())));
    }

    // =========================================================
    //  Scroll the Flutter SingleChildScrollView (shop form) to bottom
    // =========================================================

    public void scrollShopFormToBottom() {
        WebElement scrollable = driver.findElement(
                AppiumBy.androidUIAutomator("new UiSelector().scrollable(true)")
        );

        for (int i = 0; i < 6; i++) {
            ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", Map.of(
                    "elementId", ((RemoteWebElement) scrollable).getId(),
                    "direction", "down",
                    "percent", 0.85
            ));
        }
    }
}