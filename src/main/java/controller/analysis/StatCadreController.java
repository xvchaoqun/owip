package controller.analysis;

import controller.BaseController;
import domain.base.MetaType;
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
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatCadreController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("statCadre:list")
    @RequestMapping("/stat_cadre")
    public String stat_cadre(String type,
                             @RequestParam(required = false, defaultValue = "0") int export,
                             ModelMap modelMap, HttpServletResponse response) throws IOException {

        if (export == 1) {
            XSSFWorkbook wb = statCadreService.toXlsx();

            String fileName = sysConfigService.getSchoolName()+"中层领导干部情况统计表（" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }

        Map<String, List> rs = statCadreService.stat(type);
        modelMap.put("rs", rs);

        return "analysis/cadre/stat_cadre_page";
    }

    // 分类信息统计
    @RequiresPermissions("statCadreCategory:list")
    @RequestMapping("/stat_cadre_category")
    public String stat_cadre_category(int type, Integer cadreId, ModelMap modelMap) throws IOException {

        if (cadreId != null) {
            modelMap.put("cadre", cadreService.findAll().get(cadreId));
        }

        /*switch (type){
            case 1:
                List<CadreEdu> cadreEdus = iCadreMapper.findCadreEdus(SystemConstants.CADRE_SCHOOL_TYPE_ABROAD);
                break;
        }
        */
        return "analysis/cadre/stat_cadre_category";
    }

    @RequiresPermissions("statCadreCategory:list")
    @RequestMapping("/stat_cadre_category_data")
    public void stat_cadre_category_data(int type, HttpServletResponse response,
                                         CadreCategorySearchBean searchBean,
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

        searchBean.setCadreStatus(SystemConstants.CADRE_STATUS_MIDDLE); // 统计现任中层干部
        switch (type) {
            case 1: // 查找干部的（境外）学习经历
                count = iCadreMapper.countCadreEdus(SystemConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean);
                records = iCadreMapper.findCadreEdus(SystemConstants.CADRE_SCHOOL_TYPE_ABROAD, searchBean, rowBounds);
                break;
            case 2: // 查找干部的（境外）工作经历
                MetaType abroadMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_abroad");
                count = iCadreMapper.countCadreWroks(abroadMetaType.getId(), searchBean);
                records = iCadreMapper.findCadreWroks(abroadMetaType.getId(), searchBean, rowBounds);
                break;
            case 3: // 查找（机关）干部的（院系）工作经历
                MetaType xyMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_xy");
                int unitTypeId = xyUnitMetaType.getId();
                int workType = xyMetaType.getId();
                searchBean.setNotUnitTypeId(unitTypeId);
                count = iCadreMapper.countCadreWroks(workType, searchBean);
                records = iCadreMapper.findCadreWroks(workType, searchBean, rowBounds);
                break;
            case 4: // 查找（院系）干部的（机关）工作经历
                MetaType jgMetaType = metaTypeService.codeKeyMap().get("mt_cadre_work_type_jg");
                unitTypeId = xyUnitMetaType.getId();
                workType = jgMetaType.getId();
                searchBean.setUnitTypeId(unitTypeId);
                count = iCadreMapper.countCadreWroks(workType, searchBean);
                records = iCadreMapper.findCadreWroks(workType, searchBean, rowBounds);
                break;
            case 5: // 有校外挂职经历的干部
                count = iCadreMapper.countCrpRecords(SystemConstants.CRP_RECORD_TYPE_OUT, searchBean);
                records = iCadreMapper.findCrpRecords(SystemConstants.CRP_RECORD_TYPE_OUT, searchBean, rowBounds);
                break;
            case 6: // 具有人才/荣誉称号的干部
                searchBean.setHasTalentTitle(true);
                count = iCadreMapper.countCadres(searchBean);
                records = iCadreMapper.findCadres(searchBean, rowBounds);
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
}
