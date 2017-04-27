package tests.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import tests.constants.ProjectConfig;
import tests.dto.User;

/*
 * Done
 */
public class LoginPage extends BasePage {
	static final Logger log = Logger.getLogger(LoginPage.class);

	@FindBy(how = How.NAME, using = "userName")
	private WebElement userNameTextField;

	@FindBy(how = How.NAME, using = "password")
	private WebElement passwordTextField;

	@FindBy(how = How.NAME, using = "login")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public LoginPage setUsername(User user) {
		userNameTextField.clear();
		userNameTextField.sendKeys(user.getUsername());
	
		return this;
	}
	
	public LoginPage setPassword(User user) {
		passwordTextField.clear();
		passwordTextField.sendKeys(user.getPassword());
	
		return this;
	}
	
	public BasePage clickLogin() {	
		loginButton.click();
		
		// below is quickfix for implicit wait issue with geckodriver (not waiting for page to fully load)
		waitForPageToLoad(driver, 5000);

		if (ProjectConfig.LOGIN_PAGE_TITLE.equals(driver.getTitle())) {
			log.debug("Login not successful...");
			return this;
		}else{
			log.debug("Login successful...");
			return PageFactory.initElements(driver, FindFlightsPage.class);
		}
	}
}