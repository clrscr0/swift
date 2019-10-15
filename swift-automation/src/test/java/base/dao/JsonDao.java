package base.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.stream.JsonReader;

public class JsonDao {
	static final Logger log = LogManager.getLogger(PoiExcelDao.class);
	private JsonReader jsonReader = null;
	
	JsonDao(String filePath) throws FileNotFoundException{
		this.jsonReader = new JsonReader(new FileReader(filePath));
	}
	
	public JsonReader getReader(){
		return this.jsonReader;
	}
	
}
