package com.young.appiumdemo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

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
 * @decription appium 处理混合app:使用Selendroid测试引擎
 * 
 * */

public class AppiumAutoHybridAppWithSelendroidTest {
	public AppiumDriver<WebElement> driver;
	  @BeforeClass
	  public void startTest() throws MalformedURLException {
	        File classpathRoot = new File(System.getProperty("user.dir"));
	        File app = new File(classpathRoot, "res/app/selendroid-test-app.apk");
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability("deviceName","appium-test-avd-api16");
	        capabilities.setCapability("automationName","Selendroid");
	        capabilities.setCapability("platformName","Android");
	        capabilities.setCapability("platformVersion","4.1.2");
	        capabilities.setCapability("app", app.getAbsolutePath());
	        capabilities.setCapability("appPackage", "io.selendroid.testapp");
	        capabilities.setCapability("appActivity", ".HomeScreenActivity");
	        capabilities.setCapability("sessionOverride", true);
	        capabilities.setCapability("unicodeKeyboard", true);
	        capabilities.setCapability("resetKeyboard", false);
	        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	  }
	  
  @Test
  public void webView() throws InterruptedException {
	  //chrome图标按钮
	   WebElement button = driver.findElement(By.id("buttonStartWebview"));
	   //点击chrome图标按钮
       button.click();
       Thread.sleep(2000);
       //这里使用driver.getContextHandles();获取app的handles，原生应用会有一个handle，webview也会有一个handle
       Set<String> contextNames = driver.getContextHandles();
       for (String contextName : contextNames) {
         System.out.println(contextName);
         if (contextName.contains("WEBVIEW")){       	 
           driver.context(contextName);
           System.out.println("进入了webview:"+contextName);

         }
       }
       WebElement inputField = driver.findElement(By.id("name_input"));
       inputField.clear();
       inputField.sendKeys("Young");
       inputField.submit();
       
       Thread.sleep(4000);
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
  
  
	
}
