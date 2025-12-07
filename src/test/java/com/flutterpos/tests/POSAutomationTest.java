package com.flutterpos.tests;//package com.flutterpos.tests;
//
//import com.flutterpos.pages.LoginPage;
//import com.flutterpos.utils.AppiumDriverManager;
//import io.appium.java_client.AppiumDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import static org.testng.Assert.assertTrue;
//
//public class POSAutomationTests {
//
//    private AppiumDriver driver;
//    private LoginPage loginPage;
//
//    @BeforeClass
//    public void setUp() {
//        System.out.println("Setting up test...");
//        driver = AppiumDriverManager.getDriver();
//        loginPage = new LoginPage(driver);
//        System.out.println("Test setup completed!");
//    }
//
//    @Test(priority = 1)
//    public void testLogin() {
//        System.out.println("Starting login test...");
//        loginPage.login("achintha@gmail.com", "12345678");
//        System.out.println("Login test completed!");
//        // Add a small delay to see the action
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test(priority = 2)
//    public void testAppOpen() {
//        System.out.println("Testing if app opened successfully...");
//
//        // Get page source to verify app opened
//        String pageSource = driver.getPageSource();
//        System.out.println("Page source length: " + pageSource.length());
//
//        // Check if app contains expected elements
//        assertTrue(pageSource.length() > 0, "Page source should not be empty");
//
//        // For Android, get package info if available
//        if (driver instanceof io.appium.java_client.android.AndroidDriver) {
//            io.appium.java_client.android.AndroidDriver androidDriver =
//                    (io.appium.java_client.android.AndroidDriver) driver;
//            String currentPackage = androidDriver.getCurrentPackage();
//            System.out.println("Current package: " + currentPackage);
//        }
//
//        System.out.println("App opened successfully!");
//    }
//    @AfterClass
//    public void tearDown() {
//        System.out.println("Tearing down test...");
//        AppiumDriverManager.quitDriver();
//        System.out.println("Test completed!");
//    }
//}

import com.flutterpos.pages.LoginPage;
import com.flutterpos.pages.ManagerDashboard;
import com.flutterpos.utils.AppiumDriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class POSAutomationTest {

    private AndroidDriver driver;
    private LoginPage login;
    private ManagerDashboard dashboard;

    @BeforeClass
    public void setUp() {
        driver = AppiumDriverManager.getDriver();
        System.out.println("🔍 Package at test start: " + driver.getCurrentPackage());
        login = new LoginPage(driver);
        dashboard = new ManagerDashboard(driver);
    }

    @Test
    public void testManagerDashboardFlow() {
        // Make sure app is in login state
        login.ensureOnLoginScreen();

        // login first
        login.loginAsManager("achintha@gmail.com", "12345678");

        // verify dashboard
        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");

        // click tiles
        dashboard.openAddUser();
        dashboard.openUserManagement();
        dashboard.openCreditors();
        dashboard.openSalesReports();
        dashboard.openInsights();
        dashboard.openProfitMargins();
        dashboard.openAuditLogs();
        dashboard.openPromotions();
    }
}


