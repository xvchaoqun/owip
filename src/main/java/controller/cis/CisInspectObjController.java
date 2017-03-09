package controller.cis;

import controller.BaseController;
import domain.cadre.Cadre;
import domain.cis.*;
import domain.cis.CisInspectObjExample.Criteria;
import domain.unit.Unit;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CisInspectObjController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj")
    public String cisInspectObj() {

        return "index";
    }

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj_page")
    public String cisInspectObj_page(HttpServletResponse response,
                                     Integer cadreId,
                                     ModelMap modelMap) {

        if(cadreId!=null) {
            Map<Integer, Cadre> cadreMap = cadreService.findAll();
            modelMap.put("cadre", cadreMap.get(cadreId));
        }
        List<CisInspectorView> nowInspectors = cisInspectorService.getNowInspectors(SystemConstants.CIS_INSPECTOR_STATUS_NOW);
        modelMap.put("inspectors", nowInspectors);

        return "cis/cisInspectObj/cisInspectObj_page";
    }

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj_data")
    public void cisInspectObj_data(HttpServletResponse response,
                                   Integer year,
                                   Integer typeId,
                                   Integer seq,
                                   Integer cadreId,
                                   String _inspectDate,
                                   Integer inspectorId,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CisInspectObjExample example = new CisInspectObjExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("inspect_date desc");

        if (year != null) {
            criteria.andYearEqualTo(year);
        }

        if (typeId != null) {
            criteria.andTypeIdEqualTo(typeId);
        }

        if (seq != null) {
            criteria.andSeqEqualTo(seq);
        }
        if (cadreId != null) {
            criteria.andCadreIdEqualTo(cadreId);
        }
        if (StringUtils.isNotBlank(_inspectDate)) {
            String start = _inspectDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _inspectDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andInspectDateGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andInspectDateLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        if(inspectorId!=null){
            CisObjInspectorExample example1 = new CisObjInspectorExample();
            example1.createCriteria().andInspectorIdEqualTo(inspectorId);
            List<CisObjInspector> cisObjInspectors = cisObjInspectorMapper.selectByExample(example1);
            List<Integer> objIds = new ArrayList<>();
            for (CisObjInspector cisObjInspector : cisObjInspectors) {
                Integer objId = cisObjInspector.getObjId();
                objIds.add(objId);
            }
            if(objIds.size()>0){
                criteria.andIdIn(objIds);
            }
        }

        int count = cisInspectObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspectObj> cisInspectObjs = cisInspectObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", cisInspectObjs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        //sourceMixins.put(cisInspectObj.class, cisInspectObjMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping(value = "/cisInspectObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspectObj_au(CisInspectObj record,
                                   String _inspectDate,
                                   HttpServletRequest request) {

        Integer id = record.getId();
        if (StringUtils.isNotBlank(_inspectDate)) {
            record.setInspectDate(DateUtils.parseDate(_inspectDate, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            if (record.getSeq() == null) {
                record.setSeq(cisInspectObjService.genSeq(record.getTypeId(), record.getYear()));
            }
            cisInspectObjService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "添加干部考察材料：%s", record.getId()));
        } else {

            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(id);
            if (cisInspectObj.getTypeId().intValue() != record.getTypeId()
                    || cisInspectObj.getYear().intValue() != record.getYear()
                    ) { // 修改了类型或年份，要修改编号
                record.setSeq(cisInspectObjService.genSeq(record.getTypeId(), record.getYear()));
            }

            cisInspectObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部考察材料：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping("/cisInspectObj_au")
    public String cisInspectObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(id);
            modelMap.put("cisInspectObj", cisInspectObj);
            Map<Integer, Cadre> cadreMap = cadreService.findAll();
            modelMap.put("cadre", cadreMap.get(cisInspectObj.getCadreId()));
            modelMap.put("chiefInspector", cisInspectObj.getChiefInspector());
        }

        return "cis/cisInspectObj/cisInspectObj_au";
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping("/cisInspectObj_summary")
    public String cisInspectObj_summary(Integer objId, ModelMap modelMap) {

        if (objId != null) {
            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
            modelMap.put("cisInspectObj", cisInspectObj);

            List<Unit> units = cisInspectObjService.getUnits(objId);
            Set<Integer> selectIdSet = new HashSet<>();
            for (Unit unit : units) {
                selectIdSet.add(unit.getId());
            }
            modelMap.put("selectIds", new ArrayList<>(selectIdSet));
        }


        List<Unit> runUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_RUN);
        modelMap.put("runUnits", runUnits);

        return "cis/cisInspectObj/cisInspectObj_summary";
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping(value = "/cisInspectObj_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspectObj_summary(int objId, String summary,
                                        Integer talkUserCount,
                                        @RequestParam(value = "unitIds[]", required = false) Integer[] unitIds,
                                   HttpServletRequest request) {

        cisInspectObjService.updateSummary(objId, summary, unitIds, talkUserCount);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "更新干部考察材料、考察单位：%s",objId));

        return success(FormUtils.SUCCESS);
    }

    // 考察材料导出
    @RequiresPermissions("cisInspectObj:export")
    @RequestMapping("/cisInspectObj_summary_export")
    public void cisInspectObj_summary_export(int objId, HttpServletResponse response) throws IOException, TemplateException {

        CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
        //输出文件
        String filename = cisInspectObj.getCadre().getUser().getRealname() + "同志考察材料（"+DateUtils.formatDate(new Date(), "yyyy.MM.dd")+"）";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
        response.setContentType("application/msword;charset=UTF-8");

        cisInspectObjService.process(objId, response.getWriter());
    }

    @RequiresPermissions("cisInspectObj:del")
    @RequestMapping(value = "/cisInspectObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisInspectObjService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除干部考察材料：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
