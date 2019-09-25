package tests.testcases.demo;

import java.util.Map;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import base.testrunner.SwiftTestRunner;
import tests.dataproviders.SwiftDataProvider;
import tests.dto.User;
import tests.pages.LoginPage;
import tests.pages.MarketNewsPage;
import tests.pages.QuotePage;
import tests.pages.QuoteViewPage;

public class QuoteTest extends SwiftTestRunner {
	
	static final Logger log = LogManager.getLogger(QuoteTest.class);
	public static final String MODULE = "testQuote";
	
	@Test(dataProviderClass = SwiftDataProvider.class, dataProvider = "datasheet", enabled = true, groups = {"quote"})
	public void testQuote(Map<String, String> input) {
		datasheet.merge(input, MODULE);
		
		User user = new User("true", "admin", "admin");
		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);

		MarketNewsPage marketNewsPage = (MarketNewsPage) loginPage.login(user);
		
		QuotePage quotePage = marketNewsPage.clickQuote();
		QuoteViewPage quoteViewPage = quotePage.requestQuote(input.get("symbol"));
		
		quoteViewPage.clickLogout();

	}

}
