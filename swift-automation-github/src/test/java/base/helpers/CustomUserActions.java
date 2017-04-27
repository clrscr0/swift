package base.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CustomUserActions {
	public static void hoverAndClick(WebDriver driver,
			WebElement parentElement, By hiddenChildElement) {
		Actions bundle = new Actions(driver);
		bundle.moveToElement(parentElement).perform();
		WebElement hiddenChild = parentElement.findElement(hiddenChildElement);
		hiddenChild.click();
	}
}
