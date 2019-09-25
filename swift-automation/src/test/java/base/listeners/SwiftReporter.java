package base.listeners;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.enums.Status;
import base.helpers.FileHelper;
import base.helpers.Mailer;
import base.helpers.ZipUtil;

public class SwiftReporter implements IReporter, ITestListener {

	private final Logger log = Logger.getLogger(SwiftReporter.class);

	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String arg2) {
		log.debug("Generating Report...");

		if (BaseConfig.REPORT_ON_COMPLETE_SEND_EMAIL) {

			// zip file before sending
			String sourceDirPath = BaseConfig.REPORT_RESULTS_SUMMARY_LOCATION
					+ Controller.getController().getSuites().get(0).getStarted();
			FileHelper.createMissingFolderRecursively(BaseConfig.DESIGN_TEMP_FOLDER);
			String zipFilePath = BaseConfig.DESIGN_TEMP_FOLDER
					+ Controller.getController().getSuites().get(0).getStarted() + ".zip";
			log.debug("Path to Zip: " + sourceDirPath);

			try {
				ZipUtil.pack(sourceDirPath, zipFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String[] mailTo = BaseConfig.REPORT_ON_COMPLETE_SEND_TO.split(";");
			String[] attachFiles = {zipFilePath};
			
			String body = "This email is automatically generated.";
			
			for (String email : mailTo) {
				try {
					Mailer.sendMail(email.trim(), BaseConfig.REPORT_ON_COMPLETE_SUBJECT, body, attachFiles);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			

		}

	}

	@Override
	public void onFinish(ITestContext context) {
		log.debug("On Finish");
	}

	@Override
	public void onStart(ITestContext context) {
		log.debug("On Start");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.debug("On Test Failure But Within Success Percentage");
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		log.debug("On Test Failure");

		Controller.getController().getCurrentSuite().setStatus(Status.FAILED);
		Controller.getController().getCurrentSuite().getCurrentTest().setStatus(Status.FAILED);
		Controller.getController().getCurrentSuite().getCurrentTest().getCurrentRun().setStatus(Status.FAILED);
		Controller.getController().getCurrentSuite().getCurrentTest().getCurrentRun()
				.setError(arg0.getThrowable().toString());

		log.debug("Error:" + arg0.getThrowable());
		
		if (BaseConfig.REPORT_SCREENSHOTS_CAPTURE_ON_FAIL) {

		}
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		log.debug("On Test Skipped");

		if (Controller.getController().getCurrentSuite().getStatus() != Status.FAILED) {
			Controller.getController().getCurrentSuite().setStatus(Status.SKIPPED);
			Controller.getController().getCurrentSuite().getCurrentTest().setStatus(Status.SKIPPED);
		}
		Controller.getController().getCurrentSuite().getCurrentTest().getCurrentRun().setStatus(Status.SKIPPED);

	}

	@Override
	public void onTestStart(ITestResult result) {
		log.debug("On Test Start");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.debug("On Test Success");
	}

}