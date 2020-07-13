package base.models;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.enums.Status;

public class Run {
	
	private String name;
	private static Map<String, String> datamap = null;
	private Entry<String, String> data = null;
	private Status status = null;
	private String error;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Run() {
		datamap = new HashMap<String, String>();
	}

	public void merge(Map<String, String> input) throws Exception {
		datamap.putAll(input);
	}

	public String get(String field) {
		return datamap.get(field);
	}

	public Map<String, String> get() {
		return datamap;
	}

	public void set(String field, String value) throws EncryptedDocumentException, InvalidFormatException, IOException {
		datamap.put(field, value);
		data = new AbstractMap.SimpleEntry<String, String>(field, value);

		if (BaseConfig.DESIGN_OUTPUT_RECORD) {
			Controller.getController().setOutput(data);
		}
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}