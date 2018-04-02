package controller.sys;

import controller.BaseController;
import domain.sys.SchedulerJob;
import domain.sys.SchedulerJobExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.Escape;
import sys.utils.FormUtils;
import sys.utils.HtmlEscapeUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public String schedulerJob() {

        return "sys/schedulerJob/schedulerJob_page";
    }

    @RequiresPermissions("schedulerJob:list")
    @RequestMapping("/schedulerJob_data")
    @ResponseBody
    public void schedulerJob_data(HttpServletRequest request, Integer pageSize,
                                  Integer pageNo, String searchStr) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        searchStr = HtmlEscapeUtils.escape(Escape.unescape(searchStr), "UTF-8");

        SchedulerJobExample example = new SchedulerJobExample();
        example.setOrderByClause(" id desc");

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
    public Map do_schedulerJob_au(@CurrentUser SysUserView loginUser,
                             SchedulerJob schedulerJob,
                             @RequestParam(value="resIds[]",required=false) Integer[] resIds,
                             HttpServletRequest request) {

        String name = StringUtils.trimToNull(StringUtils.lowerCase(schedulerJob.getName()));
        if (name!=null && schedulerJobService.idDuplicate(schedulerJob.getId(), name)) {
            return failed("定时任务名称重复。");
        }

        if(schedulerJob.getId() == null){

            schedulerJobService.insert(schedulerJob);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "创建定时任务：%s", JSONUtils.toString(schedulerJob, false)));
        }else{

            schedulerJobService.updateByPrimaryKeySelective(schedulerJob);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "更新定时任务：%s", JSONUtils.toString(schedulerJob, false)));
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

    @RequiresPermissions("schedulerJob:del")
    @RequestMapping(value="/schedulerJob_del", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_del(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {

        if(id!=null){
            SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
            schedulerJobService.del(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除定时任务：%s", JSONUtils.toString(schedulerJob, false)));
        }

        return success(FormUtils.SUCCESS);
    }


    @RequiresPermissions("schedulerJob:start")
    @RequestMapping(value="/schedulerJob_start", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_start(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {

        if(id!=null){
            SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
            schedulerJobService.startJob(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "启动定时任务：%s", JSONUtils.toString(schedulerJob, false)));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("schedulerJob:stop")
    @RequestMapping(value="/schedulerJob_stop", method=RequestMethod.POST)
    @ResponseBody
    public Map do_schedulerJob_stop(@CurrentUser SysUserView loginUser, Integer id, HttpServletRequest request) {

        if(id!=null){
            SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(id);
            schedulerJobService.stopJob(id);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "关闭定时任务：%s", JSONUtils.toString(schedulerJob, false)));
        }

        return success(FormUtils.SUCCESS);
    }

}
