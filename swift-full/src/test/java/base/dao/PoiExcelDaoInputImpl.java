package base.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.enums.CellStyleLabel;
import base.models.Run;
import tests.constants.ProjectConfig;

public class PoiExcelDaoInputImpl extends PoiExcelDao implements BaseDao<Object[][]> {
	private static String FILEPATH = BaseConfig.DESIGN_DATASHEETS + ProjectConfig.DATASHEET_FILENAME;

	public PoiExcelDaoInputImpl(PackageAccess access) throws EncryptedDocumentException, InvalidFormatException, IOException {
		super(FILEPATH,access);
	}

	@Override
	public Object[][] get(String sheetName) {
		setActiveSheet(sheetName);
		
		ArrayList<Map<String,String>> tableDataList = new ArrayList<Map<String,String>>();

		for (int rowIndex = 1; rowIndex <= getActiveSheet().getLastRowNum(); rowIndex++) {
			Map<String, String> rowMap = new HashMap<String, String>();
			Row row = getActiveSheet().getRow(rowIndex);

			rowMap.put("row", String.valueOf(rowIndex));
			rowMap.put("sheet_name", sheetName);

			for (Cell cell : row) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellHeader = getActiveSheet().getRow(0).getCell(cell.getColumnIndex()).toString().trim();

				rowMap.put(cellHeader, cell.toString());
			}

			// don't add if execute is not "y"
			if (rowMap.get(BaseConfig.DATASHEET_HEADER_EXECUTE)
					.equalsIgnoreCase(BaseConfig.DATASHEET_HEADER_EXECUTE_YES)){
				tableDataList.add(rowMap);
			}
		}
		
		Object[][] tableData = new Object[tableDataList.size()][1];
		
		for (int i = 0; i < tableDataList.size(); i++) {
			tableData[i][0] = tableDataList.get(i);
		}

		close();

		return tableData;
	}

	@Override
	public int insert(Object[][] input) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(Object[][] input) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(Object[][] input) {
		// TODO Auto-generated method stub

	}

	public boolean update(Entry<String, String> data) throws IOException {
		log.debug("Runs: " + Controller.getController().getCurrentSuite().getCurrentTest().getRuns().size());
		Run run = Controller.getController().getCurrentSuite().getCurrentTest().getCurrentRun();
		log.debug("Sheet Name: " + run.get("sheet_name"));
		setActiveSheet(run.get("sheet_name"));
		Cell header = getCell(data.getKey(), 0, true); // get header cell
		header.setCellStyle(getCellStyles(CellStyleLabel.BG_YELLOW));
		
		int rowIndex = Integer.parseInt(run.get("row"));
		Cell cell = getActiveSheet().getRow(rowIndex).createCell(header.getColumnIndex());
				
		updateCell(cell, data.getValue(), CellStyleLabel.BOLD);
		
		save();
		close();
		
		return true;
	}

}