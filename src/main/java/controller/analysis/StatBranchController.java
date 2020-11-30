package controller.analysis;

import controller.BaseController;
import controller.global.OpException;
import domain.member.Member;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;

import java.util.List;

@Controller
@RequiresPermissions("stat:branch")
public class StatBranchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 党支部信息统计
     *
     * @return
     */
    @RequestMapping("/stat_branch_page")
    public String stat_branch_page(ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        List<Integer> branchIdList = loginUserService.adminBranchIdList();
        Member member = memberService.get(userId);
        if (member==null) throw new OpException("您不是该支部的党员");
        if (member.getBranchId()!=null&&branchIdList.contains(member.getBranchId())){
            modelMap.put("branchId", member.getBranchId());
        }else{
            throw new UnauthorizedException();
        }
        return "analysis/party/stat_branch_page";
    }

    // 党员数量统计
    @RequestMapping("/stat_branch_member_count")
    public String stat_branch_member_count(Integer type, Integer branchId, ModelMap modelMap) {

        if (branchId != null) {
            if (type != null) {
                modelMap.put("otherMap", statService.otherMap(type, null, branchId));
            }

            modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(null, branchId));
            modelMap.put("statGrowMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, branchId));
            modelMap.put("statPositiveMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, branchId));
        }

        modelMap.put("isBranchAdmin", true);
        modelMap.put("type", type);
        modelMap.put("branchId", branchId);
        return "analysis/ow/stat_branch_member_count";
    }

    // 党员年龄结构统计
    @RequestMapping("/stat_branch_member_age")
    public String stat_branch_member_age(Byte type, Integer branchId, ModelMap modelMap) {

        if (branchId != null) {
            modelMap.put("statAgeMap", statService.ageMap(type, null, branchId));
        }

        modelMap.put("isBranchAdmin", true);
        modelMap.put("type", type);
        modelMap.put("branchId", branchId);
        return "analysis/ow/stat_branch_member_age";
    }

    // 发展党员统计
    @RequestMapping("/stat_branch_member_apply")
    public String stat_branch_member_apply(Byte type, Integer branchId, ModelMap modelMap) {

        if (branchId != null) {
            modelMap.put("statApplyMap", statService.applyMap(type, null, branchId));
        }

        modelMap.put("type", type);
        return "analysis/ow/stat_branch_member_apply";
    }
}
