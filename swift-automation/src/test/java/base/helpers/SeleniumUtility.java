package base.helpers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import base.constants.BaseConfig;
import tests.constants.ProjectConfig;

public class SeleniumUtility {
	private static DesiredCapabilities capability = null;
	private static FirefoxOptions firefoxOptions = null;
	private static ChromeOptions chromeOptions = null;
	private static EdgeOptions edgeOptions = null;
	private static InternetExplorerOptions ieOptions = null;
	private static final Logger log = Logger.getLogger(SeleniumUtility.class);
	
	public WebDriver setup(String browser) throws MalformedURLException {
		return setup(browser, "", "", "");
	}

	public WebDriver setup(String browser, String ip, String platform, String browserVersion)
			throws MalformedURLException {
		WebDriver driver = null;
		log.debug("Setting up webdriver...");
		
		if (browser.isEmpty())
			browser = BaseConfig.EXECUTION_BROWSER_TYPE;
		Browser _browser = Browser.parse(browser);

		if (platform.isEmpty())
			platform = BaseConfig.EXECUTION_PLATFORM_TYPE;
		Platform _platform = Platform.extractFromSysProperty(platform);

		if (browserVersion.isEmpty())
			browserVersion = BaseConfig.EXECUTION_BROWSER_VERSION;

		setCapabilities(_browser, _platform, browserVersion);

		driver = getWebDriver(browser);
		
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
			//driver.close();
			driver.quit();
		}
	}

	public void setCapabilities(Browser browserType, Platform platform, String version) {
		switch (browserType) {
		case FIREFOX:
			//capability = DesiredCapabilities.firefox();
			firefoxOptions = new FirefoxOptions();
		    firefoxOptions.setCapability("marionette", true);
			//capability.setCapability("marionette", true);
			break;
		case EDGE:
			edgeOptions = new EdgeOptions();
			edgeOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			edgeOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			break;
		case IEXPLORE:
			/*capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(CapabilityType.BROWSER_NAME, "IE");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);*/
			
			ieOptions = new InternetExplorerOptions();
			ieOptions.setCapability(CapabilityType.BROWSER_NAME, "IE");
			ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			break;
		case CHROME:
			//capability = DesiredCapabilities.chrome();
			chromeOptions = new ChromeOptions();
			//chromeOptions.setCapability("capability_name", "capability_value");
			break;
		case SAFARI:
			capability = DesiredCapabilities.safari();
			break;
		default:
			throw new RuntimeException("Browser type unsupported");
		}
		/*capability.setBrowserName(browserType.value);
		if(platform!=null) capability.setPlatform(platform);
		if(version!=null) capability.setVersion(version);*/
	}

	public WebDriver getWebDriver(String browser) {
		if (browser.isEmpty())
			browser = BaseConfig.EXECUTION_BROWSER_TYPE;
		Browser _browser = Browser.parse(browser);
		switch (_browser) {
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
			return new FirefoxDriver(firefoxOptions);
		case IEXPLORE:
			System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
			return new InternetExplorerDriver(ieOptions);
		case EDGE:
			System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/MicrosoftWebDriver.exe");
			return new EdgeDriver(edgeOptions);
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			return new ChromeDriver(chromeOptions);
		case HTMLUNIT:
			return new HtmlUnitDriver(capability);
		/*case OPERA:
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/operadriver.exe");
			return new OperaDriver(capability);*/
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
	
	public WebElement findElementInFrames(WebDriver driver, By locator) {
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		
		for (WebElement frame : frames) {
			driver.switchTo().frame(frame);
			
			if(isElementPresent(driver,locator)) return driver.findElement(locator);
			
			List<WebElement> innerFrames = driver.findElements(By.tagName("iframe"));
			
			for (WebElement innerFrame : innerFrames) {
				driver.switchTo().frame(innerFrame);
				
				if(isElementPresent(driver,locator)) return driver.findElement(locator);
				driver.switchTo().parentFrame();
			}
			
			driver.switchTo().parentFrame();
		}
	
		return null;
	}

	public boolean isElementPresent(WebDriver driver, By locator) {
		try {
			driver.findElement(locator);
			log.debug(locator.toString() + " is present.");
			return true;
		} catch (NoSuchElementException e) {
			log.debug(e.getMessage());
			return false;
		}
	}
}
