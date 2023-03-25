package com.qa.opencart.test;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.goToRegisterPage();
	}

//	@DataProvider
//	public Object[][] getRegData() {
//		return new Object[][] { { "Priya111", "Goel", "priyagoel161@gmail.com", "1234", "priyagoel" },
//				{ "Radha111", "Gupta", "radha11244561@gmail.com", "123445", "abc123" },
//				{ "Surya111", "Kumar", "Surya1123451@gmail.com", "123", "abc123" }
//
//		};
//	}
	
	public String randomEmail() {
		Random random=new Random();
		String email="automation"+random.nextInt(1000)+"@gmail.com";
		return email;
	}

	@DataProvider
	public Object[][] getRegExcelData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}

	@Test(dataProvider = "getRegExcelData")
	public void userRegTest(String firstName, String lastName, String telephone, String password) {
	Boolean actFlag = registerPage.userRegistration(firstName, lastName,randomEmail(), telephone, password);
		Assert.assertEquals(actFlag,true);
		registerPage.goToRegisterPage();
		

	}

}
