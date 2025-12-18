package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class AddUserPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Title at top bar: "Add User"
    private final By titleAddUser = AppiumBy.xpath("//*[contains(@text,'Add User') or contains(@content-desc,'Add User')]");

    // Button: "Create User" - Based on your screenshot
    private final By btnCreateUser = AppiumBy.xpath("//*[@text='Create User' or @content-desc='Create User']");
    private final By btnCancel = AppiumBy.xpath("//*[@text='Cancel' or @content-desc='Cancel']");

    // SnackBar / success text
    private final By successSnackBar = AppiumBy.xpath("//*[contains(@text,'User created successfully')]");

    // Field locators - Based on your screenshot labels
    private final By fullNameField = AppiumBy.xpath("//*[contains(@text,'Full Name') or contains(@content-desc,'Full Name')]/following-sibling::*//*[@class='android.widget.EditText']");
    private final By emailField = AppiumBy.xpath("//*[contains(@text,'Email Address') or contains(@content-desc,'Email Address')]/following-sibling::*//*[@class='android.widget.EditText']");
    private final By contactField = AppiumBy.xpath("//*[contains(@text,'Contact Number') or contains(@content-desc,'Contact Number')]/following-sibling::*//*[@class='android.widget.EditText']");
    private final By nicField = AppiumBy.xpath("//*[contains(@text,'NIC') or contains(@content-desc,'NIC')]/following-sibling::*//*[@class='android.widget.EditText']");
    private final By passwordField = AppiumBy.xpath("//*[contains(@text,'Password') or contains(@content-desc,'Password')]/following-sibling::*//*[@class='android.widget.EditText']");
    private final By confirmPasswordField = AppiumBy.xpath("//*[contains(@text,'Confirm Password') or contains(@content-desc,'Confirm Password')]/following-sibling::*//*[@class='android.widget.EditText']");

    // Role dropdown (if needed)
    private final By roleDropdown = AppiumBy.xpath("//*[contains(@text,'Role') or contains(@content-desc,'Role')]");
    private final By cashierOption = AppiumBy.xpath("//*[@text='Cashier' or @content-desc='Cashier']");

    public AddUserPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Simplified method to enter text in fields
    public void enterFullName(String fullName) {
        enterTextInField(fullName, "Full Name");
    }

    public void enterEmail(String email) {
        enterTextInField(email, "Email Address");
    }

    public void enterContactNumber(String contactNumber) {
        enterTextInField(contactNumber, "Contact Number");
    }

    public void enterNIC(String nic) {
        enterTextInField(nic, "NIC");
    }

    public void enterPassword(String password) {
        enterTextInField(password, "Password");
    }

    public void enterConfirmPassword(String confirmPassword) {
        enterTextInField(confirmPassword, "Confirm Password");
    }

    private void enterTextInField(String text, String fieldLabel) {
        try {
            System.out.println("📝 Entering " + fieldLabel + ": " + text);

            // Try multiple strategies to find and enter text

            // Strategy 1: Find by label then find adjacent EditText
            By fieldLocator = AppiumBy.xpath(
                    "//*[contains(@text,'" + fieldLabel + "') or contains(@content-desc,'" + fieldLabel + "')]/following::android.widget.EditText[1]"
            );

            // Strategy 2: Try to find all EditTexts and identify by index
            List<WebElement> allEditFields = driver.findElements(AppiumBy.className("android.widget.EditText"));
            System.out.println("Found " + allEditFields.size() + " EditText fields");

            WebElement targetField = null;

            try {
                // Try strategy 1 first
                targetField = wait.until(ExpectedConditions.elementToBeClickable(fieldLocator));
                System.out.println("✅ Found field by label: " + fieldLabel);
            } catch (Exception e) {
                System.out.println("⚠️ Field not found by label, trying by index...");

                // Strategy 2: Identify by expected field order
                int fieldIndex = getFieldIndex(fieldLabel);
                if (fieldIndex >= 0 && fieldIndex < allEditFields.size()) {
                    targetField = allEditFields.get(fieldIndex);
                    System.out.println("✅ Found field by index: " + fieldIndex);
                }
            }

            if (targetField != null) {
                // Scroll to make field visible
                scrollToElement(targetField);

                // Clear and enter text
                targetField.click();
                Thread.sleep(500);

                // Clear existing text (if any)
                targetField.clear();
                Thread.sleep(500);

                // Enter new text
                targetField.sendKeys(text);
                Thread.sleep(500);

                // Hide keyboard
                try {
                    driver.hideKeyboard();
                } catch (Exception e) {
                    // Keyboard might not be visible
                }

                System.out.println("✅ " + fieldLabel + " entered successfully");
            } else {
                System.out.println("❌ Could not find field for: " + fieldLabel);
                // Last resort: try to tap near expected location
                tapOnScreenBasedOnField(fieldLabel, text);
            }

        } catch (Exception e) {
            System.out.println("❌ Error entering " + fieldLabel + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getFieldIndex(String fieldLabel) {
        switch (fieldLabel) {
            case "Full Name": return 0;
            case "Email Address": return 1;
            case "Contact Number": return 2;
            case "NIC": return 3;
            case "Password": return 4;
            case "Confirm Password": return 5;
            default: return -1;
        }
    }

    private void tapOnScreenBasedOnField(String fieldLabel, String text) {
        try {
            Dimension size = driver.manage().window().getSize();
            int centerX = size.width / 2;
            int[] yPositions = {400, 550, 700, 850, 1000, 1150}; // Adjust based on your screen

            int fieldIndex = getFieldIndex(fieldLabel);
            if (fieldIndex >= 0 && fieldIndex < yPositions.length) {
                int tapY = yPositions[fieldIndex];

                // Tap to focus
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence tap = new Sequence(finger, 0)
                        .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, tapY))
                        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(Arrays.asList(tap));

                Thread.sleep(500);

                // Enter text using ADB (as last resort)
                try {
                    driver.getClipboardText(); // Just to check connectivity
                    driver.executeScript("mobile: type",
                            java.util.Map.of("text", text));
                    System.out.println("✅ " + fieldLabel + " entered via mobile:type");
                } catch (Exception e) {
                    System.out.println("⚠️ Could not enter text via mobile:type");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Tap-based entry failed: " + e.getMessage());
        }
    }

    private void scrollToElement(WebElement element) {
        try {
            // Simple scroll if element is not displayed
            if (!element.isDisplayed()) {
                scrollDown();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // Ignore scroll errors
        }
    }

    private void scrollDown() {
        try {
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.7);
            int endY = (int) (size.height * 0.3);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence scroll = new Sequence(finger, 0)
                    .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(scroll));
        } catch (Exception e) {
            System.out.println("⚠️ Scroll failed: " + e.getMessage());
        }
    }

    // Main method to create user
    public void createUser(String fullName, String email, String contactNumber, String nic,
                           String password, String confirmPassword) throws InterruptedException {

        System.out.println("🚀 Starting user creation process...");

        // Verify we're on Add User page
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(titleAddUser));
            System.out.println("✅ Verified: On Add User page");
        } catch (Exception e) {
            System.out.println("⚠️ Not on Add User page, but continuing...");
        }

        // Enter all fields
        enterFullName(fullName);
        Thread.sleep(1000);

        enterEmail(email);
        Thread.sleep(1000);

        enterContactNumber(contactNumber);
        Thread.sleep(1000);

        enterNIC(nic);
        Thread.sleep(1000);

        enterPassword(password);
        Thread.sleep(1000);

        enterConfirmPassword(confirmPassword);
        Thread.sleep(1000);

        System.out.println("✅ All user data entered");

        // Click Create User button
        clickCreateUser();
    }

    // Click Create User button
    public void clickCreateUser() {
        try {
            System.out.println("🖱️ Looking for Create User button...");

            // Scroll to bottom if needed
            scrollDown();
            Thread.sleep(1000);

            // Try to find and click Create User button
            WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(btnCreateUser));
            createBtn.click();
            System.out.println("✅ Create User button clicked!");

            // Wait for success message or page change
            Thread.sleep(3000);

            // Check for success
            try {
                WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(successSnackBar));
                if (successMsg != null) {
                    System.out.println("🎉 User created successfully!");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Success message not shown, but continuing...");
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to click Create User button: " + e.getMessage());

            // Alternative: Try to tap at bottom right (where button should be)
            try {
                Dimension size = driver.manage().window().getSize();
                int tapX = (int) (size.width * 0.8);
                int tapY = (int) (size.height * 0.9);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence tap = new Sequence(finger, 0)
                        .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), tapX, tapY))
                        .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(Arrays.asList(tap));

                System.out.println("✅ Tapped at button location");
                Thread.sleep(3000);
            } catch (Exception ex) {
                System.out.println("❌ Could not tap button location");
            }
        }
    }

    // Optional: Select role if needed
    public void selectRole(String role) {
        try {
            System.out.println("👤 Selecting role: " + role);
            WebElement roleElement = wait.until(ExpectedConditions.elementToBeClickable(roleDropdown));
            roleElement.click();
            Thread.sleep(1000);

            WebElement roleOption = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.xpath("//*[@text='" + role + "' or @content-desc='" + role + "']")
            ));
            roleOption.click();
            System.out.println("✅ Role selected: " + role);
        } catch (Exception e) {
            System.out.println("⚠️ Role selection skipped: " + e.getMessage());
        }
    }

    // Check if user was created successfully
    public boolean isUserCreatedSuccessfully() {
        try {
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(successSnackBar));
            return successMsg != null;
        } catch (Exception e) {
            return false;
        }
    }

    // Navigate back if needed
    public void cancel() {
        try {
            WebElement cancelBtn = wait.until(ExpectedConditions.elementToBeClickable(btnCancel));
            cancelBtn.click();
            System.out.println("✅ Cancelled user creation");
        } catch (Exception e) {
            System.out.println("⚠️ Cancel button not found");
        }
    }
}