package tests.dataproviders;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import base.controller.Controller;

public class SwiftDataProvider {
	private static final Logger log = Logger.getLogger(SwiftDataProvider.class);
	private static String SHEETNAME = ""; 

	@DataProvider(name = "datasheet")
	public static Object[][] getExcelData(Method m, ITestContext c) throws Exception {
		SHEETNAME = m.getDeclaringClass().getDeclaredField("SHEETNAME").get(null).toString().trim(); // get

		log.debug("SHEETNAME: " + SHEETNAME);
		log.debug("Class Name: " + m.getDeclaringClass().getName());

		return Controller.getController().getInput(SHEETNAME);
	}
}
