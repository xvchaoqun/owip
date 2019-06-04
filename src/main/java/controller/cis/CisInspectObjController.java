package controller.cis;

import domain.cis.*;
import freemarker.template.TemplateException;
import mixin.MixinUtils;
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
import org.springframework.web.multipart.MultipartFile;
import sys.constants.CisConstants;
import sys.constants.LogConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.DownloadUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CisInspectObjController extends CisBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj")
    public String cisInspectObj(HttpServletResponse response,
                                     Integer cadreId,
                                     ModelMap modelMap) {

        if(cadreId!=null) {
            modelMap.put("cadre", CmTag.getCadreById(cadreId));
        }
        List<CisInspector> nowInspectors = cisInspectorService.getInspectors(CisConstants.CIS_INSPECTOR_STATUS_NOW);
        modelMap.put("inspectors", nowInspectors);

        return "cis/cisInspectObj/cisInspectObj_page";
    }

    @RequiresPermissions("cisInspectObj:list")
    @RequestMapping("/cisInspectObj_data")
    @ResponseBody
    public void cisInspectObj_data(HttpServletResponse response,
                                   Integer year,
                                   Integer typeId,
                                   Integer seq,
                                   Integer cadreId,
                                   @RequestDateRange DateRange _inspectDate,
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

        /**
         * 考察报告要排序，规则：先按年份，新的在上，老的在下；
         * 再按考察类型，选拔任用在上，优秀年轻干部在下；
         * 最后按编号，数字大的在上，数字小的在下。基本上和发文的排序差不多。
         */
        CisInspectObjViewExample example = new CisInspectObjViewExample();
        CisInspectObjViewExample.Criteria criteria = example.createCriteria();

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
        if (_inspectDate.getStart()!=null) {
            criteria.andInspectDateGreaterThanOrEqualTo(_inspectDate.getStart());
        }

        if (_inspectDate.getEnd()!=null) {
            criteria.andInspectDateLessThanOrEqualTo(_inspectDate.getEnd());
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
            }else{
                criteria.andIdIsNull();
            }
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cisInspectObjService.export(example, response);
            return;
        }

        long count = cisInspectObjViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CisInspectObjView> records = cisInspectObjViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cisInspectObj.class, cisInspectObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping(value = "/cisInspectObj_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspectObj_au(CisInspectObj record,
                                   HttpServletRequest request) {

        Integer id = record.getId();

        /*if (_logFile != null) {
            String ext = FileUtils.getExtention(_logFile.getOriginalFilename());
            if (!StringUtils.equalsIgnoreCase(ext, ".pdf")) {
               return failed("文件格式错误，请上传pdf文件");
            }

            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "cis" + FILE_SEPARATOR
                    + DateUtils.formatDate(new Date(), "yyyyMM") + FILE_SEPARATOR
                    + fileName;
            String savePath = realPath + ext;
            FileUtils.copyFile(_logFile, new File(springProps.uploadPath + savePath));

            try {
                String swfPath = realPath + ".swf";
                pdf2Swf(savePath, swfPath);
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                logger.error("异常", e);
            }

            record.setLogFile(savePath);
        }*/

        if (id == null) {
            if (record.getSeq() == null) {
                record.setSeq(cisInspectObjService.genSeq(record.getTypeId(), record.getYear()));
            }
            cisInspectObjService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "添加干部考察材料：%s", record.getId()));
        } else {

            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(id);
            if (cisInspectObj.getTypeId().intValue() != record.getTypeId()
                    || cisInspectObj.getYear().intValue() != record.getYear()
                    ) { // 修改了类型或年份，要修改编号
                record.setSeq(cisInspectObjService.genSeq(record.getTypeId(), record.getYear()));
            }

            cisInspectObjService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部考察材料：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping("/cisInspectObj_au")
    public String cisInspectObj_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(id);
            modelMap.put("cisInspectObj", cisInspectObj);
            int cadreId = cisInspectObj.getCadreId();
            modelMap.put("cadre", CmTag.getCadreById(cadreId));
            modelMap.put("chiefInspector", cisInspectObj.getChiefInspector());
        }

        return "cis/cisInspectObj/cisInspectObj_au";
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping("/cisInspectObj_summary")
    public String cisInspectObj_summary(Integer objId,
                                        ModelMap modelMap) {

        if (objId != null) {

            CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
            modelMap.put("cisInspectObj", cisInspectObj);

            /*List<Unit> units = cisInspectObjService.getUnits(objId);
            Set<Integer> selectUnitIds = new HashSet<>();
            for (Unit unit : units) {
                selectUnitIds.add(unit.getId());
            }
            modelMap.put("selectUnitIds", new ArrayList<>(selectUnitIds));


            List<CisInspectorView> inspectors = cisInspectObjService.getInspectors(objId);
            Set<Integer> selectInspectorIds = new HashSet<>();
            for (CisInspectorView inspector : inspectors) {
                selectInspectorIds.add(inspector.getId());
            }
            modelMap.put("selectInspectorIds", new ArrayList<>(selectInspectorIds));*/
        }


        /*List<Unit> runUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_RUN);
        modelMap.put("runUnits", runUnits);
        List<Unit> historyUnits = unitService.findUnitByTypeAndStatus(null, SystemConstants.UNIT_STATUS_HISTORY);
        modelMap.put("historyUnits", historyUnits);

        List<CisInspectorView> nowInspectors = cisInspectorService.getInspectors(CisConstants.CIS_INSPECTOR_STATUS_NOW);
        modelMap.put("nowInspectors", nowInspectors);
        List<CisInspectorView> historyInspectors = cisInspectorService.getInspectors(CisConstants.CIS_INSPECTOR_STATUS_HISTORY);
        modelMap.put("historyInspectors", historyInspectors);
        List<CisInspectorView> deleteInspectors = cisInspectorService.getInspectors(CisConstants.CIS_INSPECTOR_STATUS_DELETE);
        modelMap.put("deleteInspectors", deleteInspectors);*/


        return "cis/cisInspectObj/cisInspectObj_summary";
    }

    @RequiresPermissions("cisInspectObj:edit")
    @RequestMapping(value = "/cisInspectObj_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cisInspectObj_summary(CisInspectObj record,
                                        MultipartFile _logFile,
                                        @RequestParam(value = "unitIds[]", required = false) Integer[] unitIds,
                                        //@RequestParam(value = "inspectorIds[]", required = false) Integer[] inspectorIds,
                                   HttpServletRequest request) throws IOException, InterruptedException {

        record.setLogFile(uploadPdf(_logFile, "cis"));
        cisInspectObjService.updateSummary( unitIds, record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新干部考察材料、考察单位、考察组成员：%s",record.getId()));

        return success(FormUtils.SUCCESS);
    }

    // 考察材料导出
    @RequiresPermissions("cisInspectObj:export")
    @RequestMapping("/cisInspectObj_summary_export")
    public void cisInspectObj_summary_export(int objId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {

        CisInspectObj cisInspectObj = cisInspectObjMapper.selectByPrimaryKey(objId);
        //输出文件
        String filename = cisInspectObj.getCadre().getRealname() + "同志考察材料（"+DateUtils.formatDate(new Date(), "yyyy.MM.dd")+"）";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + DownloadUtils.encodeFilename(request, filename + ".doc"));
        response.setContentType("application/msword;charset=UTF-8");
        DownloadUtils.addFileDownloadCookieHeader(response);

        cisInspectObjService.process(cisInspectObjService.getDataMap(cisInspectObj), response.getWriter());
    }

    @RequiresPermissions("cisInspectObj:del")
    @RequestMapping(value = "/cisInspectObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            cisInspectObjService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除干部考察材料：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }
}
