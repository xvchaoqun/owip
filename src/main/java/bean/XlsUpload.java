package bean;

import controller.global.OpException;
import domain.base.MetaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import sys.tags.CmTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XlsUpload {

    public final static int passportXLSColumnCount = 8;
    public final static int cadreXLSColumnCount = 7;
    public final static int cadreReserveXLSColumnCount = 7;
    public final static int userXLSColumnCount = 4;
    public final static int trainCourseXLSColumnCount = 4;

    public static List<XlsPassport> fetchPassports(XSSFSheet sheet) {

        List<XlsPassport> passportRows = new ArrayList<XlsPassport>();
        XSSFRow rowTitle = sheet.getRow(0);
        //System.out.println(rowTitle);
        if (null == rowTitle)
            return passportRows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < passportXLSColumnCount)
            return passportRows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsPassport passportRow = new XlsPassport();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String userCode = getCell(cell);
                if (StringUtils.isBlank(userCode)) {
                    continue;
                }

                passportRow.setUserCode(userCode);
            } else {
                continue;
            }

            // 因私普通护照,内地居民往来港澳通行证,大陆居民往来台湾通行证
            MetaType normalPassportType = CmTag.getMetaTypeByCode("mt_passport_normal");
            MetaType hkPassportType = CmTag.getMetaTypeByCode("mt_passport_hk");
            MetaType twPassportType = CmTag.getMetaTypeByCode("mt_passport_tw");

            cell = row.getCell(1);
            if (null != cell) {
                String passportName = getCell(cell);
                if (StringUtils.equals(normalPassportType.getName(), passportName))
                    passportRow.setPassportType(normalPassportType.getId());
                else if (StringUtils.equals(hkPassportType.getName(), passportName))
                    passportRow.setPassportType(hkPassportType.getId());
                else if (StringUtils.equals(twPassportType.getName(), passportName))
                    passportRow.setPassportType(twPassportType.getId());
                else
                    throw new OpException("证件类型：" + passportName + "不存在");
            }

            cell = row.getCell(2);
            if (null != cell) {
                passportRow.setCode(getCell(cell));
            }

            cell = row.getCell(3);
            if (null != cell) {
                passportRow.setAuthority(getCell(cell));
            }

            cell = row.getCell(4);
            if (null != cell) {
                passportRow.setIssueDate(cell.getDateCellValue());
            }
            cell = row.getCell(5);
            if (null != cell) {
                passportRow.setExpiryDate(cell.getDateCellValue());
            }
            cell = row.getCell(6);
            if (null != cell) {
                passportRow.setKeepDate(cell.getDateCellValue());
            }

            cell = row.getCell(7);
            if (null != cell) {
                passportRow.setSafeCode(getCell(cell));
            }

            passportRows.add(passportRow);
        }

        return passportRows;
    }

    public static List<XlsCadre> fetchCadres(XSSFSheet sheet) {

        List<XlsCadre> cadreRows = new ArrayList<XlsCadre>();
        XSSFRow rowTitle = sheet.getRow(0);
        //System.out.println(rowTitle);
        if (null == rowTitle)
            return cadreRows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < cadreXLSColumnCount)
            return cadreRows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsCadre cadreRow = new XlsCadre();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String userCode = getCell(cell);
                if (StringUtils.isBlank(userCode)) {
                    continue;
                }
                cadreRow.setUserCode(userCode);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) { // 行政级别
                MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", getCell(cell));
                if (adminLevelType == null) throw new OpException("行政级别：" + getCell(cell) + " 不存在");
                cadreRow.setAdminLevel(adminLevelType.getId());
            }

            cell = row.getCell(2);
            if (null != cell) { // 职务属性
                MetaType postType = CmTag.getMetaTypeByName("mc_post", getCell(cell));
                if (postType == null) throw new OpException("职务属性：" + getCell(cell) + " 不存在");
                cadreRow.setPostId(postType.getId());
            }

            cell = row.getCell(3);
            if (null != cell) { // 所属单位
                cadreRow.setUnitCode(getCell(cell));
            }

            cell = row.getCell(4);
            if (null != cell) {
                cadreRow.setTitle(getCell(cell));
            }
            cell = row.getCell(5);
            if (null != cell) {
                cadreRow.setPost(getCell(cell));
            }

            cell = row.getCell(6);
            if (null != cell) {
                cadreRow.setRemark(getCell(cell));
            }

            cadreRows.add(cadreRow);
        }

        return cadreRows;
    }

    public static List<XlsCadreReserve> fetchCadreReserves(XSSFSheet sheet) {

        List<XlsCadreReserve> rows = new ArrayList<XlsCadreReserve>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < cadreReserveXLSColumnCount)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsCadreReserve bean = new XlsCadreReserve();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String userCode = getCell(cell);
                if (StringUtils.isBlank(userCode)) {
                    continue;
                }
                bean.setUserCode(userCode);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) { // 行政级别
                MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", getCell(cell));
                if (adminLevelType == null) throw new OpException("行政级别：" + getCell(cell) + " 不存在");
                bean.setAdminLevel(adminLevelType.getId());
            }

            cell = row.getCell(2);
            if (null != cell) { // 职务属性
                MetaType postType = CmTag.getMetaTypeByName("mc_post", getCell(cell));
                if (postType == null) throw new OpException("职务属性：" + getCell(cell) + " 不存在");
                bean.setPostId(postType.getId());
            }

            cell = row.getCell(3);
            if (null != cell) { // 所属单位
                bean.setUnitCode(getCell(cell));
            }

            cell = row.getCell(4);
            if (null != cell) {
                bean.setPost(getCell(cell));
            }

            cell = row.getCell(5);
            if (null != cell) {
                bean.setTitle(getCell(cell));
            }

            cell = row.getCell(6);
            if (null != cell) {
                bean.setReserveRemark(getCell(cell));
            }

            rows.add(bean);
        }

        return rows;
    }


    public static List<XlsCadreInspect> fetchCadreInspects(XSSFSheet sheet) {

        List<XlsCadreInspect> rows = new ArrayList<XlsCadreInspect>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < cadreReserveXLSColumnCount)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsCadreInspect bean = new XlsCadreInspect();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String userCode = getCell(cell);
                if (StringUtils.isBlank(userCode)) {
                    continue;
                }
                bean.setUserCode(userCode);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) { // 行政级别
                MetaType adminLevelType = CmTag.getMetaTypeByName("mc_admin_level", getCell(cell));
                if (adminLevelType == null) throw new OpException("行政级别：" + getCell(cell) + " 不存在");
                bean.setAdminLevel(adminLevelType.getId());
            }

            cell = row.getCell(2);
            if (null != cell) { // 职务属性
                MetaType postType = CmTag.getMetaTypeByName("mc_post", getCell(cell));
                if (postType == null) throw new OpException("职务属性：" + getCell(cell) + " 不存在");
                bean.setPostId(postType.getId());
            }

            cell = row.getCell(3);
            if (null != cell) { // 所属单位
                bean.setUnitCode(getCell(cell));
            }

            cell = row.getCell(4);
            if (null != cell) {
                bean.setPost(getCell(cell));
            }

            cell = row.getCell(5);
            if (null != cell) {
                bean.setTitle(getCell(cell));
            }

            cell = row.getCell(6);
            if (null != cell) {
                bean.setInspectRemark(getCell(cell));
            }

            rows.add(bean);
        }

        return rows;
    }

    public static List<XlsUser> fetchUsers(XSSFSheet sheet) {

        List<XlsUser> rows = new ArrayList<XlsUser>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < userXLSColumnCount)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsUser dataRow = new XlsUser();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String realname = getCell(cell);
                if (StringUtils.isBlank(realname)) {
                    continue;
                }
                dataRow.setRealname(realname);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) {
                String _gender = getCell(cell);
                if (StringUtils.isBlank(_gender)) {
                    continue;
                }
                dataRow.setGender((byte) (StringUtils.equals(_gender, "男") ? 1 : 2));
            } else {
                continue;
            }

            cell = row.getCell(2);
            if (null != cell) {
                String idcard = getCell(cell);
                if (StringUtils.isBlank(idcard)) {
                    continue;
                }
                dataRow.setIdcard(idcard);
            } else {
                continue;
            }

            cell = row.getCell(3);
            dataRow.setUnit(getCell(cell));

            rows.add(dataRow);
        }

        return rows;
    }

    public static List<XlsTrainCourse> fetchTrainCourses(XSSFSheet sheet) {

        List<XlsTrainCourse> rows = new ArrayList<XlsTrainCourse>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < trainCourseXLSColumnCount)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsTrainCourse dataRow = new XlsTrainCourse();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String name = getCell(cell);
                if (StringUtils.isBlank(name)) {
                    continue;
                }
                dataRow.setName(name);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) {
                String teacher = getCell(cell);
                if (StringUtils.isBlank(teacher)) {
                    continue;
                }
                dataRow.setTeacher(teacher);
            } else {
                continue;
            }

            cell = row.getCell(2);
            if (null != cell) {
                Date startTime = cell.getDateCellValue();
                if (startTime == null) {
                    continue;
                }
                dataRow.setStartTime(startTime);
            } else {
                continue;
            }

            cell = row.getCell(3);
            if (null != cell) {
                Date endTime = cell.getDateCellValue();
                if (endTime == null) {
                    continue;
                }
                dataRow.setEndTime(endTime);
            } else {
                continue;
            }

            rows.add(dataRow);
        }

        return rows;
    }

    public static List<XlsPmdSpecialUser> fetchPmdSpecialUsers(XSSFSheet sheet) {

        List<XlsPmdSpecialUser> rows = new ArrayList<XlsPmdSpecialUser>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < 4)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsPmdSpecialUser dataRow = new XlsPmdSpecialUser();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String code = getCell(cell);
                if (StringUtils.isBlank(code)) {
                    continue;
                }
                dataRow.setCode(code);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) {
                String realname = getCell(cell);
                if (StringUtils.isBlank(realname)) {
                    continue;
                }
                dataRow.setRealname(realname);
            } else {
                continue;
            }

            cell = row.getCell(2);
            if (null != cell) {
                String unit = getCell(cell);
                if (StringUtils.isBlank(unit)) {
                    continue;
                }
                dataRow.setUnit(unit);
            } else {
                continue;
            }

            cell = row.getCell(3);
            if (null != cell) {
                String type = getCell(cell);
                if (StringUtils.isBlank(type)) {
                    continue;
                }
                dataRow.setType(type);
            } else {
                continue;
            }
            rows.add(dataRow);
        }

        return rows;
    }

    // 每行数据key从0开始计数
    public static List<Map<Integer, String>> getXlsRows(XSSFSheet sheet) {

        List<Map<Integer, String>> xlsRows = new ArrayList<>();
        XSSFRow rowTitle = sheet.getRow(0);
        //System.out.println(rowTitle);
        if (null == rowTitle) return xlsRows; // 第一行标题

        int cellNum = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum(); // 列数

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            // 行数据如果为空，不处理
            if (row == null) continue;

            boolean allIsNull = true;
            Map<Integer, String> xlsRow = new HashMap<>();
            for (int j = 0; j < cellNum; j++) {

                XSSFCell cell = row.getCell(j);
                String val = null;
                if(cell!=null){
                    val = getCellValue(cell);
                    allIsNull = false;
                }

                xlsRow.put(j, StringUtils.trimToNull(val));
            }
            // 如果所有的列都是空的，不处理
            if(!allIsNull) xlsRows.add(xlsRow);
        }

        return xlsRows;
    }

    public static List<XlsCetTrainInspector> fetchCetTrainInspectors(XSSFSheet sheet) {

        List<XlsCetTrainInspector> rows = new ArrayList<XlsCetTrainInspector>();
        XSSFRow rowTitle = sheet.getRow(0);
        if (null == rowTitle)
            return rows;
        int cellCount = rowTitle.getLastCellNum() - rowTitle.getFirstCellNum();
        if (cellCount < 2)
            return rows;

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {

            XSSFRow row = sheet.getRow(i);
            if (row == null) {// 如果为空，不处理
                continue;
            }

            XlsCetTrainInspector dataRow = new XlsCetTrainInspector();
            XSSFCell cell = row.getCell(0);
            if (null != cell) {
                String mobile = getCell(cell);
                if (StringUtils.isBlank(mobile)) {
                    continue;
                }
                dataRow.setMobile(mobile);
            } else {
                continue;
            }

            cell = row.getCell(1);
            if (null != cell) {
                String realname = getCell(cell);
                if (StringUtils.isBlank(realname)) {
                    continue;
                }
                dataRow.setRealname(realname);
            } else {
                continue;
            }

            rows.add(dataRow);
        }

        return rows;
    }

    public static String getCell(XSSFCell cell) {

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();

		/*if (cell == null)
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
		return "";*/
    }

    @SuppressWarnings("deprecation")
    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            //return cell.getCellFormula();
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            //System.out.println("format:"+format+";;;;;value:"+cell.getNumericCellValue());
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58
                    || (176<=format && format<=178) || (182<=format && format<=196)
                    || (210<=format && format<=213) || (208==format ) ) { // 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
            } else { // 不是日期格式
                //return String.valueOf(cell.getNumericCellValue());
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
            }
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            if(date==null || "".equals(date)){
                return "";
            }
            String result="";
            try {
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return result;
        }
        return "";
    }
}
