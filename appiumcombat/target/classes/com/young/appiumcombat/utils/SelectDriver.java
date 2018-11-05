package com.young.appiumcombat.utils;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;

import io.appium.java_client.AppiumDriver;
/**
 * @author Young
 * @description 根据测试平台的不同生成不同的driver 比如AndroidDriver 或�?是IOSDriver
 * 
 * */

public class SelectDriver {
	//声明driver
	public  AppiumDriver<WebElement> driver; 
	//声明DesiredCapabilities
    public DesiredCapabilities capabilities;
    //声明ITestContext，用于获取testng配置文件内容
	public ITestContext testContext;
	//appium server地址
	public String serverURL;
	//测试引擎名字
	public String automationName;
	//测试平台名字
	public String platformName;
	//测试平台版本�?
	public String platformVersion;
	//设备名字
	public String deviceName;
	//ios app的路�?
	public String iosAppPath;
	//android app路径
	public String androidAppPath;
	//android app�?package
	public String appPackage;
	//android app的activity
	public String appActivity;
	//安卓独有 - 是否使用unicode键盘，使用此键盘可以输入中文字符
	public boolean unicodeKeyboard;
	//android独有 - 是否重置键盘，如果设置了unicodeKeyboard键盘，可以将此参数设置为true，然后键盘会重置为系统默认的
	public boolean resetKeyboard;
	//是否覆盖已有的seesssion，这个用于多用例执行，如果不设置的话，会提示前一个session还没有结束，用例就不能继续执行了
	public boolean sessionOverride;
	//暂停的等待时�?
	public int sleepTime;
	//元素等待超时时间
	public int elementTimeOut;
	//app文件路径，主要存储的是app的名�?
	public String appFilePath;
	//webview的名字或者叫标识�?�?��以WEBVIEW�?��，例如WEBVIEW_com.microsoft.bing
	public final static String WEBVIEW_NAME = null;
	//原生app的名字或者标识符，一般是NATIVE_APP
	public final static String NATIVEAPP_NAME = null;
	//实例化本类的日志输出对象
	public static Logger logger = Logger.getLogger(SelectDriver.class);

	public  AppiumDriver<WebElement> selectDriver(ITestContext context,AppiumUtil appiumUtil){
		  //通过testng的xml文件获取serverURL参数值，并赋�? serverURL变量
		  serverURL = context.getCurrentXmlTest().getParameter("serverURL");
		  //通过testng的xml文件获取automationName参数值，并赋�? automationName变量
		  automationName = context.getCurrentXmlTest().getParameter("automationName");
		  //通过testng的xml文件获取platformName参数值，并赋�? platformName变量
		  platformName = context.getCurrentXmlTest().getParameter("platformName");
		  //通过testng的xml文件获取platformVersion参数值，并赋�? platformVersion变量
		  platformVersion = context.getCurrentXmlTest().getParameter("platformVersion");
		  //通过testng的xml文件获取deviceName参数值，并赋�? deviceName变量
	      deviceName = context.getCurrentXmlTest().getParameter("deviceName");
		  //通过testng的xml文件获取androidAppPath参数值，并赋�? androidAppPath变量
	      androidAppPath = context.getCurrentXmlTest().getParameter("androidAppPath");
		  //通过testng的xml文件获取iosAppPath参数值，并赋�? iosAppPath变量
	      iosAppPath = context.getCurrentXmlTest().getParameter("iosAppPath");
	      //通过testng的xml文件获取appPackage参数值，并赋�? appPackage变量
	      appPackage = context.getCurrentXmlTest().getParameter("appPackage");
	      //通过testng的xml文件获取appActivity参数值，并赋�? appActivity变量
	      appActivity = context.getCurrentXmlTest().getParameter("appActivity");
	      //通过testng的xml文件获取unicodeKeyboard参数值，并赋�? unicodeKeyboard变量
	      unicodeKeyboard = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("unicodeKeyboard"));
	      //通过testng的xml文件获取resetKeyboard参数值，并赋�? resetKeyboard变量
	      resetKeyboard = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("resetKeyboard"));
	      //通过testng的xml文件获取sessionOverride参数值，并赋�? sessionOverride变量
	      sessionOverride = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("sessionOverride"));
	      //通过testng的xml文件获取sleepTime参数值，并赋�? sleepTime变量
	      sleepTime = Integer.valueOf(context.getCurrentXmlTest().getParameter("sleepTime"));
	      //通过testng的xml文件获取elementTimeOut参数值，并赋�? elementTimeOut变量
	      elementTimeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("elementTimeOut"));
	      //通过testng的xml文件获取appFilePath参数值，并赋�? appFilePath变量
	      appFilePath = context.getCurrentXmlTest().getParameter("appFilePath");
	      this.testContext = context;
	      capabilities = new DesiredCapabilities();
	      //告诉测试程序，当前项目目录在哪里
	      File classpathRoot = new File(System.getProperty("user.dir"));
	      //设置capability，以便和appium创建session
	      capabilities.setCapability("platformName",platformName);
	      capabilities.setCapability("platformVersion",platformVersion);
	      capabilities.setCapability("deviceName",deviceName);
	      capabilities.setCapability("sessionOverride", sessionOverride);
	      //如果测试平台是android的话，执行下面这个if语句内容
		if(platformName.equalsIgnoreCase("android")){
				/**
				 * 设置和android  测试相关的capability并实例化driver对象
				 * */
		      File app = new File(classpathRoot, androidAppPath);
		      capabilities.setCapability("app", app.getAbsolutePath());
		      capabilities.setCapability("unicodeKeyboard", unicodeKeyboard);
		      capabilities.setCapability("resetKeyboard", resetKeyboard);
		      capabilities.setCapability("automationName",automationName);
		      capabilities.setCapability("appPackage", appPackage);
		      capabilities.setCapability("appActivity", appActivity);
		      driver = appiumUtil.getDriver(serverURL, capabilities,platformName);
		      testContext.setAttribute("APPIUM_DRIVER", driver);
		      logger.info(PropertiesDataProvider.getTestData(appFilePath, appPackage)+"已经启动");
		      driver.manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
		      return driver;
		      
		      //如果测试平台是ios的话，执行下面这个if语句内容
		}else if(platformName.equalsIgnoreCase("ios")){
			/**
			 * 设置和ios  测试相关的capability并实例化driver对象
			 * */
		      File app = new File(classpathRoot, iosAppPath);
		      capabilities.setCapability("app", app.getAbsolutePath());
		      //ios设置自动接收系统alert，注意IOS弹出的alert，APPIUM可以自动处理掉，支持ios8以上系统
		      capabilities.setCapability("autoAcceptAlerts", true);
		      driver = appiumUtil.getDriver(serverURL, capabilities,platformName);
		      testContext.setAttribute("APPIUM_DRIVER", driver);
		      driver.manage().timeouts().implicitlyWait(elementTimeOut,TimeUnit.SECONDS);
			
		}else{
		logger.error("初始化driver失败");
		Assert.fail("初始化driver失败");
		}
		
		//�?��返回dirver对象
		return driver;

		
	}
	
	
}
