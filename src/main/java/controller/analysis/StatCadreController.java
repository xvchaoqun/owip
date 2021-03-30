package controller.analysis;

import bean.MetaClassOption;
import controller.BaseController;
import domain.base.MetaClass;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.crp.CrpRecord;
import mixin.MixinUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.cadre.common.*;
import sys.constants.CadreConstants;
import sys.constants.CrpConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class StatCadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("statCadre:list")
    @RequestMapping("/stat_cadre")
    public String stat_cadre(String unitTypeGroup,
                             Integer[] adminLevels,
                             Integer[] labels,
                             @RequestParam(required = false, defaultValue = CadreConstants.CADRE_CATEGORY_CJ+"") byte cadreCategory,
                             Boolean isPrincipal, // 是否正职
                             //是否为保留待遇干部信息，指第一主职无关联岗位的干部
                             Boolean isKeepSalary,
                             Integer startNowPostAge,
                             Integer endNowPostAge,
                             @RequestParam(required = false, defaultValue = "0") int export, // 1：导出， 2：弹出列表

                             // 点击数字弹出明细列表参数
                             String firstTypeCode,//类别
                             Integer firstTypeNum,//类别分类
                             Integer secondNum,//第二类别分类
                             ModelMap modelMap,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        CadreSearchBean searchBean = CadreSearchBean.getInstance(unitTypeGroup, cadreCategory);

        if (labels != null) {

            List<Integer> selectLabels = Arrays.asList(labels);
            searchBean.setLabels(selectLabels);

            modelMap.put("selectLabels", selectLabels);
        }
        if (adminLevels != null) {

            List<Integer> selectAdminLevels = Arrays.asList(adminLevels);
            searchBean.setAdminLevels(selectAdminLevels);

            modelMap.put("selectAdminLevels", selectAdminLevels);
        }

        searchBean.setPrincipal(isPrincipal);
        searchBean.setKeepSalary(isKeepSalary);
        searchBean.setMinNowPostAge(startNowPostAge);
        searchBean.setMaxNowPostAge(endNowPostAge);

        if (export == 1) {

            searchBean.setUnitTypeGroup(null);
            XSSFWorkbook wb = statCadreService.toXlsx(searchBean);

            String fileName = sysConfigService.getSchoolName()+
                    CadreConstants.CADRE_CATEGORY_MAP.get(cadreCategory)
                    + "情况统计表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }else if(export==2){

            // 点击数字弹出明细列表
            return stat_cadre_list(firstTypeCode,
                    firstTypeNum, secondNum, searchBean, modelMap);
        }

        Map<String, List> rs;
        // 如果是默认首页（即只有cadreType参数），则读取缓存 （如果CadreSearchBean有字段的增加，需要修改此处判断条件）
        if(searchBean.getKeepSalary()==null
                && searchBean.getPrincipal()==null
                && searchBean.getUnitTypeGroup()==null
                && CollectionUtils.size(searchBean.getAdminLevels())==0
                && CollectionUtils.size(searchBean.getLabels())==0
                && searchBean.getMaxNowPostAge()==null
                && searchBean.getMinNowPostAge()==null){

             rs = statCadreService.statCache(cadreCategory);
        }else{
             rs = statCadreService.stat(searchBean);
        }

        Map<Integer, List> eduRowMap = statCadreService.eduRowMap(searchBean);
        modelMap.put("eduRowMap", eduRowMap);
        int s = rs.size();
        for (Map.Entry<Integer, List> entry : eduRowMap.entrySet()) {
            rs.put("row"+(++s), entry.getValue());
        }

        modelMap.put("rs", rs);

        MetaClass mcUnitType = CmTag.getMetaClassByCode("mc_unit_type");
        Map<String, MetaClassOption> unitTypeGroupMap = mcUnitType.getOptions();
        modelMap.put("unitTypeGroupMap", unitTypeGroupMap);

        modelMap.put("unitTypeGroup",unitTypeGroup);
        modelMap.put("cadreCategory",cadreCategory);

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
    public void stat_cadre_category_data(int cls, HttpServletResponse response,
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

        searchBean.setCadreStatus(CadreConstants.CADRE_STATUS_CJ); // 统计现任干部
        switch (cls) {
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
                int workType = xyMetaType.getId();

                MetaType mtCadreTypeJg = CmTag.getMetaTypeByCode("mt_cadre_type_jg");
                searchBean.setType(mtCadreTypeJg.getId());
                count = iCadreMapper.countCadreWorkList(workType, searchBean);
                records = iCadreMapper.selectCadreWorkList(workType, searchBean, rowBounds);
                break;
            case 4: // 查找（院系）干部的（机关）工作经历
                MetaType jgMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_jg");
                if (export == 1) {
                    export_cadreWorks(jgMetaType, searchBean, response);
                    return;
                }
                workType = jgMetaType.getId();
                MetaType mtCadreTypeYx = CmTag.getMetaTypeByCode("mt_cadre_type_yx");
                searchBean.setType(mtCadreTypeYx.getId());
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
                "出生日期|100", "年龄|50", "政治面貌|100", "专业技术职务|150", "人才/荣誉称号|800|left"};

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ICarde record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();

            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getUserId(), cv.getIsOw(), cv.getOwGrowTime(), cv.getOwPositiveTime(),
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    birth== null ? "" : DateUtils.yearOffNow(birthToDay?birth:DateUtils.getFirstDayOfMonth(birth)) + "",
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
                "出生日期|100", "年龄|50", "政治面貌|100", "专业技术职务|150",
                "挂职开始日期|120", "挂职结束日期|120", "挂职工作单位|150", "担任职务或者专技职务|300|left"};

        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CrpRecord record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getUserId(), cv.getIsOw(), cv.getOwGrowTime(), cv.getOwPositiveTime(),
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

                    DateUtils.formatDate(birth, birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    birth== null ? "" : DateUtils.yearOffNow(birthToDay?birth:DateUtils.getFirstDayOfMonth(birth)) + "",

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
                "出生日期|100", "年龄|50", "政治面貌|100", "专业技术职务|150",
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
        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ICadreWork record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getUserId(), cv.getIsOw(), cv.getOwGrowTime(),
                    cv.getOwPositiveTime(), cv.getDpTypeId(), cv.getDpGrowTime(), true);

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    birth== null ? "" : DateUtils.yearOffNow(birthToDay?birth:DateUtils.getFirstDayOfMonth(birth)) + "",
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
                "出生日期|100", "年龄|50", "政治面貌|100", "专业技术职务|150", "最高学历|100",
                "最高学位|100", "国（境）外学历|150", "入学时间|100", "毕业时间|100", "毕业/在读学校|200|left",
                "院系|200|left", "所学专业|150"};
        boolean birthToDay = CmTag.getBoolProperty("birthToDay");
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            ICadreEdu record = records.get(i);
            CadreView cv = record.getCadre();
            Byte gender = cv.getGender();
            Date birth = cv.getBirth();
            Map<String, String> cadreParty = CmTag.getCadreParty(cv.getUserId(), cv.getIsOw(), cv.getOwGrowTime(), cv.getOwPositiveTime(),
                    cv.getDpTypeId(), cv.getDpGrowTime(), true);

            String[] values = {
                    cv.getCode(),
                    cv.getRealname(),
                    cv.getTitle(),
                    metaTypeService.getName(cv.getAdminLevel()),
                    gender==null?"": SystemConstants.GENDER_MAP.get(gender),

                    DateUtils.formatDate(birth, birthToDay?DateUtils.YYYYMMDD_DOT:DateUtils.YYYYMM),
                    birth== null ? "" : DateUtils.yearOffNow(birthToDay?birth:DateUtils.getFirstDayOfMonth(birth)) + "",
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

    // 点击数字弹出明细列表
    private String stat_cadre_list(  String firstTypeCode,//类别
                                     Integer firstTypeNum,//类别分类
                                     Integer secondNum,//第二类别分类
                                     CadreSearchBean searchBean,
                                     ModelMap modelMap){

        List<CadreView> cadreViews = new ArrayList<>();

        firstTypeCode = StringUtils.trimToNull(firstTypeCode);

        if (StringUtils.equals(firstTypeCode,"all"))//全部类型
            cadreViews = statCadreService.allCadreList(searchBean, secondNum);

        if (StringUtils.equals(firstTypeCode,"adminLevel"))//行政级别
            cadreViews = statCadreService.adminLevelList(searchBean, firstTypeNum, secondNum);

         if (StringUtils.equals(firstTypeCode,"nation"))//民族
            cadreViews = statCadreService.nationList(searchBean, firstTypeNum, secondNum);

         if (StringUtils.equals(firstTypeCode,"politicsStatus"))//政治面貌
             cadreViews = statCadreService.psList(searchBean, firstTypeNum, secondNum);

         if (StringUtils.equals(firstTypeCode,"age"))//年龄分布
             cadreViews = statCadreService.ageList(searchBean, firstTypeNum, secondNum);

         if (StringUtils.equals(firstTypeCode,"postLevel"))//职称分布
             cadreViews = statCadreService.postLevelList(searchBean, firstTypeNum, secondNum);

         if (StringUtils.equals(firstTypeCode,"degree"))//学位分布
             cadreViews = statCadreService.degreeTypeList(searchBean, firstTypeNum, secondNum);

         if (StringUtils.equals(firstTypeCode,"isNotDouble"))//专职干部
             cadreViews = statCadreService.isDoubleList(searchBean, secondNum, false);
         if (StringUtils.equals(firstTypeCode,"isDouble"))//双肩挑干部
             cadreViews = statCadreService.isDoubleList(searchBean, secondNum, true);

         if (StringUtils.equals(firstTypeCode,"education"))//学历
             cadreViews = statCadreService.educationList(searchBean, firstTypeNum, secondNum);

        modelMap.put("cadreViews",cadreViews);
        return "analysis/cadre/cadres";
    }

    @RequestMapping("/stat_cadre_index_page")
    public String stat_cadre_index_page(ModelMap modelMap) {

        Integer statPartyMemberCount = CmTag.getIntProperty("statPartyMemberCount");
        statPartyMemberCount = (statPartyMemberCount == null) ? 20 : statPartyMemberCount;

        modelMap.put("statPartyMemberCount", NumberUtils.toHanStr(statPartyMemberCount));

        return "analysis/cadre/stat_cadre_index_page";
    }

    // 干部数量统计
    @RequestMapping("/stat_cadre_count")
    public String stat_cadre_count() {

        return "analysis/cadre/stat_cadre_count";
    }

    @RequestMapping("/stat_cadre_count_data")
    @ResponseBody
    public Map stat_cadre_count_data(Byte cadreCategory) {

        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);

        int cadreCount  = statCadreMapper.countCadre(searchBean);
        int adminLevelCount=0;
        // 行政级别
        Map<String,Integer> statCadreCountMap = new LinkedHashMap<>();
        List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_adminLevel(searchBean);
        for(StatCadreBean scb:adminLevelList){
            statCadreCountMap.put(CmTag.getMetaTypeByCode(scb.adminLevelCode).getName(),scb.num);
            adminLevelCount += scb.num;
        }
        if(cadreCount != adminLevelCount){
            statCadreCountMap.put("其他",cadreCount-adminLevelCount);
        }

        return statCadreCountMap;
    }

    // 干部性别，民族统计
    @RequestMapping("/stat_cadreOther_count")
    public String stat_cadreOther_count() {

        return "analysis/cadre/stat_cadreOther_count";
    }

    // 干部性别，民族统计数据
    @RequestMapping("/stat_cadreOther_count_data")
    @ResponseBody
    public Map stat_cadreOther_count(Integer type ,Byte cadreCategory) {

        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);
        Map otherMap = new LinkedHashMap();
        int cadreCount  = statCadreMapper.countCadre(searchBean);
        if (type == 1) {
            List<StatCadreBean> genderList = statCadreMapper.cadre_stat_gender(searchBean);

            int gender1 = 0, gender2 = 0, genderCount = 0;
            for (StatCadreBean bean : genderList) {
                if (bean.getGender() == SystemConstants.GENDER_MALE) {
                    gender1=bean.getNum();
                    otherMap.put("男", bean.getNum());
                } else if (bean.getGender() == SystemConstants.GENDER_FEMALE) {
                    gender2=bean.getNum();
                }
                genderCount +=bean.getNum();
            }
            otherMap.put("男", gender1);
            otherMap.put("女", gender2);
            if(cadreCount != genderCount){
                otherMap.put("其他", cadreCount-genderCount);
            }

        } else if(type == 2) {
            List<StatCadreBean> nationList = statCadreMapper.cadre_stat_nation(searchBean);

            int nation1 = 0, nation2 = 0, nationCount = 0;
            for (StatCadreBean bean : nationList) {
                if (StringUtils.contains(bean.getNation(), "汉")) {
                    nation1 += bean.getNum();
                } else {
                    nation2 += bean.getNum();
                }
                nationCount +=bean.getNum();
            }
            otherMap.put("汉族", nation1);
            otherMap.put("少数民族", nation2);
            if(cadreCount != nationCount){
                otherMap.put("其他", cadreCount-nationCount);
            }

        }

        return otherMap;
    }

    // 干部政治面貌统计
    @RequestMapping("/stat_cadreDp_count")
    public String stat_cadreDp_count() {

        return "analysis/cadre/stat_cadre_dp";
    }

    // 干部政治面貌统计数据
    @RequestMapping("/stat_cadreDp_count_data")
    @ResponseBody
    public Map stat_cadreDp_count(Byte cadreCategory) {
        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);

        MetaType metaType = CmTag.getMetaTypeByCode("mt_dp_qz");  //群众
        int crowdId = metaType.getId();
        Map cadreDpMap = new LinkedHashMap();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_dp(crowdId, searchBean);
        cadreDpMap.put("中共党员",totalBean == null ?0:totalBean.getNum2());
        cadreDpMap.put("民主党派",totalBean == null ?0:totalBean.getNum1());
        cadreDpMap.put("群众",totalBean == null ?0:totalBean.getNum3());

        return cadreDpMap;
    }

    // 干部年龄统计
    @RequestMapping("/stat_cadreAge_count")
    public String stat_cadreAge_count() {

        return "analysis/cadre/stat_cadre_age";
    }

    // 干部年龄统计数据
    @RequestMapping("/stat_cadre_age_data")
    @ResponseBody
    public Map stat_cadreAge_count(Byte cadreCategory) {
        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);
        int cadreCount  = statCadreMapper.countCadre(searchBean);
        int ageCount = 0;
        Map cadreAgeMap = new LinkedHashMap();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_age(searchBean);
        cadreAgeMap.put("30岁及以下",totalBean == null ?0:totalBean.getNum1());
        cadreAgeMap.put("31-35岁",totalBean == null ?0:totalBean.getNum2());
        cadreAgeMap.put("36-40岁",totalBean == null ?0:totalBean.getNum3());
        cadreAgeMap.put("41-45岁",totalBean == null ?0:totalBean.getNum4());
        cadreAgeMap.put("46-50岁",totalBean == null ?0:totalBean.getNum5());
        cadreAgeMap.put("51-55岁",totalBean == null ?0:totalBean.getNum6());
        cadreAgeMap.put("55岁以上",totalBean == null ?0:totalBean.getNum7());
        ageCount=totalBean.getNum1()+totalBean.getNum2()+totalBean.getNum3()+totalBean.getNum4()
                +totalBean.getNum5()+totalBean.getNum6()+totalBean.getNum7();
        if(totalBean != null && cadreCount != ageCount){
            cadreAgeMap.put("其他",cadreCount-ageCount);
        }

        return cadreAgeMap;
    }

    // 干部职级统计
    @RequestMapping("/stat_cadrePost_count")
    public String stat_cadrePost_count() {
        return "analysis/cadre/stat_cadre_post";
    }

    // 干部职级统计数据
    @RequestMapping("/stat_cadrePost_count_data")
    @ResponseBody
    public Map stat_cadrePost_count(Byte cadreCategory) {
        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);

        Map cadrePostMap = new LinkedHashMap();

        StatCadreBean totalBean = statCadreMapper.cadre_stat_post(searchBean);
        cadrePostMap.put("正高",totalBean == null ?0:totalBean.getNum1());
        cadrePostMap.put("副高",totalBean == null ?0:totalBean.getNum2());
        cadrePostMap.put("中（初）级",totalBean == null ?0:totalBean.getNum3());
        cadrePostMap.put("其他",totalBean == null ?0:totalBean.getNum4());

        return cadrePostMap;
    }

    // 干部学位统计
    @RequestMapping("/stat_cadreDegree_count")
    public String stat_cadreDegree_count() {
        return "analysis/cadre/stat_cadre_degree";
    }

    // 干部学位统计数据
    @RequestMapping("/stat_cadreDegree_count_data")
    @ResponseBody
    public Map stat_cadreDegree_count_data(Byte cadreCategory) {
        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);

        Map cadreDegreeMap = new LinkedHashMap();
        int bs = 0, ss = 0, xs = 0, otherDegree=0;
        List<StatCadreBean> eduList = statCadreMapper.cadre_stat_degree(searchBean);
        if (eduList != null) {
            for (StatCadreBean bean : eduList) {
                if (bean.getDegreeType() == null
                        || !SystemConstants.DEGREE_TYPE_MAP.containsKey(bean.getDegreeType())) {
                    otherDegree +=bean.getNum();
                }else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_BS) {
                    bs += bean.getNum();
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_SS) {
                    ss += bean.getNum();
                } else if (bean.getDegreeType() == SystemConstants.DEGREE_TYPE_XS) {
                    xs += bean.getNum();
                }
            }
        }
        cadreDegreeMap.put("博士",bs);
        cadreDegreeMap.put("硕士",ss);
        cadreDegreeMap.put("学士",xs);
        cadreDegreeMap.put("其他",otherDegree);

        return cadreDegreeMap;
    }

    // 干部学历统计
    @RequestMapping("/stat_cadreEdu_count")
    public String stat_cadreEdu_count() {
        return "analysis/cadre/stat_cadre_edu";
    }

    // 干部学历统计
    @RequestMapping("/stat_cadreEdu_count_data")
    @ResponseBody
    public Map stat_cadreEdu_count_data(Byte cadreCategory, ModelMap modelMap) {
        CadreSearchBean searchBean = new CadreSearchBean();
        searchBean.setCadreType(cadreCategory);
        int cadreCount  = statCadreMapper.countCadre(searchBean);
        int eduCount = 0;
        Map cadreEduMap = new LinkedHashMap();
        List<StatCadreBean> statCadreBeans = statCadreMapper.cadre_stat_edu(searchBean);

        for (StatCadreBean statCadreBean : statCadreBeans) {
            cadreEduMap.put(CmTag.getMetaType(statCadreBean.eduId).getName(), statCadreBean.num);
            eduCount += statCadreBean.num;
        }
        if(cadreCount != eduCount){
            cadreEduMap.put("其他",cadreCount-eduCount);
        }

        return cadreEduMap;
    }

    // 干部平均年龄统计
    @RequestMapping("/stat_cadre_avgAge")
    public String stat_cadre_avgAge() {
        return "analysis/cadre/stat_cadre_avgAge";
    }

    @RequestMapping("/stat_cadre_avgAge_data")
    @ResponseBody
    public Map stat_cadre_avgAge_data() {
        boolean hasKjCadre = CmTag.getBoolProperty("hasKjCadre");
        CadreSearchBean searchBeanCJ = new CadreSearchBean();
        searchBeanCJ.setCadreType((byte)1);

        Map<String,BigDecimal> cadreAvgAge = new HashMap<>();
        List<StatCadreBean> adminLevelCJList = statCadreMapper.cadre_avg_age_adminLevel(searchBeanCJ);

        if (adminLevelCJList != null) {
            for (StatCadreBean bean : adminLevelCJList) {
                if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBeanCJ.getCadreType()))) {
                    cadreAvgAge.put(CmTag.getMetaTypeByCode(bean.adminLevelCode).getName(),bean.val.setScale(1, BigDecimal.ROUND_DOWN));
                } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBeanCJ.getCadreType()))) {
                    cadreAvgAge.put(CmTag.getMetaTypeByCode(bean.adminLevelCode).getName(),bean.val.setScale(1, BigDecimal.ROUND_DOWN));
                } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_none")) {
                    cadreAvgAge.put(CmTag.getMetaTypeByCode(bean.adminLevelCode).getName(),bean.val.setScale(1, BigDecimal.ROUND_DOWN));
                }
            }
        }

      if(hasKjCadre){
            CadreSearchBean searchBeanKJ = new CadreSearchBean();
            searchBeanKJ.setCadreType((byte)2);
            List<StatCadreBean> adminLevelKJList = statCadreMapper.cadre_avg_age_adminLevel(searchBeanKJ);
            if (adminLevelKJList != null) {
                for (StatCadreBean bean : adminLevelKJList) {
                    if (StringUtils.equals(bean.getAdminLevelCode(), getMainAdminLevelCode(searchBeanKJ.getCadreType()))) {
                        cadreAvgAge.put(CmTag.getMetaTypeByCode(bean.adminLevelCode).getName(),bean.val.setScale(1, BigDecimal.ROUND_DOWN));
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), getViceAdminLevelCode(searchBeanKJ.getCadreType()))) {
                        cadreAvgAge.put(CmTag.getMetaTypeByCode(bean.adminLevelCode).getName(),bean.val.setScale(1, BigDecimal.ROUND_DOWN));
                    } else if (StringUtils.equals(bean.getAdminLevelCode(), "mt_admin_level_vice_kj")) {
                        cadreAvgAge.put(CmTag.getMetaTypeByCode(bean.adminLevelCode).getName(),bean.val.setScale(1, BigDecimal.ROUND_DOWN));
                    }
                }
            }
      }
        return cadreAvgAge;
    }
}
