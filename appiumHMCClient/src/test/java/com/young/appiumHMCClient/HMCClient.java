package com.young.appiumHMCClient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;;

public class HMCClient {
  
	public AppiumDriver<WebElement> driver;
	  
  @BeforeClass
//  public void beforeClass() {
//	  	  
//  }

  	public void startTest() throws MalformedURLException{
	  
	  File classpathRoot = new File(System.getProperty("user.dir"));
	  File appDir = new File(classpathRoot,"res/app");
	  File app = new File(appDir,"com.xuanyan.haomaiche.webuserapp.apk");
	  
	  DesiredCapabilities capabilities = new DesiredCapabilities();
	  capabilities.setCapability("automationName", "Appium");
	  capabilities.setCapability("platformName", "Android");
	  capabilities.setCapability("deviceName","appium-test-hmc");
	  capabilities.setCapability("platformVersion", "4.4.2");
	  capabilities.setCapability("app", app.getAbsolutePath());
	  
	  driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
  }
  @Test
//public void f() {
//}
  	public void clickClient(){
	  
	  WebElement el = driver.findElement(By.id("13"));
	  el.click();
	  List<WebElement> textFieldsList = driver.findElements(By.className("android.widget.TextView"));
  }


  @AfterClass
  	public void afterClass() {
	  
	  driver.quit();
  }

}
