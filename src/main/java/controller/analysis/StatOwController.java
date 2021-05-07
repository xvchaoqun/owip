package controller.analysis;

import controller.BaseController;
import domain.base.MetaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import persistence.member.common.MemberStatByPartyBean;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.NumberUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
public class StatOwController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 党建信息统计
     *
     * @return
     */
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_ow_page")
    public String stat_ow_page(ModelMap modelMap) {

        Integer statPartyMemberCount = CmTag.getIntProperty("statPartyMemberCount");
        statPartyMemberCount = (statPartyMemberCount == null) ? 20 : statPartyMemberCount;

        modelMap.put("statPartyMemberCount", NumberUtils.toHanStr(statPartyMemberCount));

        return "analysis/ow/stat_ow_page";
    }

    // 党员数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_count")
    public String stat_member_count(Integer type, ModelMap modelMap) {

        if (ShiroHelper.isPermitted("statOw:showPart")){
            type = 3;
        }
        modelMap.put("type", type);
        return "analysis/ow/stat_member_count";
    }

    // 党员数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_count_data")
    @ResponseBody
    public Map stat_member_count_data(Integer type, Integer partyId, Integer branchId) {

        if (ShiroHelper.isPermitted("statOw:showPart")){
            type = 3;
        }

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
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_age")
    public String stat_member_age(Byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        return "analysis/ow/stat_member_age";
    }
    // 党员年龄结构统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_age_data")
    @ResponseBody
    public Map stat_member_age_data(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {
        Map<Byte, Integer> statAgeMap = new HashMap<>();
        statAgeMap = statService.ageMap(type, partyId, branchId);
        return statAgeMap;
    }

    // 发展党员数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_apply")
    public String stat_member_apply(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {

        modelMap.put("type", type);
        return "analysis/ow/stat_member_apply";
    }
    // 发展党员数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_apply_data")
    @ResponseBody
    public Map stat_member_apply_data(Byte type, Integer partyId, Integer branchId, ModelMap modelMap) {
        Map<Byte, Integer> statApplyMap = new HashMap<>();
        statApplyMap = statService.applyMap(type, partyId, branchId);

        return statApplyMap;
    }

    // 二级党委党员数量分布情况
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_party")
    public String stat_member_party() {

        return "analysis/ow/stat_member_party";
    }
    // 二级党委党员数量分布情况
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_member_party_data")
    @ResponseBody
    public Map stat_member_party_data() {

        Map<String, Object> resultMap = new HashMap<>();
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

        resultMap.put("categories", categories);
        resultMap.put("teachers", teachers);
        resultMap.put("students", students);
        return resultMap;
    }


    //支部类型统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_branch_type")
    public String stat_branch_type(ModelMap modelMap,Integer partyId) {

        modelMap.put("metaTypes", CmTag.getMetaTypes("mc_branch_type"));

        return "analysis/ow/stat_branch_type";
    }

    //支部类型统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_branch_type_data")
    @ResponseBody
    public Map stat_branch_type_data(Integer partyId) {

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
    // 分党委换届数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_party_tran_time")
    public String stat_party_tran_time() {

        return "analysis/ow/stat_party_tran_time";
    }
    // 分党委换届数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_party_tran_data")
    @ResponseBody
    public Map stat_party_tran_data(ModelMap modelMap) {

        Map<String, Integer> statPartyTranMap = new HashMap<>();

        List<Map> statPartyTranList = statOwInfoMapper.countPartyByTranTime();
        for (Map entity : statPartyTranList) {
            String year = entity.get("year").toString();
            int num = ((Long) entity.get("num")).intValue();
            statPartyTranMap.put(year,num);
        }
        return statPartyTranMap;
    }
    // 党支部换届数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_branch_tran_time")
    public String stat_branch_tran_time() {

        return "analysis/ow/stat_branch_tran_time";
    }
    // 党支部换届数量统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_branch_tran_data")
    @ResponseBody
    public Map stat_branch_tran_data(ModelMap modelMap) {

        Map<String, Integer> statBranchTranMap = new HashMap<>();

        List<Map> statBranchTranList = statOwInfoMapper.countBranchByTranTime(null);
        for (Map entity : statBranchTranList) {
            String year = entity.get("year").toString();
            int num = ((Long) entity.get("num")).intValue();
            statBranchTranMap.put(year,num);
        }
        return statBranchTranMap;
    }
    //党员每月转入转出统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_ow_member_inout")
    public String stat_ow_member_inout(){

        return "analysis/ow/stat_member_inout";
    }

    //党员每月转入转出统计
    @RequiresPermissions("stat:ow")
    @RequestMapping("/stat_ow_member_inout_data")
    @ResponseBody
    public Map stat_ow_member_inout_data(Integer partyId) throws ParseException {

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

    // 党组织年统数据
    @RequiresPermissions("stat:owSum")
    @RequestMapping("/stat/owSum")
    public String stat_ow_sum(@RequestParam(required = false, defaultValue = "0") int export,
                                 ModelMap modelMap,
                                 HttpServletResponse response) throws IOException {

        List<MetaType> metaTypes = new ArrayList<>();
        List<Integer> partyCounts = new ArrayList<>();
        int partySumCount = 0;
        Map<Integer, MetaType> metaTypeMap = CmTag.getMetaTypes("mc_party_class");
        for (Map.Entry<Integer, MetaType> entry : metaTypeMap.entrySet()){
            metaTypes.add(entry.getValue());
            PartyExample example = new PartyExample();
            example.createCriteria().andIsDeletedEqualTo(false).andClassIdEqualTo(entry.getValue().getId()).andFidIsNull();
            int partyCount = (int) partyMapper.countByExample(example);
            partyCounts.add(partyCount);
            partySumCount += partyCount;
        }
        modelMap.put("metaTypes", metaTypes);
        modelMap.put("partyCounts", partyCounts);
        modelMap.put("partySumCount", partySumCount);

        statService.getMemberSum(modelMap, null);

        if (export == 1) {

            XSSFWorkbook wb = statService.owToXlsx(modelMap, partyCounts);

            String fileName = sysConfigService.getSchoolName()
                    + "基层党组织及党员信息总表（" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD) + "）";
            ExportHelper.output(wb, fileName + ".xlsx", response);
            return null;
        }
        return "analysis/ow/stat_ow_sum";
    }
}
