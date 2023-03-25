package com.qa.opencart.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductSearchTest extends BaseTest {

	@BeforeClass
	public void productSearchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] productData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "iphone", "iPhone" } };
	}

	@Test(dataProvider = "productData")
	public void productSearchTest(String searchKey, String productName) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		String actProductHeaderName = productInfoPage.getProductHeaderValue();
		Assert.assertEquals(actProductHeaderName, productName);
	}

	@DataProvider
	public Object[][] imgData() {
		return new Object[][] { { "Macbook", "MacBook Pro", 4 }, { "iphone", "iPhone", 6 }

		};
	}

	@Test(dataProvider = "imgData")
	public void imageCountTest(String searchKey, String productName, int imgCount) {
		searchResPage = accPage.doSearch(searchKey);
		productInfoPage = searchResPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImageCount();
		Assert.assertEquals(actImagesCount, imgCount);

	}

	@Test
	public void productDataTest() {
		searchResPage = accPage.doSearch("macbook");
		productInfoPage = searchResPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfo = productInfoPage.getProductInfo();
		softAssert.assertEquals(actProductInfo.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfo.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfo.get("Reward Points"), "800");
		softAssert.assertEquals(actProductInfo.get("Availability"), "In Stock");
		softAssert.assertEquals(actProductInfo.get("productPrice"), "$2,000.00");
		softAssert.assertEquals(actProductInfo.get("exTaxPrice"), "Ex Tax: $2,000.00");
		softAssert.assertAll();

	}

}
