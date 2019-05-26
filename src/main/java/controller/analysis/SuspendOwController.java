package controller.analysis;

import controller.member.MemberBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;

// 党建待办事项
@Controller
@RequiresPermissions("suspend:ow")
public class SuspendOwController extends MemberBaseController {

    @RequestMapping("/suspend_ow")
    public String suspend_ow(ModelMap modelMap) {

        modelMap.put("studentGrowOdCheckCount", memberApplyService.count(null, null,
                OwConstants.OW_APPLY_TYPE_STU, OwConstants.OW_APPLY_STAGE_DRAW, (byte) -1));
        modelMap.put("teacherGrowOdCheckCount", memberApplyService.count(null, null, OwConstants.OW_APPLY_TYPE_TEACHER,
                OwConstants.OW_APPLY_STAGE_DRAW, (byte) -1));
        modelMap.put("memberOutCount", memberOutService.count(null, null, (byte) 2, null));
        modelMap.put("memberInCount", memberInService.count(null, null, (byte)2));
        modelMap.put("memberStayCount_abroad", memberStayService.count(null, null, (byte) 3,
                MemberConstants.MEMBER_STAY_TYPE_ABROAD, null));
        modelMap.put("memberStayCount_internal", memberStayService.count(null, null, (byte) 3,
                MemberConstants.MEMBER_STAY_TYPE_INTERNAL, null));

        modelMap.put("studentPositiveOdCheckCount", memberApplyService.count(null, null, OwConstants.OW_APPLY_TYPE_STU,
                OwConstants.OW_APPLY_STAGE_GROW, (byte) 1));
        modelMap.put("teacherPositiveOdCheckCount", memberApplyService.count(null, null, OwConstants.OW_APPLY_TYPE_TEACHER,
                OwConstants.OW_APPLY_STAGE_GROW, (byte) 1));

        return "analysis/suspend/suspend_ow";
    }
}
