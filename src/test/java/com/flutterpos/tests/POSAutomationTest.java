package com.flutterpos.tests;

import com.flutterpos.pages.AddUserPage;
import com.flutterpos.pages.LoginPage;
import com.flutterpos.pages.ManagerDashboard;
import com.flutterpos.pages.SalesReport;
import com.flutterpos.pages.UsersListPage;
import com.flutterpos.utils.AppiumDriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class POSAutomationTest {

    private AndroidDriver driver;
    private LoginPage login;
    private ManagerDashboard dashboard;
    private AddUserPage addUserPage;

    @BeforeClass
    public void setUp() {
        driver = AppiumDriverManager.getDriver();
        System.out.println("🔍 Package at test start: " + driver.getCurrentPackage());
        login = new LoginPage(driver);
        dashboard = new ManagerDashboard(driver);
    }

    @Test
    public void testManagerDashboardFlow() {
        // 1) Make sure app is in login state
        login.ensureOnLoginScreen();

        // 2) Login as Manager
        login.loginAsManager("achintha@gmail.com", "12345678");

        // 3) Verify dashboard
//        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");
//
//        // 4) Click tiles (navigate but come back to dashboard)
//        dashboard.openAddUser();
//        dashboard.openUserManagement();
//        dashboard.openCreditors();
//        dashboard.openSalesReports();
//        dashboard.openInsights();
//        dashboard.openProfitMargins();
//        dashboard.openAuditLogs();
//        dashboard.openPromotions();

        // 5) Now navigate AGAIN to Add User, but STAY there
        dashboard.openAddUserAndStay();

        UsersListPage usersListPage = new UsersListPage(driver);
        Assert.assertTrue(usersListPage.isVisible(), "Users list page is not visible after Add User tile");

        // 6) Click the 'Add User' button/FAB on that page
        usersListPage.tapAddUserButton();

        // 7) Use AddUserPage to create a new user
        addUserPage = new AddUserPage(driver);

        // Create a unique email each run to avoid "email already exists"
        String uniqueEmail = "autotest+" + System.currentTimeMillis() + "@example.com";

        addUserPage.createUser(
                "Auto Test Cashier",
                uniqueEmail,
                "0771234569",
                "123456779V",
                "Password1"
        );

        // 8) After createUser(), the real app keeps you on the Users list page
        UsersListPage usersListAfterCreate = new UsersListPage(driver);
        Assert.assertTrue(
                usersListAfterCreate.isVisible(),
                "Users list page is not visible after creating user"
        );

        // (Optional) Here you could assert that the new user/email is visible in the list

        // 9) Now go BACK to Manager Dashboard from Users list
        System.out.println("[ACTION] Going back from Users list to Manager Dashboard...");
        driver.navigate().back();

        Assert.assertTrue(
                dashboard.isDashboardVisible(),
                "Dashboard not visible after going back from Users list"
        );

        // 10) Go to Sales Report screen and stay there
        dashboard.openSalesReportAndStay();

        // 11) Use SalesReport page object to open each report tile
        SalesReport salesReport = new SalesReport(driver);

        salesReport.openItemDetailsReport();
        salesReport.openCustomerDetailsReport();
        salesReport.openUserDetailsReport();
        salesReport.openInvoiceListReport();
        salesReport.openRefundBillsReport();
        salesReport.openCardPayment();
        salesReport.openCashPayment();
        salesReport.openPaymentReport();
        salesReport.openDailySalesReport();
        salesReport.openProfitMarginsReport();
        salesReport.openCreditSalesReport();
        salesReport.openCashEntryReport();
        salesReport.openDiscountGranted();
        salesReport.openUnpaidPurchaseReport();
        salesReport.openSupplierListReport();
        salesReport.openStockReport();
        salesReport.openTransactionHistoryReport();
        salesReport.openReorderHistoryReport();
        salesReport.openLowStockWarningReport();


        // 12) Back from Sales Report → Manager Dashboard
        System.out.println("[ACTION] Going back from Sales Report to Manager Dashboard...");
        driver.navigate().back();
        Assert.assertTrue(
                dashboard.isDashboardVisible(),
                "Dashboard not visible after going back from Sales Report"
        );

        // 13) Again navigate to Users list page
        System.out.println("[ACTION] Opening Users list again from Dashboard...");
        dashboard.openAddUserAndStay();   // or dashboard.openUserManagementAndStay() if you have it

        UsersListPage usersListForSearch = new UsersListPage(driver);
        Assert.assertTrue(
                usersListForSearch.isVisible(),
                "Users list page is not visible for search"
        );

        // 14) Search bar: search 'Achintha'
        usersListForSearch.searchUser("Achintha");

        // 15) Assert that user 'Achintha' is visible in the filtered list
        Assert.assertTrue(
                usersListForSearch.isUserVisibleByName("Achintha"),
                "User 'Achintha' not visible after search"
        );

        System.out.println("[ACTION] Going back from Users list to Manager Dashboard...");
        driver.navigate().back();

        Assert.assertTrue(
                dashboard.isDashboardVisible(),
                "Dashboard not visible after going back from Users list"
        );

        dashboard.logout();

        login.ensureOnLoginScreen();
    }

}
