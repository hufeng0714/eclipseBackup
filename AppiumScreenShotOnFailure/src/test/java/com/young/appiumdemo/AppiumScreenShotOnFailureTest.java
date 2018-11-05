package com.young.appiumdemo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
/**
 * @author Young
 * @decription appium 失败截图
 * 
 * */
@Listeners({ScreenshotListener.class})
public class AppiumScreenShotOnFailureTest {
	static  AppiumDriver<WebElement> driver;
	  @BeforeClass
	  public void startTest() throws MalformedURLException {
	        File classpathRoot = new File(System.getProperty("user.dir"));
	        File app = new File(classpathRoot, "res/app/AndroidUI.apk");
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability("deviceName","appium-test-avd");
	        capabilities.setCapability("automationName","Appium");
	        capabilities.setCapability("platformName","Android");
	        capabilities.setCapability("platformVersion","4.4.2");
	        capabilities.setCapability("app", app.getAbsolutePath());
	        capabilities.setCapability("appPackage", "com.android.androidui");
	        capabilities.setCapability("appActivity", "com.android.androidui.MainActivity");
	        capabilities.setCapability("sessionOverride", true);
	        capabilities.setCapability("unicodeKeyboard", true);
	        capabilities.setCapability("resetKeyboard", false);
	        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	  }
	  
  @Test
  public void screenshotOnFailure() throws InterruptedException {
	  //元素 Show Alert
	  WebElement showAlert = driver.findElement(By.id("com.android.androidui:id/buttonAlert"));
	  //故意制作失败情况，看看会不会截图
	  Assert.assertEquals(showAlert.getText().trim(), "Shw Alert");
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.closeApp();
  }
  
  public static AppiumDriver<WebElement> getDriver(){
	  return driver;
	  }
	
}
