package service.cadreReserve;

import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.cadre.CadrePostService;
import service.unit.UnitService;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CadreReserveExportService extends BaseMapper {

    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected CadrePostService cadrePostService;

    /*public SXSSFWorkbook export(Integer type, CadreReserveViewExample example) {

        String cadreReserveType = metaTypeService.getName(type);

        Map<Integer, Unit> unitMap = unitService.findAll();
        List<CadreReserveView> records = cadreReserveViewMapper.selectByExample(example);

        int rowNum = 0;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //sheet.setDefaultColumnWidth(12);
        //sheet.setDefaultRowHeight((short)(20*60));
        {
            Row titleRow = sheet.createRow(rowNum);
            titleRow.setHeight((short) (35.7 * 30));
            Cell headerCell = titleRow.createCell(0);
            CellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = wb.createFont();
            // 设置字体加粗
            font.setFontName("宋体");
            font.setFontHeight((short) 350);
            cellStyle.setFont(font);
            headerCell.setCellStyle(cellStyle);
            if(cadreReserveType!=null)
                headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() +"优秀年轻干部（" + cadreReserveType +"）一览表");
            else
                headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() +"优秀年轻干部一览表");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        int count = records.size();
        String[] titles = {"工作证号","姓名","部门属性","所在单位","现任职务",
                "所在单位及职务","任职时间","行政级别","职务属性", "是否正职","性别",
                "民族","籍贯","出生地","身份证号","出生时间",
                "年龄","政治面貌","党派加入时间","参加工作时间","到校日期",
                "最高学历","最高学位","毕业时间","学习方式","毕业学校",
                "学校类型","所学专业",*//*"岗位类别", "主岗等级",*//*"专业技术职务",
                "专技职务评定时间",*//*"专技职务等级","专技岗位分级时间","管理岗位等级", "管理岗位分级时间",*//*
                "现职务任命文件","任现职时间","现职务始任时间","现职务始任年限","现职级始任时间",
                "任现职级年限","兼任单位及职务", "兼任职务现任时间", "兼任职务始任时间", "是否双肩挑",
                "双肩挑单位","联系方式",*//*"党委委员", "纪委委员",*//*"电子信箱",
                "是否有挂职经历", "备注"};

        int columnCount = titles.length;
        Row firstRow = sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 12));
        for (int i = 0; i < columnCount; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));
        }

        int columnIndex = 0;
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 工作证号
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 160));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 300)); // 所在单位及职务
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 民族
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 50));// 年龄
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120)); // 最高学历
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100)); // 学校类型
        *//*sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));*//*
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 160));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200)); // 专技职务评定时间
        *//*sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));*//*

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150)); // 现职务任命文件
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));//任现职级年限
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 250));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 180));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 150));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));

        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        *//*sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 120));*//*
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 200));

        //sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 100));
        sheet.setColumnWidth(columnIndex++, (short) (35.7 * 500));

        for (int i = 0; i < count; i++) {
            CadreReserveView record = records.get(i);
            SysUserView sysUser =  record.getUser();

            String isPositive = BooleanUtils.isTrue(record.getIsPrincipal())?"是":"否"; // 是否正职

            Map<String, String> cadreParty = CmTag.getCadreParty(record.getIsOw(), record.getOwGrowTime(),
                    record.getOwPositiveTime(), "中共党员",
                    record.getDpTypeId(), record.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");
            String partyAddTime = cadreParty.get("growTime");

            String postDispatchCode = ""; // 现职务任命文件
            String postTime = ""; // 任现职时间
            String postStartTime = ""; // 现职务始任时间
            String postYear = ""; // 现职务始任年限
            String adminLevelStartTime = ""; // 现职级始任时间
            String adminLevelYear = ""; // 任现职级年限
            String isDouble = ""; // 是否双肩挑
            String doubleUnit = ""; // 双肩挑单位

            Dispatch first = CmTag.getDispatch(record.getNpDispatchId()); // 现职务始任文件
            if (first != null) {
                postDispatchCode = first.getDispatchCode();
            }

            if(record.getNpWorkTime()!=null) {
                postStartTime = DateUtils.formatDate(record.getNpWorkTime(), DateUtils.YYYYMMDD_DOT);
                Integer year = DateUtils.intervalYearsUntilNow(record.getNpWorkTime());
                if (year == 0) postYear = "未满一年";
                else postYear = year + "";
            }

            if(record.getLpWorkTime()!=null) {
                postTime = DateUtils.formatDate(record.getLpWorkTime(), DateUtils.YYYYMMDD_DOT);
            }

            if(record.getsWorkTime()!=null) {
                adminLevelStartTime = DateUtils.formatDate(record.getsWorkTime(), DateUtils.YYYYMMDD_DOT);
                Date eWorkTime = record.geteWorkTime();
                Integer monthDiff = DateUtils.monthDiff(record.getsWorkTime(), eWorkTime == null ? new Date() : eWorkTime);
                int year = monthDiff / 12;
                if (year == 0) adminLevelYear = "未满一年";
                else adminLevelYear = year + "";
            }

            isDouble = BooleanUtils.isTrue(record.getIsDouble()) ? "是" : "否";
            String doubleUnitIds = record.getDoubleUnitIds();
            if (doubleUnitIds != null) {
                List<String> doubleUnits = new ArrayList<>();
                for (String doubleUnitId : doubleUnitIds.split(",")) {
                    Unit unit = unitMap.get(Integer.valueOf(doubleUnitId));
                    doubleUnits.add((unit != null) ? unit.getName() : "");
                }
                doubleUnit = StringUtils.join(doubleUnits, ",");
            }

            *//*String partyFullName = ""; // 所在党组织
            if (record.getPartyId() != null) {
                Party party = partyMap.get(record.getPartyId());
                if (party != null) {
                    partyFullName = party.getName();
                    if (record.getBranchId() != null) {
                        Branch branch = branchMap.get(record.getBranchId());
                        if (branch != null) {
                            partyFullName += "-" + branch.getName();
                        }
                    }
                }
            }*//*

            String subPost = ""; // 兼任单位及职务
            String subPostTime = ""; // 兼任职务现任时间
            String subPostStartTime = ""; // 兼任职务始任时间
            List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(record.getId());
            if (subCadrePosts.size() > 0) {
                CadrePost cadrePost = subCadrePosts.get(0);
                //Unit unit = unitMap.get(cadrePost.getUnitId());
                //MetaType metaType = metaTypeMap.get(cadrePost.getPostId());
                //subPost = unit.getName() + ((metaType==null)?"":metaType.getName());
                if (cadrePost.getUnitId() != null) {
                    Unit unit = unitMap.get(cadrePost.getUnitId());
                    if (unit != null) {
                        subPost += StringUtils.trimToEmpty(unit.getName());
                    }
                }
                subPost += StringUtils.trimToEmpty(cadrePost.getPost());

                subPostTime = DateUtils.formatDate(cadrePost.getLpWorkTime(), DateUtils.YYYYMMDD_DOT);
                subPostStartTime = DateUtils.formatDate(cadrePost.getNpWorkTime(), DateUtils.YYYYMMDD_DOT);
            }

            Unit unit = record.getUnit();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    unit==null?"":unit.getUnitType().getName(),
                    unit==null?"":unit.getName(),
                    record.getPost(),

                    record.getTitle(),
                    DateUtils.formatDate(record.getReservePostTime(), DateUtils.YYYYMM),
                    metaTypeService.getName(record.getAdminLevel()),
                    metaTypeService.getName(record.getPostType()),
                    isPositive,
                    record.getGender()==null?"":SystemConstants.GENDER_MAP.get(record.getGender()),

                    record.getNation(),
                    record.getNativePlace(),
                    record.getUser().getHomeplace(),
                    record.getIdcard(),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),

                    DateUtils.calAge(record.getBirth()),
                    StringUtils.trimToEmpty(partyName),
                    StringUtils.trimToEmpty(partyAddTime),
                    "", //参加工作时间
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYYMMDD_DOT),

                    metaTypeService.getName(record.getEduId()),
                    record.getDegree(),
                    DateUtils.formatDate(record.getFinishTime(), DateUtils.YYYYMM),
                    metaTypeService.getName(record.getLearnStyle()),
                    record.getSchool(),

                    record.getSchoolType()==null?"":CadreConstants.CADRE_SCHOOL_TYPE_MAP.get(record.getSchoolType()),
                    record.getMajor(),
                    *//*record.getPostClass(),
                    record.getMainPostLevel(),*//*
                    record.getProPost(),

                    DateUtils.formatDate(record.getProPostTime(), DateUtils.YYYYMMDD_DOT),
                    *//*record.getProPostLevel(),
                    DateUtils.formatDate(record.getProPostLevelTime(), DateUtils.YYYYMMDD_DOT),
                    record.getManageLevel(),
                    DateUtils.formatDate(record.getManageLevelTime(), DateUtils.YYYYMMDD_DOT),*//*

                    postDispatchCode, // 现职务任命文件
                    postTime,
                    postStartTime,
                    postYear,
                    adminLevelStartTime,

                    adminLevelYear,
                    subPost,
                    subPostTime,
                    subPostStartTime,
                    isDouble,

                    doubleUnit,
                    record.getMobile(),
                    *//*"", // 党委委员
                    "",*//*
                    record.getEmail(),

                    //partyFullName,
                    BooleanUtils.isTrue(record.getHasCrp()) ? "是" : "否",
                    record.getRemark()
            };

            Row row = sheet.createRow(rowNum++);
            row.setHeight((short) (35.7 * 18));
            for (int j = 0; j < columnCount; j++) {

                Cell cell = row.createCell(j);
                String value = values[j];
                if(StringUtils.isBlank(value)) value="-";
                cell.setCellValue(value);
                cell.setCellStyle(ExportHelper.getBodyStyle(wb));
            }
        }

       return wb;
    }*/

    //导出优秀年轻干部名单
    public void export2(Byte reserveStatus, Integer reserveType, CadreReserveViewExample example, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cadre/cadres.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String schoolName = CmTag.getSysConfig().getSchoolName();
        String typeStr = "";
        if(reserveType!=null){
            String name = metaTypeService.getName(reserveType);
            if(StringUtils.isNotBlank(name)){
                typeStr = "（" + name + "）";
            }
        }
        String str = cell.getStringCellValue()
                .replace("title", schoolName + "年轻干部名单" + typeStr);
        cell.setCellValue(str);

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        List<CadreReserveView> records = cadreReserveViewMapper.selectByExample(example);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            CadreReserveView cr = records.get(i);
            SysUserView uv = cr.getUser();
            int column = 0;
            row = sheet.getRow(startRow++);
            // 序号
            cell = row.getCell(column++);
            cell.setCellValue(i + 1);

            // 姓名
            cell = row.getCell(column++);
            cell.setCellValue(CmTag.realnameWithEmpty(uv.getRealname()));

            // 所在单位及职务
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cr.getTitle()));

            // 性别
            String gender = null;
            cell = row.getCell(column++);
            if (uv.getGender() != null) {
                gender = SystemConstants.GENDER_MAP.get(uv.getGender());
            }
            cell.setCellValue(StringUtils.trimToEmpty(gender));

            // 民族 （全都去掉“族”， 显示“汉”)
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(StringUtils.replace(uv.getNation(), "族", "")));

            // 出生时间
            String birth = DateUtils.formatDate(uv.getBirth(), birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            String age = cr.getBirth() == null ? "" : DateUtils.yearOffNow(cr.getBirth()) + "";
            cell = row.getCell(column++);
            cell.setCellValue(age);

            Map<String, String> cadreParty = CmTag.getCadreParty(cr.getIsOw(), cr.getOwGrowTime(), cr.getOwPositiveTime(), "中共",
                    cr.getDpTypeId(), cr.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");

            // 党派
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(partyName));

            // 最高学历学位
            String edu = "";
            Integer eduId = cr.getEduId();
            if (eduId != null) {
                edu = CmTag.getEduName(eduId);
            }
            if (StringUtils.isNotBlank(cr.getDegree())) {
                edu += "\r\n" + StringUtils.trimToEmpty(cr.getDegree());
            }
            cell = row.getCell(column++);
            cell.setCellValue(edu);

            // 所学专业
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cr.getMajor()));

            // 专业技术职务
            String proPost = StringUtils.defaultString(cr.getProPost(), "--");
            String manageLevel = StringUtils.defaultString(cr.getManageLevel(), "--");
            if (cr.getProPost() != null && cr.getManageLevel() == null) {
                cell = row.getCell(column++);
                cell.setCellValue(proPost);
            } else if (cr.getProPost() == null && cr.getManageLevel() != null) {
                cell = row.getCell(column++);
                cell.setCellValue(manageLevel);
            } else if (cr.getProPost() != null && cr.getManageLevel() != null) {
                cell = row.getCell(column++);
                cell.setCellValue(proPost + "\r\n" + manageLevel);
            } else {
                cell = row.getCell(column++);
                cell.setCellValue("--");
            }

            String postStartTime = ""; // 现职务始任时间
            String postYear = ""; // 现职务始任年限
            String adminLevelStartTime = ""; // 现职级始任时间
            String adminLevelYear = ""; // 任现职级年限

            if(cr.getNpWorkTime()!=null) {
                postStartTime = DateUtils.formatDate(cr.getNpWorkTime(), CmTag.getBoolProperty("postTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM);
                Integer year = DateUtils.intervalYearsUntilNow(cr.getNpWorkTime());
                if (year == 0) postYear = "未满一年";
                else postYear = year + "";
            }

            if(cr.getsWorkTime()!=null) {
                adminLevelStartTime = DateUtils.formatDate(cr.getsWorkTime(), CmTag.getBoolProperty("postTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM);
                Date eWorkTime = cr.geteWorkTime();
                Integer monthDiff = DateUtils.monthDiff(cr.getsWorkTime(), eWorkTime==null?new Date():eWorkTime);
                int year = monthDiff / 12;
                if (year == 0) adminLevelYear = "未满一年";
                else adminLevelYear = year + "";
            }

            // 现职务始任时间
            cell = row.getCell(column++);
            cell.setCellValue(postStartTime);
            // 现职务始任年限
            cell = row.getCell(column++);
            cell.setCellValue(postYear);
            // 现职级始任时间
            cell = row.getCell(column++);
            cell.setCellValue(adminLevelStartTime);
            // 任现职级年限
            cell = row.getCell(column++);
            cell.setCellValue(adminLevelYear);
        }

        String suffix = null;
        if (reserveType != null) {
            suffix = metaTypeService.getName(reserveType);
        }else {
            suffix = CadreConstants.CADRE_RESERVE_STATUS_MAP.get(reserveStatus);
        }
        String fileName = String.format("%s年轻干部名单", schoolName);
        if (StringUtils.isNotBlank(suffix)){
            fileName = String.format("%s年轻干部（" + suffix + "）名单", schoolName);
        }
        ExportHelper.output(wb, fileName + ".xlsx", response);

    }
}
