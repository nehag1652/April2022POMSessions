package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author Neha
 *
 */
public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> t1Driver = new ThreadLocal<WebDriver>();

	/**
	 * this method is used to initialize the driver on the basis of given
	 * browserName.
	 * 
	 * @param prop
	 * @return driver
	 */
	public WebDriver initDriver(Properties prop) {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		String browserName = prop.getProperty("browser");
		optionsManager = new OptionsManager(prop);
		System.out.println("browser name is:" + browserName);
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsManager.getChromeOptions());

			t1Driver.set(new ChromeDriver(optionsManager.getChromeOptions()));

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());

			t1Driver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			// driver = new EdgeDriver();

			t1Driver.set(new EdgeDriver());

		} else {
			System.out.println("Please pass the right browser:" + browserName);
		}

		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();

	}

	public static synchronized WebDriver getDriver() {

		return t1Driver.get();
	}

	/**
	 * this returns properties reference with all config properties
	 * 
	 * @return this returns properties reference
	 */
	public Properties initProp() {
		prop = new Properties();

		FileInputStream ip = null;
		// command line args--> maven
		// mvn clean install -Denv="qa"(if enviornment is passed)
		// mvn clean install(if no enviornment is passed)

		String envName = System.getProperty("env");
		System.out.println("Running test cases on enviornment:" + envName);

		if (envName == null) {
			System.out.println("No env is given...hence running it on QA enviornment");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		else {
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/UAT.config.properties");
					break;
				default:
					System.out.println("Please pass the right enviornment:+" + envName);
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take screenshot
	 */

	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path =System.getProperty("user.dir")+"./screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
