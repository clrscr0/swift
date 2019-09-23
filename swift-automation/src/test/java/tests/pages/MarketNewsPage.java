package tests.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class MarketNewsPage extends NavBar{
	static final Logger log = Logger.getLogger(MarketNewsPage.class);
	
	public MarketNewsPage(WebDriver driver) {
		super(driver);
	}

}
