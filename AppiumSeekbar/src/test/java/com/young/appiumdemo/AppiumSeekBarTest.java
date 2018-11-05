package com.young.appiumdemo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
/**
 * @author Young
 * @decription appium seekbar-操作拖动条
 * 
 * */

public class AppiumSeekBarTest {
	public AppiumDriver<WebElement> driver;
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
  public void testSeekbar() throws InterruptedException {
	  //找到拖动条
	  WebElement slider=driver.findElementById("com.android.androidui:id/seekBar1");
	  //获取拖动条的开始拖动点的x坐标
	  int xAxisStartPoint = slider.getLocation().getX();
	  //获取拖动条的结束点的x坐标  = 开始x坐标+滑动条元素的宽度
	  int xAxisEndPoint = xAxisStartPoint + slider.getSize().getWidth();
	  //滚动条的y坐标
	  int yAxis = slider.getLocation().getY();
	  TouchAction act=new TouchAction(driver);
	  act.press(xAxisStartPoint,yAxis).waitAction(800).moveTo(xAxisEndPoint-
	  1,yAxis).release().perform();
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.closeApp();
  }
  
  
	
}
