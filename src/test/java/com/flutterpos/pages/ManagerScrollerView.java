package com.flutterpos.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public class ManagerScrollerView {

    private final AppiumDriver driver;

    public ManagerScrollerView(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * After manager dashboard login:
     * wait 10 seconds then scroll down once.
     */
    public void wait10SecondsThenScrollDown() {
        sleep(10);

        // Scroll Down (swipe up gesture)
        swipeVertical(0.80, 0.25, 700);
    }

    /**
     * If you need multiple scrolls:
     */
    public void wait10SecondsThenScrollDownTimes(int times) {
        sleep(10);
        for (int i = 0; i < times; i++) {
            swipeVertical(0.80, 0.25, 700);
            sleepMillis(300);
        }
    }

    // -------------------- Helpers --------------------

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sleepMillis(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Vertical swipe using W3C actions.
     * startYRatio: e.g. 0.80 (near bottom)
     * endYRatio  : e.g. 0.25 (near top)
     */
    private void swipeVertical(double startYRatio, double endYRatio, int durationMs) {
        Dimension size = driver.manage().window().getSize();
        int width = size.getWidth();
        int height = size.getHeight();

        int x = (int) (width * 0.50); // center
        int startY = (int) (height * startYRatio);
        int endY = (int) (height * endYRatio);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(new Pause(finger, Duration.ofMillis(150)));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), x, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}

