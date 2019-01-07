package controller.cet;

import domain.cet.*;
import domain.cet.CetAnnualObjExample.Criteria;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import persistence.cet.common.TrainRecord;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.tool.tree.TreeNode;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cet")
public class CetAnnualObjController extends CetBaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_detail")
    public String cetAnnualObj_detail( int objId, ModelMap modelMap) {
    
        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        modelMap.put("cetAnnualObj", cetAnnualObj);
        Integer annualId = cetAnnualObj.getAnnualId();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        modelMap.put("cetAnnual", cetAnnual);

        return "cet/cetAnnualObj/cetAnnualObj_detail";
    }
    
    
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_items")
    public String cetAnnualObj_items( int objId,  @RequestParam(defaultValue = "0") Boolean isValid, ModelMap modelMap) {
    
        CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(objId);
        int userId = cetAnnualObj.getUserId();
        Integer annualId = cetAnnualObj.getAnnualId();
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        int year = cetAnnual.getYear();
        
        List<TrainRecord> trainRecords = cetAnnualObjService.getTrainRecords(userId, year, isValid);
        modelMap.put("trainRecords", trainRecords);
    
        return "cet/cetAnnualObj/cetAnnualObj_items";
    }
    
    
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj")
    public String cetAnnualObj( Integer userId, @RequestParam(defaultValue = "0") Boolean isQuit,
                                @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                                   @RequestParam(required = false, value = "postTypes") Integer[] postTypes,
                                ModelMap modelMap) {

        modelMap.put("isQuit", isQuit);
        
        if (adminLevels != null) {
            modelMap.put("selectAdminLevels", Arrays.asList(adminLevels));
        }
        if (postTypes != null) {
            modelMap.put("selectPostTypes", Arrays.asList(postTypes));
        }
        
        if (userId != null)
            modelMap.put("sysUser", CmTag.getUserById(userId));
        
        return "cet/cetAnnualObj/cetAnnualObj_page";
    }
    
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_data")
    @ResponseBody
    public void cetAnnualObj_data(HttpServletResponse response,
                                  int annualId,
                                  Integer userId,
                                  @RequestParam(defaultValue = "0") Boolean isQuit,
                                  @RequestParam(required = false, value = "adminLevels") Integer[] adminLevels,
                                  @RequestParam(required = false, value = "postTypes") Integer[] postTypes,
                                  Boolean isFinished,
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
        
        CetAnnualObjExample example = new CetAnnualObjExample();
        Criteria criteria = example.createCriteria()
                .andAnnualIdEqualTo(annualId)
                .andIsQuitEqualTo(isQuit);
        example.setOrderByClause("sort_order desc");
        
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        
        if (adminLevels != null) {
            criteria.andAdminLevelIn(Arrays.asList(adminLevels));
        }
        if (postTypes != null) {
            criteria.andPostTypeIn(Arrays.asList(postTypes));
        }
        if(isFinished!=null){
           criteria.isFinished(BooleanUtils.isTrue(isFinished));
        }
        
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            cetAnnualObj_export(example, response);
            return;
        }
        
        long count = cetAnnualObjMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetAnnualObj> records = cetAnnualObjMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetAnnualObj.class, cetAnnualObjMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/archiveFinishPeriod", method = RequestMethod.POST)
    @ResponseBody
    public Map do_archiveFinishPeriod(int annualId,
                                   //boolean isQuit,
                                   HttpServletRequest request) {
        
        cetAnnualObjService.archiveFinishPeriod(annualId);
        
        logger.info(addLog(LogConstants.LOG_CET, "归档已完成学时： %s", annualId));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/archiveObjFinishPeriod", method = RequestMethod.POST)
    @ResponseBody
    public Map do_archiveObjFinishPeriod(int annualId,
                                   int objId,
                                   //boolean isQuit,
                                   HttpServletRequest request) {
        
        cetAnnualObjService.archiveObjFinishPeriod(annualId, objId);
        
        logger.info(addLog(LogConstants.LOG_CET, "归档已完成学时： %s, %s", annualId, objId));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_add(int annualId,
                                   @RequestParam(value = "userIds[]", required = false) Integer[] userIds,
                                   HttpServletRequest request) {
        
        cetAnnualObjService.addOrUpdate(annualId, userIds);
        logger.info(addLog(LogConstants.LOG_CET, "编辑培训对象： %s, %s", annualId,
                StringUtils.join(userIds, ",")));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_add")
    public String cetAnnualObj_add(int annualId, ModelMap modelMap) {
        
        CetAnnual cetAnnual = cetAnnualMapper.selectByPrimaryKey(annualId);
        modelMap.put("cetAnnual", cetAnnual);
        CetTraineeType cetTraineeType = cetTraineeTypeMapper.selectByPrimaryKey(cetAnnual.getTraineeTypeId());
        modelMap.put("cetTraineeType", cetTraineeType);
        String code = cetTraineeType.getCode();
        switch (code) {
            // 中层干部
            case "t_cadre":
                return "cet/cetAnnualObj/cetAnnualObj_selectCadres";
        }
        
        return null;
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_selectCadres_tree")
    @ResponseBody
    public Map cetAnnualObj_selectCadres_tree(int annualId) throws IOException {
        
        Set<Integer> selectIdSet = cetAnnualObjService.getSelectedAnnualObjUserIdSet(annualId);
        
        Set<Byte> cadreStatusList = new HashSet<>();
        cadreStatusList.add(CadreConstants.CADRE_STATUS_MIDDLE);
        TreeNode tree = cadreCommonService.getTree(new LinkedHashSet<>(cadreService.findAll().values()),
                cadreStatusList, selectIdSet, null, false, true, false);
        
        Map<String, Object> resultMap = success();
        resultMap.put("tree", tree);
        return resultMap;
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_quit", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_quit(boolean isQuit,
                                    @RequestParam(value = "ids[]", required = false) Integer[] ids,
                                    HttpServletRequest request) {

        cetAnnualObjService.quit(isQuit, ids);
        logger.info(addLog(LogConstants.LOG_CET, "培训对象： %s, %s", isQuit ? "退出" : "返回",
                StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_singleRequire", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_singleRequire(int objId, CetAnnualRequire record, HttpServletRequest request) {
        
        cetAnnualObjService.singleRequire(objId, record);
        logger.info(addLog(LogConstants.LOG_CET, "个别设定年度学习任务：objId=%s", objId));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_singleRequire")
    public String cetAnnualObj_singleRequire(Integer id, ModelMap modelMap) {
        
        if (id != null) {
            CetAnnualObj cetAnnualObj = cetAnnualObjMapper.selectByPrimaryKey(id);
            modelMap.put("cetAnnualObj", cetAnnualObj);
        }
        
        return "cet/cetAnnualObj/cetAnnualObj_singleRequire";
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping(value = "/cetAnnualObj_batchRequire", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_batchRequire(CetAnnualRequire record, HttpServletRequest request) {
        
        cetAnnualObjService.batchRequire(record);
        logger.info(addLog(LogConstants.LOG_CET, "设定年度学习任务：annualId=%s", record.getAnnualId()));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:edit")
    @RequestMapping("/cetAnnualObj_batchRequire")
    public String cetAnnualObj_batchRequire(int annualId, ModelMap modelMap) {
        
        return "cet/cetAnnualObj/cetAnnualObj_batchRequire";
    }
    
    @RequiresPermissions("cetAnnualObj:del")
    @RequestMapping(value = "/cetAnnualObj_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetAnnualObj_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {
        
        
        if (null != ids && ids.length > 0) {
            cetAnnualObjService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_CET, "批量删除年度学习档案包含的培训对象：%s", StringUtils.join(ids, ",")));
        }
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("cetAnnualObj:changeOrder")
    @RequestMapping(value = "/cetAnnualObj_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetAnnualObj_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {
        
        cetAnnualObjService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_CET, "年度学习档案包含的培训对象调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }
    
    // 导出明细表
    @RequiresPermissions("cetAnnualObj:list")
    @RequestMapping("/cetAnnualObj_exportDetails")
    public void cetAnnual_exportObjs(int objId, HttpServletResponse response) throws IOException {
    
        cetExportService.cetAnnual_exportObjDetails(objId, response);
        return;
    }
    
    public void cetAnnualObj_export(CetAnnualObjExample example, HttpServletResponse response) {
        
        List<CetAnnualObj> records = cetAnnualObjMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训对象|100", "时任单位及职务|100", "行政级别|100", "职务属性|100", "任现职时间|100", "年度学习任务|100", "已完成学时数|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetAnnualObj record = records.get(i);
            String[] values = {
                    record.getUserId() + "",
                    record.getTitle(),
                    record.getAdminLevel() + "",
                    record.getPostType() + "",
                    DateUtils.formatDate(record.getLpWorkTime(), DateUtils.YYYY_MM_DD),
                    record.getPeriod() + "",
                    record.getFinishPeriod() + ""
            };
            valuesList.add(values);
        }
        String fileName = "年度学习档案包含的培训对象_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
