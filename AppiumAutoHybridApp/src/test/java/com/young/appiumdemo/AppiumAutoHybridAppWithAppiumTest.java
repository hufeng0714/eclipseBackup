package com.young.appiumdemo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
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
 * @decription appium 处理混合app:使用appium测试引擎
 * 
 * */

public class AppiumAutoHybridAppWithAppiumTest {
	public AppiumDriver<WebElement> driver;
	  @BeforeClass
	  public void startTest() throws MalformedURLException {
	        File classpathRoot = new File(System.getProperty("user.dir"));
	        File app = new File(classpathRoot, "res/app/testApp.apk");
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability("deviceName","appium-test-avd");
	        capabilities.setCapability("automationName","Appium");
	        capabilities.setCapability("platformName","Android");
	        capabilities.setCapability("platformVersion","4.4.2");
	        capabilities.setCapability("app", app.getAbsolutePath());
	        capabilities.setCapability("appPackage", "com.example.testapp");
	        capabilities.setCapability("appActivity", "com.example.testapp.MainActivity");
	        capabilities.setCapability("sessionOverride", true);
	        capabilities.setCapability("unicodeKeyboard", true);
	        capabilities.setCapability("resetKeyboard", false);
	        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	  }
	  
  @Test
  public void webView() throws InterruptedException {
	  //原生应用的URL输入框
	  WebElement editBox=driver.findElement(By.id("com.example.testapp:id/urlField"));
	  //向输入框输入百度首页地址
	  editBox.sendKeys("https://www.baidu.com");
	  //Go按钮
	  WebElement goButton=driver.findElement(By.name("Go"));
	  //点击Go按钮
	  goButton.click();
	  //这里使用driver.getContextHandles();获取app的handles，原生应用会有一个handle，webview也会有一个handle
	  Set<String> contexts = driver.getContextHandles();
	  for (String context : contexts) {
	  System.out.println(context); //会打印出  NATIVE_APP（原生app handle）和WEBVIEW_com.example.testapp(webview的 handle)
	
	  }
	  //进入webview中
	  driver.context((String) contexts.toArray()[1]);
	  //找到百度搜索框
	  WebElement inputBox=driver.findElement(By.id("index-kw"));
	  //输入JAVA关键字
	  inputBox.sendKeys("JAVA");
	  //找到百度一下按钮
	  WebElement searchBtn =driver.findElement(By.id("index-bn"));
	  //点击百度一下按钮
	  searchBtn.click();
	  
  }


  @AfterClass
  public void afterClass() {
	  driver.closeApp();
  }
  
  
	
}
