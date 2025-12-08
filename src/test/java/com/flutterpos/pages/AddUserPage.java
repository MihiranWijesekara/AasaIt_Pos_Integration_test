package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddUserPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Title at top bar: "Add User"
    private final By titleAddUser =
            AppiumBy.xpath("//*[contains(@text,'Add User') || contains(@content-desc,'Add User')]");

    // Button: "Create User" (Flutter AppButton)
    // 1) Preferred: accessibility id from Semantics(label: 'create_user_button')
    private final By btnCreateUserAcc =
            AppiumBy.accessibilityId("create_user_button");

    // 2) Text-based locator (fallback)
    private final By btnCreateUserTextUiSelector =
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Create User\")");

    // 3) Original XPath fallback
    private final By btnCreateUser =
            AppiumBy.xpath("//*[contains(@text,'Create User') or contains(@content-desc,'Create User')]");

    // SnackBar / success text: "User created successfully!"
    private final By successSnackBar =
            AppiumBy.xpath("//*[contains(@text,'User created successfully') or " +
                    "contains(@content-desc,'User created successfully')]");

    public AddUserPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /** Helper: get all EditText fields on current view */
    private List<WebElement> getAllInputFields() {
        List<WebElement> fields =
                driver.findElements(AppiumBy.className("android.widget.EditText"));
        System.out.println("🔎 Found " + fields.size() + " EditText fields on Add User page.");
        return fields;
    }

    /** Single swipe up (scroll down) gesture */
    private void scrollDownOnce() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);  // near bottom
        int endY   = (int) (size.height * 0.3);  // near middle/top

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX, startY
        ));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(600),
                PointerInput.Origin.viewport(),
                startX, endY
        ));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /** Hide soft keyboard if it is visible */
    private void hideKeyboardIfVisible() {
        try {
            driver.hideKeyboard();
            System.out.println("⌨️ Soft keyboard hidden.");
        } catch (Exception e) {
            System.out.println("ℹ️ hideKeyboard() failed or keyboard not visible: " + e.getMessage());
        }
    }

    /**
     * Find all EditText fields that are password-type inputs, using the native
     * "password" attribute exposed by Android.
     */
    private List<WebElement> getPasswordLikeFields() {
        List<WebElement> all = getAllInputFields();
        List<WebElement> result = new ArrayList<>();

        for (WebElement el : all) {
            try {
                String pwdAttr = el.getAttribute("password"); // "true" / "false"
                System.out.println("   ↳ EditText at Y=" + el.getRect().getY()
                        + " password=" + pwdAttr);

                if ("true".equalsIgnoreCase(pwdAttr)) {
                    result.add(el);
                }
            } catch (Exception ignored) {
                // some elements may not expose "password", just skip
            }
        }

        System.out.println("🔐 Password-like EditTexts found: " + result.size());
        return result;
    }

    /** Fill the Add User form */
    public void fillUserForm(String fullName,
                             String email,
                             String contact,
                             String nic,
                             String password) {

        // -------- 1) TOP FIELDS (always visible) --------
        wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        AppiumBy.className("android.widget.EditText"), 3
                )
        );
        List<WebElement> initialFields = getAllInputFields();

        if (initialFields.size() < 4) {
            throw new RuntimeException(
                    "Expected at least 4 EditText fields (FullName/Email/Contact/NIC), but found: "
                            + initialFields.size()
            );
        }

        // 0: Full Name
        WebElement fullNameField = initialFields.get(0);
        fullNameField.click();
        fullNameField.clear();
        fullNameField.sendKeys(fullName);

        // 1: Email
        WebElement emailField = initialFields.get(1);
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);

        // 2: Contact Number
        WebElement contactField = initialFields.get(2);
        contactField.click();
        contactField.clear();
        contactField.sendKeys(contact);

        // 3: NIC
        WebElement nicField = initialFields.get(3);
        nicField.click();
        nicField.clear();
        nicField.sendKeys(nic);

        // -------- 2) SCROLL TO ENSURE PASSWORD FIELDS ARE IN VIEW --------
        System.out.println("🔽 Scrolling to reveal password fields...");
        for (int i = 0; i < 4; i++) {
            scrollDownOnce();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        // -------- 3) FIND PASSWORD + CONFIRM PASSWORD FIELDS (SAFE LOGIC) --------
        List<WebElement> allFields = getAllInputFields();
        List<WebElement> passwordFields = getPasswordLikeFields();

        WebElement pwdField;
        WebElement confirmField;

        if (passwordFields.size() >= 2) {
            // Best case: first = password, second = confirm
            pwdField = passwordFields.get(0);
            confirmField = passwordFields.get(1);
            System.out.println("✅ Using 2 password fields (pwd + confirm) from password=true list.");
        } else if (passwordFields.size() == 1) {
            // Only one password=true → use it + nearest neighbour / last field as confirm
            WebElement pwdCandidate = passwordFields.get(0);
            int idx = allFields.indexOf(pwdCandidate);

            if (idx != -1 && idx < allFields.size() - 1) {
                // Use this as password, the next EditText as confirm password
                pwdField = pwdCandidate;
                confirmField = allFields.get(idx + 1);
                System.out.println("⚠️ Only 1 password=true field found. Using it + next EditText as confirm.");
            } else if (allFields.size() >= 2) {
                // Fallback to last two fields
                pwdField = allFields.get(allFields.size() - 2);
                confirmField = allFields.get(allFields.size() - 1);
                System.out.println("⚠️ Only 1 password=true field found. Falling back to last two EditTexts.");
            } else {
                throw new RuntimeException("Not enough EditText fields to resolve password + confirm password.");
            }
        } else {
            // No password=true attribute detected → fallback to last two EditTexts
            if (allFields.size() < 2) {
                throw new RuntimeException(
                        "Could not detect password fields: no password=true and less than 2 EditTexts overall."
                );
            }
            pwdField = allFields.get(allFields.size() - 2);
            confirmField = allFields.get(allFields.size() - 1);
            System.out.println("⚠️ No password=true fields. Falling back to last two EditTexts as pwd + confirm.");
        }

        // -------- 4) TYPE PASSWORD + CONFIRM PASSWORD --------
        pwdField.click();
        pwdField.clear();
        pwdField.sendKeys(password);

        confirmField.click();
        confirmField.clear();
        confirmField.sendKeys(password);

        // Hide keyboard so it doesn't cover the button
        System.out.println("✅ Filled Add User form (name, email, contact, NIC, password, confirm).");
    }

    /**
     * Tap the Create User button if present.
     * If NOT present (your case), submit by pressing BACK (app auto-saves and returns to Users list).
     */
    public void tapCreateUser() {
        System.out.println("🔘 Trying to tap 'Create User' button...");

        // Scroll a few times to bring bottom row (Cancel / Create) into view
        for (int i = 0; i < 3; i++) {
            scrollDownOnce();
            try {
                Thread.sleep(400);
            } catch (InterruptedException ignored) {}
        }

        WebElement btn = null;

        // Strategy 1: accessibility id (if Semantics label is added in Flutter)
        try {
            btn = wait.until(ExpectedConditions.elementToBeClickable(btnCreateUserAcc));
            System.out.println("✅ 'Create User' button found via accessibilityId('create_user_button').");
        } catch (Exception e) {
            System.out.println("ℹ️ accessibilityId('create_user_button') not found: " + e.getMessage());
        }

        // Strategy 2: Android UIAutomator textContains("Create User")
        if (btn == null) {
            try {
                btn = wait.until(ExpectedConditions.elementToBeClickable(btnCreateUserTextUiSelector));
                System.out.println("✅ 'Create User' button found via UiSelector textContains('Create User').");
            } catch (Exception e) {
                System.out.println("⚠️ UiSelector text('Create User') failed: " + e.getMessage());
            }
        }

        // Strategy 3: original XPath text "Create User"
        if (btn == null) {
            try {
                btn = wait.until(ExpectedConditions.elementToBeClickable(btnCreateUser));
                System.out.println("✅ 'Create User' button found via original XPath (Create User).");
            } catch (Exception e) {
                System.out.println("⚠️ XPath locator for 'Create User' failed: " + e.getMessage());
            }
        }

        // If still null → use BACK to submit (your real app behaviour)
        if (btn == null) {
            System.out.println("⚠️ Create User button not found. Submitting form by pressing BACK (app auto-saves).");
            driver.navigate().back();
            return;
        }

        // Try to click the resolved button
        try {
            btn.click();
            System.out.println("✅ Create User button tapped.");
        } catch (Exception e) {
            System.out.println("⚠️ Direct click failed, trying again after a short wait: " + e.getMessage());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
            btn.click();
            System.out.println("✅ Create User button tapped on retry.");
        }
    }

    /** Wait for success message from SnackBar (optional – may or may not appear after BACK) */
    public boolean waitForSuccessMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successSnackBar));
            System.out.println("🎉 Success snackbar detected: User created successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("⚠️ Success snackbar NOT detected.");
            return false;
        }
    }

    /** High-level flow: fill form + submit + wait for success (best effort) */
    public void createUser(String fullName,
                           String email,
                           String contact,
                           String nic,
                           String password) {

        System.out.println("🧪 Starting createUser() flow on Add User page...");

        fillUserForm(fullName, email, contact, nic, password);
        tapCreateUser();  // either taps button OR presses BACK

        // Optional: keep this if snackbar appears; if not, you can remove this block.
        boolean success = waitForSuccessMessage();
        if (!success) {
            System.out.println("ℹ️ No success snackbar seen – but app may still have saved user on BACK.");
        }
    }
}
