/*
package com.haomaiche.PC;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AskPrice {

	public static void main(String []args ){
		
		//���chromeû�а�װ��Ĭ��Ŀ¼��Ҫ�ڹ���������
		System.setProperty("webdriver.chrome.driver", "E:\\selenium2\\chromedriver_win32\\chromedriver.exe");
		//����һ��ChromeDriver�Ľӿڣ���������chrome
		//����һ��chrome�������ʵ��
		WebDriver driver = new ChromeDriver();
		//�������
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//�����������baidu
		/*2�ַ�ʽҲ�ǿ��Ե�
		 * 
		 * driver.get("https://www.baidu.com");
		 * 
		 * driver.navigate().to("https://www.baidu.com");
		 * */
/*
		String URL = "http://test.haomaiche.com";
		
		driver.get(URL);
		
		//WebElement element = driver.findElement(By.xpath("/html/body/div/ul/li/a"));
		WebElement element = driver.findElement(By.linkText("ѡ��ȥ�ȼ�"));
		element.click();
		
		element = driver.findElement(By.linkText("���Դ�"));
		element.click();
		
		driver.findElement(By.xpath("//img[contains(@src,'http://static.haomaiche.com/common/images/type/cover/ask/5adee8230b234879bbc3de82b4b0eddb.png')]")).click();
		//element.click();
		//driver.close();
		
		
	}
	
	
}
**/


package com.haomaiche.PC;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AskPrice {

  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://test.haomaiche.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitled() throws Exception {
    driver.get(baseUrl + "/sh");
    driver.findElement(By.linkText("ѡ��ȥ�ȼ�")).click();
    driver.findElement(By.linkText("���Դ�")).click();
    driver.findElement(By.xpath("//img[contains(@src,'http://static.haomaiche.com/common/images/type/cover/ask/5adee8230b234879bbc3de82b4b0eddb.png')]")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
