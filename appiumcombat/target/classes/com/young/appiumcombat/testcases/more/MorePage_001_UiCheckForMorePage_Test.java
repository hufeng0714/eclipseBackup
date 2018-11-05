package com.young.appiumcombat.testcases.more;
import java.util.Map;
import org.testng.annotations.Test;
import com.young.appiumcombat.base.BasePrepare;
import com.young.appiumcombat.pages.HomePage;
import com.young.appiumcombat.pages.InitPage;
import com.young.appiumcombat.pageshelper.HomePageHelper;
import com.young.appiumcombat.pageshelper.InitPageHelper;
import com.young.appiumcombat.pageshelper.MorePageHelper;

public class MorePage_001_UiCheckForMorePage_Test  extends BasePrepare{
	
	@Test(dataProvider="testData")
	public void uiCheckForMorePage(Map<String, String> data){
		//去除欢迎界面和定位弹�?
		InitPageHelper.handleInit(appiumUtil, InitPage.byElements);
		//在首页上点击更多按钮
		HomePageHelper.clickOnHomePage(appiumUtil, HomePage.HP_BUTTON_MORE);
		//�?��更多页面的文�?
		MorePageHelper.checkMorePageUIText(appiumUtil, data.get("LOGIN"),data.get("HISTORY"),data.get("SETTINGS"),data.get("FEEDBACK"));
	}

}
