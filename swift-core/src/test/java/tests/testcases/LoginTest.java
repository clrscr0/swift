package tests.testcases;

import static org.testng.AssertJUnit.assertTrue;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.testrunner.SwiftTestRunner;
import tests.dataproviders.SwiftDataProvider;
import tests.dto.User;
import tests.pages.BasePage;
import tests.pages.LoginPage;
import tests.pages.DashboardPage;

public class LoginTest extends SwiftTestRunner {
	static final Logger log = LogManager.getLogger(LoginTest.class);
	public static final String SHEETNAME = "testLogin";

	@Test(dataProviderClass = SwiftDataProvider.class, dataProvider = "datasheet", enabled = true, groups = {"login"})
	public void testLogin(Map<String, String> input) throws Exception {		
		test.startRun(input);
		
		User user = new User(input.get("isValid"), input.get("username"), input.get("password"));
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		BasePage unknownPage = loginPage.login(user);
		String title = driver.getTitle();
		
		log.info("Page Title: " + title);

		if (unknownPage instanceof DashboardPage) {
			DashboardPage dashboardPage = (DashboardPage) unknownPage;
			dashboardPage.clickLogout();
			assertTrue("Verify user is valid.", user.isValid() == true);
		} else {			
			assertTrue("Verify user is NOT valid.", user.isValid() == false);
		}
		
	}

}