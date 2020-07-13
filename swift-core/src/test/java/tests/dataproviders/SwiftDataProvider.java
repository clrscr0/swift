package tests.dataproviders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import base.controller.Controller;

public class SwiftDataProvider {
	static final Logger log = Logger.getLogger(SwiftDataProvider.class);

	@DataProvider(name = "datasheet")
	public static Object[][] getExcelData(Method m, ITestContext c) throws Exception {
		Controller controller = Controller.getController();

		String module = "";

		module = m.getDeclaringClass().getDeclaredField("MODULE").get(null).toString().trim(); // get
																								// from
																								// Test
																								// class
																								// field
																								// MODULE

		if (module.isEmpty()) // if MODULE field is empty, use method name
								// instead
			module = m.getName();

		log.info("Module: " + module);
		log.info("Class Name: " + m.getDeclaringClass().getName());

		return controller.getTestData(module);
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
