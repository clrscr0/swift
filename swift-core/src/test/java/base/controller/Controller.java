package base.controller;

import org.apache.log4j.Logger;

import base.constants.BaseConfig;
import base.dao.TestIterationDaoImpl;
import base.dao.TestResultDaoImpl;
import base.helpers.DateTimeHelper;
import base.models.DataSheet;

public class Controller implements Cloneable {
	private static Controller controllerInstance = null;
	static final Logger log = Logger.getLogger(Controller.class);
	private static StringBuffer verificationErrors = new StringBuffer();
	private static String started;

	private Controller() {
	}

	public static Controller getController() {
		// double locking
		if (controllerInstance == null) {// check 1
			synchronized (Controller.class) {
				if (controllerInstance == null) {// check 2
					controllerInstance = new Controller();
					started = DateTimeHelper.getCurrentDateTime("Y-M-d_kkmmss");
				}
			}
		}
		return controllerInstance;
	}

	public static String getStarted() {
		getController();
		return started;
	}

	public Object[][] getTestData(String sheetName) throws Exception {
		Object[][] tableData = null;
		Datasource datasource = Datasource.valueOf(BaseConfig.DESIGN_INPUT_API);

		switch (datasource) {
		case excel_poi:
			TestIterationDaoImpl poiDao = new TestIterationDaoImpl();
			tableData = poiDao.getTableData(sheetName);
			break;
		default:
			throw new Exception("Input API not supported.");
		}

		return tableData;
	}

	public StringBuffer getVerificationErrors() {
		return verificationErrors;
	}

	public static void setVerificationErrors(StringBuffer verificationErrors) {
		Controller.verificationErrors = verificationErrors;
	}

	public void insertResultHistory(DataSheet datasheet) throws Exception {

		if (BaseConfig.REPORT_RESULTS_HISTORY) {
			Datasource datasource = Datasource.valueOf(BaseConfig.DESIGN_INPUT_API);

			switch (datasource) {
			case excel_poi:
				TestResultDaoImpl dao = new TestResultDaoImpl();
				dao.insertResultHistory(datasheet);
				break;
			default:
				throw new Exception("Output API not supported.");
			}
		}
	}

	public static enum Datasource {
		excel_poi, db_mysql, alm_rest
	}

}