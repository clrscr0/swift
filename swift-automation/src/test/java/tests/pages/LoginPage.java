package tests.pages;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
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
	static final Logger log = LogManager.getLogger(LoginPage.class);

	@FindBy(how = How.NAME, using = "login")
	private WebElement userNameTextField;

	@FindBy(how = How.NAME, using = "password")
	private WebElement passwordTextField;

	@FindBy(how = How.CSS, using = "input[alt=Login]")
	private WebElement loginButton;

	public WebElement loginErrorMsg;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public MarketNewsPage loginValid(User user) {
		log.debug("Logging in...");
		userNameTextField.clear();
		userNameTextField.sendKeys(user.getUsername());
		passwordTextField.clear();
		passwordTextField.sendKeys(user.getPassword());
		loginButton.click();

		return PageFactory.initElements(driver, MarketNewsPage.class);

	}
	
	public BasePage login(User user) {
		log.debug("Logging in...");
		userNameTextField.clear();
		userNameTextField.sendKeys(user.getUsername());
		passwordTextField.clear();
		passwordTextField.sendKeys(user.getPassword());
		loginButton.click();
		
		// below is quickfix for implicit wait issue with geckodriver (not waiting for page to fully load)
		waitForPageToLoad(driver, 5000);

		if (ProjectConfig.LOGIN_PAGE_TITLE.equals(driver.getTitle())) {
			return this;
		}else{
			return PageFactory.initElements(driver, MarketNewsPage.class);
		}
	}
}
