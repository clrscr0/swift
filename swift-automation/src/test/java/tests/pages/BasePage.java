package tests.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.constants.BaseConfig;
import base.helpers.SeleniumUtility;

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

		if (driver instanceof FirefoxDriver) {
			pause(milliseconds);
		}
	}

}