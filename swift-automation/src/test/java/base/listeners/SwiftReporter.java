package base.listeners;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import base.constants.BaseConfig;
import base.models.DataSheet;

public class SwiftReporter implements IReporter, ITestListener {

	private final Logger log = LogManager.getLogger(SwiftReporter.class);

	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String arg2) {
		log.info("This is where suite summary is generated.");
		log.info("arg2: " + arg2);

		for (ISuite suite : arg1) {
			
			// Following code gets the suite name
			String suiteName = suite.getName();
			log.info("Results: " + suite.getResults());
			
			// Getting the results for the said suite
			Map<String, ISuiteResult> suiteResults = suite.getResults();

			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();

				log.info("Class: " + tc.getCurrentXmlTest());

				log.info("Passed tests for suite '" + suiteName + "' is:" + tc.getPassedTests().getAllResults().size());
				log.info("Failed tests for suite '" + suiteName + "' is:" + tc.getFailedTests().getAllResults().size());
				log.info("Skipped tests for suite '" + suiteName + "' is:"
						+ tc.getSkippedTests().getAllResults().size());
			}
		}

	}

	@Override
	public void onFinish(ITestContext arg0) {
		log.info("onFinish...");
	}

	@Override
	public void onStart(ITestContext arg0) {
		log.info("onStart...");
		String suiteName = arg0.getCurrentXmlTest().getSuite().getName();
		log.info("Suite: " + suiteName);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		log.info("onTestFailedButWithinSuccessPercentage...");
		displayDatasheet(arg0.getInstance());
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		log.info("onTestFailure...");
		
		displayDatasheet(arg0.getInstance());

		if (BaseConfig.REPORT_SCREENSHOTS_CAPTURE_ON_FAIL) {

		}
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		log.info("onTestSkipped...");

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		log.info("onTestStart...");
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		log.info("onTestSuccess...");
	}

	public void displayDatasheet(Object testClass) {

		Class<?> c = testClass.getClass().getSuperclass();
		try {
			// get the field "h" declared in the test-class.
			// getDeclaredField() works for protected members.
			Field hField = c.getDeclaredField("datasheet");

			// get the name and class of the field h.
			DataSheet datasheet = (DataSheet) hField.get(testClass);
			log.info("data: " + datasheet.get());

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}