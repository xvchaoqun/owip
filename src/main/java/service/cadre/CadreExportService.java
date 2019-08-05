package service.cadre;

import domain.base.MetaType;
import domain.cadre.CadreEdu;
import domain.cadre.CadrePost;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.dispatch.Dispatch;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import domain.unit.Unit;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import service.BaseMapper;
import service.base.MetaTypeService;
import service.party.BranchService;
import service.party.PartyService;
import service.unit.UnitService;
import sys.constants.CadreConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.xlsx.ExcelTool;
import sys.utils.DateUtils;
import sys.utils.ExcelUtils;
import sys.utils.ExportHelper;
import sys.utils.HtmlEscapeUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by fafa on 2017/1/19.
 */
@Service
public class CadreExportService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected MetaTypeService metaTypeService;
    @Autowired
    protected UnitService unitService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected CadrePostService cadrePostService;

    public List<String> getTitles(){

        String cadreStateName = HtmlEscapeUtils.getTextFromHTML(CmTag.getStringProperty("cadreStateName"));
        return new ArrayList<>(Arrays.asList(new String[]{
                /*1*/"工作证号|100", "姓名|100", "干部类型|100", cadreStateName + "|100", "部门属性|150",
                /*6*/"所在单位|300", "现任职务|160", "所在单位及职务|300", "行政级别|100", "职务属性|100",
                /*11*/"是否正职|120",  "是否班子负责人|120", "性别|50", "民族|100", "籍贯|100",
                /*16*/"出生地|100", "身份证号|150", "出生时间|100", "年龄|50", "党派|150",
                /*21*/"党派加入时间|120", "参加工作时间|120", "到校时间|100", "最高学历|120", "最高学位|120",
                /*26*/"毕业时间|100", "学习方式|120", "毕业学校|200", "学校类型|100", "所学专业|200",
                /*31*/"全日制教育学历|150", "全日制教育毕业院校系及专业|400", "在职教育学历|150", "在职教育毕业院系及专业|400",
                /*35*//*"岗位类别|100", "主岗等级|160",*/ "专业技术职务|150", "专技职务评定时间|200",
                /*37*//*"专技职务等级|200", "专技岗位分级时间|200", "管理岗位等级|120", "管理岗位分级时间|200", */"现职务任命文件|150",
                /*38*/"任现职时间|100", "现职务始任时间|150", "现职务始任年限|120", "现职级始任时间|150", "任现职级年限|120",
                /*43*/"兼任单位及职务|250", "兼任职务现任时间|180", "兼任职务始任时间|150", "是否双肩挑|100", "双肩挑单位|100",
                /*48*/"联系方式|100", /*"党委委员|100", "纪委委员|120",*/ "电子信箱|200", "所属党组织|500",
                 /*51*/"是否有挂职经历|100", "备注|500"}));
    }

    // 导出一览表
    public SXSSFWorkbook export(Byte status, CadreViewExample example, int exportType, Integer[] cols) {

        String cadreType = CadreConstants.CADRE_STATUS_MAP.get(status);

        Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
        Map<Integer, Unit> unitMap = unitService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        List<CadreView> records = cadreViewMapper.selectByExample(example);

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
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + cadreType + "一览表");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        int count = records.size();

        List<String> titles = getTitles();

        boolean hasKjCadre = CmTag.getBoolProperty("hasKjCadre");
        boolean useCadreState = CmTag.getBoolProperty("useCadreState");
        boolean hasPartyModule = CmTag.getBoolProperty("hasPartyModule");

        int[] exportCloumns_1 = new int[]{1, 2, 3, 5, 6, 8, 9, 10, 13, 17, 18, 19, 20, 21, 24, 35, 38, 48, 49};
        if (exportType == 1) {
            //新增一个角色，限制查看干部库权限，
            // 字段为：1工作证号，姓名，干部类型，5部门属性，所在单位、8所在单位及职务、行政级别、职务属性，13性别，
            // 17身份证号、出生时间、19年龄、党派、党派加入时间、24最高学历、
            // 35专业技术职务、任现职时间、联系方式、电子邮箱。
            List<String> _titles = new ArrayList<>();
            for (int exportCloumn : exportCloumns_1) {
                _titles.add(titles.get(exportCloumn - 1));
            }
            titles.clear();
            titles.addAll(_titles);

            if(!hasKjCadre){
                titles.remove(2);
            }
        }else {
            if(!hasKjCadre && !useCadreState){
                titles.remove(2);
                titles.remove(2);
            }else if(!hasKjCadre){
                titles.remove(2);
            }else if(!useCadreState){
                titles.remove(3);
            }
            if(!hasPartyModule){
                titles.remove(titles.size()-3); // 去掉所在党组织
            }

            if(cols!=null && cols.length>0){
                // 选择导出列
                List<String> _titles = new ArrayList<>();
                for (int col : cols) {
                    _titles.add(titles.get(col));
                }
                titles.clear();
                titles.addAll(_titles);
            }
        }

        int columnCount = titles.size();
        Row firstRow = sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 18));

        int width;
        for (int i = 0; i < columnCount; i++) {

            String _title = titles.get(i);
            String[] split = _title.split("\\|");

            Cell cell = firstRow.createCell(i);
            cell.setCellValue(split[0]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));

            if (split.length > 1) {
                try {
                    width = Integer.valueOf(split[1]);
                    sheet.setColumnWidth(i, (short) (35.7 * width));
                } catch (Exception e) {
                    logger.error("异常", e);
                }
            }
        }

        for (int i = 0; i < count; i++) {
            CadreView record = records.get(i);
            SysUserView sysUser = record.getUser();

            String isPositive = BooleanUtils.isTrue(record.getIsPrincipal())?"是":"否"; // 是否正职

            String _leaderType = "--"; // 是否班子负责人
            Byte leaderType = record.getLeaderType();
            if(leaderType!=null) {
                _leaderType = SystemConstants.UNIT_POST_LEADER_TYPE_MAP.get(leaderType);
            }

            Map<String, String> cadreParty = CmTag.getCadreParty(record.getIsOw(), record.getOwGrowTime(), "中共党员",
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

            String partyFullName = ""; // 所属党组织
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
            }

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

            String _fulltimeEdu = "";
            String _fulltimeMajor = "";
            String _onjobEdu = "";
            String _onjobMajor = "";
            CadreEdu[] cadreEdus = record.getCadreEdus();
            CadreEdu fulltimeEdu = cadreEdus[0];
            CadreEdu onjobEdu = cadreEdus[1];
            if (fulltimeEdu != null) {
                Integer eduId = fulltimeEdu.getEduId();
                if(eduId!=null) {
                    //String degree = fulltimeEdu.getDegree();
                    _fulltimeEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
                    _fulltimeMajor = fulltimeEdu.getSchool() + fulltimeEdu.getDep() + fulltimeEdu.getMajor();
                }
            }
            if (onjobEdu != null) {
                Integer eduId = onjobEdu.getEduId();
                if(eduId!=null) {
                    //String degree = onjobEdu.getDegree();
                    _onjobEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
                    _onjobMajor = onjobEdu.getSchool() + onjobEdu.getDep() + onjobEdu.getMajor();
                }
            }

            Unit unit = record.getUnit();
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    CadreConstants.CADRE_TYPE_MAP.get(record.getType()),
                    metaTypeService.getName(record.getState()),
                    unit == null ? "" : unit.getUnitType().getName(),

                    unit == null ? "" : unit.getName(),
                    record.getPost(),
                    record.getTitle(),
                    metaTypeService.getName(record.getAdminLevel()),
                    metaTypeService.getName(record.getPostType()),

                    isPositive,
                    _leaderType,
                    record.getGender() == null ? "" : SystemConstants.GENDER_MAP.get(record.getGender()),
                    record.getNation(),
                    record.getNativePlace(),

                    record.getUser().getHomeplace(),
                    record.getIdcard(),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYYMMDD_DOT),
                    record.getBirth() == null ? "" : DateUtils.yearOffNow(record.getBirth()) + "",
                    StringUtils.trimToEmpty(partyName),

                    StringUtils.trimToEmpty(partyAddTime),
                    DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYYMM), //参加工作时间
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYYMM),
                    metaTypeService.getName(record.getEduId()),
                    record.getDegree(),

                    DateUtils.formatDate(record.getFinishTime(), DateUtils.YYYYMM),
                    metaTypeService.getName(record.getLearnStyle()),
                    record.getSchool(),
                    // 学校类型
                    record.getSchoolType() == null ? "" : CadreConstants.CADRE_SCHOOL_TYPE_MAP.get(record.getSchoolType()),
                    record.getMajor(),

                    _fulltimeEdu,
                    _fulltimeMajor,
                    _onjobEdu,
                    _onjobMajor,

                    /*record.getPostClass(),
                    record.getMainPostLevel(),*/
                    record.getProPost(),
                    DateUtils.formatDate(record.getProPostTime(),
                            CmTag.getBoolProperty("proPostTimeToDay")?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),

                    /*record.getProPostLevel(),
                    DateUtils.formatDate(record.getProPostLevelTime(), DateUtils.YYYYMMDD_DOT),
                    record.getManageLevel(),
                    DateUtils.formatDate(record.getManageLevelTime(), DateUtils.YYYYMMDD_DOT),*/
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
                    /*"", // 党委委员
                    "",*/
                    record.getEmail(),
                    partyFullName,

                    BooleanUtils.isTrue(record.getHasCrp()) ? "是" : "否",
                    record.getRemark()
            }));

            if (exportType == 1) {

                List<String> _values = new ArrayList<>();
                for (int exportCloumn : exportCloumns_1) {
                    _values.add(values.get(exportCloumn - 1));
                }
                values.clear();
                values.addAll(_values);

                if(!hasKjCadre){
                    values.remove(2);
                }
            }else{
                if(!hasKjCadre && !useCadreState){
                    values.remove(2);
                    values.remove(2);
                }else if(!hasKjCadre){
                    values.remove(2);
                }else if(!useCadreState){
                    values.remove(3);
                }
                if(!hasPartyModule){
                    values.remove(values.size()-3); // 去掉所在党组织
                }

                if(cols!=null && cols.length>0){
                    // 选择导出列
                    List<String> _values = new ArrayList<>();
                    for (int col : cols) {
                        _values.add(values.get(col));
                    }
                    values.clear();
                    values.addAll(_values);
                }
            }

            Row row = sheet.createRow(rowNum++);
            row.setHeight((short) (35.7 * 18));
            for (int j = 0; j < columnCount; j++) {

                Cell cell = row.createCell(j);
                String value = values.get(j);
                if (StringUtils.isBlank(value)) value = "-";
                cell.setCellValue(value);
                cell.setCellStyle(ExportHelper.getBodyStyle(wb));
            }
        }

        return wb;
    }

    // 导出干部名单
    public void exportCadres(CadreViewExample example, HttpServletResponse response) throws IOException {

        InputStream is = new FileInputStream(ResourceUtils.getFile("classpath:xlsx/cadre/cadres.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        XSSFCell cell = row.getCell(0);
        String schoolName = CmTag.getSysConfig().getSchoolName();
        String str = cell.getStringCellValue()
                .replace("school", schoolName);
        cell.setCellValue(str);

        List<CadreView> records = cadreViewMapper.selectByExample(example);

        int startRow = 2;
        int rowCount = records.size();
        ExcelUtils.insertRow(wb, sheet, startRow, rowCount - 1);
        for (int i = 0; i < rowCount; i++) {

            CadreView cv = records.get(i);
            SysUserView uv = cv.getUser();
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
            cell.setCellValue(StringUtils.trimToEmpty(cv.getTitle()));

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
            String birth = DateUtils.formatDate(uv.getBirth(), DateUtils.YYYYMM);
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(birth));

            // 年龄
            String age = cv.getBirth() == null ? "" : DateUtils.yearOffNow(cv.getBirth()) + "";
            cell = row.getCell(column++);
            cell.setCellValue(age);

            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);
            String partyName = cadreParty.get("partyName");
            //String partyAddTime = cadreParty.get("growTime");

            // 党派
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(partyName));

            // 最高学历学位
            String edu = "";
            Integer eduId = cv.getEduId();
            if (eduId != null) {

                MetaType metaType = metaTypeMapper.selectByPrimaryKey(eduId);
                if (StringUtils.equals(metaType.getCode(), "mt_edu_doctor")) {
                    edu += "博士";
                } else if (StringUtils.equals(metaType.getCode(), "mt_edu_master")
                        || StringUtils.equals(metaType.getCode(), "mt_edu_sstd")) {
                    edu += "硕士";
                } else if (StringUtils.equals(metaType.getCode(), "mt_edu_yjskcb")) {
                    edu += "研究生课程班";
                } else if (StringUtils.equals(metaType.getCode(), "mt_edu_bk")) {
                    edu += "学士";
                } else if (StringUtils.equals(metaType.getCode(), "mt_edu_zk")) {
                    edu += "大专";
                } else if (StringUtils.equals(metaType.getCode(), "mt_edu_zz")) {
                    edu += "中专";
                } else {
                    edu += metaType.getName();
                }
            }
            cell = row.getCell(column++);
            cell.setCellValue(edu);

            // 所学专业
            cell = row.getCell(column++);
            cell.setCellValue(StringUtils.trimToEmpty(cv.getMajor()));

            // 专业技术职务
            String proPost = StringUtils.defaultString(cv.getProPost(), "--");
            String manageLevel = StringUtils.defaultString(cv.getManageLevel(), "--");
            if (cv.getProPost() != null && cv.getManageLevel() == null) {
                cell = row.getCell(column++);
                cell.setCellValue(proPost);
            } else if (cv.getProPost() == null && cv.getManageLevel() != null) {
                cell = row.getCell(column++);
                cell.setCellValue(manageLevel);
            } else if (cv.getProPost() != null && cv.getManageLevel() != null) {
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

            if(cv.getNpWorkTime()!=null) {
                postStartTime = DateUtils.formatDate(cv.getNpWorkTime(), DateUtils.YYYYMM);
                Integer year = DateUtils.intervalYearsUntilNow(cv.getNpWorkTime());
                if (year == 0) postYear = "未满一年";
                else postYear = year + "";
            }

            if(cv.getsWorkTime()!=null) {
                adminLevelStartTime = DateUtils.formatDate(cv.getsWorkTime(), DateUtils.YYYYMM);
                Date eWorkTime = cv.geteWorkTime();
                Integer monthDiff = DateUtils.monthDiff(cv.getsWorkTime(), eWorkTime==null?new Date():eWorkTime);
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

        String fileName = String.format("%s干部名单", schoolName);
        ExportHelper.output(wb, fileName + ".xlsx", response);
    }
}
