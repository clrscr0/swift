package base.testrunner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.helpers.SeleniumUtility;
import base.models.DataSheet;
import base.models.Status;
import tests.pages.BasePage;

@Listeners({ base.listeners.SwiftReporter.class })
public class SwiftTestRunner{
	public WebDriver driver;
	protected SoftAssert softAssert = new SoftAssert();
	protected SeleniumUtility seleniumUtil = new SeleniumUtility();
	private final Logger log = Logger.getLogger(SwiftTestRunner.class);	
	public DataSheet datasheet;
	
	@Parameters({ "browser", "ip", "platform", "version" })
	@BeforeTest(alwaysRun = true)
	public void init(@Optional("") String browser, @Optional("") String ip, @Optional("") String platform,
			@Optional("") String version) throws Exception {
		log.info("Initializing driver...");
		log.info("Date Started: " + Controller.getStarted());
		datasheet = new DataSheet();
		driver = seleniumUtil.setUp(browser, ip, version, version);
		driver.manage().timeouts()
		.implicitlyWait(BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
		seleniumUtil.launchBrowser(driver);
	}
	
	@AfterTest(alwaysRun = true)
	public void close() throws ParseException {
		log.info("Closing driver...");
		
		seleniumUtil.tearDown(driver);
		seleniumUtil.verifyErrors();
	}
	
	@BeforeMethod
	public void beforeMethod(ITestContext context){
		log.info("CONTEXT: " + context.getStartDate());
	}

	@AfterMethod
	public void afterMethod(ITestResult testResult) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, Exception {
		Status status = Status.get(testResult.getStatus());
		datasheet.set("status",status.name());
		
		//log.info("Start Start Millis: " + new Date(testResult.getStartMillis()));
		
		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		Map<String,String> datamap = datasheet.get();
		datamap.put("session", sessionId);
		
		String suiteName = testResult.getTestContext().getCurrentXmlTest().getSuite().getName();
		log.info("Suite: " + suiteName);
		datamap.put("suite", suiteName);
		
		BasePage basePage = PageFactory.initElements(driver, BasePage.class);
		basePage.takeScreenShot(testResult);
				
		recordTestRun(testResult);
	}

	@AfterSuite
	public void afterSuite() throws AddressException, MessagingException{
		String[] attachedFiles = {""}; //zip report/regression folder
		String mailTo = ""; //get in BaseConfig
		String subject = ""; //get in BaseConfig
		String body = ""; //write summary
		//Mailer.sendMail(mailTo, subject, body, attachedFiles);
	}

	private void recordTestRun(ITestResult testResult) throws Exception{
		Object testClass = testResult.getInstance();
		
		Class<?> c = testClass.getClass().getSuperclass();
		try {
			// get the field "h" declared in the test-class.
			// getDeclaredField() works for protected members.
			Field hField = c.getDeclaredField("datasheet");
			DataSheet datasheet = (DataSheet) hField.get(testClass);
			
			/*TestResultDaoImpl dao = new TestResultDaoImpl();
			dao.insert(datasheet);*/
			Controller controller = Controller.getController();
			controller.insertResultHistory(datasheet);
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}