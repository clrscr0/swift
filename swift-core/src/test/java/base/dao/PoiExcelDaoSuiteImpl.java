package base.dao;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.enums.CellStyleLabel;
import base.models.Suite;
import base.models.Test;

public class PoiExcelDaoSuiteImpl extends PoiExcelDao implements BaseDao<Suite> {
	private static String FILEPATH = BaseConfig.REPORT_RESULTS_SUMMARY_LOCATION + Controller.getController().getSuites().get(0).getStarted() + "\\";

	public PoiExcelDaoSuiteImpl(PackageAccess access)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		super(FILEPATH + Controller.getController().getCurrentSuite().getName() + BaseConfig.REPORT_RESULTS_FORMAT, access);
	}

	@Override
	public Suite get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Suite suite) {
		setOrCreateActiveSheet("Summary");

		updateCell(getActiveSheet().createRow(0).createCell(0), "Suite:", CellStyleLabel.BOLD);
		updateCell(getActiveSheet().getRow(0).createCell(1), suite.getName(), CellStyleLabel.SIMPLE);

		updateCell(getActiveSheet().createRow(1).createCell(0), "No. of Test Runs:", CellStyleLabel.BOLD);
		int numTestRuns = 0;
		int numPassed = 0;
		int numFailed = 0;
		int numSkiped = 0;

		for (Test test : suite.getTests()) {
			numTestRuns = numTestRuns + test.getRuns().size();
			numPassed = numPassed + test.getNumPassed();
			numFailed = numFailed + test.getNumFailed();
			numSkiped = numSkiped + test.getNumSkipped();
		}
		
		suite.setNumTestRuns(numTestRuns);
		suite.setNumPassed(numPassed);
		updateCell(getActiveSheet().getRow(1).createCell(1), numTestRuns, CellStyleLabel.SIMPLE);

		updateCell(getActiveSheet().createRow(2).createCell(0), "% Passed:", CellStyleLabel.BOLD);
		float passedPCT = (float) numPassed / numTestRuns;
		log.debug("numPassed: " + numPassed);
		log.debug("numTestRuns: " + numTestRuns);
		log.debug("passedPCT: " + passedPCT);
		updateCell(getActiveSheet().getRow(2).createCell(1), passedPCT*100, CellStyleLabel.SIMPLE);

		Row headerRow = getActiveSheet().createRow(10);
		String[] header = { "Test Cases", "Passed", "Failed", "Skipped", "Total" };
		writeToRow(headerRow, header, 0);
		CellStyle cellStyle = getCellStyles(CellStyleLabel.BOLD);
		setRowStyle(headerRow, cellStyle);

		int rowNum = 11;

		for (Test test : suite.getTests()) {
			updateCell(getActiveSheet().createRow(rowNum).createCell(0), test.getName(), CellStyleLabel.SIMPLE);
			int[] data = { test.getNumPassed(), test.getNumFailed(), test.getNumSkipped(), test.getRuns().size() };
			writeToRow(getActiveSheet().getRow(rowNum), data, 1);
			rowNum++;
		}


		Row totalsRow = getActiveSheet().createRow(rowNum);
		int[] data = { numPassed, numFailed, numSkiped, numTestRuns };
		writeToRow(getActiveSheet().getRow(rowNum), data, 1);
		setRowStyle(totalsRow, cellStyle);

		autoResize(headerRow);
		save();
		close();
		return 0;
	}

	@Override
	public boolean update(Suite type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(Suite type) {
		// TODO Auto-generated method stub

	}

}