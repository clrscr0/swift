package tests.constants;

import org.apache.commons.io.FilenameUtils;

import base.helpers.PropsLoader;

public class ProjectConfig {
	public static final String BASE_URL = PropsLoader.getProjectConfigProperty("base_url");
	public static final String DATASHEET_FILENAME = PropsLoader.getProjectConfigProperty("datasheet_filename");
	public static final String DATASHEET_EXTENSION = FilenameUtils.getExtension(DATASHEET_FILENAME);
	
	public static final String DASHBOARD_PAGE_TITLE = PropsLoader
			.getProjectConfigProperty("dashboard_page_title");
	public static final String LOGIN_PAGE_TITLE = PropsLoader
			.getProjectConfigProperty("login_page_title");
	public static final String LOGIN_ERROR_MSG = PropsLoader
			.getProjectConfigProperty("login_error_msg");

}
