package tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NavBar extends BasePage {
	
	/* Web Elements */
	@FindBy(how = How.XPATH, using = "//a[@href='quote.asp']")
	private WebElement quoteMenu;
	
	@FindBy(how = How.LINK_TEXT, using = "market.asp")
	private WebElement marketNewsMenu;
	
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
	
	public MarketNewsPage clickMarketNews() {
		marketNewsMenu.click();
		return PageFactory.initElements(driver, MarketNewsPage.class);
	}
	
	public QuotePage clickQuote() {
		quoteMenu.click();		
		return PageFactory.initElements(driver, QuotePage.class);
	}

}
