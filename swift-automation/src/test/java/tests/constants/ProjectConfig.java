package tests.constants;

import base.helpers.PropsLoader;

public class ProjectConfig {
	public static final String BASE_URL = PropsLoader.getProjectConfigProperty("base_url");
	public static final String DATASHEET_FILENAME = PropsLoader.getProjectConfigProperty("datasheet_filename");
	
	public static final String MARKET_NEWS_PAGE_TITLE = PropsLoader
			.getProjectConfigProperty("market_news_title");
	public static final String LOGIN_PAGE_TITLE = PropsLoader
			.getProjectConfigProperty("login_page_title");
	public static final String LOGIN_ERROR_MSG = PropsLoader
			.getProjectConfigProperty("login_error_msg");
}
