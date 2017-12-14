package controller.pmd;

import controller.PmdBaseController;
import domain.member.Member;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMonth;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import shiro.ShiroHelper;

import java.util.List;

/**
 * Created by lm on 2017/12/15.
 */
@Controller
public class PmdMemberHelpPayController extends PmdBaseController {

    @RequiresPermissions("pmdMember:helpPay")
    @RequestMapping("/pmdMemberHelpPay")
    public String pmdMemberHelpPay(ModelMap modelMap) {

        Member member = memberService.get(ShiroHelper.getCurrentUserId());
        modelMap.put("member", member);
        return "pmd/pmdMember/pmdMemberHelpPay";
    }

    // 查询本支部的党员未缴费信息
    @RequiresPermissions("pmdMember:helpPay")
    @RequestMapping(value = "/pmdMemberHelpPay_records", method = RequestMethod.POST)
    public String pmdMemberHelpPay_records(int userId, ModelMap modelMap) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberService.get(currentPmdMonth.getId(), userId);
        modelMap.put("pmdMember", pmdMember);

        Member member = memberService.get(ShiroHelper.getCurrentUserId());
        if(member==null || member.getPartyId().intValue()!=pmdMember.getPartyId()) {
            throw new UnauthorizedException();
        }
        if(!partyService.isDirectBranch(member.getPartyId())
                && member.getBranchId().intValue()!=pmdMember.getBranchId()){
            throw new UnauthorizedException();
        }

        // 延迟缴费列表
        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andHasPayEqualTo(false).andIsDelayEqualTo(true)
                .andMonthIdNotEqualTo(currentPmdMonth.getId());
        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);

        // 如果当月未缴费
        if(!pmdMember.getHasPay() && !pmdMember.getIsDelay()){

            pmdMembers.add(0, pmdMember);
        }

        modelMap.put("pmdMembers", pmdMembers);

        return "pmd/pmdMember/pmdMemberHelpPay_records";
    }
}
