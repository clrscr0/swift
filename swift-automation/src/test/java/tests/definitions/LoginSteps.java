package tests.definitions;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import base.helpers.SeleniumUtility;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import tests.constants.ProjectConfig;
import tests.dto.User;
import tests.pages.LoginPage;

public class LoginSteps {
	private final Logger log = Logger.getLogger(LoginSteps.class);

	protected WebDriver driver;
	protected SeleniumUtility seleniumUtil = new SeleniumUtility();
	protected LoginPage loginPage;

	protected User user;

	@Given("^that the user opens the website in \"([^\"]*)\"$")
	public void that_the_user_opens_the_website(String browser) throws Throwable {
		log.debug("Logging in...");
		driver = seleniumUtil.setUp(browser);
		seleniumUtil.launchBrowser(driver);

		loginPage = PageFactory.initElements(driver, LoginPage.class);
		Assert.assertEquals(ProjectConfig.LOGIN_PAGE_TITLE, driver.getTitle());
	}

	@When("^the user enter the username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void the_user_enter_the_username_and_password(String username, String password) throws Throwable {

		User user = new User("true", username, password);
		loginPage.setUsername(user);
		loginPage.setPassword(user);
	}

	@When("^the user clicks the login button$")
	public void the_user_clicks_the_login_button() throws Throwable {
		loginPage.clickLogin();
	}

	@Then("^the home page should be displayed$")
	public void the_home_page_should_be_displayed() throws Throwable {
		try {
			Assert.assertEquals(ProjectConfig.FIND_FLIGHTS_PAGE_TITLE, driver.getTitle());
		} finally {
			driver.quit();
		}


	}
}