package base.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class PoiExcelDao {
	static final Logger log = LogManager.getLogger(PoiExcelDao.class);

	FileInputStream file = null;
	private HSSFWorkbook workbook;

	public PoiExcelDao() {}

	public HSSFWorkbook getWorkbook(String filePath) {
		log.debug("POI getWorkbook()");
		HSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(new File(filePath));
			workbook = new HSSFWorkbook(file); // Get the workbook instance for
												// XLS file
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return workbook;
	}
	
	public int findColumnIndex(HSSFSheet sheet, String header) {
		Row headerRow = sheet.getRow(0);
		int columnIndex = -1;
		for (Cell cell : headerRow) {
			if (cell.getCellType() == CellType.STRING) {
				if (cell.getRichStringCellValue().getString().trim().equals(header)) {
					columnIndex = cell.getColumnIndex();
					break;
				}
			}
		}

		if (columnIndex == -1) {
			columnIndex = headerRow.getLastCellNum();
			Cell newHeader = headerRow.createCell(columnIndex);
			newHeader.setCellValue(header);
			newHeader.setCellStyle(getCellStyles(workbook, Style.BG_YELLOW));
		}

		return columnIndex;
	}
	
	public enum Style {
		SIMPLE, BOLD, TOP, RIGHT, BOTTOM, LEFT, TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, BG_YELLOW
	}
	
	public void update(String filePath, String sheetName, int rowIndex, String header, String value) {
		File f = new File(filePath);
		
		try {
			FileInputStream fileInputStream = new FileInputStream(f);

			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet sheet = workbook.getSheet(sheetName);

			Row row = sheet.getRow(rowIndex);
			int columnIndex = findColumnIndex(sheet, header.trim());
			Cell cell = row.createCell(columnIndex);
			cell.setCellValue(value);

			fileInputStream.close();
			FileOutputStream outFile = new FileOutputStream(f);
			workbook.write(outFile);
			workbook.close();
			outFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object[][] getSheetData(String sheetName, String filePath) {
		Object[][] tableData = null;

		try {
			HSSFSheet sheet = getWorkbook(filePath).getSheet(sheetName);

			tableData = new Object[sheet.getLastRowNum()][1];

			for(int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++){
				Map<String, String> rowMap = new HashMap<String, String>();
				Row row = sheet.getRow(rowIndex);
				
				rowMap.put("row", String.valueOf(rowIndex)); //get rowIndex for reference in writing output

				for (Cell cell : row) {
					//cell.setCellType(CellType.STRING);
					String cellHeader = sheet.getRow(0).getCell(cell.getColumnIndex()).toString().trim();
					rowMap.put(cellHeader, cell.toString());
				}
				tableData[rowIndex-1][0] = rowMap;
			}

			file.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tableData;
	}

	public void createFile(String filePath) {
		HSSFWorkbook workbook = new HSSFWorkbook();

		try {
			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			workbook.close();
			log.info("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Row findRow(HSSFSheet sheet, String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
						return row;
					}
				}
			}
		}
		return null;
	}

	public void insert(String filePath, String sheetName, String[] header, String[] testResult) {

		File f = new File(filePath);
		if (!f.exists()) {
			createFile(filePath);
		}

		try {
			FileInputStream fileInputStream = new FileInputStream(f);

			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet sheet = workbook.getSheet(sheetName);

			if (workbook.getSheet(sheetName) == null) {
				sheet = workbook.createSheet(sheetName);
			}

			int lastRow = sheet.getLastRowNum();
			if (sheet.getRow(lastRow) != null) {
				lastRow++;
			}

			log.debug("lastRow: " + lastRow);
			Row row = sheet.createRow(lastRow);

			if (lastRow == 0) {
				writeToRow(row, header);
				CellStyle cellStyle = getCellStyles(workbook, Style.BOLD);
				setStyle(row, cellStyle);
				row = sheet.createRow(lastRow + 1);
			}

			writeToRow(row, testResult);
			autoResize(sheet, row);

			fileInputStream.close();
			FileOutputStream outFile = new FileOutputStream(f);
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void autoResize(HSSFSheet sheet, Row row) {
		for (Cell cell : row) {
			if (cell.getColumnIndex() != row.getLastCellNum()) {
				sheet.autoSizeColumn(cell.getColumnIndex());
			}
		}
	}

	public CellStyle getCellStyles(Workbook workbook, Style style) {

		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();

		switch (style) {
		case SIMPLE:
			font.setFontHeightInPoints((short) 10);
			cellStyle.setFont(font);
			break;
		case BOLD:
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case TOP:
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;

		case RIGHT:
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case BOTTOM:
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case LEFT:
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case TOPLEFT:
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case TOPRIGHT:
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case BOTTOMLEFT:
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case BOTTOMRIGHT:
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		default:
			break;
		}

		return cellStyle;
	}

	public void setStyle(Row row, CellStyle cellStyle) {
		for (Cell cell : row) {
			cell.setCellStyle(cellStyle);
		}
	}

	public void writeToRow(Row row, String[] data) {
		for (int i = 0; i < data.length; i++) {
			row.createCell(i).setCellValue(data[i]);
		}
	}
}
