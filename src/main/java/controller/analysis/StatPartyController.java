package controller.analysis;

import controller.BaseController;
import domain.party.Branch;
import domain.party.Party;
import domain.party.PartyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.member.common.MemberStatByBranchBean;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiresPermissions("stat:party")
public class StatPartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分党委信息统计
     *
     * @return
     */
    @RequestMapping("/stat_party_page")
    public String stat_ow_page(ModelMap modelMap, Integer partyId) {

        int userId = ShiroHelper.getCurrentUserId();
        if (ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) && partyId == null) {
            PartyExample example = new PartyExample();
            example.setOrderByClause("sort_order desc");
            List<Party> parties = partyMapper.selectByExample(example);
            if (parties != null && parties.size() > 0)
                partyId = parties.get(0).getId();

        }else if (partyId == null) {
            List<Integer> partyIds = partyAdminService.adminPartyIdList(userId);
            if (partyIds != null && partyIds.size() > 0) {
                partyId = partyIds.get(0);
            }
        }
        modelMap.put("partyId", partyId);
        return "analysis/party/stat_party_page";
    }

    // 党员数量统计
    @RequestMapping("/stat_party_member_count")
    public String stat_member_count(Integer type, Integer partyId, Integer branchId, ModelMap modelMap) {

        if (partyId != null) {
            if (type != null) {
                modelMap.put("otherMap", statService.otherMap(type, partyId, branchId));
            }

            modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(partyId, branchId));
            modelMap.put("statGrowMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId));
            modelMap.put("statPositiveMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId));
        }

        modelMap.put("isPartyAdmin", true);
        modelMap.put("type", type);
        modelMap.put("partyId", partyId);
        return "analysis/ow/stat_member_count";
    }

    // 党员年龄结构统计
    @RequestMapping("/stat_party_member_age")
    public String stat_member_age(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        if (partyId != null) {
            modelMap.put("statAgeMap", statService.ageMap(type, partyId, branchId));
        }

        modelMap.put("isPartyAdmin", true);
        modelMap.put("type", type);
        modelMap.put("partyId", partyId);
        return "analysis/ow/stat_member_age";
    }

    // 发展党员统计
    @RequestMapping("/stat_party_member_apply")
    public String stat_member_apply(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        if (partyId != null) {
            modelMap.put("statApplyMap", statService.applyMap(type, partyId, branchId));
        }

        modelMap.put("type", type);
        return "analysis/ow/stat_member_apply";
    }

    // 二级党委党员数量分布情况
    @RequestMapping("/stat_party_member_party")
    public String stat_member_party(ModelMap modelMap, Integer partyId) {

        if (partyId != null) {
            List<String> categories = new ArrayList<>();
            List<Integer> teachers = new ArrayList<>();
            List<Integer> students = new ArrayList<>();

            Map<Integer, Branch> branchMap = branchService.findAll();
            List<MemberStatByBranchBean> memberStatByPartyBeans = statService.branchMap(partyId);
            for (MemberStatByBranchBean bean : memberStatByPartyBeans) {
                Branch branch = branchMap.get(bean.getBranchId());
                if (branch != null) {
                    categories.add(StringUtils.defaultIfBlank(branch.getShortName(), branch.getName()));
                } else {
                    categories.add("支部不存在");
                }
                teachers.add(bean.getTeacher());
                students.add(bean.getStudent());
            }

            modelMap.put("categories", categories);
            modelMap.put("teachers", teachers);
            modelMap.put("students", students);
        }

        return "analysis/ow/stat_member_party";
    }

    //支部类型统计
    @RequestMapping("/stat_party_branch_type")
    public String stat_branch_type(ModelMap modelMap, Integer partyId) {

        if (partyId != null) {
            modelMap.put("metaTypes", CmTag.getMetaTypes("mc_branch_type"));
            modelMap.put("branchTypeMap", statService.branchTypeMap(partyId));
        }
        return "analysis/ow/stat_branch_type";
    }
}
