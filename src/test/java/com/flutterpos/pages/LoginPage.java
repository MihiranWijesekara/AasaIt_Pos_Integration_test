package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final String APP_PACKAGE = "com.example.pos";

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    // ---------- EMAIL FIELD LOCATOR WITH FALLBACK ----------
    private WebElement getEmailField() {
        String primaryXpath = "//*[contains(@content-desc,'Email ID') or contains(@text,'Email ID')]";

        try {
            // Try “nice” semantic locator first
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath))
            );
        } catch (TimeoutException e) {
            System.out.println("⚠️ Primary Email locator (text/content-desc='Email ID') failed. " +
                    "Trying generic EditText[0]...");

            // Fallback: use first EditText on screen
            List<WebElement> inputs =
                    driver.findElements(AppiumBy.className("android.widget.EditText"));

            if (!inputs.isEmpty()) {
                System.out.println("✅ Using first android.widget.EditText as email field. Count=" + inputs.size());
                return inputs.get(0);
            }

            // Last fallback: dump a bit of page source for debugging
            String src = driver.getPageSource();
            System.out.println("❌ No EditText found. Page source (first 3000 chars):");
            System.out.println(src.substring(0, Math.min(3000, src.length())));

            throw e; // rethrow so the test still fails visibly
        }
    }

    // ---------- PASSWORD FIELD LOCATOR WITH FALLBACK ----------
    private WebElement getPasswordField() {
        String primaryXpath = "//*[contains(@content-desc,'Password') or contains(@text,'Password')]";

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(primaryXpath))
            );
        } catch (TimeoutException e) {
            System.out.println("⚠️ Primary Password locator failed. Trying generic EditText[1]...");

            List<WebElement> inputs =
                    driver.findElements(AppiumBy.className("android.widget.EditText"));

            if (inputs.size() >= 2) {
                System.out.println("✅ Using second android.widget.EditText as password field. Count=" + inputs.size());
                return inputs.get(1);
            }

            String src = driver.getPageSource();
            System.out.println("❌ Not enough EditText fields for password. Page source (first 3000 chars):");
            System.out.println(src.substring(0, Math.min(3000, src.length())));

            throw e;
        }
    }

    private WebElement getLoginButton() {
        String xpath = "//*[contains(@content-desc,'Login') or contains(@text,'Login')]";
        return wait.until(
                ExpectedConditions.elementToBeClickable(AppiumBy.xpath(xpath))
        );
    }

    // ---------- HIGH-LEVEL ACTIONS ----------

    public void enterEmail(String email) {
        WebElement field = getEmailField();
        field.click();
        try {
            field.clear(); // may or may not work in Flutter, but safe to call
        } catch (Exception ignored) {}
        field.sendKeys(email);
        System.out.println("✅ Email entered: " + email);
    }

    public void enterPassword(String password) {
        WebElement field = getPasswordField();
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {}
        field.sendKeys(password);
        System.out.println("✅ Password entered.");
    }

    public void tapLogin() {
        WebElement btn = getLoginButton();
        btn.click();
        System.out.println("✅ Login button tapped.");
    }

    public void loginAsManager(String email, String pwd) {
        System.out.println("🔐 Logging in as manager...");
        ensureOnLoginScreen();
        enterEmail(email);
        enterPassword(pwd);
        tapLogin();
        System.out.println("✅ Login flow triggered.");
    }

    // ---------- ENSURE LOGIN SCREEN ----------

    public void ensureOnLoginScreen() {
        try {
            String currentPackage = driver.getCurrentPackage();
            System.out.println("📦 Current package before ensureOnLoginScreen: " + currentPackage);

            if (!APP_PACKAGE.equals(currentPackage)) {
                System.out.println("⚠️ Not in POS app. Activating " + APP_PACKAGE);
                driver.activateApp(APP_PACKAGE);
            }

            String loginTitleXpath =
                    "//*[contains(@text,'Sign in to your account') or " +
                            "contains(@content-desc,'Sign in to your account') or " +
                            "contains(@text,'Login') or contains(@content-desc,'Login')]";

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(loginTitleXpath))
            );
            System.out.println("📱 Login screen detected.");

        } catch (Exception e) {
            System.out.println("⚠️ Could not confirm login screen. Page source (first 3000 chars):");
            String src = driver.getPageSource();
            System.out.println(src.substring(0, Math.min(3000, src.length())));
            // rethrow if you want the test to fail here instead of later
        }
    }
}
