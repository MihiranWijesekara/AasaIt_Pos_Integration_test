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
    private InventoryManagement inventoryManagement;


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
        addUserPage = new AddUserPage(driver);
        inventoryManagement = new InventoryManagement(driver);


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
        registerPage.getNICField("200224103410");
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
//        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");

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
    @Test(priority = 1)
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

//Full Logout Flow
//Test 05
    @Test(priority = 5)
    public void testFullLogoutFlow() {

        login.ensureOnLoginScreen();

        // 2) Login as Manager
        login.loginAsManager("manager@pos.local", "Manager@12345");

        // 3) Verify dashboard
        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");

        stockKeeperLogoutPage.logout();

        login.ensureOnLoginScreen();

        login.loginAsManager("stock@pos.local", "12345678");
        // 3) Verify dashboard
        Assert.assertTrue(stockKeeper.isDashboardVisible(), "Dashboard not visible after login");
        stockKeeperLogoutPage.logout();

        login.ensureOnLoginScreen();
        login.loginAsManager("cashier@pos.local", "12345678");
        cashierDashboard.tapEnter();
        cashierLogoutPage.logoutFromCashier();
    }

    @Test(priority = 6)
    public void testInventoryManagement() {

        login.ensureOnLoginScreen();

        // 2) Login as Manager
        login.loginAsManager("stock@pos.local", "12345678");

        // 3) Verify dashboard
        Assert.assertTrue(stockKeeper.isDashboardVisible(), "Dashboard not visible after login");

        inventoryManagement.openInventoryNotBack();

        inventoryManagement.tapTotalItems();
        inventoryManagement.tapLowStock();
        inventoryManagement.tapReStock();

    }

    //Test 07
    @Test(priority = 7)
    public void testManagerDashboardScrollerFlow() {

        // 1) Ensure we are on login screen
        login.ensureOnLoginScreen();

        // 2) Login as Manager
        login.loginAsManager("manager@pos.local", "Manager@12345");

        // 3) Verify dashboard visible
        Assert.assertTrue(dashboard.isDashboardVisible(), "Dashboard not visible after login");

        // 4) Wait 10 seconds then scroll (your requirement)
        ManagerScrollerView scroller = new ManagerScrollerView(driver);
        scroller.wait10SecondsThenScrollDown();

        // (Optional) You can add a small assertion after scroll if you have a bottom element
        // Assert.assertTrue(dashboard.isSomeElementVisibleAfterScroll(), "Scroll didn't move to expected section");
    }
}