package controller.sys;

import controller.BaseController;
import domain.sys.SchedulerJob;
import domain.sys.SchedulerJobExample;
import mixin.MixinUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.sys.SchedulerJobMapper;
import service.sys.SchedulerJobService;
import sys.constants.LogConstants;
import sys.tool.jackson.Select2Option;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/9/17.
 */
@Controller
public class SchedulerJobController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SchedulerJobMapper schedulerJobMapper;
    @Autowired
    private SchedulerJobService schedulerJobService;

    @RequiresPermissions("schedulerJob:list")
    @RequestMapping("/schedulerJob")
    public String schedulerJob(@RequestParam(required = false, defaultValue = "1")Byte cls,
                               @RequestParam(required = false, defaultValue = "0") Boolean isDeleted,
                               ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("isDeleted",isDeleted);
        if(cls==2){
            return "sys/schedulerLog/schedulerLog_page";
        }

        return "sys/schedulerJob/schedulerJob_page";
    }

    @RequiresPermissions("schedulerJob:list")
    @RequestMapping("/schedulerJob_data")
    @ResponseBody
    public void schedulerJob_data(HttpServletRequest request,
                                  @RequestParam(required = false, defaultValue = "0") Boolean isDeleted,
                                  Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SchedulerJobExample example = new SchedulerJobExample();
        example.setOrderByClause(" sort_order desc");

        if (isDeleted != null) {
            example.createCriteria().andIsDeletedEqualTo(isDeleted);
        }

        long count = schedulerJobMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SchedulerJob> schedulerJobs = schedulerJobMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", schedulerJobs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        resultMap.put("allJobs", StringUtils.join(schedulerJobService.allJobsMap().keySet(), "|"));
        resultMap.put("runJobs", StringUtils.join(schedulerJobService.runJobsMap().keySet(), "|"));

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("schedulerJob:edit")
    @RequestMapping(value="/schedulerJob_au", method= RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_au(SchedulerJob record,
                             @RequestParam(value="resIds[]",required=false) Integer[] resIds,
                             HttpServletRequest request) {

        String name = StringUtils.trimToNull(StringUtils.lowerCase(record.getName()));
        if (name!=null && schedulerJobService.idDuplicate(record.getId(), name)) {
            return failed("定时任务名称重复。");
        }

        record.setIsStarted(BooleanUtils.isTrue(record.getIsStarted()));
        record.setNeedLog(BooleanUtils.isTrue(record.getNeedLog()));

        if(record.getId() == null){

            schedulerJobService.insert(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "创建定时任务：%s", JSONUtils.toString(record, false)));
        }else{

            schedulerJobService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_ADMIN, "更新定时任务：%s", JSONUtils.toString(record, false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("schedulerJob:edit")
    @RequestMapping("/schedulerJob_au")
    public String schedulerJob_au(Integer id, ModelMap modelMap) throws IOException {

        modelMap.addAttribute("op", "添加");

        if(id != null){
            SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
            modelMap.put("schedulerJob", schedulerJob);
            modelMap.addAttribute("op", "修改");
        }

        return "sys/schedulerJob/schedulerJob_au";
    }

    //删除定时任务
    @RequiresPermissions("schedulerJob:del")
    @RequestMapping(value="/schedulerJob_del", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_del(@RequestParam(value = "ids[]") Integer[] ids) {

        schedulerJobService.batchDel(ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "删除定时任务：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    //彻底删除定时任务
    @RequiresPermissions("schedulerJob:del")
    @RequestMapping(value="/schedulerJob_doBatchDel", method=RequestMethod.POST)
    @ResponseBody
    public Map doBatchDel(@RequestParam(value = "ids[]") Integer[] ids) {

        schedulerJobService.doBatchDel(ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "彻底删除定时任务：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    //返回列表
    @RequiresPermissions("schedulerJob:del")
    @RequestMapping(value="/schedulerJob_batchUnDel", method=RequestMethod.POST)
    @ResponseBody
    public Map batchUnDel(@RequestParam(value = "ids[]") Integer[] ids) {

        schedulerJobService.batchUnDel(ids);
        logger.info(addLog(LogConstants.LOG_ADMIN, "彻底删除定时任务：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    // 手动触发执行一次
    @RequiresPermissions("schedulerJob:trigger")
    @RequestMapping(value="/schedulerJob_trigger", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_trigger(int id, HttpServletRequest request) {

        SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
        schedulerJobService.triggerJob(id);
        logger.info(addLog(LogConstants.LOG_ADMIN, "触发定时任务：%s",
                JSONUtils.toString(schedulerJob, false)));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("jobName", schedulerJob.getName());
        return resultMap;
    }

    @RequiresPermissions("schedulerJob:start")
    @RequestMapping(value="/schedulerJob_start", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_start(@RequestParam(value = "ids[]") Integer[] ids, HttpServletRequest request) {

        if(ids!=null){
            for (Integer id : ids) {
                SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
                schedulerJobService.startJob(id);
                logger.info(addLog(LogConstants.LOG_ADMIN, "启动定时任务：%s",
                        JSONUtils.toString(schedulerJob, false)));
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("schedulerJob:stop")
    @RequestMapping(value="/schedulerJob_stop", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_stop(@RequestParam(value = "ids[]") Integer[] ids, HttpServletRequest request) {

        if(ids!=null) {
            for (Integer id : ids) {
                SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
                schedulerJobService.stopJob(id);
                logger.info(addLog(LogConstants.LOG_ADMIN, "关闭定时任务：%s",
                        JSONUtils.toString(schedulerJob, false)));
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("schedulerJob:changeOrder")
    @RequestMapping(value = "/schedulerJob_changeOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_changeOrder(Integer id, Integer addNum, HttpServletRequest request) {

        schedulerJobService.changeOrder(id, addNum);
        logger.info(addLog(LogConstants.LOG_ADMIN, "定时任务调序：%s, %s", id, addNum));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("schedulerJob:edit")
    @RequestMapping("/schedulerJob_selects")
    @ResponseBody
    public Map schedulerJob_selects(Integer pageSize, Integer pageNo,String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        SchedulerJobExample example = new SchedulerJobExample();
        SchedulerJobExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" sort_order desc");

        if(StringUtils.isNotBlank(searchStr)){
            criteria.andNameLike(searchStr);
        }

        int count = (int) schedulerJobMapper.countByExample(example);
        if((pageNo-1)*pageSize >= count){

            pageNo = Math.max(1, pageNo-1);
        }
        List<SchedulerJob> schedulerJobs = schedulerJobMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo-1)*pageSize, pageSize));

        List<Select2Option> options = new ArrayList<Select2Option>();
        for(SchedulerJob schedulerJob:schedulerJobs){

            Select2Option option = new Select2Option();
            option.setText(schedulerJob.getName());
            option.setId(schedulerJob.getId() + "");

            options.add(option);
        }

        Map resultMap = success();
        resultMap.put("totalCount", count);
        resultMap.put("options", options);
        return resultMap;
    }

    //返回列表
    @RequiresPermissions("schedulerJob:edit")
    @RequestMapping(value="/schedulerJob_changeIsStarted", method=RequestMethod.POST)
    @ResponseBody
    public Map changeIsStarted(@RequestParam(value = "ids[]") Integer[] ids,Boolean isStarted) {

        isStarted = BooleanUtils.isTrue(isStarted);
        schedulerJobService.changeIsStarted(ids,isStarted);
        logger.info(addLog(LogConstants.LOG_ADMIN, "修改定时任务为%s：%s",isStarted?"自动启动":"手动启动", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }
}
