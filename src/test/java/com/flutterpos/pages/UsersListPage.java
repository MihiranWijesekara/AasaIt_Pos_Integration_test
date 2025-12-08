package com.flutterpos.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UsersListPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // AppTopBar(title: 'Users', ...)
    private final By titleUsers =
            AppiumBy.xpath("//*[contains(@text,'Users') or contains(@content-desc,'Users')]");

    // FAB: label 'Add User'
    private final By btnAddUser =
            AppiumBy.xpath("//*[contains(@text,'Add User') or contains(@content-desc,'Add User')]");

    // Search TextField (there is only one EditText in this page → the search bar)
    private final By searchFieldByHint =
            AppiumBy.xpath("//*[contains(@text,'Search by name, contact, email, role') or " +
                    "contains(@content-desc,'Search by name, contact, email, role')]");

    public UsersListPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(titleUsers));
            System.out.println("✅ UsersListPage visible (title 'Users').");
            return true;
        } catch (Exception e) {
            System.out.println("⚠️ UsersListPage NOT visible. Page source (first 2000 chars):");
            String src = driver.getPageSource();
            System.out.println(src.substring(0, Math.min(src.length(), 2000)));
            return false;
        }
    }

    public void tapAddUserButton() {
        System.out.println("[ACTION] Tapping 'Add User' button on UsersListPage");
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(btnAddUser)
        );
        btn.click();
        System.out.println("✅ 'Add User' button tapped.");
    }

    /** Type into the search bar (Find Users) */
    public void searchUser(String query) {
        System.out.println("[ACTION] Searching user with query: " + query);

        WebElement searchField = null;

        // Strategy 1: by hint / content-desc
        try {
            searchField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(searchFieldByHint)
            );
            System.out.println("✅ Search field found via hint/content-desc.");
        } catch (Exception e) {
            System.out.println("ℹ️ Search field via hint not found, falling back to first EditText: " + e.getMessage());
        }

        // Strategy 2: first android.widget.EditText on Users page
        if (searchField == null) {
            List<WebElement> edits = driver.findElements(AppiumBy.className("android.widget.EditText"));
            if (!edits.isEmpty()) {
                searchField = edits.get(0);
                System.out.println("✅ Using first android.widget.EditText as search field. Count=" + edits.size());
            } else {
                System.out.println("❌ No EditText found on UsersListPage for search.");
                String src = driver.getPageSource();
                System.out.println(src.substring(0, Math.min(src.length(), 2000)));
                throw new RuntimeException("Search field not found on UsersListPage.");
            }
        }

        searchField.click();
        searchField.clear();
        searchField.sendKeys(query);
        System.out.println("✅ Search text entered: " + query);

        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
        }

        // Give Provider time to reload list
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    /** Check if a user with given name is visible in the list */
    public boolean isUserVisibleByName(String name) {
        By userNameLocator = AppiumBy.xpath(
                "//*[contains(@text,'" + name + "') or contains(@content-desc,'" + name + "')]"
        );
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userNameLocator));
            System.out.println("✅ User with name containing '" + name + "' is visible in the list.");
            return true;
        } catch (Exception e) {
            System.out.println("⚠️ User with name containing '" + name + "' NOT found after search.");
            String src = driver.getPageSource();
            System.out.println(src.substring(0, Math.min(src.length(), 2000)));
            return false;
        }
    }
}
