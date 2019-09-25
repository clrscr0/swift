package tests.testcases.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ base.listeners.SwiftReporter.class })
public class SearchGoogleTest {
	
	static final Logger log = LogManager.getLogger(SearchGoogleTest.class);

	@Test
	public void test() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.google.com");
		
		if (driver != null) {
			driver.quit();
		}
	}
}
