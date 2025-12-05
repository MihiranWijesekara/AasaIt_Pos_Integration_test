package com.flutterpos.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class AppiumDriverManager {
    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            driver = initializeDriver();
        }
        return driver;
    }

    private static AndroidDriver initializeDriver() {
        try {
            System.out.println("🚀 Initializing AndroidDriver with extended timeouts for Flutter app...");

            DesiredCapabilities capabilities = new DesiredCapabilities();

            // ===== BASIC CAPABILITIES =====
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");

            // ===== APP CAPABILITIES =====
            capabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\Asus\\Desktop\\POS\\POS\\pos\\build\\app\\outputs\\flutter-apk\\app-release.apk");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "com.example.pos.MainActivity");

            // ===== CRITICAL: EXTENDED TIMEOUTS FOR FLUTTER APPS =====
            capabilities.setCapability("uiautomator2ServerLaunchTimeout", 120000); // 2 minutes
            capabilities.setCapability("uiautomator2ServerInstallTimeout", 120000);
            capabilities.setCapability("androidInstallTimeout", 180000); // 3 minutes
            capabilities.setCapability("adbExecTimeout", 180000);
            capabilities.setCapability("appWaitDuration", 120000);
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);

            // ===== FLUTTER-SPECIFIC OPTIMIZATIONS =====
            capabilities.setCapability("appWaitForLaunch", false); // Don't wait for app to fully launch
            capabilities.setCapability("autoLaunch", false); // Launch app manually
            capabilities.setCapability("disableWindowAnimation", true);

            // ===== PERFORMANCE & STABILITY =====
            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true); // Changed to true to avoid re-installation
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
            capabilities.setCapability("ignoreHiddenApiPolicyError", true);
            capabilities.setCapability("skipDeviceInitialization", true); // Skip device init to save time
            capabilities.setCapability("skipServerInstallation", true); // Skip server re-installation
            capabilities.setCapability("skipUnlock", true);
            capabilities.setCapability("enforceAppInstall", false); // Don't enforce re-installation

            URL appiumServerUrl = new URL("http://127.0.0.1:4723");

            System.out.println("⏰ Starting driver initialization - this may take 2-3 minutes for Flutter app...");
            System.out.println("📱 App Package: com.example.pos");
            System.out.println("🎯 App Activity: com.example.pos.MainActivity");

            AndroidDriver androidDriver = new AndroidDriver(appiumServerUrl, capabilities);

            // Add implicit wait
            androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            System.out.println("✅ Appium driver initialized successfully!");
            System.out.println("📱 Session ID: " + androidDriver.getSessionId());
            System.out.println("🔧 Capabilities: " + androidDriver.getCapabilities());

            return androidDriver;

        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Appium driver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Appium driver", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                System.out.println("✅ Appium driver quit successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error while quitting driver: " + e.getMessage());
            }
        }
    }
}