package controller.analysis;

import controller.oa.OaBaseController;
import domain.oa.OaTaskUserViewExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.OaConstants;

import java.util.Arrays;

// 党建待办事项
@Controller
@RequiresPermissions("suspend:oa")
public class SuspendOaController extends OaBaseController {

    @RequestMapping("/suspend_oa")
    public String suspend_oa(ModelMap modelMap) {

        if(ShiroHelper.isPermitted("userOaTask:report")){   // 待处理任务数量
            OaTaskUserViewExample example = new OaTaskUserViewExample();
            example.createCriteria()
                    .andTaskIsDeleteEqualTo(false)
                    .andTaskIsPublishEqualTo(true)
                    .andTaskStatusIn(Arrays.asList(OaConstants.OA_TASK_STATUS_PUBLISH,
                            OaConstants.OA_TASK_STATUS_FINISH))
                    .andIsDeleteEqualTo(false)
                    .isTaskUser(ShiroHelper.getCurrentUserId())
                    .andHasReportEqualTo(false);
            long taskCount = oaTaskUserViewMapper.countByExample(example);
            modelMap.put("taskCount", taskCount);
        }

        if(ShiroHelper.isPermitted("oaTaskUser:check")){   // 待审批任务数量
            OaTaskUserViewExample example = new OaTaskUserViewExample();
            example.createCriteria()
                    .andTaskIsDeleteEqualTo(false)
                    .andTaskIsPublishEqualTo(true)
                    .andTaskStatusIn(Arrays.asList(OaConstants.OA_TASK_STATUS_PUBLISH,
                            OaConstants.OA_TASK_STATUS_FINISH))
                    .andIsDeleteEqualTo(false)
                    .andHasReportEqualTo(true)
                    .andIsBackEqualTo(false)
                    .andStatusEqualTo(OaConstants.OA_TASK_USER_STATUS_INIT);
            long approvalCount = oaTaskUserViewMapper.countByExample(example);
            modelMap.put("approvalCount", approvalCount);
        }

        return "analysis/suspend/suspend_oa";
    }
}
