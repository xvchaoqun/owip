package controller.analysis;

import bean.analysis.MemberStatByPartyBean;
import bean.analysis.StatIntBean;
import controller.BaseController;
import domain.party.Party;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.common.StatMapper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2016/8/1.
 */
@Controller
public class StatMemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_page")
    public String stat_page(ModelMap modelMap) {

        modelMap.put("growOdCheckCount", memberApplyService.count(null, null, null, SystemConstants.APPLY_STAGE_DRAW, (byte) 1));
        modelMap.put("memberOutCount", memberOutService.count(null, null, (byte) 2, null));
        modelMap.put("memberInCount", memberInService.count(null, null, (byte)2));
        modelMap.put("graduateAbroadCount", graduateAbroadService.count(null, null, (byte) 3, null));
        return "analysis/party/stat_member_page";
    }

    @RequiresPermissions("stat:list")
    @RequestMapping("/stat_member_count")
    public String stat_member_count(Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(partyId, branchId));
        modelMap.put("statGrowMap", statService.typeMap(SystemConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId));
        modelMap.put("statPositiveMap", statService.typeMap(SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId));
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
