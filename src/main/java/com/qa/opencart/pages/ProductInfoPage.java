package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By imgCount = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[1]/li");
	private By productPriceData = By.xpath("(//div[@id=\"content\"]//ul[@class=\"list-unstyled\"])[2]/li");

	private Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {

		String productHeaderValue = eleUtil.getElementText(productHeader);
		System.out.println("Product header is:" + productHeaderValue);
		return productHeaderValue;

	}

	public int getProductImageCount() {
		int imagesCount = eleUtil.waitForElementsToBeVisible(imgCount, AppConstants.SMALL_DEFAULT_TIME_OUT).size();
		System.out.println("Images count is: " + imagesCount);
		return imagesCount;
	}

	public Map<String, String> getProductInfo() {
		//productMap = new HashMap<String, String>();
		productMap=new LinkedHashMap<String, String>();//will print the value in order
		//productMap=new TreeMap<String, String>();//will print in alphabetical order
		productMap.put("productName", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		System.out.println("**************ProductMap********************");
		productMap.forEach((k,v)->System.out.println(k+":"+v));
		return productMap;
	}

	private void getProductMetaData() {
		// Product MetaData
		// Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock
		List<WebElement> metaDataList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaDataList) {
			String text = e.getText();
			String meta[] = text.split(":");
			String key = meta[0].trim();
			String value = meta[1].trim();
			productMap.put(key, value);

		}
	}

	private void getProductPriceData() {
		// $2,000.00
		// Ex Tax: $2,000.00

		List<WebElement> metaPriceList = eleUtil.getElements(productPriceData);
		String productPrice = metaPriceList.get(0).getText().trim();
		String productexTaxPrice = metaPriceList.get(1).getText().trim();
		productMap.put("productPrice", productPrice);
		productMap.put("exTaxPrice", productexTaxPrice);

	}
}
