package com.young.appiumdemo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
/**
 * @author Young
 * @decription appium 演示滚动屏幕
 * 
 * */

public class AppiumScrollTest {
	public AppiumDriver<WebElement> driver;
	  @BeforeClass
	  public void startTest() throws MalformedURLException {
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability("deviceName","appium-test-avd");
	        capabilities.setCapability("automationName","Appium");
	        capabilities.setCapability("platformName","Android");
	        capabilities.setCapability("platformVersion","4.4.2");
	        capabilities.setCapability("appPackage", "com.android.contacts");
	        capabilities.setCapability("appActivity", ".activities.PeopleActivity");
	        capabilities.setCapability("sessionOverride", true);
	        capabilities.setCapability("unicodeKeyboard", true);
	        capabilities.setCapability("resetKeyboard", false);
	        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	  }
	  
  @Test
  public void scroll() throws InterruptedException {
	  //滑动直到查找到E
	  driver.scrollToExact("E");
	  
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
	
}
