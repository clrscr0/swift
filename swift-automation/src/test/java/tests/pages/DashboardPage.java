package tests.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends NavBar{
	static final Logger log = LogManager.getLogger(DashboardPage.class);
	
	public DashboardPage(WebDriver driver) {
		super(driver);
	}

}
