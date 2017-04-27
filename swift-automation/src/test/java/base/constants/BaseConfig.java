package base.constants;

import base.helpers.PropsLoader;

public class BaseConfig {

	// Execute properties
	public static final String EXECUTION_DESIGN_WITH = PropsLoader.getBaseConfigProperty("execution.design_with");
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

	// Date formats
	public static final String DATETIME_FORMAT_AS_FILENAME = "yyyy-MMdd_HH-mm-ss";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_FORMAT = "MMMM dd, yyyy";
	
	// Other static labels
	public static final String DATASHEET_HEADER_EXECUTE = "execute";
	public static final String DATASHEET_HEADER_EXECUTE_YES = "y";
}
