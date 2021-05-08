package controller.oa;

import controller.global.OpException;
import domain.oa.*;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import mixin.OptionMixin;
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
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OaConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/oa")
public class OaTaskUserController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("oaTaskUser:list")
    @RequestMapping("/oaTaskUser")
    public String oaTaskUser(int taskId,
                             Integer userId,
                             ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

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
                                HttpServletRequest request,
                                Integer taskId,
                                Integer userId,
                                String mobile,
                                Boolean hasReport,
                                Byte status,
                                Integer type,
                                String name,
                                @RequestParam(required = false, defaultValue = "0") int export,
                                Integer[] ids, // 导出的记录, userId
                                Integer pageSize,
                                Integer pageNo) throws IOException {

        oaTaskService.checkAuth(taskId);

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        OaTaskUserViewExample example = new OaTaskUserViewExample();
        OaTaskUserViewExample.Criteria criteria =
                example.createCriteria().andIsDeleteEqualTo(false);
        example.setOrderByClause("sort_order asc");

        Boolean showAll = ShiroHelper.isPermitted("oaTaskShowAll:*");

        if (!showAll) {
            criteria.listCreateOrShareTasks(ShiroHelper.getCurrentUserId());
        }

        if (taskId != null) {
            criteria.andTaskIdEqualTo(taskId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike(SqlUtils.like(mobile));
        }
        if(hasReport!=null){
            criteria.andHasReportEqualTo(hasReport);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (type != null) {
            criteria.andTaskTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andTaskNameLike(SqlUtils.like(name));
        }
        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andUserIdIn(Arrays.asList(ids));
            oaTaskUser_export(taskId, example, response);
            return;
        } else if (export == 2) {

            if (ids != null && ids.length > 0)
                criteria.andUserIdIn(Arrays.asList(ids));
            oaTaskUser_export2(taskId, example, request, response);
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

    // 批量报送
    @RequiresPermissions("oaTaskUser:check")
    @RequestMapping(value = "/oaTaskUser_isReport", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_isReport(int taskId, Integer[] ids) {

        oaTaskUserService.isReport(taskId, ids);

        logger.info(addLog(LogConstants.LOG_PCS, "批量设置为已报送%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("oaTaskUser:check")
    @RequestMapping("/oaTaskUser_check")
    public String oaTaskUser_check(int taskId,
                                   Integer[] taskUserIds,
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
    public Map do_oaTaskUser_check(int taskId, Integer[] taskUserIds,
                                   Boolean pass, String remark) {

        oaTaskUserService.check(taskId, taskUserIds,
                BooleanUtils.isTrue(pass) ? OaConstants.OA_TASK_USER_STATUS_PASS
                        : OaConstants.OA_TASK_USER_STATUS_DENY, remark);

        logger.info(addLog(LogConstants.LOG_PCS, "审核任务报送情况-%s-%s", StringUtils.join(taskUserIds, ","), pass));
        return success(FormUtils.SUCCESS);
    }

    // 退回
    @RequiresPermissions("oaTaskUser:back")
    @RequestMapping(value = "/oaTaskUser_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_back(int id) {

        OaTaskUser oaTaskUser = oaTaskUserMapper.selectByPrimaryKey(id);
        oaTaskService.checkAuth(oaTaskUser.getTaskId());

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

        oaTaskService.checkAuth(taskId);

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

        List<SysUserView> failedUsers = (List<SysUserView>) result.get("failedUsers");
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

    public void oaTaskUser_export(int taskId, OaTaskUserViewExample example, HttpServletResponse response) {

        List<OaTaskUserView> records = oaTaskUserViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"工作证号|110", "姓名|90",
                "所在单位及职务|250", "手机号码|100", "指定负责人|100", "指定负责人手机号|100", "报送情况|80",
                "报送人|90", "报送时间|150", "审核情况|80", "是否退回|80"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            OaTaskUserView record = records.get(i);

            SysUserView user = sysUserService.findById(record.getUserId());
            String _assignUser = "--";
            if (record.getAssignUserId() != null) {
                SysUserView assignUser = sysUserService.findById(record.getAssignUserId());
                _assignUser = assignUser.getRealname();
            }
            String _reportUser = "--";
            if (record.getReportUserId() != null) {
                SysUserView reportUser = sysUserService.findById(record.getReportUserId());
                _reportUser = reportUser.getRealname();
            }

            String[] values = {
                    user.getCode(),
                    user.getRealname(),
                    record.getTitle(),
                    record.getMobile(),
                    _assignUser,
                    record.getAssignUserId() != null ? record.getAssignUserMobile() : "--",
                    BooleanUtils.isTrue(record.getHasReport()) ? "已报送" : "未报送",
                    _reportUser,
                    DateUtils.formatDate(record.getReportTime(), DateUtils.YYYY_MM_DD_HH_MM_SS),
                    OaConstants.OA_TASK_USER_STATUS_MAP.get(record.getStatus()),
                    BooleanUtils.isTrue(record.getIsBack()) ? "已退回" : "--",
            };
            valuesList.add(values);
        }

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        String fileName = oaTask.getName() + "报送详情";
        ExportHelper.export(titles, valuesList, fileName, response);
    }

    // 打包下载附件
    public void oaTaskUser_export2(int taskId, OaTaskUserViewExample example,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<OaTaskUserView> oaTaskUserViews = oaTaskUserViewMapper.selectByExample(example);
        if (oaTaskUserViews.size() == 0) {
            throw new OpException("没有要导出的附件。");
        }

        List<Integer> userIds = new ArrayList<>();
        for (OaTaskUserView record : oaTaskUserViews) {
            userIds.add(record.getUserId());
        }

        OaTaskUserFileExample example2 = new OaTaskUserFileExample();
        example2.createCriteria().andTaskIdEqualTo(taskId)
                .andUserIdIn(userIds);
        example2.setOrderByClause("field(user_id," + StringUtils.join(userIds, ",") + ") asc");
        List<OaTaskUserFile> oaTaskUserFiles = oaTaskUserFileMapper.selectByExample(example2);

        Map<String, File> fileMap = new LinkedHashMap<>();
        for (OaTaskUserFile record : oaTaskUserFiles) {

            String filePath = record.getFilePath();
            SysUserView uv = sysUserService.findById(record.getUserId());

            fileMap.put(uv.getRealname() + "(" + uv.getCode() + ")" + record.getFileName(),
                    new File(springProps.uploadPath + filePath));
        }
        DownloadUtils.addFileDownloadCookieHeader(response);

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        DownloadUtils.zip(fileMap, String.format("[%s]报送附件", oaTask.getName()), request, response);
    }
}
