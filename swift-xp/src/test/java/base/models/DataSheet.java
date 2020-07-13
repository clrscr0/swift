package base.models;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import base.constants.BaseConfig;
import base.dao.TestIterationDaoImpl;
import base.helpers.DateTimeHelper;

public class DataSheet {
	// private static DataSheet instance = null;
	private static Map<String, String> datamap = null;
	private Entry<String, String> status = null;
	private Entry<String, String> last_run = null;

	public DataSheet() {
		datamap = new HashMap<String, String>();
	}

	public void merge(Map<String, String> input) {
		datamap.putAll(input);
	}

	public void merge(Map<String, String> input, String module) {
		datamap.put("module", module);
		datamap.putAll(input);
		datamap.put("last_run", DateTimeHelper.getCurrentDateTime("Y-M-d kk:mm:ss"));
		int rowIndex = Integer.parseInt(datamap.get("row"));

		TestIterationDaoImpl dao = new TestIterationDaoImpl();
		last_run = new AbstractMap.SimpleEntry<String, String>("last_run", datamap.get("last_run"));
		dao.update(datamap.get("module"), rowIndex, last_run);
	}

	public String get(String field) {
		return datamap.get(field);
	}

	public Map<String, String> get() {
		return datamap;
	}

	public Entry<String, String> getStatus() {
		return status;
	}

	public void set(String field, String value) {
		datamap.put(field, value);
		status = new AbstractMap.SimpleEntry<String, String>(field, value);

		if (BaseConfig.DESIGN_OUTPUT_RECORD) {
			TestIterationDaoImpl dao = new TestIterationDaoImpl();
			int rowIndex = Integer.parseInt(datamap.get("row"));
			dao.update(datamap.get("module"), rowIndex, status);
		}
	}

}
