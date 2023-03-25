package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtil {

	private WebDriver driver;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	

	public String getElementText(By locator) {// capturating text of single webelement
		String eleText = getElement(locator).getText();
		System.out.println(eleText);
		return eleText;

	}

	public int getElementsTextCount(By locator) {
		return getElementTextList(locator).size();
	}

	public List<String> getElementTextList(By locator) {// capturing text of non blank elements
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public String doGetAttributeValue(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

//	public void doSendKeys(String locatorType, String selector, String value) {
//
//		By locator = getBy(locatorType, selector);
//
//		getElement(locator).sendKeys(value);
//
//	}

	public void doSendKeys(By locator, String value) {

		getElement(locator).sendKeys(value);
	}

	public int getPageLinksCount(By locator) {

		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {

		return driver.findElements(locator);
	}

	public WebElement getElement(By locator) {

		return driver.findElement(locator);
	}

	public void performSearch(By search, String searchKey, By suggListLocator, String suggName)
			throws InterruptedException {

		doSendKeys(search, searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(suggListLocator);
		System.out.println("Total Suggestions:" + suggList.size());

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}

	}

	// ************************element displayed utility*********//

	public Boolean doIsDisplayed(By locator) {

		return getElement(locator).isDisplayed();
	}

	public int eleCount(By locator) {

		int count = getElements(locator).size();
		return count;
	}

	public Boolean singleElementExist(By locator) {
		if (eleCount(locator) == 1) {
			return true;

		}
		return false;
	}

	public Boolean multiElementExist(By locator) {
		if (eleCount(locator) > 1) {
			return true;
		}
		return false;
	}

	// **********************SelectDropDownOptionsList****************************
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);

	}

	public void doSelectByVisibleText(By locator, String Text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(Text);

	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownCount(By locator) {

		Select select = new Select(getElement(locator));
		return select.getOptions().size();

	}

	// to find the value of all the values in dropDownList

	public List<String> getDropDownValueList(By locator) {
		Select select = new Select(getElement(locator));
		List<String> optionsValList = new ArrayList<String>();

		List<WebElement> optionsEleList = select.getOptions();
		for (WebElement e : optionsEleList) {
			String text = e.getText();
			optionsValList.add(text);

		}

		return optionsValList;
	}

	/**
	 * Wait for Title utility
	 * 
	 * @param titleFractionValue,titleValue
	 * @param timeOut
	 * @return
	 */

	public String waitForTitleContains(String titleFractionValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFractionValue))) {
			return driver.getTitle();
		} else {
			System.out.println("title is not found..");
			return null;
		}
	}

	public String waitForTitleToBe(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleValue))) {
			return driver.getTitle();
		} else {
			System.out.println("title is not found..");
			return null;
		}
	}

	/**
	 * Wait for URL
	 * 
	 * @param timeOut
	 * @param urlFractionValue
	 * @param urlValue
	 * @return
	 */

	public String waitForUrl(int timeOut, String urlFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFractionValue))) {
			return driver.getCurrentUrl();
		} else {
			System.out.println("URl is not found:");
			return null;
		}
	}

	public String waitForUrlToBe(int timeOut, String urlValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			return driver.getCurrentUrl();
		} else {
			System.out.println("URl is not found:");
			return null;
		}
	}

// Explicit Wait utility

	public void doSendKeysWithWait(By locator, int timeout, String value) {
		WebElement ele=waitForElementPresence(locator, timeout);
		ele.clear();
		ele.sendKeys(value);

	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param Locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForVisibleElement(By Locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(getElement(locator))).click();
	}
	
	public void printAllElementsText(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
		}
	}

	public List<String> getAllElementsTextList(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}
	
	
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

}



