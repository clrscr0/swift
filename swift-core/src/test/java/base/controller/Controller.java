package base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;

import base.constants.BaseConfig;
import base.dao.DaoRuntimeException;
import base.dao.PoiExcelDaoInputImpl;
import base.dao.PoiExcelDaoRunImpl;
import base.dao.PoiExcelDaoSuiteImpl;
import base.enums.Datasource;
import base.enums.Status;
import base.helpers.DateTimeHelper;
import base.models.Run;
import base.models.Suite;

public class Controller implements Cloneable {
	private static Controller instance = null;
	private static final Logger log = LogManager.getLogger(Controller.class);
	private ArrayList<Suite> suites = null;

	private Controller() {
	}

	public static Controller getController() {
		// double locking
		if (instance == null) {// check 1
			log.info("Initializing new controller...");
			synchronized (Controller.class) {
				if (instance == null) {// check 2
					instance = new Controller();
					instance.suites = new ArrayList<Suite>();
				}
			}
		}
		log.info("Initializing existing controller...");
		return instance;
	}
	
	public ArrayList<Suite> getSuites(){
		return this.suites;
	}

	public Suite startSuite() {
		Suite suite = new Suite();
		suite.setStarted(DateTimeHelper.getCurrentDateTime(BaseConfig.DATETIME_FORMAT_AS_FILENAME));
		suite.setStatus(Status.PASSED); // default
		this.suites.add(suite);
		return suite;
	}

	public Suite getCurrentSuite() {
		return this.suites.get(this.suites.size() - 1);
	}

	public Object[][] getInput(String sheetName) throws Exception {
		Object[][] input = null;
		Datasource datasource = Datasource.valueOf(BaseConfig.DESIGN_INPUT_API);

		switch (datasource) {
		case excel_poi:
			PoiExcelDaoInputImpl dao = new PoiExcelDaoInputImpl(PackageAccess.READ);
			input = dao.get(sheetName);
			break;
		default:
			throw new Exception("Input API not supported.");
		}

		return input;
	}

	public int setOutput(Entry<String, String> data)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Datasource datasource = Datasource.valueOf(BaseConfig.DESIGN_OUTPUT_API);

		switch (datasource) {
		case excel_poi:
			PoiExcelDaoInputImpl dao = new PoiExcelDaoInputImpl(PackageAccess.READ_WRITE);
			dao.update(data);
			break;
		default:
			throw new DaoRuntimeException("API not supported.", null);
		}

		return 1;
	}

	public void insertRunRecord() throws EncryptedDocumentException, InvalidFormatException, IOException {

		if (BaseConfig.REPORT_RESULTS_HISTORY) {
			Datasource datasource = Datasource.valueOf(BaseConfig.DESIGN_OUTPUT_API);
			Run run = Controller.getController().getCurrentSuite().getCurrentTest().getCurrentRun();

			switch (datasource) {
			case excel_poi:
				PoiExcelDaoRunImpl dao = new PoiExcelDaoRunImpl(PackageAccess.READ_WRITE);
				dao.insert(run);
				break;
			default:
				throw new DaoRuntimeException("Output API not supported.", null);
			}
		}
	}

	public void insertSuiteRecord() throws EncryptedDocumentException, InvalidFormatException, IOException {
		if (BaseConfig.REPORT_RESULTS_SUMMARY) {
			Datasource datasource = Datasource.valueOf(BaseConfig.DESIGN_OUTPUT_API);

			Suite suite = Controller.getController().getCurrentSuite();

			switch (datasource) {
			case excel_poi:
				PoiExcelDaoSuiteImpl dao = new PoiExcelDaoSuiteImpl(PackageAccess.WRITE);
				dao.insert(suite);
				break;
			default:
				throw new DaoRuntimeException("Output API not supported.", null);
			}
		}
	}

}