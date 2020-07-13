package tests.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends NavBar{
	static final Logger log = Logger.getLogger(DashboardPage.class);
	
	public DashboardPage(WebDriver driver) {
		super(driver);
	}

}
