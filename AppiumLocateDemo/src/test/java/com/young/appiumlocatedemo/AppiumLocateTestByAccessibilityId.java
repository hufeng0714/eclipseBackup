package com.young.appiumlocatedemo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author Young
 * @description 通过AccessibilityId查找元素
 * 
 */

public class AppiumLocateTestByAccessibilityId {
	public AppiumDriver<WebElement> driver;

	@BeforeClass
	public void startTest() throws MalformedURLException {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "res/app");
		File app = new File(appDir, "ContactManager.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformVersion", "4.4.2");
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appPackage", "com.example.android.contactmanager");
		capabilities.setCapability("appActivity", ".ContactManager");
		capabilities.setCapability("sessionOverride", true);
		capabilities.setCapability("noreset", true);
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test
	public void addContact() {
		// 通过AccessibilityId查找元素
		WebElement el = driver.findElementByAccessibilityId("Add Contact");
		el.click();
		// 等待5秒观察显示
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
