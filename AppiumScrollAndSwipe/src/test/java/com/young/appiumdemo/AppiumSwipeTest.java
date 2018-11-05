package com.young.appiumdemo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
/**
 * @author Young
 * @decription appium 滑动滚动屏幕
 * 
 * */

public class AppiumSwipeTest {
	public AndroidDriver<WebElement> driver;
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
  public void swipe() throws InterruptedException {
	  Thread.sleep(1000);
	  TouchAction tAction=new TouchAction(driver);
	  tAction.press(400,500).waitAction(800).moveTo(50,500).release().perform();
	  Thread.sleep(4000);
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
  
  
	
}
