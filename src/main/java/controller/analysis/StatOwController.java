package controller.analysis;

import controller.BaseController;
import domain.party.Party;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.member.common.MemberStatByPartyBean;
import sys.constants.MemberConstants;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiresPermissions("stat:ow")
public class StatOwController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 党建信息统计
     *
     * @return
     */
    @RequestMapping("/stat_ow_page")
    public String stat_ow_page(ModelMap modelMap) {

        Integer statPartyMemberCount = CmTag.getIntProperty("statPartyMemberCount");
        statPartyMemberCount = (statPartyMemberCount == null) ? 20 : statPartyMemberCount;

        modelMap.put("statPartyMemberCount", NumberUtils.toHanStr(statPartyMemberCount));

        return "analysis/ow/stat_ow_page";
    }

    // 党员数量统计
    @RequestMapping("/stat_member_count")
    public String stat_member_count(Integer type, Integer partyId, Integer branchId, ModelMap modelMap) {

        if (type != null) {
            modelMap.put("otherMap", statService.otherMap(type, partyId, branchId));
        }
        modelMap.put("type", type);
        modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(partyId, branchId));
        modelMap.put("statGrowMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId));
        modelMap.put("statPositiveMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId));
        return "analysis/ow/stat_member_count";
    }

    // 党员年龄结构统计
    @RequestMapping("/stat_member_age")
    public String stat_member_age(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("statAgeMap", statService.ageMap(type, partyId, branchId));
        return "analysis/ow/stat_member_age";
    }

    // 发展党员统计
    @RequestMapping("/stat_member_apply")
    public String stat_member_apply(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("statApplyMap", statService.applyMap(type, partyId, branchId));
        return "analysis/ow/stat_member_apply";
    }

    // 二级党委党员数量分布情况
    @RequestMapping("/stat_member_party")
    public String stat_member_party(ModelMap modelMap) {

        List<String> categories = new ArrayList<>();
        List<Integer> teachers = new ArrayList<>();
        List<Integer> students = new ArrayList<>();

        Integer statPartyMemberCount = CmTag.getIntProperty("statPartyMemberCount");
        Map<Integer, Party> partyMap = partyService.findAll();
        List<MemberStatByPartyBean> memberStatByPartyBeans = statService.partyMap(statPartyMemberCount);
        for (MemberStatByPartyBean bean : memberStatByPartyBeans) {
            Party party = partyMap.get(bean.getPartyId());
            categories.add(StringUtils.defaultIfBlank(party.getShortName(), party.getName()));
            //Integer num = (Integer)map.get("num");
            teachers.add(bean.getTeacher());
            students.add(bean.getStudent());
        }

        modelMap.put("categories", categories);
        modelMap.put("teachers", teachers);
        modelMap.put("students", students);
        return "analysis/ow/stat_member_party";
    }

    //支部类型统计
    @RequestMapping("/stat_branch_type")
    public String stat_branch_type(ModelMap modelMap, Integer partyId) {

        modelMap.put("metaTypes", CmTag.getMetaTypes("mc_branch_type"));
        modelMap.put("branchTypeMap", statService.branchTypeMap(partyId));

        return "analysis/ow/stat_branch_type";
    }
}
