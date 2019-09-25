package base.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropsLoader {
	static final Logger log = Logger.getLogger(PropsLoader.class);
	private final static String CONFIGFILE = "src/test/resources/config.properties";
	private static Properties CONFIGPROP = null;
	private final static String PROJECTFILE = "src/test/resources/project.properties";
	private static Properties PROJECTPROP = null;

	public static String getProjectConfigProperty(String key) {
		if (PROJECTPROP == null) {
			PROJECTPROP = new Properties();
			try {
				PROJECTPROP.load(new FileInputStream(new File(PROJECTFILE)));
			} catch (FileNotFoundException e) {
				log.error("Unable to locate src/project.properties file.", e);
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Unable to retrieve value.", e);
				e.printStackTrace();
			}
		}
		return PROJECTPROP.getProperty(key);
	}

	public static String getBaseConfigProperty(String key) {
		if (CONFIGPROP == null) {
			CONFIGPROP = new Properties();
			try {
				CONFIGPROP.load(new FileInputStream(new File(CONFIGFILE)));
			} catch (FileNotFoundException e) {
				log.error("Unable to locate tests/config.properties file.", e);
				e.printStackTrace();
			} catch (IOException e) {
				log.error("Unable to retrieve value.", e);
				e.printStackTrace();
			}
		}
		return CONFIGPROP.getProperty(key);
	}
}
