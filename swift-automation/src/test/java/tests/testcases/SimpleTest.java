package tests.testcases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import base.constants.BaseConfig;
import base.dao.PoiExcelDaoInputImpl;
import base.dao.PoiExcelDaoRunImpl;
import base.dao.PoiExcelDaoSuiteImpl;
import base.helpers.DateTimeHelper;
import base.helpers.Mailer;
import base.models.Run;
import base.models.Suite;
import base.testrunner.SwiftTestRunner;
import tests.dataproviders.SwiftDataProvider;

public class SimpleTest extends SwiftTestRunner {

	private static final Logger log = LogManager.getLogger(SimpleTest.class);
	public static final String SHEETNAME = "testLogin";

	@Test(dataProviderClass = SwiftDataProvider.class, dataProvider = "datasheet", enabled = true, groups = { "login" })
	public void testLogin(Map<String, String> input) throws Exception {
		test.startRun(input);
		log.info(input);

		test.getCurrentRun().set("testField2", "testFieldValue2");
	}

	public static void main(String[] args) throws Exception {
		String mailTo = "clarissa.ortiaga@gmail.com";
		String subject = "Test Email";
		String body = "<table><tr><th>Suite:</th><td>Regression</td></tr><tr><th>% Passed:</th><td>80.0</td></tr></table>";
		String[] attachFiles = {"temp\\2017-0415_01-32-03.zip"};
		Mailer.sendMail(mailTo, subject, body, attachFiles);
	}

	public void testInputDao() throws EncryptedDocumentException, InvalidFormatException, IOException {
		PoiExcelDaoInputImpl dao = new PoiExcelDaoInputImpl(PackageAccess.READ);

		Object[][] input = dao.get("testlogin");

		for (Object[] objects : input) {
			for (Object object : objects) {
				log.info(object);
			}
		}

		dao.close();
	}

	public void testRunDao() throws EncryptedDocumentException, InvalidFormatException, IOException {
		PoiExcelDaoRunImpl dao = new PoiExcelDaoRunImpl(PackageAccess.READ_WRITE);
		Run run = new Run();
		run.get().put("status", "FAILED");
		run.get().put("last_run", DateTimeHelper.getCurrentDateTime("Y-M-d kk:mm:ss"));
		run.get().put("row", "1");
		run.get().put("execute", "y");
		run.get().put("username", "newtest");
		run.get().put("password", "newtest");

		dao.insert(run);
	}
	
	public void testSuiteDao() throws EncryptedDocumentException, InvalidFormatException, IOException {
		PoiExcelDaoSuiteImpl dao = new PoiExcelDaoSuiteImpl(PackageAccess.WRITE);
		Suite suite = new Suite();
		dao.insert(suite);
	}
	
	public void testWriteExcel() throws IOException{
		String filePath = BaseConfig.REPORT_RESULTS_HISTORY_LOCATION + "test_run.xlsx";
		File file = new File(filePath); 
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Runs");
		
		Row row = sheet.getRow(0);
		
		log.debug("row: " + row);
		
		if(row==null) row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("TEST");
		
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
}