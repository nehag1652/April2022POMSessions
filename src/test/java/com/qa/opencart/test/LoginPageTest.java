package com.qa.opencart.test;

import java.io.Serial;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import net.bytebuddy.build.HashCodeAndEqualsPlugin.Enhance;


@Epic("Epic-100:Design the login page feature for open cart application")
@Story("US-101: Design login page features with login,forgot pwd,title,url")
public class LoginPageTest extends BaseTest {
	
	
	@Test
	@Description("TC_01:login page title test")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test
	@Description("TC_02:login page url test")
	public void loginPageUrlTest() {
		String actUrl = loginPage.getLoginPageUrl();
		Assert.assertEquals(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), true);
	}

	@Test
	@Description("TC_03:Forgot password link exist or not test")
	public void forgotPwdLinkExistTest() {
		Boolean actFlag = loginPage.isForgotPwdLinkExist();
		Assert.assertEquals(actFlag, true);
	}

	@Test(priority = 1)
	public void doLoginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

//	@Test
//	public void imgExistTest() {
//		Boolean imgflag = loginPage.imgExist();
//		Assert.assertEquals(imgflag, true);
//		if (imgflag) {
//			System.out.println("Img Exist");
//		}
//	}

//	@Test
//	public void searchTest(String title) {
//		loginPage.search("macbook");
//		Assert.assertEquals(title, "Search - macbook");
//
//	}

}
