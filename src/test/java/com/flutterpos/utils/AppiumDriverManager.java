package com.flutterpos.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.time.Duration;

public class AppiumDriverManager {

    private static AndroidDriver driver;

    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    private static final String UDID = "emulator-5554";

    private static final String APP_PATH = "C:\\Users\\Asus\\Desktop\\POS-APK\\app-debug.apk";
    private static final String APP_PACKAGE = "com.example.pos";
    private static final String APP_ACTIVITY = "com.example.pos.MainActivity";
    private static final String APP_WAIT_ACTIVITY = "com.example.pos.MainActivity";

    public static AndroidDriver getDriver() {
        if (driver == null) {
            driver = initializeDriver();
        }
        return driver;
    }

    private static AndroidDriver initializeDriver() {
        try {
            System.out.println("🚀 Initializing AndroidDriver (UiAutomator2) ...");
            System.out.println("📱 UDID: " + UDID);
            System.out.println("📦 App: " + APP_PATH);
            System.out.println("🎯 Package: " + APP_PACKAGE);
            System.out.println("🎯 Activity: " + APP_ACTIVITY);

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName("Android Emulator")
                    .setUdid(UDID)

                    // App under test
                    .setApp(APP_PATH)
                    .setAppPackage(APP_PACKAGE)
                    .setAppActivity(APP_ACTIVITY)
                    .setAppWaitActivity(APP_WAIT_ACTIVITY)

                    // First run on new emulator must be clean
                    .setNoReset(false)
                    .setFullReset(false)

                    // Useful stability flags
                    .setAutoGrantPermissions(true)
                    .setDisableWindowAnimation(true)
                    .setNewCommandTimeout(Duration.ofSeconds(300));

            // --- Timeouts (Flutter + emulator can be slow) ---
            options.setAdbExecTimeout(Duration.ofSeconds(180));
            options.setAndroidInstallTimeout(Duration.ofSeconds(180));
            options.setAppWaitDuration(Duration.ofSeconds(180));

            // UiAutomator2 server timeouts
            options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(180));
            options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(180));

            // IMPORTANT: Let Appium launch the app
//            options.setAutoLaunch(true);

            // Hidden API policy errors ignored (good for some devices)
            options.setIgnoreHiddenApiPolicyError(true);

            URL serverUrl = new URL(APPIUM_SERVER_URL);

            System.out.println("⏰ Creating driver session...");
            AndroidDriver androidDriver = new AndroidDriver(serverUrl, options);

            // Implicit wait (keep small; prefer explicit waits in tests)
            androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            System.out.println("✅ Driver initialized successfully!");
            System.out.println("📱 Session ID: " + androidDriver.getSessionId());
            System.out.println("📦 Current package: " + androidDriver.getCurrentPackage());

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
