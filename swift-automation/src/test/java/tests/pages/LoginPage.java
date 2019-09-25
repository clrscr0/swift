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

	@FindBy(how = How.ID, using = "inputEmail")
	private WebElement userNameTextField;

	@FindBy(how = How.ID, using = "inputPassword")
	private WebElement passwordTextField;

	@FindBy(how = How.XPATH, using = "//button[.='Sign in']")
	private WebElement loginButton;

	public WebElement loginErrorMsg;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public DashboardPage loginValid(User user) {
		log.debug("Logging in...");
		userNameTextField.clear();
		userNameTextField.sendKeys(user.getUsername());
		passwordTextField.clear();
		passwordTextField.sendKeys(user.getPassword());
		loginButton.click();

		return PageFactory.initElements(driver, DashboardPage.class);

	}
	
	public BasePage login(User user) {
		log.debug("Logging in...");
		userNameTextField.clear();
		userNameTextField.sendKeys(user.getUsername());
		passwordTextField.clear();
		passwordTextField.sendKeys(user.getPassword());
		loginButton.click();

		if (ProjectConfig.LOGIN_PAGE_TITLE.equals(driver.getTitle())) {
			return this;
		}else{
			return PageFactory.initElements(driver, DashboardPage.class);
		}
	}
}
