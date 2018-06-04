package service.cadre;

import persistence.dispatch.common.DispatchCadreRelateBean;
import domain.base.MetaType;
import domain.cadre.CadreAdminLevel;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import sys.utils.ExportHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2017/1/19.
 */
@Service
public class CadreExportService extends BaseMapper {

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

    public SXSSFWorkbook export(Byte status, CadreViewExample example, int exportType) {

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
            headerCell.setCellValue(CmTag.getSysConfig().getSchoolName() + cadreType +"一览表");
            sheet.addMergedRegion(ExcelTool.getCellRangeAddress(rowNum, 0, rowNum, 9));
            rowNum++;
        }

        int count = records.size();
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{
                "工作证号|100", "姓名|100", "部门属性|150", "所在单位|300", "现任职务|160",
                "所在单位及职务|300", "行政级别|100", "职务属性|100", "是否正职|120", "性别|50",
                "民族|100", "籍贯|100", "出生地|100", "身份证号|150", "出生时间|100",
                "年龄|50", "党派|150", "党派加入时间|120", "参加工作时间|120", "到校时间|100",
                "最高学历|120", "最高学位|120", "毕业时间|100", "学习方式|120", "毕业学校|200",
                "学校类型|100", "所学专业|200", "全日制教育学历|150", "全日制教育毕业院校系及专业|400", "在职教育学历|150",
                "在职教育毕业院系及专业|400", "岗位类别|100", "主岗等级|160", "专业技术职务|150", "专技职务评定时间|200",
                "专技职务等级|200", "专技岗位分级时间|200", "管理岗位等级|120", "管理岗位分级时间|200",  "现职务任命文件|150",
                "任现职时间|100", "现职务始任时间|150", "现职务始任年限|120", "现职级始任时间|150",  "任现职级年限|120",
                "兼任单位及职务|250", "兼任职务现任时间|180", "兼任职务始任时间|150", "是否双肩挑|100", "双肩挑单位|100",
                "联系方式|100", "党委委员|100", "纪委委员|120", "电子信箱|200", "所属党组织|500",
                "备注|500"}));

        int[] exportCloumns_1 = new int[]{1, 2, 3, 4, 6, 7, 8, 10, 14,15,16, 17, 18,20,34, 41,51, 54};
        if(exportType==1) {
            //新增一个角色，限制查看中层干部库权限，
            // 字段为：工作证号，姓名，性别，身份证号、出生时间、年龄、学历、专业技术职务、任现职时间、
            // 部门属性、所在单位、所在单位及职务、行政级别、职务属性、党派、党派加入时间、联系方式、电子邮箱。
            List<String> _titles = new ArrayList<>();
            for (int exportCloumn : exportCloumns_1) {
                _titles.add(titles.get(exportCloumn - 1));
            }
            titles.clear();
            titles.addAll(_titles);
        }

        int columnCount = titles.size();
        Row firstRow = sheet.createRow(rowNum++);
        firstRow.setHeight((short) (35.7 * 12));

        int width;
        for (int i = 0; i < columnCount; i++) {

            String _title = titles.get(i);
            String[] split = _title.split("\\|");

            Cell cell = firstRow.createCell(i);
            cell.setCellValue(split[0]);
            cell.setCellStyle(ExportHelper.getHeadStyle(wb));

            if(split.length>1) {
                try {
                    width = Integer.valueOf(split[1]);
                    sheet.setColumnWidth(i, (short) (35.7 * width));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < count; i++) {
            CadreView record = records.get(i);
            SysUserView sysUser =  record.getUser();

            String isPositive = ""; // 是否正职
            CadrePost mainCadrePost = record.getMainCadrePost();
            if(mainCadrePost!=null && mainCadrePost.getPostId()!=null){
                MetaType metaType = metaTypeMap.get(mainCadrePost.getPostId());
                if(metaType!=null){
                    isPositive = (BooleanUtils.isTrue(metaType.getBoolAttr()))?"是":"否";
                }
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
            if(mainCadrePost!=null){
                DispatchCadreRelateBean dispatchCadreRelateBean = mainCadrePost.getDispatchCadreRelateBean();
                if(dispatchCadreRelateBean!=null){
                    Dispatch first = dispatchCadreRelateBean.getFirst();
                    Dispatch last = dispatchCadreRelateBean.getLast();
                    if(first!=null){
                        postDispatchCode = first.getDispatchCode();
                        postStartTime = DateUtils.formatDate(first.getWorkTime(), DateUtils.YYYY_MM_DD);
                        Integer year = DateUtils.intervalYearsUntilNow(first.getWorkTime());
                        if(year==0) postYear= "未满一年";
                        else postYear = year + "";
                    }

                    if(last!=null){
                        postTime = DateUtils.formatDate(last.getWorkTime(), DateUtils.YYYY_MM_DD);
                    }
                }
                isDouble = BooleanUtils.isTrue(mainCadrePost.getIsDouble())?"是":"否";
                Integer doubleUnitId = mainCadrePost.getDoubleUnitId();
                if(doubleUnitId!=null){
                    Unit unit = unitMap.get(doubleUnitId);
                    doubleUnit = (unit!=null)?unit.getName():"";
                }
            }

            CadreAdminLevel presentAdminLevel = record.getPresentAdminLevel();
            if(presentAdminLevel!=null){
                Dispatch startDispatch = presentAdminLevel.getStartDispatch();
                Dispatch endDispatch = presentAdminLevel.getEndDispatch();

                Date endDate = new Date();
                if(endDispatch!=null) endDate =endDispatch.getWorkTime();
                if(startDispatch!=null){
                    adminLevelStartTime = DateUtils.formatDate(startDispatch.getWorkTime(), DateUtils.YYYY_MM_DD);

                    Integer monthDiff = DateUtils.monthDiff(startDispatch.getWorkTime(), endDate);
                    int year = monthDiff/12;
                    if(year==0) adminLevelYear= "未满一年";
                    else adminLevelYear = year + "";
                }
            }

            String partyFullName = ""; // 所属党组织
            if(record.getPartyId()!=null){
                Party party = partyMap.get(record.getPartyId());
                if(party!=null){
                    partyFullName = party.getName();
                    if(record.getBranchId()!=null){
                        Branch branch = branchMap.get(record.getBranchId());
                        if(branch!=null){
                            partyFullName += "-" + branch.getName();
                        }
                    }
                }
            }

            String subPost = ""; // 兼任单位及职务
            String subPostTime = ""; // 兼任职务现任时间
            String subPostStartTime = ""; // 兼任职务始任时间
            List<CadrePost> subCadrePosts = cadrePostService.getSubCadrePosts(record.getId());
            if(subCadrePosts.size()>0){
                CadrePost cadrePost = subCadrePosts.get(0);
                //Unit unit = unitMap.get(cadrePost.getUnitId());
                //MetaType metaType = metaTypeMap.get(cadrePost.getPostId());
                //subPost = unit.getName() + ((metaType==null)?"":metaType.getName());
                if(cadrePost.getUnitId()!=null) {
                    Unit unit = unitMap.get(cadrePost.getUnitId());
                    if(unit!=null){
                        subPost += StringUtils.trimToEmpty(unit.getName());
                    }
                }
                subPost += cadrePost.getPost();

                DispatchCadreRelateBean dispatchCadreRelateBean = cadrePost.getDispatchCadreRelateBean();
                if(dispatchCadreRelateBean!=null){
                    Dispatch first = dispatchCadreRelateBean.getFirst();
                    Dispatch last = dispatchCadreRelateBean.getLast();

                    if(last!=null) subPostTime = DateUtils.formatDate(last.getWorkTime(), DateUtils.YYYY_MM_DD);
                    if(first!=null) subPostStartTime = DateUtils.formatDate(first.getWorkTime(), DateUtils.YYYY_MM_DD);
                }
            }

            String _fulltimeEdu = "";
            String _fulltimeMajor = "";
            String _onjobEdu = "";
            String _onjobMajor = "";
            CadreEdu[] cadreEdus = record.getCadreEdus();
            CadreEdu fulltimeEdu = cadreEdus[0];
            CadreEdu onjobEdu = cadreEdus[1];
            if(fulltimeEdu!=null){
                Integer eduId = fulltimeEdu.getEduId();
                //String degree = fulltimeEdu.getDegree();
                _fulltimeEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
                _fulltimeMajor = fulltimeEdu.getSchool() + fulltimeEdu.getDep() + fulltimeEdu.getMajor();
            }
            if(onjobEdu!=null){
                Integer eduId = onjobEdu.getEduId();
                //String degree = onjobEdu.getDegree();
                _onjobEdu = metaTypeMap.get(eduId).getName() /*+ (degree!=null?degree:"")*/;
                _onjobMajor = onjobEdu.getSchool() + onjobEdu.getDep() + onjobEdu.getMajor();
            }

            Unit unit = record.getUnit();
            List<String> values = new ArrayList<>(Arrays.asList(new String[]{
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    unit==null?"":unit.getUnitType().getName(),
                    unit==null?"":unit.getName(),
                    record.getPost(),

                    record.getTitle(),
                    metaTypeService.getName(record.getTypeId()),
                    metaTypeService.getName(record.getPostId()),
                    isPositive,
                    record.getGender()==null?"":SystemConstants.GENDER_MAP.get(record.getGender()),

                    record.getNation(),
                    record.getNativePlace(),
                    record.getUser().getHomeplace(),
                    record.getIdcard(),
                    DateUtils.formatDate(record.getBirth(), DateUtils.YYYY_MM_DD),

                    record.getBirth()==null?"":DateUtils.yearOffNow(record.getBirth())+"",
                    StringUtils.trimToEmpty(partyName),
                    StringUtils.trimToEmpty(partyAddTime),
                    DateUtils.formatDate(record.getWorkTime(), DateUtils.YYYY_MM_DD), //参加工作时间
                    DateUtils.formatDate(record.getArriveTime(), DateUtils.YYYY_MM_DD),

                    metaTypeService.getName(record.getEduId()),
                    record.getDegree(),
                    DateUtils.formatDate(record.getFinishTime(), "yyyy.MM"),
                    metaTypeService.getName(record.getLearnStyle()),
                    record.getSchool(),

                    // 学校类型
                    record.getSchoolType()==null?"":CadreConstants.CADRE_SCHOOL_TYPE_MAP.get(record.getSchoolType()),
                    record.getMajor(),
                    _fulltimeEdu,
                    _fulltimeMajor,
                    _onjobEdu,
                    _onjobMajor,
                    record.getPostClass(),
                    record.getMainPostLevel(),
                    record.getProPost(),

                    DateUtils.formatDate(record.getProPostTime(), DateUtils.YYYY_MM_DD),
                    record.getProPostLevel(),
                    DateUtils.formatDate(record.getProPostLevelTime(), DateUtils.YYYY_MM_DD),
                    record.getManageLevel(),
                    DateUtils.formatDate(record.getManageLevelTime(), DateUtils.YYYY_MM_DD),

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
                    "", // 党委委员
                    "",
                    record.getEmail(),

                    partyFullName,
                    record.getRemark()
            }));

            if(exportType==1) {
                //新增一个角色，限制查看中层干部库权限，
                // 字段为：工作证号，姓名，部门属性、所在单位、所在单位及职务、行政级别、职务属性、党派、党派加入时间、联系方式、电子邮箱。
                List<String> _values = new ArrayList<>();
                //int[] exportCloumns = new int[]{1, 2, 3, 4, 6, 7, 8, 17, 18, 51, 54};
                for (int exportCloumn : exportCloumns_1) {
                    _values.add(values.get(exportCloumn - 1));
                }
                values.clear();
                values.addAll(_values);
            }

            Row row = sheet.createRow(rowNum++);
            row.setHeight((short) (35.7 * 18));
            for (int j = 0; j < columnCount; j++) {

                Cell cell = row.createCell(j);
                String value = values.get(j);
                if(StringUtils.isBlank(value)) value="-";
                cell.setCellValue(value);
                cell.setCellStyle(ExportHelper.getBodyStyle(wb));
            }
        }

       return wb;
    }
}
