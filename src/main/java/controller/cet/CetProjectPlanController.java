package controller.cet;

import domain.cet.CetProject;
import domain.cet.CetProjectPlan;
import domain.cet.CetProjectPlanExample;
import domain.cet.CetProjectPlanExample.Criteria;
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
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cet")
public class CetProjectPlanController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetProjectPlan")
    public String cetProjectPlan() {

        return "cet/cetProjectPlan/cetProjectPlan_page";
    }

    @RequiresPermissions("cetProjectPlan:list")
    @RequestMapping("/cetProjectPlan_data")
    public void cetProjectPlan_data(HttpServletResponse response,
                                    int projectId,
                                    Byte type,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                 Integer pageSize, Integer pageNo)  throws IOException{

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        CetProjectPlanExample example = new CetProjectPlanExample();
        Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("sort_order asc");

        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            cetProjectPlan_export(example, response);
            return;
        }

        long count = cetProjectPlanMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<CetProjectPlan> records= cetProjectPlanMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(cetProjectPlan.class, cetProjectPlanMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetProjectPlan_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectPlan_au(CetProjectPlan record, HttpServletRequest request) {

        Integer id = record.getId();

        if(cetProjectPlanService.idDuplicate(record.getId(), record.getProjectId(), record.getType())){
            return failed("添加重复。");
        }

        if (id == null) {
            cetProjectPlanService.insertSelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "添加培训方案：%s", record.getId()));
        } else {

            cetProjectPlanService.updateByPrimaryKeySelective(record);
            logger.info(addLog( SystemConstants.LOG_CET, "更新培训方案：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetProjectPlan_au")
    public String cetProjectPlan_au(Integer id,
                                    Integer projectId,
                                    ModelMap modelMap) {

        if (id != null) {
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(id);
            if(cetProjectPlan!=null)
                projectId = cetProjectPlan.getProjectId();
            modelMap.put("cetProjectPlan", cetProjectPlan);
        }
        modelMap.put("projectId", projectId);
        return "cet/cetProjectPlan/cetProjectPlan_au";
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping("/cetProjectPlan_detail")
    public String cetProjectPlan_detail(int planId, ModelMap modelMap) {

        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
        modelMap.put("cetProject", cetProject);

        byte type = cetProjectPlan.getType();
        switch (type){

            case CetConstants.CET_PROJECT_PLAN_TYPE_OFFLINE: // 线下培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_PRACTICE: // 实践教学
                return "forward:/cet/cetTrain";

            case CetConstants.CET_PROJECT_PLAN_TYPE_SELF: // 自主学习
            case CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL: // 上级网上专题班
                return "forward:/cet/cetPlanCourse";

            case CetConstants.CET_PROJECT_PLAN_TYPE_WRITE:
                Integer projectId = cetProjectPlan.getProjectId();
                return "forward:/cet/cetProject_detail_obj?cls=4&projectId="+projectId + "&planId="+planId;

            case CetConstants.CET_PROJECT_PLAN_TYPE_GROUP: // 分组研讨
                return "forward:/cet/cetDiscuss";
        }

        return null;
    }

    @RequestMapping("/cetProjectPlan_summary")
    public String cetProjectPlan_summary(Integer id, Boolean view, ModelMap modelMap) {

        if (id != null) {
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(id);
            modelMap.put("cetProjectPlan", cetProjectPlan);
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
            modelMap.put("cetProject", cetProject);
        }

        if(BooleanUtils.isTrue(view))
            return "cet/cetProjectPlan/cetProjectPlan_summary_view";

        return "cet/cetProjectPlan/cetProjectPlan_summary";
    }

    @RequiresPermissions("cetProjectPlan:edit")
    @RequestMapping(value = "/cetProjectPlan_summary", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectPlan_summary(Integer id, String summary) {

        CetProjectPlan record = new CetProjectPlan();
        record.setId(id);
        record.setSummary(summary);
        record.setHasSummary(StringUtils.isNotBlank(summary));
        cetProjectPlanMapper.updateByPrimaryKeySelective(record);
        logger.info(addLog(SystemConstants.LOG_CET, "更新培训内容：%s", id));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:del")
    @RequestMapping(value = "/cetProjectPlan_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectPlan_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            cetProjectPlanService.del(id);
            logger.info(addLog( SystemConstants.LOG_CET, "删除培训方案：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:del")
    @RequestMapping(value = "/cetProjectPlan_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map cetProjectPlan_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            cetProjectPlanService.batchDel(ids);
            logger.info(addLog( SystemConstants.LOG_CET, "批量删除培训方案：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("cetProjectPlan:changeOrder")
    @RequestMapping(value = "/cetProjectPlan_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cetProjectPlan_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        cetProjectPlanService.changeOrder(id, addNum);
        logger.info(addLog( SystemConstants.LOG_CET, "培训方案调序：%s,%s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    public void cetProjectPlan_export(CetProjectPlanExample example, HttpServletResponse response) {

        List<CetProjectPlan> records = cetProjectPlanMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"培训时间（开始）|100","培训时间（结束）|100","培训形式|100","培训内容|100","学时|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            CetProjectPlan record = records.get(i);
            String[] values = {
                DateUtils.formatDate(record.getStartDate(), DateUtils.YYYY_MM_DD),
                            DateUtils.formatDate(record.getEndDate(), DateUtils.YYYY_MM_DD),
                            record.getType() + "",
                            record.getSummary(),
                            record.getPeriod() + "",
                            record.getRemark()
            };
            valuesList.add(values);
        }
        String fileName = "培训方案_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
