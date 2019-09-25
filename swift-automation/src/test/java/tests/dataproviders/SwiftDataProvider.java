package tests.dataproviders;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	 
	@DataProvider(name = "demo-datasheet")
	public static Object[][] getJsonData() throws Exception {
		JsonReader reader = new JsonReader(new FileReader("json\\input.json"));

		HashMap<String, String> input = new Gson().fromJson(reader, new TypeToken<HashMap<String, String>>() {
		}.getType());
		Object[][] data = { { input } };
		return data;
	}
	
	public static void main(String[] args) throws IOException {
		String json = "json\\input.json";
		
		JsonReader reader = new JsonReader(new FileReader(json));
		HashMap<String, String> input = new Gson().fromJson(reader, new TypeToken<HashMap<String, String>>() {
		}.getType());
		String row = String.valueOf(Integer.parseInt(input.get("row"))+1);
		input.put("row", row);
		
		try (Writer writer = new FileWriter(json)) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(input, writer);
		}		
		
		System.out.println(input);
	}
}
