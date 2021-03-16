package controller.analysis;

import controller.BaseController;
import domain.base.MetaType;
import domain.party.Branch;
import domain.party.BranchExample;
import domain.party.Party;
import domain.party.PartyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.member.common.MemberStatByBranchBean;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatPartyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分党委信息统计
     *
     * @return
     */
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_page")
    public String stat_party_page(Integer partyId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        if (ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL) && partyId == null) {
            PartyExample example = new PartyExample();
            example.createCriteria().andFidIsNull();
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
        if (partyId == null)
            throw new UnauthorizedException();

        modelMap.put("partyId", partyId);

        Map<Integer, Party> partyMap = partyService.findAll();
        List<Integer> partyIds = partyAdminService.adminPartyIdList(userId);
        List<Party> parties = new ArrayList<>();

        if (ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
            for (Integer _partyId : partyMap.keySet()) {
                //跳过内设党总支
                if (partyMap.get(_partyId).getFid() == null) {
                    parties.add(partyMap.get(_partyId));
                }
            }
            modelMap.put("parties", parties);
            modelMap.put("checkParty", partyMap.get(partyId));
        }else if (partyIds.size() > 0) {
            for (Integer _partyId : partyIds) {
                parties.add(partyMap.get(_partyId));
            }

            modelMap.put("parties", parties);
            modelMap.put("checkParty", partyMap.get(partyId));
        }

        return "analysis/party/stat_party_page";
    }

    // 党员数量统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_count")
    public String stat_party_member_count(Integer type,int partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("isPartyAdmin", true);
        modelMap.put("type", type);
        modelMap.put("partyId", partyId);
        modelMap.put("branchId", branchId);
        return "analysis/ow/stat_member_count";
    }

    // 党员数量统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_count_data")
    @ResponseBody
    public Map stat_party_member_count_data(Integer type, int partyId, Integer branchId) {

        PartyHelper.checkAuth(partyId);

        Map<String, Object> resultMap = new HashMap<>();
        if (type != null && type !=3) {

            resultMap.put("otherMap", statService.otherMap(type, partyId, branchId));
        }
        Map<Byte, Integer> isRetireGrowMap = new LinkedHashMap<>();
        Map<Byte, Integer> isRetirePositiveMap = new LinkedHashMap<>();
        Map<Byte, Integer> statGrowMap = new LinkedHashMap<>();
        Map<Byte, Integer> statPositiveMap = new LinkedHashMap<>();
        if (type == null){
            isRetireGrowMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId, (byte) 1);
            isRetirePositiveMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId, (byte) 1);
            statGrowMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId, null);
            statPositiveMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId, null);
        }else if (type != null && type ==3){
            isRetirePositiveMap = statService.typeMap(null, partyId, branchId, (byte) 1);
            statPositiveMap = statService.typeMap(null, partyId, branchId, null);
        }


        resultMap.put("statPoliticalStatusMap", statService.politicalStatusMap(partyId, branchId));
        resultMap.put("isRetireGrowMap", isRetireGrowMap);
        resultMap.put("isRetirePositiveMap", isRetirePositiveMap);
        resultMap.put("statGrowMap", statGrowMap);
        resultMap.put("statPositiveMap", statPositiveMap);

        return resultMap;
    }

    // 党员年龄结构统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_age")
    public String stat_party_member_age(Byte type, int partyId, Integer branchId, ModelMap modelMap) {

        PartyHelper.checkAuth(partyId);
        modelMap.put("isPartyAdmin", true);
        modelMap.put("type", type);
        modelMap.put("partyId", partyId);
        modelMap.put("branchId", branchId);

        return "analysis/ow/stat_member_age";
    }

    // 党员年龄结构统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_age_data")
    @ResponseBody
    public Map stat_party_member_age_data(Byte type, int partyId, Integer branchId, ModelMap modelMap) {

        PartyHelper.checkAuth(partyId);
        Map<Byte, Integer> statAgeMap = new HashMap<>();
        statAgeMap = statService.ageMap(type, partyId, branchId);

        return statAgeMap;
    }

    // 发展党员统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_apply")
    public String stat_party_member_apply(Byte type, int partyId, Integer branchId ,ModelMap modelMap) {
        modelMap.put("type", type);
        return "analysis/ow/stat_member_apply";
    }

    // 发展党员统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_apply_data")
    @ResponseBody
    public Map stat_party_member_apply_data(Byte type, int partyId, Integer branchId) {

        PartyHelper.checkAuth(partyId);
        Map<Byte, Integer> statApplyMap = new HashMap<>();
        statApplyMap = statService.applyMap(type, partyId, branchId);

        return statApplyMap;
    }

    // 支部党员数量分布情况
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_party")
    public String stat_party_member_party() {

        return "analysis/ow/stat_member_party";
    }
    // 支部党员数量分布情况
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_party_data")
    @ResponseBody
    public Map stat_party_member_party_data(int partyId) {

        PartyHelper.checkAuth(partyId);

        Map<String, Object> resultMap = new HashMap<>();
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
                if (PartyHelper.isDirectBranch(partyId)){
                    Party party = partyMapper.selectByPrimaryKey(partyId);
                    categories.add(StringUtils.isBlank(party.getShortName())?party.getName():party.getShortName());
                }else {
                    categories.add("支部不存在");
                }
            }
            teachers.add(bean.getTeacher());
            students.add(bean.getStudent());
        }

        resultMap.put("categories", categories);
        resultMap.put("teachers", teachers);
        resultMap.put("students", students);

        return resultMap;
    }

    //支部类型统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_branch_type")
    public String stat_party_branch_type(ModelMap modelMap){
        modelMap.put("metaTypes", CmTag.getMetaTypes("mc_branch_type"));
        return "analysis/ow/stat_branch_type";
    }

    //支部类型统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_branch_type_data")
    @ResponseBody
    public Map stat_party_branch_type(int partyId) {

        PartyHelper.checkAuth(partyId);

        Map<Integer, Integer> branchTypeMap = new HashMap();
        branchTypeMap=statService.branchTypeMap(partyId);

        return branchTypeMap;
    }
    // 换届时间统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_pb_tran_time")
    public String stat_pb_tran_time(int partyId,ModelMap modelMap) {

        return "analysis/ow/stat_branch_tran_time";
    }

    // 换届时间统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_pb_tran_data")
    @ResponseBody
    public Map stat_pb_tran_data(int partyId) {

        PartyHelper.checkAuth(partyId);
        Map<String, Integer> statBranchTranMap = new HashMap<>();

        List<Map> statBranchTranList = statOwInfoMapper.countBranchByTranTime(partyId);
        for (Map entity : statBranchTranList) {
            String year = entity.get("year").toString();
            int num = ((Long) entity.get("num")).intValue();
            statBranchTranMap.put(year,num);
        }
        return statBranchTranMap;
    }
    //党员每月转入转出统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_inout")
    public String stat_party_member_inout(){

        return "analysis/ow/stat_member_inout";
    }

    //党员每月转入转出统计
    @RequiresPermissions("stat:party")
    @RequestMapping("/stat_party_member_inout_data")
    @ResponseBody
    public Map stat_party_member_inout_data(int partyId) throws ParseException {

        PartyHelper.checkAuth(partyId);

        Map<String, Object> resultMap = new HashMap<>();
        Date now = new Date();

        Set<String> months = new TreeSet<>();
        months = statService.getMonthBetween(statService.getOtherYear(now, -2), DateUtils.formatDate(now, "yyyy-MM"));
        Map<String, Integer> countMemberIn = new TreeMap<>();
        Map<String, Integer> countMemberOut = new TreeMap<>();
        for (String month : months) {

            countMemberIn.put(month, iMemberMapper.countMemberIn(month, partyId));
            countMemberOut.put(month, iMemberMapper.countMemberOut(month, partyId));
        }

        resultMap.put("months", months);
        resultMap.put("countMemberIn", countMemberIn);
        resultMap.put("countMemberOut", countMemberOut);

        return resultMap;
    }

    // 二级党委年统数据
    @RequiresPermissions("stat:partySum")
    @RequestMapping("/stat/partySum")
    public String stat_party_sum(Integer partyId,
                                 @RequestParam(required = false, defaultValue = "0") Integer export,
                                 ModelMap modelMap,
                                 HttpServletResponse response) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();
        Map<Integer, Party> partyMap = partyService.findAll();
        List<Integer> partyIds = partyAdminService.adminPartyIdList(userId);
        List<Party> parties = new ArrayList<>();

        if (ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {
            for (Integer _partyId : partyMap.keySet()) {
                if (partyMap.get(_partyId).getFid() == null) {
                    parties.add(partyMap.get(_partyId));
                }
            }
        }else if (partyIds.size() > 0) {
            for (Integer _partyId : partyIds) {
                parties.add(partyMap.get(_partyId));
            }
        }else {
            throw new UnauthorizedException();
        }
        if (partyId == null && parties != null && parties.size() >0)
            partyId = parties.get(0).getId();
        Party checkParty = parties.get(0);
        modelMap.put("parties", parties);
        modelMap.put("checkParty", checkParty);

        if (partyId!=null) {
            checkParty = partyMap.get(partyId);
            modelMap.put("checkParty", checkParty);

            //内设党总支
            int pgbCount = 0;
            if (CmTag.getBoolProperty("use_inside_pgb")){
                pgbCount = statMemberMapper.getPgbCount(checkParty.getId());
            }
            modelMap.put("pgbCount", pgbCount);

            //专任教师党支部
            Integer professionalCount = null;
            MetaType mtProfessionalTeacher = CmTag.getMetaTypeByCode("mt_professional_teacher");
            if (mtProfessionalTeacher != null) {
                professionalCount = statMemberMapper.getBCByPartyId(mtProfessionalTeacher.getId(), partyId);
                modelMap.put("professionalCount", professionalCount);
            }

            //机关行政产业后勤教工党支部
            Integer supportCount = null;
            MetaType mtSupportTeacher = CmTag.getMetaTypeByCode("mt_support_teacher");
            if (mtSupportTeacher != null) {
                supportCount = statMemberMapper.getBCByPartyId(mtSupportTeacher.getId(), partyId);
                modelMap.put("supportCount", supportCount);
            }

            //离退休党支部总数
            Integer retireCount = null;
            MetaType mtRetire = CmTag.getMetaTypeByCode("mt_retire");
            if (mtSupportTeacher != null) {
                retireCount = statMemberMapper.getBCByPartyId(mtRetire.getId(), partyId);
                modelMap.put("retireCount", retireCount);
            }

            //本科生辅导员纵向党支部
            Integer undergraduateCount = null;
            MetaType mtUndergraduateAssistant = CmTag.getMetaTypeByCode("mt_undergraduate_assistant");
            if (mtUndergraduateAssistant != null) {
                undergraduateCount = statMemberMapper.getBCByPartyId(mtUndergraduateAssistant.getId(), partyId);
                modelMap.put("undergraduateCount", undergraduateCount);
            }
            //研究生导师纵向党支部
            Integer graduateCount = null;
            MetaType mtGraduateTeacher = CmTag.getMetaTypeByCode("mt_graduate_teacher");
            if (mtGraduateTeacher != null) {
                graduateCount = statMemberMapper.getBCByPartyId(mtGraduateTeacher.getId(), partyId);
                modelMap.put("graduateCount", graduateCount);
            }
            //硕士生党支部
            Integer ssCount = null;
            MetaType mtSsGraduate = CmTag.getMetaTypeByCode("mt_ss_graduate");
            if (mtSsGraduate != null) {
                ssCount = statMemberMapper.getBCByPartyId(mtSsGraduate.getId(), partyId);
                modelMap.put("ssCount", ssCount);
            }
            //硕博研究生党支部
            Integer sbCount = null;
            MetaType mtSbGraduate = CmTag.getMetaTypeByCode("mt_sb_graduate");
            if (mtSbGraduate != null) {
                sbCount = statMemberMapper.getBCByPartyId(mtSbGraduate.getId(), partyId);
                modelMap.put("sbCount", sbCount);
            }
            //博士生党支部
            Integer bsCount = null;
            MetaType mtBsGraduate = CmTag.getMetaTypeByCode("mt_bs_graduate");
            if (mtBsGraduate != null) {
                bsCount = statMemberMapper.getBCByPartyId(mtBsGraduate.getId(), partyId);
                modelMap.put("bsCount", bsCount);
            }

            Integer branchTotalCount = NumberUtils.trimToZero(professionalCount)
                    + NumberUtils.trimToZero(supportCount)
                    + NumberUtils.trimToZero(retireCount)
                    + NumberUtils.trimToZero(undergraduateCount)
                    + NumberUtils.trimToZero(graduateCount)
                    + NumberUtils.trimToZero(ssCount)
                    + NumberUtils.trimToZero(sbCount)
                    + NumberUtils.trimToZero(bsCount);

            modelMap.put("branchTotalCount", branchTotalCount);

            BranchExample branchExample = new BranchExample();
            BranchExample.Criteria criteria = branchExample.createCriteria().andIsDeletedEqualTo(false);

            Set<String> typesSet = new HashSet<>();
            if (mtProfessionalTeacher != null) {
                typesSet.add(mtProfessionalTeacher.getId() + "");
                criteria.andTypesContain(typesSet);
            }

            List<Branch> branchList = branchMapper.selectByExample(branchExample);
            List<Integer> branchIdList = branchList.stream().map(Branch::getId).collect(Collectors.toList());

            //师生党员总数
            modelMap.put("totalCount", statMemberMapper.getMemberCount(null, null, null, null, null, null, null, partyId));
            //教工党员总数
            modelMap.put("teacherCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, false, null, null, null, null, partyId));
            //正高级
            modelMap.put("chiefCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, false, "正高", branchIdList, null, null, partyId));
            //副高级
            modelMap.put("deputyCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, null, "副高", branchIdList, null, null, partyId));
            //中级及以下
            modelMap.put("middleCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, null, null, branchIdList, "正高", "副高", partyId));
            //离退休教工党员总数
            modelMap.put("retireTeacherCount", statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_TEACHER, null, true, null, null, null, null, partyId));
            //本科生党员
            int bksStuCount = statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_STUDENT, SystemConstants.USER_TYPE_BKS, null, null, null, null, null, partyId);
            modelMap.put("bksStuCount", bksStuCount);

            int stuCount = statMemberMapper.getMemberCount(MemberConstants.MEMBER_TYPE_STUDENT, null, null, null, null, null, null, partyId);
            int bsStuCount = statMemberMapper.getPartyBsMemberCount(partyId);
            //硕士生党员
            modelMap.put("ssStuCount", stuCount - bsStuCount - bksStuCount);
            //博士生党员
            modelMap.put("bsStuCount", bsStuCount);
        }

        if (export == 1) {

            XSSFWorkbook wb = statService.partyToXlsx(modelMap, partyId);

            String fileName = sysConfigService.getSchoolName() + checkParty.getName()
                    + "信息总表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }
        return "analysis/ow/stat_ow_sum";
    }
}
