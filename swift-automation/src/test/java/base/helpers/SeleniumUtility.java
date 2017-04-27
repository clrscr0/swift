package base.helpers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import base.constants.BaseConfig;
import tests.constants.ProjectConfig;

public class SeleniumUtility {
	private static final Logger log = Logger.getLogger(SeleniumUtility.class);

	public WebDriver setUp(String browser)
			throws MalformedURLException {
		WebDriver driver = null;
		log.debug("Setting up webdriver...");
		
		if (browser.isEmpty())
			browser = BaseConfig.EXECUTION_BROWSER_TYPE;
		
		driver = getWebDriver(browser);
		driver.manage().timeouts().implicitlyWait(BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
		
		return driver;
	}

	public WebDriver launchBrowser(WebDriver driver) {
		log.debug("Opening url...");
		driver.get(ProjectConfig.BASE_URL);
		return driver;
	}

	public void tearDown(WebDriver driver) {
		log.debug("Closing up webdriver...");
		if (driver != null) {
			driver.quit();
		}
	}

	public WebDriver getWebDriver(String browser) {
		if (browser.isEmpty())
			browser = BaseConfig.EXECUTION_BROWSER_TYPE;
		Browser _browser = Browser.parse(browser);
		switch (_browser) {
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
			return new FirefoxDriver();
		case IEXPLORE:
			System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
			return new InternetExplorerDriver();
		case EDGE:
			System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/MicrosoftWebDriver.exe");
			return new EdgeDriver();
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			return new ChromeDriver();
		default:
			throw new RuntimeException("Browser type unsupported");
		}
	}

	public WebElement getRadioButton(String value, List<WebElement> radioButtonGrp) {
		for (WebElement radioButton : radioButtonGrp) {
			if (radioButton.getAttribute("value").equalsIgnoreCase(value)) {
				return radioButton;
			}
		}
		throw new NoSuchElementException("Cannot locate radion button: " + value);
	}

	public WebElement getRadioButtonByIndex(int indexNumber, List<WebElement> radioButtonGrp) {
		WebElement radioButton = radioButtonGrp.get(indexNumber);
		if (radioButton != null) {
			return radioButton;
		} else {
			throw new NoSuchElementException("Cannot locate radion button: " + indexNumber);
		}
	}

	public boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isTextPresent(WebDriver driver, String text) {
		String findBy = PropsLoader.getBaseConfigProperty("body_findHow");
		String findValue = PropsLoader.getBaseConfigProperty("body_findValue");
		By byBody = getByObject(findBy, findValue);
		if (driver.findElement(byBody).getText().matches("^[\\s\\S]*" + text + " [\\s\\S]*$")) {
			return true;
		} else {
			return false;
		}
	}

	public By getByObject(String findHow, String findValue) {
		FindByType findByType = FindByType.valueOf(findHow);
		switch (findByType) {
		case ID:
			return By.id(findValue);
		case NAME:
			return By.name(findValue);
		case CSS:
			return By.cssSelector(findValue);
		case XPATH:
			return By.xpath(findValue);
		case CLASS:
			return By.className(findValue);
		default:
			throw new RuntimeException("By method is not supported.");
		}
	}

	public void captureScreen(WebDriver driver, String filePath) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static enum FindByType {
		ID, NAME, CSS, XPATH, CLASS
	}

	public static enum Browser {
		FIREFOX("firefox"), IEXPLORE("internet explorer"), CHROME("chrome"), HTMLUNIT("htmlunit"), SAFARI("safari"), EDGE("edge"), OPERA("opera");

		public String value;

		private Browser(String value) {
			this.value = value;
		}

		public static Browser parse(String browser) {
			return Browser.valueOf(browser.trim().toUpperCase());
		}
	}
}