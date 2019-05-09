package controller.analysis;

import bean.MetaClassOption;
import controller.BaseController;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.crp.CrpRecord;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.cadre.common.ICadreEdu;
import persistence.cadre.common.ICadreWork;
import persistence.cadre.common.ICarde;
import sys.constants.CadreConstants;
import sys.constants.CrpConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class StatCadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("statCadre:list")
    @RequestMapping("/stat_cadre")
    public String stat_cadre(String unitTypeGroup,
                             @RequestParam(required = false, defaultValue = CadreConstants.CADRE_TYPE_CJ+"") byte cadreType,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             ModelMap modelMap, HttpServletResponse response) throws IOException {

        if (export == 1) {
            XSSFWorkbook wb = statCadreService.toXlsx(cadreType);

            String fileName = sysConfigService.getSchoolName()+
                    CadreConstants.CADRE_TYPE_MAP.get(cadreType)
                    + "情况统计表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }

        Map<String, List> rs = statCadreService.stat(unitTypeGroup, cadreType);
        modelMap.put("rs", rs);

        MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
        Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();
        modelMap.put("unitTypeGroupMap", unitTypeGroupMap);

        modelMap.put("cadreType", cadreType);

        return "analysis/cadre/stat_cadre_page";
    }

    // 分类信息统计
    @RequiresPermissions("statCadreCategory:list")
    @RequestMapping("/stat_cadre_category")
    public String stat_cadre_category(Integer cadreId, ModelMap modelMap) throws IOException {

        if (cadreId != null) {
            modelMap.put("cadre", iCadreMapper.getCadre(cadreId));
        }

        return "analysis/cadre/stat_cadre_category";
    }

    @RequiresPermissions("statCadreCategory:list")
    @RequestMapping("/stat_cadre_category_data")
    public void stat_cadre_category_data(int type, HttpServletResponse response,
                                         CadreCategorySearchBean searchBean,
                                         @RequestParam(required = false, defaultValue = "0") int export,
                                         Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }

        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        RowBounds rowBounds = new RowBounds((pageNo - 1) * pageSize, pageSize);

        int count = 0;
        List records = new ArrayList<>();

        MetaType xyUnitMetaType = metaTypeService.codeKeyMap().get("mt_unit_type_xy");

        searchBean.setCadreStatus(CadreConstants.CADRE_STATUS_MIDDLE); // 统计现任干部
        switch (type) {
            case 1: // 查找干部的（境外）学习经历

                if (export == 1) {
                    export_cadreEdus(searchBean, response);
                    return;
                }
                count = iCadreMapper.countCadreEduList(CadreConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean);
                records = iCadreMapper.selectCadreEduList(CadreConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean, rowBounds);
                break;
            case 2: // 查找干部的（境外）工作经历
                MetaType abroadMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_abroad");
                if (export == 1) {
                    export_cadreWorks(abroadMetaType, searchBean, response);
                    return;
                }
                count = iCadreMapper.countCadreWorkList(abroadMetaType.getId(), searchBean);
                records = iCadreMapper.selectCadreWorkList(abroadMetaType.getId(), searchBean, rowBounds);
                break;
            case 3: // 查找（机关）干部的（院系）工作经历
                MetaType xyMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_xy");
                if (export == 1) {
                    export_cadreWorks(xyMetaType, searchBean, response);
                    return;
                }
                int unitTypeId = xyUnitMetaType.getId();
                int workType = xyMetaType.getId();
                searchBean.setNotUnitTypeId(unitTypeId);
                count = iCadreMapper.countCadreWorkList(workType, searchBean);
                records = iCadreMapper.selectCadreWorkList(workType, searchBean, rowBounds);
                break;
            case 4: // 查找（院系）干部的（机关）工作经历
                MetaType jgMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_jg");
                if (export == 1) {
                    export_cadreWorks(jgMetaType, searchBean, response);
                    return;
                }
                unitTypeId = xyUnitMetaType.getId();
                workType = jgMetaType.getId();
                searchBean.setUnitTypeId(unitTypeId);
                count = iCadreMapper.countCadreWorkList(workType, searchBean);
                records = iCadreMapper.selectCadreWorkList(workType, searchBean, rowBounds);
                break;
            case 5: // 有校外挂职经历的干部
                if (export == 1) {
                    export_crpRecords(searchBean, response);
                    return;
                }
                count = iCadreMapper.countCrpRecordList(CrpConstants.CRP_RECORD_TYPE_OUT, searchBean);
                records = iCadreMapper.selectCrpRecordList(CrpConstants.CRP_RECORD_TYPE_OUT, searchBean, rowBounds);
                break;
            case 6: // 具有人才/荣誉称号的干部
                searchBean.setHasTalentTitle(true);
                if (export == 1) {
                    export_talentCadres(searchBean, response);
                    return;
                }
                count = iCadreMapper.countTalentCadreList(searchBean);
                records = iCadreMapper.selectTalentCadreList(searchBean, rowBounds);
                break;
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    public void export_talentCadres(CadreCategorySearchBean searchBean, HttpServletResponse response) {

        List<ICarde> records = iCadreMapper.selectTalentCadreList(searchBean, new RowBounds());
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|50", "所在单位及职务|300|left", "行政级别|100", "性别|50",
                "出生日期|100", "年龄|50", "党派|100", "专业技术职务|150", "人才/荣誉称号|800|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ICarde record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共党员",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth==null?"": DateUtils.calAge(birth),
                    cadreParty.get("partyName"),
                    cv.getProPost(),
                    cv.getTalentTitle()
            };
            valuesList.add(values);
        }
        String fileName = "具有人才/荣誉称号的干部(截止" + DateUtils.formatDate(new Date(), "yyyyMMdd")+")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }


    public void export_crpRecords(CadreCategorySearchBean searchBean, HttpServletResponse response) {

        List<CrpRecord> records = iCadreMapper.selectCrpRecordList(CrpConstants.CRP_RECORD_TYPE_OUT, searchBean, new RowBounds());
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|50", "所在单位及职务|300|left", "行政级别|100", "性别|50",
                "出生日期|100", "年龄|50", "党派|100", "专业技术职务|150",
                "挂职开始日期|120", "挂职结束日期|120", "挂职工作单位|150", "担任职务或者专技职务|300|left"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrpRecord record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共党员",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);
            String toUnit = "";
            int toUnitType = record.getToUnitType().intValue();
            if(record.getType()==CrpConstants.CRP_RECORD_TYPE_IN){
                toUnit = record.getToUnit();
            }else {
                int metaTypeId = -1;
                if (record.getType() == CrpConstants.CRP_RECORD_TYPE_OUT) {
                    metaTypeId = metaTypeService.codeKeyMap().get("mt_temppost_out_unit_other").getId();
                }else if (record.getType() == CrpConstants.CRP_RECORD_TYPE_TRANSFER) {
                    metaTypeId = metaTypeService.codeKeyMap().get("mt_temppost_transfer_unit_other").getId();
                }
                toUnit = metaTypeService.getName(toUnitType);
                if(toUnitType == metaTypeId){
                    toUnit += "：" + record.getToUnit();
                }
            }
            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth==null?"": DateUtils.calAge(birth),
                    cadreParty.get("partyName"),
                    cv.getProPost(),

                    DateUtils.formatDate(record.getStartDate(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getRealEndDate(), DateUtils.YYYYMM),
                    toUnit,
                    record.getPresentPost()
            };
            valuesList.add(values);
        }
        String fileName = "具有校外挂职经历的干部(截止" + DateUtils.formatDate(new Date(), "yyyyMMdd")+")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
    public void export_cadreWorks(MetaType cadreWorkType, CadreCategorySearchBean searchBean, HttpServletResponse response) {

        String code = cadreWorkType.getCode();
        List<ICadreWork> records = iCadreMapper.selectCadreWorkList(cadreWorkType.getId(), searchBean, new RowBounds());
        int rownum = records.size();
        List<String> titles = new ArrayList<>(Arrays.asList(new String[]{"工作证号|100", "姓名|50", "所在单位及职务|300", "行政级别|100", "性别|50",
                "出生日期|100", "年龄|50", "党派|100", "专业技术职务|150",
                "国（境）外工作开始日期|150", "国（境）外工作结束日期|150", "国（境）外工作单位及担任职务或者专技职务|450"}));

        String filename = "";
        switch (code){
            case "mt_cadre_work_type_abroad":
                filename = "具有国（境）外学习经历的干部";
                break;
            case "mt_cadre_work_type_xy":
                filename = "机关干部具有院系工作经历的";
                titles.set(9, "院系工作开始日期");
                titles.set(10, "院系工作结束日期");
                titles.set(11, "院系工作单位及担任职务或者专技职务");
                break;
            case "mt_cadre_work_type_jg":
                filename = "院系干部具有机关工作经历的";
                titles.set(9, "机关工作开始日期");
                titles.set(10, "机关工作结束日期");
                titles.set(11, "机关工作单位及担任职务或者专技职务");
                break;
        }

        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ICadreWork record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共党员",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth==null?"": DateUtils.calAge(birth),
                    cadreParty.get("partyName"),
                    cv.getProPost(),

                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYYMM),
                    record.getDetail()
            };
            valuesList.add(values);
        }
        String fileName = filename+"(截止" + DateUtils.formatDate(new Date(), "yyyyMMdd")+")";
        ExportHelper.export(titles.toArray(new String[]{}), valuesList, fileName, response);
    }

    public void export_cadreEdus(CadreCategorySearchBean searchBean, HttpServletResponse response) {

        List<ICadreEdu> records = iCadreMapper.selectCadreEduList(CadreConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean, new RowBounds());
        int rownum = records.size();
        String[] titles = {"工作证号|100", "姓名|50", "所在单位及职务|300|left", "行政级别|100", "性别|50",
                "出生日期|100", "年龄|50", "党派|100", "专业技术职务|150", "最高学历|100",
                "最高学位|100", "国（境）外学历|150", "入学时间|100", "毕业时间|100", "毕业/在读学校|200|left",
                "院系|200|left", "所学专业|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ICadreEdu record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getIsOw(), cv.getOwGrowTime(), "中共党员",
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, DateUtils.YYYY_MM_DD),
                    birth==null?"": DateUtils.calAge(birth),
                    cadreParty.get("partyName"),
                    cv.getProPost(),
                    metaTypeService.getName(cv.getEduId()),

                    cv.getDegree(),
                    metaTypeService.getName(record.getEduId()),
                    DateUtils.formatDate(record.getEnrolTime(), DateUtils.YYYYMM),
                    DateUtils.formatDate(record.getFinishTime(), DateUtils.YYYYMM),
                    record.getSchool(),

                    record.getDep(),
                    record.getMajor(),
            };
            valuesList.add(values);
        }
        String fileName = "具有国（境）外学习经历的干部(截止" + DateUtils.formatDate(new Date(), "yyyyMMdd")+")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
