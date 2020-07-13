package base.dao;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import base.constants.BaseConfig;
import tests.constants.ProjectConfig;

public class TestIterationDaoImpl extends PoiExcelDao implements BaseDao<String[]> {

	@Override
	public String[] get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(String[] testRun) {
		return -1;
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

	/**
	 * This method gets the contents of the testSet specified in sheetName
	 * 
	 * @param sheetName
	 * @return
	 */
	public Object[][] getTableData(String sheetName) {
		PoiExcelDao dao = new PoiExcelDao();
		String filePath = BaseConfig.DESIGN_DATASHEETS
				+ ProjectConfig.DATASHEET_FILENAME;
		Object[][] sheetData = dao.getSheetData(sheetName, filePath);
		Object[][] iterations = sheetData;
		
		for (int i=0; i<sheetData.length; i++) {
			for (Object object : sheetData[i]) {
				Map<?, ?> map = (Map<?, ?>) object;
				if(!"y".equalsIgnoreCase((String) map.get("execute"))){
					iterations = ArrayUtils.removeElement(iterations, sheetData[i]);
				}
			}
		}
		
		return iterations;
	}
	
	public void update(String sheetName, int rowIndex, Map.Entry<String, String> data){
		PoiExcelDao dao = new PoiExcelDao();
		String filePath = BaseConfig.DESIGN_DATASHEETS
				+ ProjectConfig.DATASHEET_FILENAME;
		
		dao.update(filePath, sheetName, rowIndex, data.getKey(), data.getValue());
	}


}
