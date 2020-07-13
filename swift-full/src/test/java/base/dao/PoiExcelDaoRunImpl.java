package base.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.enums.CellStyleLabel;
import base.models.Run;

public class PoiExcelDaoRunImpl extends PoiExcelDao implements BaseDao<Run> {
	private static String FILEPATH = BaseConfig.REPORT_RESULTS_HISTORY_LOCATION;
			/*+ Controller.getController().getCurrentSuite().getCurrentTest().getName() + ".xlsx";*/
	/*private static final String FILEPATH = BaseConfig.REPORT_RESULTS_HISTORY_LOCATION + "test_run.xlsx";*/
	
	public PoiExcelDaoRunImpl(PackageAccess access) throws EncryptedDocumentException, InvalidFormatException, IOException {
		super(FILEPATH + Controller.getController().getCurrentSuite().getCurrentTest().getName() + BaseConfig.REPORT_RESULTS_FORMAT,access);
	}

	@Override
	public Run get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Run run) {
		setOrCreateActiveSheet("Runs");
		int lastRow = getActiveSheet().getLastRowNum();
		if (getActiveSheet().getRow(lastRow) != null) {
			lastRow++;
		}

		Row row = getActiveSheet().createRow(lastRow);

		if (lastRow == 0) {
			String[] header = { "executed", "status", "row", "input/output", "error message"};
			writeToRow(row, header, 0);
			CellStyle cellStyle = getCellStyles(CellStyleLabel.BOLD);
			setRowStyle(row, cellStyle);
			row = getActiveSheet().createRow(lastRow + 1);
		}
		
		Map<String,String> datamap = new HashMap<String,String>(); 
		datamap.putAll(run.get());
		datamap.remove("status");
		datamap.remove("last_run");
		datamap.remove("row");
		datamap.remove("execute");

		String[] testResult = {run.get("last_run"),run.get("status"),run.get("row"),datamap.toString(),run.getError()};
		
		writeToRow(row, testResult, 0);
		autoResize(row);
		save();
		close();
		
		return Integer.parseInt(run.get("row"));
	}
	
	@Override
	public boolean update(Run type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(Run type) {
		// TODO Auto-generated method stub

	}

}