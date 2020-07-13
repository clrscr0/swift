package tests.testcases;

import static org.testng.AssertJUnit.assertTrue;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.testrunner.SwiftTestRunner;
import tests.dataproviders.SwiftDataProvider;
import tests.dto.User;
import tests.pages.BasePage;
import tests.pages.LoginPage;
import tests.pages.MarketNewsPage;

public class LoginTest extends SwiftTestRunner {
	static final Logger log = Logger.getLogger(LoginTest.class);
	public static final String MODULE = "testLogin";
	public String testField = "Hello";

	@Test(dataProviderClass = SwiftDataProvider.class, dataProvider = "datasheet", enabled = true, groups = {"login"})
	public void testLogin(Map<String, String> input) {
		datasheet.merge(input, MODULE);
	
		User user = new User(input.get("isValid"), input.get("username"), input.get("password"));

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);

		BasePage unknownPage = loginPage.login(user);

		String title = driver.getTitle();

		log.info("Page Title: " + title);

		if (unknownPage instanceof MarketNewsPage) {
			MarketNewsPage marketNewsPage = (MarketNewsPage) unknownPage;
			marketNewsPage.clickLogout();
			assertTrue("Verify user is valid.", user.isValid() == true);
		} else {			
			assertTrue("Verify user is NOT valid.", user.isValid() == false);
		}
		
	}

}