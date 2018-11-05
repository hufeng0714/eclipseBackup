package com.young.appiumcombat.testcases.home;

import java.util.Map;

import org.testng.annotations.Test;

import com.young.appiumcombat.base.BasePrepare;
import com.young.appiumcombat.pages.InitPage;
import com.young.appiumcombat.pageshelper.HomePageHelper;
import com.young.appiumcombat.pageshelper.InitPageHelper;

public class HomePage_001_UiCheck_Test extends BasePrepare{
	
	@Test(dataProvider="testData")
	public void uiCheck(Map<String, String> data){
		//去除欢迎界面和定位弹�?
		InitPageHelper.handleInit(appiumUtil, InitPage.byElements);
		//等待首页元素加载，这个条件可以不要因为是原生APP的，代码都是在本地的，基本上打开了APP就显示了原生元素
		HomePageHelper.waitHomeUI(appiumUtil, elementTimeOut);
		//�?��文本
		HomePageHelper.checkHomeUIText(appiumUtil, data.get("STORY"));
		
	}
	
	
	

}
