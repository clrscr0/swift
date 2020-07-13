package base.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

public class CustomExpectedConditions {
	public static Function<WebDriver, WebElement> elementIsEnabled(
			final WebElement element) {
		return new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				if (element.isEnabled())
					return element;
				return null;
			}
		};
	}
}
