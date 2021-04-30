package controller.analysis;

import controller.BaseController;
import domain.base.MetaType;
import domain.party.Branch;
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
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

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
        Map<Byte, Integer> statGrowMap = new LinkedHashMap<>();
        Map<Byte, Integer> statPositiveMap = new LinkedHashMap<>();
        Map<Byte, Integer> statMap = new LinkedHashMap<>();
        if (type == null){
            statGrowMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, partyId, branchId);
            statPositiveMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, partyId, branchId);
        }else if (type != null && type ==3){
            statMap = statService.typeMap(null, partyId, branchId);
        }


        resultMap.put("statPoliticalStatusMap", statService.politicalStatusMap(partyId, branchId));
        resultMap.put("statGrowMap", statGrowMap);
        resultMap.put("statPositiveMap", statPositiveMap);
        resultMap.put("statMap", statMap);

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

        int  nullBranchType = statMemberMapper.getNullBranchTypes(partyId);

        Map<String, Integer> branchTypeMap = new LinkedHashMap<>();
        Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_branch_type");
        Map<Integer,Integer> branchTypes=statService.branchTypeMap(partyId);

        for (Integer key : branchTypes.keySet()) {
            branchTypeMap.put(metaTypeMap.get(key).getName(),branchTypes.get(key));
        }

        if(nullBranchType != 0){
            branchTypeMap.put("其他",nullBranchType);
        }

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

            statService.getMemberSum(modelMap, partyId);
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
