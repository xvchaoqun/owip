package controller.analysis;

import controller.BaseController;
import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberApplyViewExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StatBranchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 党支部信息统计
     *
     * @return
     */
    @RequiresPermissions("stat:branch")
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
        return "analysis/branch/stat_branch_page";
    }

    // 党员数量统计
    @RequiresPermissions("stat:branch")
    @RequestMapping("/stat_branch_member_count")
    public String stat_branch_member_count(Integer type, int branchId, ModelMap modelMap) {

        PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), null, branchId);
        if (type != null && type !=3) {

            modelMap.put("otherMap", statService.otherMap(type, null, branchId));
        }
        Map<Byte, Integer> statGrowMap = new LinkedHashMap<>();
        Map<Byte, Integer> statPositiveMap = new LinkedHashMap<>();
        if (type == null){
            statGrowMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, branchId);
            statPositiveMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, branchId);
        }else if (type != null && type ==3){
            statPositiveMap = statService.typeMap(null, null, branchId);
        }

        modelMap.put("type", type);
        modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(null, branchId));
        modelMap.put("statGrowMap", statGrowMap);
        modelMap.put("statPositiveMap", statPositiveMap);

        modelMap.put("branchId", branchId);
        return "analysis/branch/stat_branch_member_count";
    }

    // 党员年龄结构统计
    @RequiresPermissions("stat:branch")
    @RequestMapping("/stat_branch_member_age")
    public String stat_branch_member_age(Byte type, int branchId, ModelMap modelMap) {

        PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), null, branchId);
        modelMap.put("statAgeMap", statService.ageMap(type, null, branchId));
        modelMap.put("type", type);
        modelMap.put("branchId", branchId);
        return "analysis/branch/stat_branch_member_age";
    }

    // 发展党员统计
    @RequiresPermissions("stat:branch")
    @RequestMapping("/stat_branch_member_apply")
    public String stat_branch_member_apply(Byte type, int branchId, ModelMap modelMap) {

        PartyHelper.hasBranchAuth(ShiroHelper.getCurrentUserId(), null, branchId);
        modelMap.put("statApplyMap", statService.applyMap(type, null, branchId));
        modelMap.put("type", type);
        return "analysis/branch/stat_branch_member_apply";
    }

    @RequiresPermissions("stat:branch")
    @RequestMapping(value = "/stat_branch_member_remind", method = RequestMethod.POST)
    @ResponseBody
    public Map stat_branch_member_remind(Integer branchId, ModelMap modelMap) {
        if (!ShiroHelper.hasRole(RoleConstants.ROLE_BRANCHADMIN)) {
            throw new UnauthorizedException();
        }
        Date before = DateUtils.getDateBeforeOrAfterYears(new Date(), -1);
        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria();
        criteria.andStageEqualTo(OwConstants.OW_APPLY_STAGE_GROW).andGrowTimeLessThanOrEqualTo(before)
                .andPositiveStatusIsNull().andIsRemoveEqualTo(false);
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        long count = memberApplyViewMapper.countByExample(example);
        return success(String.valueOf(count));
    }
}
