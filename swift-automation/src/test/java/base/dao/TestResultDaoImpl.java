package base.dao;

import java.util.HashMap;
import java.util.Map;

import org.testng.ITestResult;

import base.constants.BaseConfig;
import base.controller.Controller;
import base.helpers.DateTimeHelper;
import base.models.DataSheet;
import tests.constants.ProjectConfig;

public class TestResultDaoImpl extends PoiExcelDao implements BaseDao<String[]> {

	@Override
	public String[] get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(String[] type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(String[] type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(String[] type) {
		// TODO Auto-generated method stub

	}

	public void insertResultHistory(DataSheet datasheet) {
		String filePath = BaseConfig.REPORT_RESULTS_LOCATION + "test_runs\\" + datasheet.get("module") +".xls";
		log.info("result path: " + filePath);
		
		Map<String,String> datamap = new HashMap<String,String>(); 
		datamap.putAll(datasheet.get());
		datamap.remove("status");
		datamap.remove("last_run");
		datamap.remove("row");
		datamap.remove("module");
		datamap.remove("execute");

		String[] header = { "executed", "status", "iteration", "testdata"};
		String[] testResult = {datasheet.get("last_run"),datasheet.get("status"),datasheet.get("row"),datamap.toString()};
		insert(filePath, datasheet.get("module"), header, testResult);
	}
	
	public void writeSummary(ITestResult testResult) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Class<?> c = testResult.getInstance().getClass();
		DataSheet datasheet = (DataSheet) c.getSuperclass().getDeclaredField("datasheet").get(testResult.getInstance());
		String iteration = datasheet.get("row");
		String testName = c.getSimpleName();
		log.info("test class: " + testName);
		
		String suiteName = datasheet.get("suite") + "_" + Controller.getStarted();
		//String suiteName = datasheet.get("suite") + "_" + DateTimeHelper.getCurrentDateTime("Y-M-d_kkmmss");
		
		String filepath = BaseConfig.REPORT_RESULTS_LOCATION.replace("###", suiteName);

		filepath = filepath + "\\" + suiteName + ".xls";
		
		log.info("Filepath: " + filepath);

		log.info("Suite Name" + datasheet.get("suite"));
		//log.info("No. of Test Cases" + );
	}

}
