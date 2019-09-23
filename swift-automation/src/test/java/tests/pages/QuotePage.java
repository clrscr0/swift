package tests.pages;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class QuotePage extends NavBar{
	
	static final Logger log = LogManager.getLogger(QuotePage.class);
	
	@FindBy(how = How.NAME, using = "symbol")
	private WebElement symbolTxtBx;
	
	@FindBy(how = How.CSS, using = "input[alt='Get Quote']")
	private WebElement getQuoteBtn;

	public QuotePage(WebDriver driver) {
		super(driver);
	}
	
	public QuoteViewPage requestQuote(String symbol){
		symbolTxtBx.sendKeys(symbol);
		getQuoteBtn.click();
		
		// below is quickfix for implicit wait issue with geckodriver (not waiting for page to fully load)
		waitForPageToLoad(driver, 5000);
		
		return PageFactory.initElements(driver, QuoteViewPage.class);
	}

}
