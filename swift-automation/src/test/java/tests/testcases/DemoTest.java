package tests.testcases;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.models.DataSheet;
import tests.dataproviders.SwiftDataProvider;

@Listeners({ base.listeners.SwiftReporter.class })
public class DemoTest {
	
	static final Logger log = Logger.getLogger(LoginTest.class);
	public static final String MODULE = "testLogin";

	@Test(dataProviderClass = SwiftDataProvider.class, dataProvider = "datasheet", enabled = true)
	public void test(Map<String, String> input) {
		Assert.assertTrue(true);
	}
}
