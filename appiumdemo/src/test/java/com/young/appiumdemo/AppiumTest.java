package com.young.appiumdemo;

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
import io.appium.java_client.android.AndroidDriver;

public class AppiumTest {
	
	public AppiumDriver<WebElement> driver;

	@BeforeClass
	public void startTest() throws MalformedURLException{
		
		//apk放置在本项目的根目录下,要新建的
		//D:\EcliWorkSpace\appiumdemo\res\app
		
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot,"res/app");
		File app = new File(appDir,"ContactManager.apk");
		
		/*
		 * capability全称是Desired capabilities
		 * Desired capability是一个JSON对象，包含一组key和value值。
		 * 它由客户端发送给服务端，告诉服务端期望的Capabilities（可以理解为一种能力）有哪些，
		 * 然后服务端根据这些capabilities创建自动化会话（session）。
		 * */
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//定义测试引擎，如果sdk版本小于17的话
		//capabilities.setCapability("automationName","Selendroid");
		capabilities.setCapability("automationName","appium");
		
		//定义测试平台的名字，通常用于移动设备。值有：Android,IOS,FirefoxOS
		capabilities.setCapability("platformName","Android");
		
		//移动设备名字
		capabilities.setCapability("deviceName","6&343295d3&0&0002");
		//capabilities.setCapability("deviceName","Android Emulator");
		
		//测试平台版本
		capabilities.setCapability("platformVerion","4.4.2");
		
		//app的路径
		capabilities.setCapability("app",app.getAbsolutePath());
		
		//设置app的包名，告诉appium你要运行哪个app【但是目前的环境貌似不需要这两句，有的话反而报错】
//		capabilities.setCapability("appPackage","com.example.android.contactmanager");
		
		//设置你想要运行的app的activity（相当于一个界面或网页），比如：LoginActivity，可以理解为登录界面
//		capabilities.setCapability("appActivity",".ContractManager");
		
		//如果测试web app，就需要定义browserName
		//capabilities.setCapability("browserName","Chrome");
		
		//
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	}
	
//	public void beforeClass() {
//	}
		
		  
	@Test
	public void addContract(){
		WebElement el = driver.findElement(By.xpath(".//*[@text='Add Contact']"));
		el.click();
		List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
		textFieldsList.get(0).sendKeys("Some name");
		textFieldsList.get(2).sendKeys("some@example.com");
		driver.swipe(100,500,100,100,2);
		driver.findElementByXPath(".//*[@text='Save']").click();
	}
	
//	public void f() {
//	}
		  
	@AfterClass
	
	public void afterClass() {
		driver.quit();
	}
	
}
