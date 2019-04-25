package controller.oa;

import domain.oa.OaTask;
import domain.oa.OaTaskUser;
import domain.oa.OaTaskUserView;
import domain.oa.OaTaskUserViewExample;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import mixin.OptionMixin;
import org.apache.commons.lang3.BooleanUtils;
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
import sys.constants.LogConstants;
import sys.constants.OaConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oa")
public class OaTaskUserController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaTaskUser:list")
    @RequestMapping("/oaTaskUser")
    public String oaTaskUser(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        int totalCount = oaTaskUserService.countTaskUsers(taskId, null, null);
        int hasReportCount = oaTaskUserService.countTaskUsers(taskId, null, true);
        int passCount = oaTaskUserService.countTaskUsers(taskId, OaConstants.OA_TASK_USER_STATUS_PASS, true);
        modelMap.put("totalCount", totalCount);
        modelMap.put("hasReportCount", hasReportCount);
        modelMap.put("passCount", passCount);

        return "oa/oaTaskUser/oaTaskUser_page";
    }

    @RequiresPermissions("oaTaskUser:list")
    @RequestMapping("/oaTaskUser_data")
    public void oaTaskUser_data(HttpServletResponse response,
                                int taskId,
                                Integer userId,
                                Integer pageSize, Integer pageNo) throws IOException {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        oaTaskService.checkAuth(oaTask.getType());

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        OaTaskUserViewExample.Criteria criteria =
                example.createCriteria().andTaskIdEqualTo(taskId).andIsDeleteEqualTo(false);
        example.setOrderByClause("sort_order asc");

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }

        long count = oaTaskUserViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<OaTaskUserView> records = oaTaskUserViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(oaTaskUser.class, oaTaskUserMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("oaTaskUser:check")
    @RequestMapping("/oaTaskUser_check")
    public String oaTaskUser_check(int taskId,
                                   @RequestParam(value = "taskUserIds[]") int[] taskUserIds,
                                   ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        if (taskUserIds.length == 1) {
            modelMap.put("sysUser", sysUserService.findById(taskUserIds[0]));
        }
        return "oa/oaTaskUser/oaTaskUser_check";
    }

    // 审批
    @RequiresPermissions("oaTaskUser:check")
    @RequestMapping(value = "/oaTaskUser_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_check(int taskId, @RequestParam(value = "taskUserIds[]") int[] taskUserIds,
                                   Boolean pass, String remark) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        oaTaskService.checkAuth(oaTask.getType());

        oaTaskUserService.check(taskId, taskUserIds,
                BooleanUtils.isTrue(pass) ? OaConstants.OA_TASK_USER_STATUS_PASS
                        : OaConstants.OA_TASK_USER_STATUS_DENY, remark);

        logger.info(addLog(LogConstants.LOG_PCS, "审核任务报送情况-%s-%s", taskUserIds, pass));
        return success(FormUtils.SUCCESS);
    }

    // 退回
    @RequiresPermissions("oaTaskUser:back")
    @RequestMapping(value = "/oaTaskUser_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_back(int id) {

        OaTaskUser oaTaskUser = oaTaskUserMapper.selectByPrimaryKey(id);
        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(oaTaskUser.getTaskId());
        oaTaskService.checkAuth(oaTask.getType());

        oaTaskUserService.back(id);

        logger.info(addLog(LogConstants.LOG_PCS, "退回任务报送-%s-%s", oaTaskUser.getUserId(), oaTaskUser.getTaskId()));
        return success(FormUtils.SUCCESS);
    }

    // 查看报送情况
    @RequiresPermissions("oaTaskUser:report")
    @RequestMapping("/oaTaskUser_report")
    public String oaTaskUser_report(int id, ModelMap modelMap) {

        OaTaskUser oaTaskUser = oaTaskUserMapper.selectByPrimaryKey(id);
        int taskId = oaTaskUser.getTaskId();
        int userId = oaTaskUser.getUserId();

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);
        modelMap.put("sysUser", sysUserService.findById(userId));

        modelMap.put("oaTaskUser", oaTaskUser);
        modelMap.put("oaTaskUserFiles", oaTaskUserService.getUserFiles(taskId, userId));

        return "oa/oaTaskUser/oaTaskUser_report";
    }

    // 下发通知短信
    @RequiresPermissions("oaTaskUser:infoMsg")
    @RequestMapping(value = "/oaTaskUser_infoMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_infoMsg(int taskId, String msg, HttpServletRequest request) {

        Map<String, Integer> result = oaTaskUserService.sendInfoMsg(taskId, msg);
        logger.info(addLog(LogConstants.LOG_OA, "下发通知短信：%s", taskId));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        return resultMap;
    }

    @RequiresPermissions("oaTaskUser:infoMsg")
    @RequestMapping("/oaTaskUser_infoMsg")
    public String oaTaskUser_infoMsg(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        return "oa/oaTaskUser/oaTaskUser_infoMsg";
    }

    // 短信催促未报送对象
    @RequiresPermissions("oaTaskUser:unreportMsg")
    @RequestMapping(value = "/oaTaskUser_unreportMsg", method = RequestMethod.POST)
    @ResponseBody
    public void do_oaTaskUser_unreportMsg(int taskId, String msg, HttpServletResponse response) throws IOException {

        Map<String, Object> result = oaTaskUserService.sendUnReportMsg(taskId, msg);
        logger.info(addLog(LogConstants.LOG_OA, "短信催促未报送对象：%s", taskId));

        List<SysUserView> failedUsers =  (List<SysUserView>) result.get("failedUsers");
        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("totalCount", result.get("total"));
        resultMap.put("successCount", result.get("success"));
        resultMap.put("failedUsers", failedUsers);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        baseMixins.put(SysUserView.class, OptionMixin.class);
        JSONUtils.write(response, resultMap, "success", "msg", "totalCount", "successCount",
                "failedUsers", "failedUsers.id", "failedUsers.realname", "failedUsers.mobile");
        //return resultMap;
    }

    @RequiresPermissions("oaTaskUser:unreportMsg")
    @RequestMapping("/oaTaskUser_unreportMsg")
    public String oaTaskUser_unreportMsg(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        List<OaTaskUserView> unReports = oaTaskUserService.getUnReports(taskId);
        modelMap.put("taskUserViews", unReports);

        return "oa/oaTaskUser/oaTaskUser_unreportMsg";
    }

    // 审核未通过短信提醒
    @RequiresPermissions("oaTaskUser:denyMsg")
    @RequestMapping(value = "/oaTaskUser_denyMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_denyMsg(int id, String msg, HttpServletResponse response) throws IOException {

        Boolean success = oaTaskUserService.sendDenyMsg(id, msg);
        logger.info(addLog(LogConstants.LOG_OA, "审核未通过短信提醒：%s", id));

        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("sendSuccess", success);
        return resultMap;
    }

    @RequiresPermissions("oaTaskUser:denyMsg")
    @RequestMapping("/oaTaskUser_denyMsg")
    public String oaTaskUser_denyMsg(int id, ModelMap modelMap) {

        OaTaskUser oaTaskUser = oaTaskUserMapper.selectByPrimaryKey(id);
        int taskId = oaTaskUser.getTaskId();
        int userId = oaTaskUser.getUserId();

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "oa/oaTaskUser/oaTaskUser_denyMsg";
    }
}
