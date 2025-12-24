package com.flutterpos.tests;

import com.flutterpos.pages.*;
import com.flutterpos.pages.stockKeeper.AddCategory;
import com.flutterpos.pages.stockKeeper.AddItem;
import com.flutterpos.pages.stockKeeper.InventoryPage;
import com.flutterpos.pages.stockKeeper.stockKeeperDashboard;
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
    private stockKeeperDashboard stockKeeper;
    private AddCategory addCategoryPage;
    private AddItem addItemPage;
    private InventoryPage inventoryPage;
    private RegisterPage registerPage;
    private ManagerFooter managerFooter;
    private ManagerReportPage managerReportPage;


    @BeforeClass
    public void setUp() {
        driver = AppiumDriverManager.getDriver();
        System.out.println("🔍 Package at test start: " + driver.getCurrentPackage());
        login = new LoginPage(driver);
        dashboard = new ManagerDashboard(driver);
        stockKeeper = new stockKeeperDashboard(driver);
        addCategoryPage = new AddCategory(driver);
        addItemPage = new AddItem(driver);
        inventoryPage = new InventoryPage(driver);
        registerPage = new RegisterPage(driver);
        managerFooter = new ManagerFooter(driver);
        managerReportPage = new ManagerReportPage(driver);
    }

    //Test 01
//    @Test
//    public void testRegisterPageFlow() {
//        //1) Make sure app is in login state
//        login.ensureOnLoginScreen();
//        login.tapRegister();
//
//        registerPage.waitForInstallationPage();
//
//        // Step 1 (optional): you can keep blank values to skip
//        registerPage.nextStep(
//                "AasaIT POS",           // display name
//                "AasaIT POS",                    // legal name
//                "0771234567",          // phone
//                "test@pos.lk",         // email
//                "Anuradhapura",        // address
//                "LKR",                 // currency
//                "VAT",                 // tax regime
//                "1234"                     // taxId
//        );
//
//        // Step 2 (required)
//        registerPage.fillManagerDetails(
//                "Manager One",
//                "manager1@pos.lk",
//                "0779999999",
//                "123456",
//                "123456"
//        );
//    }

    @Test
    public void testManagerDashboardFlow() {

        login.ensureOnLoginScreen();

        // 2) Login as Manager
        login.loginAsManager("manager@pos.local", "Manager@12345");

        // 3) Verify dashboard
        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");

        managerFooter.navigateReports();
        managerFooter.navigateNotification();
        managerFooter.navigateSetting();
        managerFooter.navigateHome();

        dashboard.openRegisteredUser();
        dashboard.openUserSettings();
        dashboard.openAuditLogs();
//        dashboard.openPromotions();
//        dashboard.openCreditors();
//        dashboard.openProfitDetail();

        managerReportPage.clickReportFooterAndStay();
        managerReportPage.navigateDailySummary();
        managerReportPage.navigateReportHub();
        managerReportPage.navigateTopProducts();
        managerReportPage.navigateProfitSplit();




    }


//    @Test
//    public void testManagerDashboardFlow() {
////         1) Make sure app is in login state
//        login.ensureOnLoginScreen();
//
//        // 2) Login as Manager
//        login.loginAsManager("achintha@gmail.com", "12345678");
//
//        // 3) Verify dashboard
//        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");

//
////        // 4) Click tiles (navigate but come back to dashboard)
//        dashboard.openAddUser();
//        dashboard.openUserManagement();
//        dashboard.openCreditors();
//        dashboard.openSalesReports();
//        dashboard.openInsights();
//        dashboard.openProfitMargins();
//        dashboard.openAuditLogs();
//        dashboard.openPromotions();
//
//        // 5) Now navigate AGAIN to Add User, but STAY there
//        dashboard.openAddUserAndStay();
//
//        UsersListPage usersListPage = new UsersListPage(driver);
//        Assert.assertTrue(usersListPage.isVisible(), "Users list page is not visible after Add User tile");
//
//        // 6) Click the 'Add User' button/FAB on that page
//        usersListPage.tapAddUserButton();
//
//        // 7) Use AddUserPage to create a new user
//        addUserPage = new AddUserPage(driver);
//
//        // Create a unique email each run to avoid "email already exists"
//        String uniqueEmail = "autotest+" + System.currentTimeMillis() + "@example.com";
//
//        addUserPage.createUser(
//                "Auto Test Cashier",
//                uniqueEmail,
//                "0771234569",
//                "123456779V",
//                "Password1"
//        );
//
//        // 8) After createUser(), the real app keeps you on the Users list page
//        UsersListPage usersListAfterCreate = new UsersListPage(driver);
//        Assert.assertTrue(
//                usersListAfterCreate.isVisible(),
//                "Users list page is not visible after creating user"
//        );
//
//        // (Optional) Here you could assert that the new user/email is visible in the list
//
//        // 9) Now go BACK to Manager Dashboard from Users list
//        System.out.println("[ACTION] Going back from Users list to Manager Dashboard...");
//        driver.navigate().back();
//
//        Assert.assertTrue(
//                dashboard.isDashboardVisible(),
//                "Dashboard not visible after going back from Users list"
//        );
//
//        // 10) Go to Sales Report screen and stay there
//        dashboard.openSalesReportAndStay();
//
//        // 11) Use SalesReport page object to open each report tile
//        SalesReport salesReport = new SalesReport(driver);
//
//        salesReport.openItemDetailsReport();
//        salesReport.openCustomerDetailsReport();
//        salesReport.openUserDetailsReport();
//        salesReport.openInvoiceListReport();
//        salesReport.openRefundBillsReport();
//        salesReport.openCardPayment();
//        salesReport.openCashPayment();
//        salesReport.openPaymentReport();
//        salesReport.openDailySalesReport();
//        salesReport.openProfitMarginsReport();
//        salesReport.openCreditSalesReport();
//        salesReport.openCashEntryReport();
//        salesReport.openDiscountGranted();
//        salesReport.openUnpaidPurchaseReport();
//        salesReport.openSupplierListReport();
//        salesReport.openStockReport();
//        salesReport.openTransactionHistoryReport();
//        salesReport.openReorderHistoryReport();
//        salesReport.openLowStockWarningReport();
//
//
//        // 12) Back from Sales Report → Manager Dashboard
//        System.out.println("[ACTION] Going back from Sales Report to Manager Dashboard...");
//        driver.navigate().back();
//        Assert.assertTrue(
//                dashboard.isDashboardVisible(),
//                "Dashboard not visible after going back from Sales Report"
//        );
//
//        // 13) Again navigate to Users list page
//        System.out.println("[ACTION] Opening Users list again from Dashboard...");
//        dashboard.openAddUserAndStay();   // or dashboard.openUserManagementAndStay() if you have it
//
//        UsersListPage usersListForSearch = new UsersListPage(driver);
//        Assert.assertTrue(
//                usersListForSearch.isVisible(),
//                "Users list page is not visible for search"
//        );
//
//        // 14) Search bar: search 'Achintha'
//        usersListForSearch.searchUser("Achintha");
//
//        // 15) Assert that user 'Achintha' is visible in the filtered list
//        Assert.assertTrue(
//                usersListForSearch.isUserVisibleByName("Achintha"),
//                "User 'Achintha' not visible after search"
//        );
//
//        System.out.println("[ACTION] Going back from Users list to Manager Dashboard...");
//        driver.navigate().back();
//
//        Assert.assertTrue(
//                dashboard.isDashboardVisible(),
//                "Dashboard not visible after going back from Users list"
//        );
//
//        dashboard.logout();
//
//        login.ensureOnLoginScreen();
    }

