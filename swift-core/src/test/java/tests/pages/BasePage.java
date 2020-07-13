package tests.pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.helpers.DateTimeHelper;
import base.helpers.SeleniumUtility;
import base.models.Run;

public class BasePage {
	protected WebDriver driver;
	static final Logger log = LogManager.getLogger(BasePage.class);
	private FluentWait<WebDriver> wait = null;
	private JavascriptExecutor jsExecutor = null;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		this.jsExecutor = (JavascriptExecutor) driver;
	}

	public void getSnapshot(String folder, SeleniumUtility seleniumUtil) {
		seleniumUtil.captureScreen(driver, folder);
	}

	public void pause() {
		try {
			Thread.sleep(BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS);
		} catch (InterruptedException e) {
			log.info("Warning: " + e.getMessage());
		}
	}

	public void pause(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.info("Warning: " + e.getMessage());
		}
	}

	public void takeScreenShot(ITestResult testResult)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> c = testResult.getInstance().getClass();
		Run datasheet = (Run) c.getSuperclass().getDeclaredField("datasheet").get(testResult.getInstance());
		String iteration = datasheet.get("row");
		String testName = c.getSimpleName();
		log.info("test class: " + testName);

		String suiteName = datasheet.get("suite") + "_" + Controller.getController().getCurrentSuite().getStarted();

		String filepath = BaseConfig.REPORT_SCREENSHOTS_LOCATION.replace("###", suiteName);

		filepath = filepath + "\\" + testName + "\\" + "ROW_" + iteration + "_"
				+ DateTimeHelper.getCurrentDateTime("kkmmss");

		if (testResult.getStatus() == ITestResult.FAILURE && BaseConfig.REPORT_SCREENSHOTS_CAPTURE_ON_FAIL) {

			filepath = filepath + "_FAILED" + BaseConfig.REPORT_SCREENSHOTS_FORMAT;

			log.info("Filepath: " + filepath);

			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// The below method will save the screen shot in d drive with test
			// method name
			try {
				FileUtils.copyFile(file, new File(filepath));
				log.info("***Placed screen shot in " + filepath + " ***");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (testResult.getStatus() == ITestResult.SUCCESS && BaseConfig.REPORT_SCREENSHOTS_CAPTURE_ON_PASS) {

			filepath = filepath + "_PASSED" + BaseConfig.REPORT_SCREENSHOTS_FORMAT;

			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// The below method will save the screen shot in d drive with
			// test
			// method name
			try {
				FileUtils.copyFile(file, new File(filepath));
				log.info("***Placed screen shot in " + filepath + " ***");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// TODO
		}
	}

	// Wait for JQuery Load
	private void waitForJQueryLoad() {
		// Wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) driver)
				.executeScript("return jQuery.active") == 0);

		// Get JQuery is Ready
		boolean jqueryReady = (Boolean) jsExecutor.executeScript("return jQuery.active==0");

		// Wait JQuery until it is Ready!
		if (!jqueryReady) {
			log.debug("JQuery is NOT Ready!");
			// Wait for jQuery to load
			wait.until(jQueryLoad);
		} else {
			log.debug("JQuery is Ready!");
		}
	}

	// Wait for Angular Load
	private void waitForAngularLoad() {

		String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

		// Wait for ANGULAR to load
		ExpectedCondition<Boolean> angularLoad = driver -> Boolean
				.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());

		// Get Angular is Ready
		boolean angularReady = Boolean.valueOf(jsExecutor.executeScript(angularReadyScript).toString());

		// Wait ANGULAR until it is Ready!
		if (!angularReady) {
			log.debug("ANGULAR is NOT Ready!");
			// Wait for Angular to load
			wait.until(angularLoad);
		} else {
			log.debug("ANGULAR is Ready!");
		}
	}

	// Wait Until JS Ready
	private void waitUntilJSReady() {

		// Wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString().equals("complete");

		// Get JS is Ready
		boolean jsReady = (Boolean) jsExecutor.executeScript("return document.readyState").toString()
				.equals("complete");

		// Wait Javascript until it is Ready!
		if (!jsReady) {
			log.debug("JS in NOT Ready!");
			// Wait for Javascript to load
			wait.until(jsLoad);
		} else {
			log.debug("JS is Ready!");
		}
	}

	// Wait Until JQuery and JS Ready
	private void waitUntilJQueryReady() {

		// First check that JQuery is defined on the page. If it is, then wait AJAX
		Boolean jQueryDefined = (Boolean) jsExecutor.executeScript("return typeof jQuery != 'undefined'");
		if (jQueryDefined == true) {
			// Pre Wait for stability (Optional)
			pause();

			// Wait JQuery Load
			waitForJQueryLoad();

			// Wait JS Load
			waitUntilJSReady();

			// Post Wait for stability (Optional)
			pause();
		} else {
			log.debug("jQuery is not defined on this site!");
		}
	}

	// Wait Until Angular and JS Ready
	private void waitUntilAngularReady() {

		// First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
		Boolean angularUnDefined = (Boolean) jsExecutor.executeScript("return window.angular === undefined");
		if (!angularUnDefined) {
			Boolean angularInjectorUnDefined = (Boolean) jsExecutor
					.executeScript("return angular.element(document).injector() === undefined");
			if (!angularInjectorUnDefined) {
				// Pre Wait for stability (Optional)
				pause();

				// Wait Angular Load
				waitForAngularLoad();

				// Wait JS Load
				waitUntilJSReady();

				// Post Wait for stability (Optional)
				pause();
			} else {
				log.debug("Angular injector is not defined on this site!");
			}
		} else {
			log.debug("Angular is not defined on this site!");
		}
	}

	// Wait Until JQuery Angular and JS is ready
	public void waitJQueryAngular() {
		waitUntilJQueryReady();
		waitUntilAngularReady();
	}
	
	//get all input texts and buttons
	public void analyze() {
		List<WebElement> textboxFields = driver.findElements(By.xpath("//input[@type=\"text\"]"));
		List<HashMap<String,String>> pageElements = new ArrayList<HashMap<String,String>>();
		
		for (WebElement webElement : textboxFields) {
			HashMap<String,String> element = new HashMap<String,String>();
			element.put("type", webElement.getAttribute("type"));
			element.put("id", webElement.getAttribute("id"));
			element.put("name", webElement.getAttribute("name"));
			element.put("value", webElement.getAttribute("value"));
			element.put("placeholder", webElement.getAttribute("placeholder"));
			element.put("alias", webElement.getAttribute("name")+webElement.getAttribute("type")); //be default, the variable name
			element.put("generate", "Y"); //by default, if you want element to be included in generating the class
			pageElements.add(element);
		}
		
		for (HashMap<String, String> mp : pageElements) {
			//System.out.println(mp);
			log.debug(mp);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String json = objectMapper.writeValueAsString(pageElements);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
