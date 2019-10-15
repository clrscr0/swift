package tests.analysis;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.constants.BaseConfig;
import base.helpers.SeleniumUtility;

public class PageAnalyst {

	public static void main(String[] args) {
		WebDriver driver = null;
		SeleniumUtility seleniumUtil = new SeleniumUtility();	
		
		try {
			driver = seleniumUtil.setup("chrome", "", "", "");
			driver.manage().timeouts()
			.implicitlyWait(BaseConfig.EXECUTION_WAIT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
			seleniumUtil.launchBrowser(driver);
			
			List<WebElement> textboxFields = driver.findElements(By.xpath("//input[@type=\"text\"]"));
			List<HashMap<String,String>> pageElements = new ArrayList<HashMap<String,String>>();
			
			for (WebElement webElement : textboxFields) {
				HashMap<String,String> element = new HashMap<String,String>();
				element.put("type", webElement.getAttribute("type"));
				element.put("id", webElement.getAttribute("id"));
				element.put("name", webElement.getAttribute("name"));
				element.put("value", webElement.getAttribute("value"));
				element.put("placeholder", webElement.getAttribute("placeholder"));
				element.put("alias", webElement.getAttribute("name")+webElement.getAttribute("type")); //be default, the variable name
				element.put("generate", "Y"); //by default, if you want element to be included in generating the class
				pageElements.add(element);
			}
			
			for (HashMap<String, String> mp : pageElements) {
				System.out.println(mp);
			}
			
			ObjectMapper objectMapper = new ObjectMapper();

			String json = objectMapper.writeValueAsString(pageElements);

			System.out.println(json);
			
		} catch (MalformedURLException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
