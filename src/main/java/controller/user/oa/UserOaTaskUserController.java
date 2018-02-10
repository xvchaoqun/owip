package controller.user.oa;

import bean.ShortMsgBean;
import controller.OaBaseController;
import domain.base.ContentTpl;
import domain.oa.OaTask;
import domain.oa.OaTaskUser;
import domain.oa.OaTaskUserView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
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
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

@Controller
@RequestMapping("/user/oa")
public class UserOaTaskUserController extends OaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userOaTask:report")
    @RequestMapping(value = "/oaTaskUser_report", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_report(int taskId, String content,
                             @RequestParam(value = "_files[]", required = false) MultipartFile[] _files,
                             String remark, HttpServletRequest request) throws IOException, InterruptedException {

        oaTaskUserService.report(taskId, content, _files, remark);
        logger.info(addLog(SystemConstants.LOG_OA, "报送任务：%s", taskId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userOaTask:report")
    @RequestMapping("/oaTaskUser_report")
    public String oaTaskUser_report(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);
        modelMap.put("oaTaskFiles", oaTaskService.getTaskFiles(taskId));

        Integer userId = ShiroHelper.getCurrentUserId();
        OaTaskUserView oaTaskUser = oaTaskUserService.getOwnTask(taskId, userId);
        modelMap.put("oaTaskUser", oaTaskUser);
        modelMap.put("oaTaskUserFiles", oaTaskUserService.getUserFiles(taskId, userId));

        return "user/oa/oaTaskUser_report";
    }

    // 撤回
    @RequiresPermissions("userOaTask:back")
    @RequestMapping(value = "/oaTaskUser_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_back(HttpServletRequest request, int taskId) {

        oaTaskUserService.selfBack(taskId);
        logger.info(addLog(SystemConstants.LOG_OA, "撤回报送：%s", taskId));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("userOaTask:delFile")
    @RequestMapping(value = "/oaTaskUser_batchDelFiles", method = RequestMethod.POST)
    @ResponseBody
    public Map oaTaskUser_batchDelFiles(@RequestParam(value = "ids[]") Integer[] ids) throws IOException, InterruptedException {

        if (null != ids && ids.length > 0) {

            oaTaskUserService.batchDelFiles(ids);
            logger.info(addLog(SystemConstants.LOG_PCS, "删除任务附件：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 指定负责人
    @RequiresPermissions("userOaTask:assign")
    @RequestMapping("/oaTaskUser_assign")
    public String oaTaskUser_assign(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);

        Integer userId = ShiroHelper.getCurrentUserId();
        OaTaskUser oaTaskUser = oaTaskUserService.getOaTaskUser(taskId, userId);
        modelMap.put("oaTaskUser", oaTaskUser);

        Integer assignUserId = oaTaskUser.getAssignUserId();
        if(assignUserId!=null)
            modelMap.put("sysUser", sysUserService.findById(assignUserId));

        return "user/oa/oaTaskUser_assign";
    }

    @RequiresPermissions("userOaTask:assign")
    @RequestMapping(value = "/oaTaskUser_assign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_assign(int taskId, int userId, String mobile) {

        if (!FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)) {
            return failed("手机号码有误");
        }

        oaTaskUserService.assign(taskId, userId, mobile);
        logger.info(addLog(SystemConstants.LOG_OA, "指定负责人：%s, %s", taskId, userId));

        return success(FormUtils.SUCCESS);
    }

    // 短信通知指定负责人
    @RequiresPermissions("userOaTask:assignMsg")
    @RequestMapping("/oaTaskUser_assignMsg")
    public String oaTaskUser_assignMsg(int taskId, ModelMap modelMap) {

        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        modelMap.put("oaTask", oaTask);
        Integer userId = ShiroHelper.getCurrentUserId();
        OaTaskUser oaTaskUser = oaTaskUserService.getOaTaskUser(taskId, userId);
        modelMap.put("oaTaskUser", oaTaskUser);

        SysUserView assignUser = sysUserService.findById(oaTaskUser.getAssignUserId());
        String assignMsgTitle = assignUser.getRealname()+"老师";

        SysUserView user = sysUserService.findById(oaTaskUser.getUserId());
        String msgTitle = user.getRealname()+"老师";

        ContentTpl tpl = shortMsgService.getShortMsgTpl(ContentTplConstants.CONTENT_TPL_OA_INFO_USER);
        String msg = MessageFormat.format(tpl.getContent(), assignMsgTitle, msgTitle, oaTask.getName());

        modelMap.put("msg", msg);

        return "user/oa/oaTaskUser_assignMsg";
    }

    @RequiresPermissions("userOaTask:assignMsg")
    @RequestMapping(value = "/oaTaskUser_assignMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map do_oaTaskUser_assignMsg(int taskId) {

        Integer userId = ShiroHelper.getCurrentUserId();
        OaTask oaTask = oaTaskMapper.selectByPrimaryKey(taskId);
        OaTaskUser oaTaskUser = oaTaskUserService.getOaTaskUser(taskId, userId);
        SysUserView assignUser = sysUserService.findById(oaTaskUser.getAssignUserId());
        String assignMsgTitle = assignUser.getRealname()+"老师";

        SysUserView user = sysUserService.findById(oaTaskUser.getUserId());
        String msgTitle = user.getRealname()+"老师";

        ContentTpl tpl = shortMsgService.getShortMsgTpl(ContentTplConstants.CONTENT_TPL_OA_INFO_USER);
        String msg = MessageFormat.format(tpl.getContent(), assignMsgTitle, msgTitle, oaTask.getName());

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(userId);
        bean.setReceiver(oaTaskUser.getAssignUserId());
        bean.setMobile(oaTaskUser.getAssignUserMobile());
        bean.setContent(msg);
        bean.setRelateId(tpl.getId());
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
        bean.setType(tpl.getName());

        shortMsgService.send(bean, ContextHelper.getRealIp());
        logger.info(addLog(SystemConstants.LOG_OA, "短信通知指定负责人：%s, %s", taskId, userId));

        return success(FormUtils.SUCCESS);
    }

}
