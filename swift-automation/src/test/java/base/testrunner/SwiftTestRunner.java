package base.testrunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.enums.Status;
import base.helpers.SeleniumUtility;
import base.models.Test;

@Listeners({ base.listeners.SwiftReporter.class })
public class SwiftTestRunner{
	private final Logger log = LogManager.getLogger(SwiftTestRunner.class);
	
	protected WebDriver driver;
	protected SeleniumUtility seleniumUtil = new SeleniumUtility();	
	protected Test test = null;

	@BeforeSuite
	public void beforeSuite(ITestContext context){
		log.info("Initializing suite...");
		Controller.getController().startSuite();
		log.info("Started: " + Controller.getController().getCurrentSuite().getStarted());
		String suiteName = context.getCurrentXmlTest().getSuite().getName();
		log.debug("Suite: " + suiteName);
		Controller.getController().getCurrentSuite().setName(suiteName);
	}
	
	@BeforeClass
	public void beforeClass(){
		String className = this.getClass().getName();
		
		log.debug("Test: " + className);
		test.setName(className);
	}
	
	@Parameters({ "browser", "ip", "platform", "version" })
	@BeforeTest
	public void beforeTest(@Optional("") String browser, @Optional("") String ip, @Optional("") String platform,
			@Optional("") String version, ITestContext context) throws MalformedURLException{
		test = Controller.getController().getCurrentSuite().startTest();
		
		driver = seleniumUtil.setup(browser, ip, version, version);
		driver.manage().timeouts()
		.implicitlyWait(BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
		seleniumUtil.launchBrowser(driver);
	}
	
	@BeforeMethod
	public void beforeMethod(ITestContext context) throws Exception{
		//TODO
	}
	
	@AfterMethod
	public void afterMethod(ITestResult testResult) throws Exception{
		String runName = testResult.getTestContext().getCurrentXmlTest().getClass().getSimpleName();
		test.getCurrentRun().setName(runName);
		
		Status status = Status.get(testResult.getStatus());
		test.getCurrentRun().set("status",status.name());
		Controller.getController().insertRunRecord();
		
	}

	@AfterTest
	public void close(ITestContext tc) throws ParseException {
		
		Test test = Controller.getController().getCurrentSuite().getCurrentTest();
		
		test.setNumFailed(tc.getFailedTests().getAllResults().size());
		test.setNumPassed(tc.getPassedTests().getAllResults().size());
		test.setNumSkipped(tc.getSkippedTests().getAllResults().size());
		
		log.debug("Passed tests for test is:" + test.getNumPassed());				
		log.debug("Failed tests for test is:" + test.getNumFailed());
		log.debug("Skipped tests for test is:" + test.getNumSkipped());
		
		
		log.info("Closing driver...");
		seleniumUtil.tearDown(driver);
	}
	
	@AfterSuite
	public void afterSuite(ITestContext tc) throws AddressException, MessagingException, EncryptedDocumentException, InvalidFormatException, IOException{
		//TODO - craete a suite file execution		
		
		Controller.getController().insertSuiteRecord();

	}
	
}