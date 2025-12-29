package com.flutterpos.tests;

import com.flutterpos.pages.*;
import com.flutterpos.pages.cashier.CashierDashboard;
import com.flutterpos.pages.cashier.CashierLogoutPage;
import com.flutterpos.pages.stockKeeper.*;
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
    private StockFooter stockFooter;
    private AddSupplier addSupplier;
    private AddNewCategory addNewCategory;
    private AddProduct addProduct;
    private StockKeeperLogoutPage stockKeeperLogoutPage;
    private AddCategory addCategoryPage;
    private AddItem addItemPage;
    private InventoryPage inventoryPage;
    private RegisterPage2 registerPage;
    private ManagerFooter managerFooter;
    private ManagerReportPage managerReportPage;
    private SalesReport salesReport;
    private CashierDashboard cashierDashboard;
    private CashierLogoutPage cashierLogoutPage;


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
        registerPage = new RegisterPage2(driver);
        managerFooter = new ManagerFooter(driver);
        managerReportPage = new ManagerReportPage(driver);
        salesReport = new SalesReport(driver);
        stockFooter = new StockFooter(driver);
        addSupplier = new AddSupplier(driver);
        addNewCategory = new AddNewCategory(driver);
        addProduct = new AddProduct(driver);
        stockKeeperLogoutPage =  new StockKeeperLogoutPage(driver);
        cashierDashboard = new CashierDashboard(driver);
        cashierLogoutPage = new CashierLogoutPage(driver);

    }



    //Test 01
    @Test(priority = 1)
    public void testRegisterPageFlow() {
        //1) Make sure app is in login state
        login.ensureOnLoginScreen();
        login.tapRegister();

        registerPage.enterDisplayName("AasaIT POS");
        registerPage.enterLegalName("AasaIT POS");
        registerPage.enterPhone("0771234567");
        registerPage.enterEmail("test@pos.lk");
        registerPage.enterAddress("Anuradhapura");
        registerPage.enterLKR("LKR");
        registerPage.enterVAT("VAT");
        registerPage.enterTaxID("1234");
        registerPage.tapNext();

        registerPage.enterFullNameField("Manager One");
        registerPage.enterEmailSecField("manager1@pos.lk");
        registerPage.enterContactNumberField("0779999999");
        registerPage.getNICField("2002243613");
        registerPage.enterPasswordField("1234eF@90g");
        registerPage.enterConfirmPasswordField("1234eF@90g");
        registerPage.tapFinish();

    }

    //Test 02
    @Test(priority = 2)
    public void testManagerDashboardFlow() {  //cashier@pos.local,stock@pos.local

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
        dashboard.openCreditors();
        dashboard.openProfitDetail();

        managerReportPage.clickReportFooterAndStay();
        managerReportPage.navigateDailySummary();
        managerReportPage.navigateReportHub();
        managerReportPage.navigateTopProducts();
        managerReportPage.navigateProfitSplit();

        managerReportPage.navigateReportHubAndStay();

        salesReport.openItemDetailsReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openCustomerDetailsReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openUserDetailsReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openInvoiceListReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openRefundBillsReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openCardPayment();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openCashPayment();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openPaymentReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openDailySalesReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openCreditSalesReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openCashEntryReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openDiscountGranted();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openUnpaidPurchaseReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openSupplierListReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openStockReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openTransactionHistoryReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openReorderHistoryReport();
        managerReportPage.navigateReportHubAndStay();
        salesReport.openLowStockWarningReport();

        stockKeeperLogoutPage.logout();

    }

    //Test 03
    @Test(priority = 3)
    public void testStockKeeperDashboardFlow() {
        // 1) Make sure app is in login state
        login.ensureOnLoginScreen();

        // 2) Login as Manager
        login.loginAsManager("stock@pos.local", "12345678");

        // 3) Verify dashboard
        Assert.assertTrue(stockKeeper.isDashboardVisible(), "Dashboard not visible after login");



        // Add Category
        stockKeeper.openAddCategoryNotBack();
        addNewCategory.navigateAddCategory();
        addNewCategory.enterCategoryName("Beverages");
        addNewCategory.saveNewCategory();

        // Add Supplier
        stockKeeper.openSuppliersNotBack();
        addSupplier.navigateAddSupplier();
        addSupplier.enterSupplierName("ABC Traders");
        addSupplier.enterContactNumber("0771234567");
        addSupplier.enterEmail("abc@gmail.com");
        addSupplier.enterBrandCompany("ABC Pvt Ltd");
        addSupplier.clickSaveSupplier();

        // Add Product
        stockKeeper.openAddItemNotBack();
        addProduct.enterProductName("Orange Juice");
        addProduct.enterInitialQuantity("50");
        addProduct.enterUnitCost("120.00");
        addProduct.enterSalesPrice("150.00");
        addProduct.enterLowStock("10");
        addProduct.tapCategory();
        addProduct.tapBeverages();
        addProduct.tapSupplier();
        addProduct.tapABCTraders();
        addProduct.tapSave();

        stockKeeper.openAddCategory();
        stockKeeper.openAddItem();
        stockKeeper.openSuppliers();
        stockKeeper.openInventory();
        stockKeeper.openTotalStockItem();
        stockKeeper.openRestockRequests();
        stockKeeper.openInsights();
        stockKeeper.openSupplierReviews();

        stockFooter.navigateReports();
        stockFooter.navigateNotification();
        stockFooter.navigateSetting();
        stockFooter.navigateHome();

        stockFooter.navigateReports();
        stockFooter.navigateDailyStockReport();
        stockFooter.navigateSetting();
        stockFooter.navigateTileAppearance();
        stockFooter.navigateHome();

        stockKeeperLogoutPage.logout();
    }

//Test 04
@Test(priority = 4)
public void testCasshierDashboardFlow() {
    // 1) Make sure app is in login state
    login.ensureOnLoginScreen();

    // 2) Login as Manager
    login.loginAsManager("cashier@pos.local", "12345678");

    cashierDashboard.tapEnter();
    cashierDashboard.tapBakery();
    cashierDashboard.tapProduct();
    cashierDashboard.tapAdd();

    cashierDashboard.tapBakery();
    cashierDashboard.tapProduct();
    cashierDashboard.tapHome();

    cashierDashboard.tapBakery();
    cashierDashboard.tapProduct();
    cashierDashboard.enterQuantity("3");
    cashierDashboard.tapAdd();

    cashierDashboard.enterSearch("Fanta");
    cashierDashboard.tapFanta();
    cashierDashboard.tapAdd();

    cashierDashboard.tapBook();
    cashierDashboard.tapTomato();
    cashierDashboard.tapAdd();

    cashierLogoutPage.logoutFromCashier();

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



