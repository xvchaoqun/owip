package bean;

import domain.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class XlsUpload {

	public final static int passportXLSColumnCount = 8;

	public static List<XlsPassport> fetchPassports(XSSFSheet sheet){
		
		List<XlsPassport> passportRows = new ArrayList<XlsPassport>();
		XSSFRow rowTitle = sheet.getRow(0);
		//System.out.println(rowTitle);
		if(null == rowTitle) 
			return passportRows;
		int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
		if(cellCount != passportXLSColumnCount)
			return passportRows;
		
		for (int i = sheet.getFirstRowNum() + 1 ; i <= sheet.getLastRowNum(); i++) {
			
			XSSFRow row = sheet.getRow(i);
			if (row == null) {// 如果为空，不处理  
				continue;  
			}

			XlsPassport passportRow = new XlsPassport();
			XSSFCell cell = row.getCell(0);
			if (null != cell){
				String userCode = getCell(cell);
				if(StringUtils.isBlank(userCode)) {
					continue;
				}

				passportRow.setUserCode(userCode);
			}else{
				continue;
			}

			// 因私普通护照,内地居民往来港澳通行证,大陆居民往来台湾通行证
			MetaType normalPassportType = CmTag.getMetaTypeByCode("mt_passport_normal");
			MetaType hkPassportType = CmTag.getMetaTypeByCode("mt_passport_hk");
			MetaType twPassportType = CmTag.getMetaTypeByCode("mt_passport_tw");

			cell = row.getCell(1);
			if (null != cell){
				String passportName = getCell(cell);
				if(StringUtils.equals(normalPassportType.getName(), passportName))
					passportRow.setPassportType(normalPassportType.getId());
				if(StringUtils.equals(hkPassportType.getName(), passportName))
					passportRow.setPassportType(hkPassportType.getId());
				if(StringUtils.equals(twPassportType.getName(), passportName))
					passportRow.setPassportType(twPassportType.getId());
			}
			
			cell = row.getCell(2);
			if (null != cell){
				passportRow.setCode(getCell(cell));
			}
			
			cell = row.getCell(3);
			if (null != cell){
				passportRow.setAuthority(getCell(cell));
			}

			cell = row.getCell(4);
			if (null != cell){
				passportRow.setIssueDate(DateUtils.parseDate(getCell(cell), DateUtils.YYYY_MM_DD));
			}
			cell = row.getCell(5);
			if (null != cell){
				passportRow.setExpiryDate(DateUtils.parseDate(getCell(cell), DateUtils.YYYY_MM_DD));
			}
			cell = row.getCell(6);
			if (null != cell){
				passportRow.setKeepDate(DateUtils.parseDate(getCell(cell), DateUtils.YYYY_MM_DD));
			}

			cell = row.getCell(7);
			if (null != cell){
				passportRow.setSafeCode(getCell(cell));
			}
			
			passportRows.add(passportRow);
		}
		
		return passportRows;
	}
	
	public static String getCell(XSSFCell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue() + "";
		case XSSFCell.CELL_TYPE_STRING:
			return StringUtils.trim(cell.getStringCellValue());
		case XSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case XSSFCell.CELL_TYPE_BLANK:
			return "";
		case XSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case XSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}
}