//    @Test
//    public void testStockKeeperDashboardFlow() {
//        // 1) Make sure app is in login state
//        login.ensureOnLoginScreen();
//
//        // 2) Login as Manager
//        login.loginAsManager("mihiran@gmail.com", "12345678");
//
//        // 3) Verify dashboard
//        Assert.assertTrue(stockKeeper.isDashboardVisible(), "Dashboard not visible after login");
//
//        stockKeeper.openAddCategory();
//        stockKeeper.openAddItem();
//        stockKeeper.openSuppliers();
//        stockKeeper.openInsights();
//        stockKeeper.openInventory();
//        stockKeeper.openProfitMargins();
//
//        stockKeeper.openAddCategoryAndStay();
//
//        addCategoryPage.openAddCategoryForm();
//        // 6) Open the Add Category form (FAB at bottom right)
//        addCategoryPage.openAddCategoryForm();
//        // 7) Create unique category name (no image)
//        String categoryName = "Auto Category " + System.currentTimeMillis();
//        addCategoryPage.enterCategoryName(categoryName);
//        // 8) Tap "Save Category" button
//        addCategoryPage.tapSaveCategory();
//
//        // 9) Assert success snackbar visible
//        boolean snackOk = addCategoryPage.waitForSuccessSnackBar();
//        Assert.assertTrue(
//                snackOk,
//                "Expected success snackbar after saving category"
//        );
//        // 10) Assert newly added category appears in list
//        boolean exists = addCategoryPage.isCategoryInList(categoryName);
//        Assert.assertTrue(
//                exists,
//                "New category not found in list: " + categoryName
//        );
//
//
//        dashboard.logout();
//
//        login.ensureOnLoginScreen();
//
//    }

//@Test
//public void testStockKeeperAddItemFlow() {
//    // 1) Ensure login screen
//    login.ensureOnLoginScreen();
//
//    // 2) Login as Manager (who sees the StockKeeper dashboard)
//    login.loginAsManager("mihiran@gmail.com", "12345678");
//
//    // 3) Verify StockKeeper dashboard visible
//    Assert.assertTrue(
//            stockKeeper.isDashboardVisible(),
//            "Dashboard not visible after login (StockKeeper)"
//    );
//
//    // 4) Open the Add Item page and stay there
//    stockKeeper.openAddItemAndStay();
//
//    // 5) Create a unique product name
//    String productName = "Auto Item " + System.currentTimeMillis();
//
//    // 6) Fill only the text fields (no category/supplier selection)
//    addItemPage.fillBasicInfo(productName);        // Product Name
//    addItemPage.enterInitialQuantity("10");        // Initial Quantity
//    addItemPage.enterUnitCost("100.50");           // Unit Cost
//    addItemPage.enterSalesPrice("150.75");         // Sales Price
//    addItemPage.enterLowStock("5");                // Low-stock warning
//
//    // ⚠️ No dropdown handling:
//     addItemPage.selectCategoryAndSupplier();  // ← removed
//     addItemPage.tapSaveProduct();             // ← removed
//     addItemPage.waitForSuccessSnackBar();     // ← removed
//
//    // 7) Go back to dashboard after filling data
//    driver.navigate().back();
//
//    // 8) Verify we are back on StockKeeper dashboardz
//    Assert.assertTrue(
//            stockKeeper.isDashboardVisible(),
//            "Dashboard not visible after returning from Add Item"
//    );
//
//    // 9) Open Inventory and navigate through tabs
//    stockKeeper.openInventoryAndStay();
//    inventoryPage.openTotalItems();
//    inventoryPage.openLowStock();
//    inventoryPage.openReStock();
//
//    // 10) Logout
//    dashboard.logout();
//    login.ensureOnLoginScreen();
//}



