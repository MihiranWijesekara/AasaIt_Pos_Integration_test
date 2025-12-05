//package com.flutterpos.pages;
//
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.pagefactory.AppiumFieldDecorator;
//import io.appium.java_client.pagefactory.Widget;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//import java.util.List;
//
//public class LoginPage {
//
//    private AppiumDriver driver;
//    private WebDriverWait wait;
//
//    // Flutter-specific locators using SemanticsLabel or Tooltip
//    @AndroidFindBy(xpath = "//*[contains(@text, 'Email ID') or contains(@content-desc, 'Email ID')]")
//    private WebElement emailField;
//
//    @AndroidFindBy(xpath = "//*[contains(@text, 'Password') or contains(@content-desc, 'Password')]")
//    private WebElement passwordField;
//
//    @AndroidFindBy(xpath = "//*[contains(@text, 'Login') or contains(@content-desc, 'Login')]")
//    private WebElement loginButton;
//
//    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'Login') or contains(@text, 'Login')]")
//    private List<WebElement> loginElements;
//
//    @AndroidFindBy(className = "android.widget.EditText")
//    private List<WebElement> textInputs;
//
//    @AndroidFindBy(className = "android.widget.Button")
//    private List<WebElement> buttons;
//
//    @AndroidFindBy(className = "android.view.View")
//    private List<WebElement> views;
//
//    public LoginPage(AppiumDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
//    }
//
//    public void enterUsername(String username) {
//        activateApp();
//
//        try {
//            wait.until(ExpectedConditions.visibilityOf(emailField));
//            emailField.clear();
//            emailField.sendKeys(username);
//            System.out.println("✅ Username entered successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Specific locator failed, trying alternative...");
//            enterUsernameAlternative(username);
//        }
//    }
//
//    public void enterPassword(String password) {
//        try {
//            wait.until(ExpectedConditions.visibilityOf(passwordField));
//            passwordField.clear();
//            passwordField.sendKeys(password);
//            System.out.println("✅ Password entered successfully");
//        } catch (Exception e) {
//            System.out.println("❌ Specific locator failed, trying alternative...");
//            enterPasswordAlternative(password);
//        }
//    }
//
//    public void clickLogin() {
//        try {
//            // Try the standard button first
//            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
//            loginButton.click();
//            System.out.println("✅ Login button clicked successfully (standard button)");
//            return;
//        } catch (Exception e) {
//            System.out.println("❌ Standard button not found, trying Flutter-specific locators...");
//        }
//
//        try {
//            // Try Flutter ElevatedButton (usually a android.view.View with text)
//            clickLoginFlutterAlternative();
//        } catch (Exception e) {
//            System.out.println("❌ Flutter-specific locators failed, trying last resort...");
//            clickLoginLastResort();
//        }
//    }
//
//    // ALTERNATIVE METHODS
//    private void enterUsernameAlternative(String username) {
//        if (!textInputs.isEmpty()) {
//            textInputs.get(0).clear();
//            textInputs.get(0).sendKeys(username);
//            System.out.println("✅ Username entered (alternative method)");
//        } else {
//            throw new RuntimeException("No text input fields found");
//        }
//    }
//
//    private void enterPasswordAlternative(String password) {
//        if (textInputs.size() >= 2) {
//            textInputs.get(1).clear();
//            textInputs.get(1).sendKeys(password);
//            System.out.println("✅ Password entered (alternative method)");
//        } else {
//            throw new RuntimeException("Not enough text input fields found for password");
//        }
//    }
//
//    private void clickLoginFlutterAlternative() {
//        // Strategy 1: Look for views with "Login" text
//        for (WebElement view : views) {
//            try {
//                String text = view.getText();
//                String contentDesc = view.getAttribute("content-desc");
//
//                if ((text != null && text.equalsIgnoreCase("Login")) ||
//                        (contentDesc != null && contentDesc.contains("Login"))) {
//                    view.click();
//                    System.out.println("✅ Login button clicked (Flutter view with text)");
//                    return;
//                }
//            } catch (Exception e) {
//                // Continue searching
//            }
//        }
//
//        // Strategy 2: Look in login elements list
//        for (WebElement element : loginElements) {
//            try {
//                if (element.isDisplayed() && element.isEnabled()) {
//                    element.click();
//                    System.out.println("✅ Login button clicked (login elements list)");
//                    return;
//                }
//            } catch (Exception e) {
//                // Continue searching
//            }
//        }
//
//        throw new RuntimeException("Login button not found with Flutter locators");
//    }
//
//    private void clickLoginLastResort() {
//        // Last resort: Try all buttons and clickable elements
//        System.out.println("🔄 Attempting last resort login button search...");
//
//        // Try buttons first
//        for (WebElement button : buttons) {
//            try {
//                if (button.isDisplayed() && button.isEnabled()) {
//                    String buttonText = button.getText();
//                    if (buttonText != null &&
//                            (buttonText.toLowerCase().contains("login") ||
//                                    buttonText.toLowerCase().contains("sign in"))) {
//                        button.click();
//                        System.out.println("✅ Login button clicked (last resort - button)");
//                        return;
//                    }
//                }
//            } catch (Exception e) {
//                // Continue
//            }
//        }
//
//        // Try all views for clickable elements
//        for (WebElement view : views) {
//            try {
//                if (view.isDisplayed() && view.isEnabled()) {
//                    String text = view.getText();
//                    if (text != null &&
//                            (text.equalsIgnoreCase("Login") ||
//                                    text.toLowerCase().contains("login"))) {
//                        view.click();
//                        System.out.println("✅ Login button clicked (last resort - view)");
//                        return;
//                    }
//                }
//            } catch (Exception e) {
//                // Continue
//            }
//        }
//
//        // Final attempt: click the 3rd button (often the login button in forms)
//        if (buttons.size() >= 3) {
//            buttons.get(2).click();
//            System.out.println("✅ Login button clicked (position-based - 3rd button)");
//            return;
//        }
//
//        throw new RuntimeException("Login button not found with any strategy");
//    }
//
//    private void activateApp() {
//        try {
//            ((AndroidDriver) driver).activateApp("com.example.pos");
//            Thread.sleep(4000); // Wait longer for Flutter app to initialize
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not activate app: " + e.getMessage());
//        }
//    }
//
//    public void login(String username, String password) {
//        System.out.println("🔐 Logging in with username: " + username);
//
//        // Activate app first
//        activateApp();
//
//        // Wait for the login form to be visible
//        try {
//            wait.until(ExpectedConditions.visibilityOf(emailField));
//        } catch (Exception e) {
//            System.out.println("⚠️ Email field not immediately visible, continuing anyway...");
//        }
//
//        enterUsername(username);
//        enterPassword(password);
//        clickLogin();
//
//        System.out.println("✅ Login process completed");
//    }
//
//    // Helper method to debug the current screen
//    public void debugScreen() {
//        try {
//            String pageSource = driver.getPageSource();
//            System.out.println("📄 Current page source (first 2000 chars):");
//            System.out.println(pageSource.substring(0, Math.min(pageSource.length(), 2000)));
//
//            System.out.println("🔍 Found " + textInputs.size() + " text inputs");
//            System.out.println("🔍 Found " + buttons.size() + " buttons");
//            System.out.println("🔍 Found " + views.size() + " views");
//            System.out.println("🔍 Found " + loginElements.size() + " login elements");
//        } catch (Exception e) {
//            System.out.println("❌ Debug failed: " + e.getMessage());
//        }
//    }
//}


package com.flutterpos.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginPage {

    private AppiumDriver driver;

    // Use Flutter Keys for reliable identification
    @AndroidFindBy(accessibility = "emailField")
    private WebElement emailField;

    @AndroidFindBy(accessibility = "passwordField")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "loginButton")
    private WebElement loginButton;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void tapLogin() {
        loginButton.click();
    }

    public void loginAsManager(String email, String pwd) {
        enterEmail(email);
        enterPassword(pwd);
        tapLogin();
    }
}
