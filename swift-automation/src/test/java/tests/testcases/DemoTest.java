package tests.testcases;

import java.util.Map;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import tests.dataproviders.SwiftDataProvider;

@Listeners({ base.listeners.SwiftReporter.class })
public class DemoTest {
	
	static final Logger log = LogManager.getLogger(LoginTest.class);
	public static final String MODULE = "testLogin";

	@Test(dataProviderClass = SwiftDataProvider.class, dataProvider = "datasheet", enabled = true)
	public void test(Map<String, String> input) {
		Assert.assertTrue(true);
	}
}
