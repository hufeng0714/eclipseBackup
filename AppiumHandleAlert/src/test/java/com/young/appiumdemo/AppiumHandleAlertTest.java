package com.young.appiumdemo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
/**
 * @author Young
 * @decription appium 关于alert的处理
 * 
 * */

public class AppiumHandleAlertTest {
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
  public void handleAlert() throws InterruptedException {
	  WebElement showAlert= driver.findElement(By.name("Show Alert"));
	  //点击之后弹出alert窗口
	  showAlert.click();
	  WebElement yes=driver.findElement(By.name("Yes"));
	  //点击yes按钮之后alert窗口消失
	  yes.click();
	  //暂停4秒观察显示
	  Thread.sleep(4000);
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.closeApp();
  }
  
  
	
}
