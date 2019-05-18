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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/8/1.
 */
@Controller
public class StatMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_count")
    public String stat_member_count(Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(partyId, branchId));
        modelMap.put("statGrowMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId));
        modelMap.put("statPositiveMap", statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId));
        return "analysis/party/stat_member_count";
    }

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_age")
    public String stat_member_age(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("statAgeMap", statService.ageMap(type, partyId, branchId));
        return "analysis/party/stat_member_age";
    }

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_apply")
    public String stat_member_apply(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("type", type);
        modelMap.put("statApplyMap", statService.applyMap(type, partyId, branchId));
        return "analysis/party/stat_member_apply";
    }

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_party")
    public String stat_member_party(Integer top, ModelMap modelMap) {

        List<String> categories = new ArrayList<>();
        List<Integer> teachers = new ArrayList<>();
        List<Integer> students = new ArrayList<>();

        Map<Integer, Party> partyMap = partyService.findAll();
        List<MemberStatByPartyBean> memberStatByPartyBeans = statService.partyMap(top);
        for (MemberStatByPartyBean bean : memberStatByPartyBeans) {
            Party party = partyMap.get(bean.getPartyId());
            categories.add(StringUtils.defaultIfBlank(party.getShortName(), party.getName()));
            //Integer num = (Integer)map.get("num");
            teachers.add(bean.getTeacher());
            students.add(bean.getStudent());
        }

        modelMap.put("categories", categories);
        modelMap.put("teachers",teachers);
        modelMap.put("students",students);
        return "analysis/party/stat_member_party";
    }
}
