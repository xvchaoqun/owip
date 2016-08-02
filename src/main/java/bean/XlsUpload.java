package bean;

import domain.sys.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.List;

public class XlsUpload {

	public final static int passportXLSColumnCount = 8;
	public final static int cadreXLSColumnCount = 6;
	public final static int userXLSColumnCount = 4;

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
				else if(StringUtils.equals(hkPassportType.getName(), passportName))
					passportRow.setPassportType(hkPassportType.getId());
				else if(StringUtils.equals(twPassportType.getName(), passportName))
					passportRow.setPassportType(twPassportType.getId());
				else
					throw  new RuntimeException("证件类型："+passportName+"不存在");
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
				passportRow.setIssueDate(cell.getDateCellValue());
			}
			cell = row.getCell(5);
			if (null != cell){
				passportRow.setExpiryDate(cell.getDateCellValue());
			}
			cell = row.getCell(6);
			if (null != cell){
				passportRow.setKeepDate(cell.getDateCellValue());
			}

			cell = row.getCell(7);
			if (null != cell){
				passportRow.setSafeCode(getCell(cell));
			}
			
			passportRows.add(passportRow);
		}
		
		return passportRows;
	}

	public static List<XlsCadre> fetchCadres(XSSFSheet sheet){

		List<XlsCadre> cadreRows = new ArrayList<XlsCadre>();
		XSSFRow rowTitle = sheet.getRow(0);
		//System.out.println(rowTitle);
		if(null == rowTitle)
			return cadreRows;
		int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
		if(cellCount != cadreXLSColumnCount)
			return cadreRows;

		for (int i = sheet.getFirstRowNum() + 1 ; i <= sheet.getLastRowNum(); i++) {

			XSSFRow row = sheet.getRow(i);
			if (row == null) {// 如果为空，不处理
				continue;
			}

			XlsCadre cadreRow = new XlsCadre();
			XSSFCell cell = row.getCell(0);
			if (null != cell){
				String userCode = getCell(cell);
				if(StringUtils.isBlank(userCode)) {
					continue;
				}
				cadreRow.setUserCode(userCode);
			}else{
				continue;
			}

			cell = row.getCell(1);
			if (null != cell){ // 行政级别
				MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", getCell(cell));
				if(adminLevelType==null) throw new RuntimeException("行政级别：" + getCell(cell) + " 不存在");
				cadreRow.setAdminLevel(adminLevelType.getId());
			}

			cell = row.getCell(2);
			if (null != cell){ // 职务属性
				MetaType postType = CmTag.getMetaTypeByName("mc_post", getCell(cell));
				if(postType==null) throw new RuntimeException("职务属性：" + getCell(cell) + " 不存在");
				cadreRow.setPostId(postType.getId());
			}

			cell = row.getCell(3);
			if (null != cell){ // 所属单位
				cadreRow.setUnitCode(getCell(cell));
			}

			cell = row.getCell(4);
			if (null != cell){
				cadreRow.setTitle(getCell(cell));
			}
			cell = row.getCell(5);
			if (null != cell){
				cadreRow.setRemark(getCell(cell));
			}

			cadreRows.add(cadreRow);
		}

		return cadreRows;
	}

	public static List<XlsUser> fetchUsers(XSSFSheet sheet){

		List<XlsUser> rows = new ArrayList<XlsUser>();
		XSSFRow rowTitle = sheet.getRow(0);
		if(null == rowTitle)
			return rows;
		int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
		if(cellCount != userXLSColumnCount)
			return rows;

		for (int i = sheet.getFirstRowNum() + 1 ; i <= sheet.getLastRowNum(); i++) {

			XSSFRow row = sheet.getRow(i);
			if (row == null) {// 如果为空，不处理
				continue;
			}

			XlsUser dataRow = new XlsUser();
			XSSFCell cell = row.getCell(0);
			if (null != cell){
				String realname = getCell(cell);
				if(StringUtils.isBlank(realname)) {
					continue;
				}
				dataRow.setRealname(realname);
			}else{
				continue;
			}

			cell = row.getCell(1);
			if (null != cell){
				String _gender = getCell(cell);
				if(StringUtils.isBlank(_gender)) {
					continue;
				}
				dataRow.setGender((byte)(StringUtils.equals(_gender, "男")?1:2));
			}else{
				continue;
			}

			cell = row.getCell(2);
			if (null != cell){
				String idcard = getCell(cell);
				if(StringUtils.isBlank(idcard)) {
					continue;
				}
				dataRow.setIdcard(idcard);
			}else{
				continue;
			}

			cell = row.getCell(3);
			dataRow.setUnit( getCell(cell));

			rows.add(dataRow);
		}

		return rows;
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
