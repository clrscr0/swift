package tests.constants;

import base.helpers.PropsLoader;

public class ProjectConfig {
	public static final String BASE_URL = PropsLoader.getProjectConfigProperty("base_url");
	
	public static final String LOGIN_PAGE_TITLE = PropsLoader
			.getProjectConfigProperty("login_page_title");

	public static final Object FIND_FLIGHTS_PAGE_TITLE = PropsLoader.getProjectConfigProperty("find_flights_page_title");
}
