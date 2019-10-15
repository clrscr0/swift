package tests.dataproviders;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import base.controller.Controller;

public class SwiftDataProvider {
	private static final Logger log = LogManager.getLogger(SwiftDataProvider.class);
	private static String SHEETNAME = ""; 

	@DataProvider(name = "datasheet")
	public static Object[][] getExcelData(Method m, ITestContext c) throws Exception {
		SHEETNAME = m.getDeclaringClass().getDeclaredField("SHEETNAME").get(null).toString().trim(); // get

		log.debug("SHEETNAME: " + SHEETNAME);
		log.debug("Class Name: " + m.getDeclaringClass().getName());

		return Controller.getController().getInput(SHEETNAME);
	}
	 
	@DataProvider(name = "json-data")
	public static Object[][] getJsonData() throws Exception {
		JsonReader reader = new JsonReader(new FileReader("json\\input.json"));

		HashMap<String, String> input = new Gson().fromJson(reader, new TypeToken<HashMap<String, String>>() {
		}.getType());
		Object[][] data = { { input } };
		return data;
	}
	
	
	@DataProvider(name = "json-string")
	public static Object[][] getJsonStringData() throws Exception {
		String json = "[{\"Success\":true,\"Message\":\"Invalid access token.\"}]";
		JsonParser parser = new JsonParser();
		JsonArray array = (JsonArray) parser.parse(json);
		HashMap<String, String> input = new Gson().fromJson(array, new TypeToken<HashMap<String, String>>() {
		}.getType());
		Object[][] data = { { input } };
		return data;
	}
	
	public static void main(String[] args) throws IOException {
		//String json = "src\\test\\resources\\design\\datasheets\\dt_regression.json";
		
		//JsonReader reader = new JsonReader(new FileReader(json));
		String json = "[{\"Success\":true,\"Message\":\"Invalid access token.\"}]";
		JsonParser parser = new JsonParser();
		JsonArray array = (JsonArray) parser.parse(json);
		
		List<HashMap<String, String>> inputlist = new Gson().fromJson(array, new TypeToken<ArrayList<HashMap<String, String>>>() {
		}.getType());
		
		System.out.println(inputlist);
		
		Object[][] data = new Object[inputlist.size()][1];
		
		for (int i = 0; i < inputlist.size(); i++) {
			data[i][0] = inputlist.get(i);
		}
	}
}
