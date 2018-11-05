package com.young.appiumdemo;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
/**
 * @author Young
 * @decription appium 截图
 * 
 * */

public class AppiumScreenShotTest {
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
  public void screenshot() throws InterruptedException, IOException {
	  //直接进行截图操作
	  File screenShot=driver.getScreenshotAs(OutputType.FILE);
	  File location=new File("screenshots");
	  if(!location.exists()){
		  location.mkdir();
	  }
	  String screenShotName=location.getAbsolutePath()+File.separator+"HOME.png";
	  FileUtils.copyFile(screenShot,new File(screenShotName));
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.closeApp();
  }
  
  
	
}
