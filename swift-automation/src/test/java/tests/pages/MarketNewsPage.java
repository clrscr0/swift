package tests.pages;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;

public class MarketNewsPage extends NavBar{
	static final Logger log = LogManager.getLogger(MarketNewsPage.class);
	
	public MarketNewsPage(WebDriver driver) {
		super(driver);
	}

}
