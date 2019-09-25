package base.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import base.enums.CellStyleLabel;
import base.enums.Extension;
import base.helpers.FileHelper;

public class PoiExcelDao {
	static final Logger log = LogManager.getLogger(PoiExcelDao.class);

	private File file = null;
	private FileOutputStream fos = null;
	private FileInputStream fis = null;

	private Workbook workbook = null;
	private Sheet activeSheet = null;

	public PoiExcelDao(String filePath, PackageAccess access)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		log.debug("FILEPATH: " + filePath);
		this.file = new File(filePath);

		switch (access) {
		case READ:
			openWorkbook();
			break;
		case WRITE:
			createWorkbook();
			break;
		case READ_WRITE:
			createOrOpenWorkbook();
		default:
			break;
		}
	}

	private void createOrOpenWorkbook() {
		try {
			if(this.file.exists()){
				openWorkbook();
				this.fos = new FileOutputStream(file);
			}else{
				createWorkbook();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	private void openWorkbook() throws InvalidFormatException {
		try {
			this.fis = new FileInputStream(file);
			this.workbook = WorkbookFactory.create(fis);
		} catch (FileNotFoundException e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	private void createWorkbook() throws FileNotFoundException {
		Extension extension = Extension.valueOf(FilenameUtils.getExtension(this.file.getName()).toUpperCase());
		log.debug("Extension: " + extension);

		FileHelper.createMissingFolderRecursively(this.file.getParent());
		this.fos = new FileOutputStream(file);

		switch (extension) {
		case XLS:
			this.workbook = new HSSFWorkbook();
			break;
		case XLSX:
			this.workbook = new XSSFWorkbook();
			break;
		default:
			throw new DaoRuntimeException("Extension not supported", null);
		}

	}
	
	public void save() {
		try {/*
			if (this.fos == null)
				this.fos = new FileOutputStream(this.file);*/
			this.workbook.write(this.fos);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (this.fis != null)
				this.fis.close();
			if (this.fos != null)
				this.fos.close();
			if (this.workbook != null)
				this.workbook.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void setActiveSheet(String sheetName) {
		this.activeSheet = this.workbook.getSheet(sheetName);
	}

	public void setOrCreateActiveSheet(String sheetName) {
		this.activeSheet = this.workbook.getSheet(sheetName);

		if (this.activeSheet == null) {
			log.debug("activeSheet: " + null);
			this.activeSheet = this.workbook.createSheet(sheetName);
		}
	}

	public Sheet getActiveSheet() {
		return this.activeSheet;
	}

	public void updateCell(Cell cell, Object value, CellStyleLabel label) {

		log.debug("Cell: " + cell.getStringCellValue());

		cell.setCellValue(String.valueOf(value));
		CellStyle cellStyle = getCellStyles(label);
		cell.setCellStyle(cellStyle);

	}

	public void setRowStyle(Row row, CellStyle cellStyle) {
		for (Cell cell : row) {
			cell.setCellStyle(cellStyle);
		}
	}

	public void autoResize(Row row) {
		for (Cell cell : row) {
			if (cell.getColumnIndex() != row.getLastCellNum()) {
				activeSheet.autoSizeColumn(cell.getColumnIndex());
			}
		}
	}

	public void writeToRow(Row row, String[] data, int startCell) {
		for (int i = 0; i < data.length; i++) {
			row.createCell(i+startCell).setCellValue(data[i]);
		}
	}
	
	public void writeToRow(Row row, int[] data, int startCell) {
		for (int i = 0; i < data.length; i++) {
			row.createCell(i+startCell).setCellValue(data[i]);
		}
	}

	public Cell getCell(String content, int rowIndex, boolean ifNotExistsAdd) {
		Row row = activeSheet.getRow(rowIndex);
		for (Cell cell : row) {
			DataFormatter formatter = new DataFormatter();
			String cellValue = formatter.formatCellValue(cell);
			if (cellValue.trim().equalsIgnoreCase(content)) {
				return cell;
			}
		}

		if (ifNotExistsAdd) {
			int columnIndex = row.getLastCellNum();
			Cell cell = row.createCell(columnIndex);
			cell.setCellValue(content);
			return cell;
		}

		return null;
	}

	public CellStyle getCellStyles(CellStyleLabel label) {

		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();

		switch (label) {
		case BG_YELLOW:
			cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			break;
		case SIMPLE:
			font.setFontHeightInPoints((short) 11);
			cellStyle.setFont(font);
			break;
		case BOLD:
			font.setFontHeightInPoints((short) 11);
			font.setBold(true);
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case TOP:
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;

		case RIGHT:
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case BOTTOM:
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case LEFT:
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case TOPLEFT:
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case TOPRIGHT:
			cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case BOTTOMLEFT:
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		case BOTTOMRIGHT:
			cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setFont(font);
			break;
		default:
			break;
		}

		return cellStyle;
	}
}