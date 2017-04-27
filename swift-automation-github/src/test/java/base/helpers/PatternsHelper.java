package base.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class PatternsHelper {
	static final Logger log = Logger.getLogger(PatternsHelper.class);

	public static boolean isValidEmail(String email) {
		return false;
	}

	public static boolean isValidPhone(String email) {
		return false;
	}

	public static boolean isValidCity(String email) {
		return false;
	}

	public static boolean isValidCountry(String email) {
		return false;
	}

	public static boolean isValidName(String email) {
		return false;
	}

	public static boolean isPartiallyMatched(String regex, String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		log.debug("Current pattern is: " + regex);
		log.debug("Current input is: " + input);
		log.debug(matcher.lookingAt());
		return matcher.lookingAt();
	}
}
