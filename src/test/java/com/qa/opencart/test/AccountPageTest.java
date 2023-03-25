package com.qa.opencart.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertEquals(accPage.isLogoutLinkExist(), true);
	}
	
	@Test
	public void accPageTitleTest(){
		Assert.assertEquals(accPage.getAccountsPageTitle(),AppConstants.ACCOUNTS_PAGE_TITLE );
	}

	@Test
	public void isSearchFieldExistTest() {
		Assert.assertEquals(accPage.isSearchFieldExist(), true);
	}
	
	@Test
	public void getAccountPageHeaderListTest() {
	List<String> accHeaderList=accPage.getAccountSectionsHeaderList();
	System.out.println(accHeaderList);
	Assert.assertEquals(accHeaderList,AppConstants.EXPECTED_ACCOUNTS_HEADERS_LIST);
	}

}
