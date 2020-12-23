package controller.analysis;

import controller.BaseController;
import domain.base.MetaType;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.member.common.MemberStatByPartyBean;
import sys.constants.MemberConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    // 党组织年统数据
    @RequiresPermissions("stat:owSum")
    @RequestMapping("/stat/owSum")
    public String stat_party_sum(@RequestParam(required = false, defaultValue = "0") int export,
                                 ModelMap modelMap,
                                 HttpServletResponse response) throws IOException {

                                 List<MetaType> metaTypes = new ArrayList<>();
        List<Integer> partyCounts = new ArrayList<>();
        int partySumCount = 0;
        Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_party_class");
        for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()){
            metaTypes.add(entry.getValue());
            PartyExample example = new PartyExample();
            example.createCriteria().andIsDeletedEqualTo(false).andClassIdEqualTo(entry.getValue().getId());
            int partyCount = (int) partyMapper.countByExample(example);
            partyCounts.add(partyCount);
            partySumCount += partyCount;
        }
        modelMap.put("metaTypes", metaTypes);
        modelMap.put("partyCounts", partyCounts);
        modelMap.put("partySumCount", partySumCount);

        //专任教师党支部
        Integer professionalCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_professional_teacher").getId());
        modelMap.put("professionalCount", professionalCount);
        //机关行政产业后勤教工党支部
        Integer supportCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_support_teacher").getId());
        modelMap.put("supportCount", supportCount);
        //离退休党支部总数
        Integer retireCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_retire").getId());
        modelMap.put("retireCount", retireCount);
        //本科生辅导员纵向党支部
        Integer undergraduateCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_undergraduate_assistant").getId());
        modelMap.put("undergraduateCount", undergraduateCount);
        //研究生导师纵向党支部
        Integer graduateCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_graduate_teacher").getId());
        modelMap.put("graduateCount", graduateCount);
        //硕士生党支部
        Integer ssCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_ss_graduate").getId());
        modelMap.put("ssCount", ssCount);
        //硕博研究生党支部
        Integer sbCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_sb_graduate").getId());
        modelMap.put("sbCount", sbCount);
        //博士生党支部
        Integer bsCount = statMemberMapper.getBranchCountByType(CmTag.getMetaTypeByCode("mt_bs_graduate").getId());
        modelMap.put("bsCount", bsCount);

        Integer branchTotalCount = professionalCount+supportCount+retireCount+undergraduateCount+graduateCount+ssCount+sbCount+bsCount;
        modelMap.put("branchTotalCount", branchTotalCount);

        Set<String> typesSet = new HashSet<>();
        typesSet.add(CmTag.getMetaTypeByCode("mt_professional_teacher").getId()+"");
        BranchExample branchExample = new BranchExample();
        branchExample.createCriteria().andIsDeletedEqualTo(false).andTypesContain(typesSet);
        List<Branch> branchList = branchMapper.selectByExample(branchExample);
        List<Integer> branchIdList = branchList.stream().map(Branch::getId).collect(Collectors.toList());

        //师生党员总数
        modelMap.put("totalCount", statMemberMapper.getMemberCount(null, null, null, null ,null,null,null));
        //教工党员总数
        modelMap.put("teacherCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, false, null ,null,null,null));
        //正高级
        modelMap.put("chiefCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, false, "正高" ,branchIdList,null,null));
        //副高级
        modelMap.put("deputyCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, null, "副高" ,branchIdList,null,null));
        //中级及以下
        modelMap.put("middleCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, null, null ,branchIdList,"正高","副高"));
        //离退休教工党员总数
        modelMap.put("retireTeacherCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, true, null ,null,null,null));
        //本科生党员
        int bksStuCount = statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_STUDENT, SystemConstants.USER_TYPE_BKS, null, null ,null,null,null);
        modelMap.put("bksStuCount", bksStuCount);

        int stuCount = statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_STUDENT, null, null, null ,null,null,null);
        int bsStuCount = statMemberMapper.getBsMemberCount();
        //硕士生党员
        modelMap.put("ssStuCount", stuCount - bsStuCount - bksStuCount);
        //博士生党员
        modelMap.put("bsStuCount", bsStuCount);

        if (export == 1) {

            XSSFWorkbook wb = statService.toXlsx(modelMap, partyCounts);

            String fileName = sysConfigService.getSchoolName()
                    + "基层党组织及党员信息总表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }
        return "analysis/ow/stat_ow_sum";
    }
}
