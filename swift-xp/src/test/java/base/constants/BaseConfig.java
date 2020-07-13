package base.constants;

import base.helpers.PropsLoader;

public class BaseConfig {

	// Design properties
	public static final String DESIGN_TEMP_FOLDER = PropsLoader.getBaseConfigProperty("design.temp.folder");
	public static final String DESIGN_SUITES = PropsLoader.getBaseConfigProperty("design.suites");
	public static final String DESIGN_DATASHEETS = PropsLoader.getBaseConfigProperty("design.datasheets");
	public static final String DESIGN_SCRIPTS = PropsLoader.getBaseConfigProperty("design.scripts");
	public static final String DESIGN_LOCATORS = PropsLoader.getBaseConfigProperty("design.locators");
	public static final String DESIGN_INPUT_API = PropsLoader.getBaseConfigProperty("design.input.api");
	public static final boolean DESIGN_OUTPUT_RECORD = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("design.output.record"));
	public static final String DESIGN_OUTPUT_API = PropsLoader.getBaseConfigProperty("design.output.api");

	// Execute properties
	public static final String EXECUTION_DESIGN_WITH = PropsLoader.getBaseConfigProperty("execution.design_with");
	public static final boolean EXECUTION_RUN_ON_GRID = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("execution.run_on_grid"));
	public static final int EXECUTION_WAIT_IN_MILLISECONDS = Integer
			.parseInt(PropsLoader.getBaseConfigProperty("execution.wait_in_milliseconds"));
	public static final String EXECUTION_BROWSER_TYPE = PropsLoader
			.getBaseConfigProperty("execution.defaults.browser.type");
	public static final String EXECUTION_BROWSER_VERSION = PropsLoader
			.getBaseConfigProperty("execution.defaults.browser.version");
	public static final String EXECUTION_PLATFORM_TYPE = PropsLoader
			.getBaseConfigProperty("execution.defaults.platform.type");
	public static final String EXECUTION_PLATFORM_VERSION = PropsLoader
			.getBaseConfigProperty("execution.defaults.platform.version");
	public static final String EXECUTION_GRID_HUB_URL = PropsLoader
			.getBaseConfigProperty("execution.defaults.grid.hub.url");

	// Report properties
	public static final boolean REPORT_SCREENSHOTS_CAPTURE_ON_PASS = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.screenshots.capture_on_pass"));
	public static final boolean REPORT_SCREENSHOTS_CAPTURE_ON_FAIL = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.screenshots.capture_on_fail"));
	public static final String REPORT_SCREENSHOTS_LOCATION = PropsLoader
			.getBaseConfigProperty("report.screenshots.location");
	public static final String REPORT_SCREENSHOTS_FORMAT = PropsLoader
			.getBaseConfigProperty("report.screenshots.format");
	public static final String REPORT_RESULTS_FORMAT = PropsLoader
			.getBaseConfigProperty("report.results.format");
	public static final boolean REPORT_RESULTS_SUMMARY = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.results.summary"));
	public static final String REPORT_RESULTS_SUMMARY_LOCATION = PropsLoader.getBaseConfigProperty("report.results.summary.location");
	public static final boolean REPORT_RESULTS_DETAILED = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.results.detailed"));
	public static final boolean REPORT_RESULTS_HISTORY = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.results.history"));
	public static final String REPORT_RESULTS_HISTORY_LOCATION = PropsLoader.getBaseConfigProperty("report.results.history.location");
	public static final String REPORT_EMAIL_FROM = PropsLoader.getBaseConfigProperty("report.email.from");
	public static final String REPORT_EMAIL_PASSWORD = "Tmp@6swi";
	public static final String REPORT_EMAIL_SMTP = PropsLoader.getBaseConfigProperty("report.email.smtp");
	public static final boolean REPORT_ON_COMPLETE_SEND_EMAIL = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.on-complete.send_email"));
	public static final String REPORT_ON_COMPLETE_SEND_TO = PropsLoader
			.getBaseConfigProperty("report.on-complete.send_to");
	public static final String REPORT_ON_COMPLETE_SUBJECT = PropsLoader
			.getBaseConfigProperty("report.on-complete.subject");
	public static final boolean REPORT_ON_FAIL_SEND_EMAIL = Boolean
			.parseBoolean(PropsLoader.getBaseConfigProperty("report.on-fail.send_email"));
	public static final String REPORT_ON_FAIL_SEND_TO = PropsLoader.getBaseConfigProperty("report.on-fail.send_to");

	// Date formats
	public static final String DATETIME_FORMAT_AS_FILENAME = "yyyy-MMdd_HH-mm-ss";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_FORMAT = "MMMM dd, yyyy";
	
	// Other static labels
	public static final String DATASHEET_HEADER_EXECUTE = "execute";
	public static final String DATASHEET_HEADER_EXECUTE_YES = "y";
}
