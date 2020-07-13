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

<<<<<<< HEAD:swift-automation/src/test/java/tests/pages/LoginPage.java
	@FindBy(how = How.NAME, using = "login")
=======
	@FindBy(how = How.ID, using = "inputEmail")
>>>>>>> dev-page-analysis:swift-full/src/test/java/tests/pages/LoginPage.java
	private WebElement userNameTextField;

	@FindBy(how = How.ID, using = "inputPassword")
	private WebElement passwordTextField;

<<<<<<< HEAD:swift-automation/src/test/java/tests/pages/LoginPage.java
	@FindBy(how = How.CSS, using = "input[alt=Login]")
=======
	@FindBy(how = How.XPATH, using = "//button[.='Sign in']")
>>>>>>> dev-page-analysis:swift-full/src/test/java/tests/pages/LoginPage.java
	private WebElement loginButton;

	public WebElement loginErrorMsg;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

<<<<<<< HEAD:swift-automation/src/test/java/tests/pages/LoginPage.java
	public MarketNewsPage loginValid(User user) {
=======
	public DashboardPage loginValid(User user) {
>>>>>>> dev-page-analysis:swift-full/src/test/java/tests/pages/LoginPage.java
		log.debug("Logging in...");
		userNameTextField.clear();
		userNameTextField.sendKeys(user.getUsername());
		passwordTextField.clear();
		passwordTextField.sendKeys(user.getPassword());
		loginButton.click();

<<<<<<< HEAD:swift-automation/src/test/java/tests/pages/LoginPage.java
		return PageFactory.initElements(driver, MarketNewsPage.class);
=======
		return PageFactory.initElements(driver, DashboardPage.class);
>>>>>>> dev-page-analysis:swift-full/src/test/java/tests/pages/LoginPage.java

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
<<<<<<< HEAD:swift-automation/src/test/java/tests/pages/LoginPage.java
			return PageFactory.initElements(driver, MarketNewsPage.class);
=======
			return PageFactory.initElements(driver, DashboardPage.class);
>>>>>>> dev-page-analysis:swift-full/src/test/java/tests/pages/LoginPage.java
		}
	}
}
