package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NavBar extends BasePage {
	
	@FindBy(how = How.XPATH, using = "//a[@href='logout.asp']")
	private WebElement logoutMenu;

	public NavBar(WebDriver driver){
		super(driver);
	}
	
	/* Methods */
	public LoginPage clickLogout() {
		logoutMenu.click();
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
}
