package controller.member;

import controller.MemberBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;

/**
 * Created by lm on 2018/1/24.
 */
@Controller
public class MemberStaticController extends MemberBaseController {

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_page")
    public String stat_page(ModelMap modelMap) {

        modelMap.put("studentGrowOdCheckCount", memberApplyService.count(null, null,
                SystemConstants.APPLY_TYPE_STU, SystemConstants.APPLY_STAGE_DRAW, (byte) -1));
        modelMap.put("teacherGrowOdCheckCount", memberApplyService.count(null, null, SystemConstants.APPLY_TYPE_TEACHER,
                SystemConstants.APPLY_STAGE_DRAW, (byte) -1));
        modelMap.put("memberOutCount", memberOutService.count(null, null, (byte) 2, null));
        modelMap.put("memberInCount", memberInService.count(null, null, (byte)2));
        modelMap.put("memberStayCount_abroad", memberStayService.count(null, null, (byte) 3,
                SystemConstants.MEMBER_STAY_TYPE_ABROAD, null));
        modelMap.put("memberStayCount_internal", memberStayService.count(null, null, (byte) 3,
                SystemConstants.MEMBER_STAY_TYPE_INTERNAL, null));

        modelMap.put("studentPositiveOdCheckCount", memberApplyService.count(null, null, SystemConstants.APPLY_TYPE_STU,
                SystemConstants.APPLY_STAGE_GROW, (byte) 1));
        modelMap.put("teacherPositiveOdCheckCount", memberApplyService.count(null, null, SystemConstants.APPLY_TYPE_TEACHER,
                SystemConstants.APPLY_STAGE_GROW, (byte) 1));
        return "analysis/party/stat_member_page";
    }
}
