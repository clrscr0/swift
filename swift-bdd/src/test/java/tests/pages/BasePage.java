package tests.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.helpers.DateTimeHelper;
import base.helpers.SeleniumUtility;
import base.models.DataSheet;

public class BasePage {
	protected WebDriver driver;
	static final Logger log = Logger.getLogger(BasePage.class);

	public BasePage(WebDriver driver) {
		this.driver = driver;
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

	public void waitForPageToLoad(WebDriver driver, long milliseconds) {
		new WebDriverWait(driver, BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS)
				.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
						.executeScript("return document.readyState").equals("complete"));

		log.info("Browser: " + BaseConfig.EXECUTION_BROWSER_TYPE);

		if (SeleniumUtility.Browser.FIREFOX.value.equalsIgnoreCase(BaseConfig.EXECUTION_BROWSER_TYPE)) {
			pause(milliseconds);
		}
	}

	public void takeScreenShot(ITestResult testResult) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> c = testResult.getInstance().getClass();
		DataSheet datasheet = (DataSheet) c.getSuperclass().getDeclaredField("datasheet").get(testResult.getInstance());
		String iteration = datasheet.get("row");
		String testName = c.getSimpleName();
		log.info("test class: " + testName);
		
		String suiteName = datasheet.get("suite") + "_" + Controller.getStarted();
		//String suiteName = datasheet.get("suite") + "_" + DateTimeHelper.getCurrentDateTime("Y-M-d_kkmmss");
		
		String filepath = BaseConfig.REPORT_SCREENSHOTS_LOCATION.replace("###", suiteName);

		filepath = filepath + "\\" + testName + "\\" + "ROW_" + iteration + "_" + DateTimeHelper.getCurrentDateTime("kkmmss");

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
}
