package controller.analysis;

import controller.member.MemberBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.party.BranchMemberGroupService;
import service.party.PartyMemberGroupService;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;

// 党建待办事项
@Controller

public class SuspendOwController extends MemberBaseController {

    @Autowired
    protected PartyMemberGroupService partyMemberGroupService;
    @Autowired
    protected BranchMemberGroupService branchMemberGroupService;

    @RequestMapping("/suspend_ow")
    @RequiresPermissions("suspend:ow")
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

    @RequestMapping("/suspend_party")
    @RequiresPermissions("suspend:party")
    public String suspend_party(int partyId, ModelMap modelMap) {

        modelMap.put("partyMemberGroupCount", partyMemberGroupService.count(partyId));
        modelMap.put("branchMemberGroupCount", branchMemberGroupService.count(partyId));
        modelMap.put("studentGrowOdCheckCount", memberApplyService.count(partyId, null,
                OwConstants.OW_APPLY_TYPE_STU, OwConstants.OW_APPLY_STAGE_DRAW, (byte) -1));
        modelMap.put("teacherGrowOdCheckCount", memberApplyService.count(partyId, null, OwConstants.OW_APPLY_TYPE_TEACHER,
                OwConstants.OW_APPLY_STAGE_DRAW, (byte) -1));
        modelMap.put("memberOutCount", memberOutService.count(partyId, null, (byte) 1, null));
        modelMap.put("memberInCount", memberInService.count(partyId, null, (byte)1));
        modelMap.put("memberStayCount_abroad", memberStayService.count(partyId, null, (byte) 2,
                MemberConstants.MEMBER_STAY_TYPE_ABROAD, null));//新申请+返回修改
        modelMap.put("memberStayCount_internal", memberStayService.count(partyId, null, (byte) 2,
                MemberConstants.MEMBER_STAY_TYPE_INTERNAL, null));//新申请+返回修改

        modelMap.put("studentPositiveOdCheckCount", memberApplyService.count(partyId, null, OwConstants.OW_APPLY_TYPE_STU,
                OwConstants.OW_APPLY_STAGE_GROW, (byte) 1));
        modelMap.put("teacherPositiveOdCheckCount", memberApplyService.count(partyId, null, OwConstants.OW_APPLY_TYPE_TEACHER,
                OwConstants.OW_APPLY_STAGE_GROW, (byte) 1));

        return "analysis/suspend/suspend_party";
    }
}
