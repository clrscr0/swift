package base.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TypeConverter {
	static final Logger log = LogManager.getLogger(TypeConverter.class);

	public static String[][] get2DArrayString(ArrayList<String[]> list) {
		String[][] testData = new String[list.size()][];

		for (int j = 0; j < list.size(); j++) {
			String[] rowData = list.get(j);
			for (int i = 0; i < rowData.length; i++) {
				testData[j] = rowData;
			}
		}
		return testData;
	}

	public static Map<String, String> convertTestDataToMap(String[] header,
			String[] dataArray) {
		Map<String, String> dataMap = new TreeMap<String, String>();

		for (int i = 0; i < dataArray.length; i++) {
			dataMap.put(header[i], dataArray[i]);
		}
		return dataMap;
	}

	public static String[] xmlToArray(String xmlFilePath) {
		String[] headers = null;
		DOMParser parser = new DOMParser();
		try {
			parser.parse(xmlFilePath);
			Document dom = parser.getDocument();
			NodeList cells = dom.getElementsByTagName("Cell");
			headers = new String[cells.getLength()];
			for (int i = 0; i < cells.getLength(); i++) {
				Node cellNodes = cells.item(i);
				headers[i] = cellNodes.getFirstChild().getNodeValue();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headers;
	}
}
