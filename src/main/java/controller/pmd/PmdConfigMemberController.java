package controller.pmd;

import domain.ext.ExtJzgSalary;
import domain.ext.ExtRetireSalary;
import domain.member.Member;
import domain.party.Branch;
import domain.party.Party;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberExample;
import domain.pmd.PmdConfigMemberExample.Criteria;
import domain.pmd.PmdConfigMemberType;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/pmd")
public class PmdConfigMemberController extends PmdBaseController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("pmdConfigMember:list")
    @RequestMapping("/pmdConfigMember")
    public String pmdConfigMember(Integer userId, ModelMap modelMap) {
        
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        
        Map<Byte, List<PmdConfigMemberType>> typeMap = new HashMap<>();
        for (Byte pmdMemberType : PmdConstants.PMD_MEMBER_TYPE_MAP.keySet()) {
            typeMap.put(pmdMemberType, pmdConfigMemberTypeService.list(pmdMemberType));
        }
        modelMap.put("typeMap", typeMap);
        
        return "pmd/pmdConfigMember/pmdConfigMember_page";
    }
    
    @RequiresPermissions("pmdConfigMember:list")
    @RequestMapping("/pmdConfigMember_data")
    public void pmdConfigMember_data(HttpServletResponse response,
                                     Boolean isOnlinePay,
                                     Boolean hasReset,
                                     Integer userId,
                                     Byte configMemberType,
                                     Integer configMemberTypeId,
                                     @RequestParam(required = false, defaultValue = "0") int export,
                                     @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                     Integer pageSize, Integer pageNo) throws IOException {
        
        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);
        
        PmdConfigMemberExample example = new PmdConfigMemberExample();
        Criteria criteria = example.createCriteria();
        
        if (isOnlinePay != null) {
            criteria.andIsOnlinePayEqualTo(isOnlinePay);
        }
        if (hasReset != null) {
            criteria.andHasResetEqualTo(hasReset);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (configMemberType != null) {
            criteria.andConfigMemberTypeEqualTo(configMemberType);
        }
        if (configMemberTypeId != null) {
            criteria.andConfigMemberTypeIdEqualTo(configMemberTypeId);
        }
        
        long count = pmdConfigMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {
            
            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdConfigMember> records = pmdConfigMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);
        
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);
        
        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdConfigMember.class, pmdConfigMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }
    
    @RequiresPermissions("pmdConfigMember:edit")
    @RequestMapping(value = "/pmdConfigMember_updateType", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMember_updateType(int userId, byte type, HttpServletRequest request) {
        
        PmdConfigMember record = new PmdConfigMember();
        record.setUserId(userId);
        record.setConfigMemberType(type);
        
        pmdConfigMemberService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_PMD, "更新缴费党员类别：%s", record.getUserId()));
        
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("pmdConfigMember:edit")
    @RequestMapping("/pmdConfigMember_updateType")
    public String pmdConfigMember_updateType(Integer userId, ModelMap modelMap) {
        
        if (userId != null) {
            PmdConfigMember pmdConfigMember = pmdConfigMemberMapper.selectByPrimaryKey(userId);
            modelMap.put("pmdConfigMember", pmdConfigMember);
        }
        
        Map<Byte, List<PmdConfigMemberType>> typeMap = new HashMap<>();
        for (Byte pmdMemberType : PmdConstants.PMD_MEMBER_TYPE_MAP.keySet()) {
            typeMap.put(pmdMemberType, pmdConfigMemberTypeService.list(pmdMemberType));
        }
        modelMap.put("typeMap", typeMap);
        
        return "pmd/pmdConfigMember/pmdConfigMember_updateType";
    }
    
    @RequiresPermissions("pmdConfigMember:edit")
    @RequestMapping("/pmdConfigMember_exportSalary")
    public String pmdConfigMember_exportSalary(
            @RequestParam(required = false, defaultValue = "0") int export,
            Byte type,
            String salaryMonth,
            HttpServletResponse response,
            ModelMap modelMap) {
        
        if(export==1){
            if(type==null) return null;
            
            if(type==1){
                List<ExtJzgSalary> extJzgSalaries = iPmdMapper.extJzgSalaryList(salaryMonth);
                extJzgSalary_export(salaryMonth, extJzgSalaries, response);
            }else if(type==2){
                List<ExtRetireSalary> extRetireSalaries = iPmdMapper.extRetireSalaryList(salaryMonth);
                extRetireSalary_export(salaryMonth, extRetireSalaries, response);
            }
            
            return null;
        }
        
        List<String> jzgSalaryMonthList = iPmdMapper.extJzgSalaryMonthList();
        modelMap.put("jzgSalaryMonthList", jzgSalaryMonthList);
        
        List<String> retireSalaryMonthList = iPmdMapper.extRetireSalaryMonthList();
        modelMap.put("retireSalaryMonthList", retireSalaryMonthList);
        
        return "pmd/pmdConfigMember/pmdConfigMember_exportSalary";
    }
    
    public void extJzgSalary_export(String salaryMonth, List<ExtJzgSalary> records, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"日期|100","工号|100","姓名|50","所在分党委|350|left","所在党支部|350|left","校聘工资|80", "薪级工资|80",
                "岗位工资|80","岗位津贴|80","职务补贴|80","职务补贴1|80","生活补贴|80",
                "书报费|80","洗理费|80","工资冲销|80","失业个人|80","养老个人|80",
                "医疗个人|80","年金个人|80","住房公积金|80","在职人员工资合计|80","校聘人员工资合计|80"};
        List<String[]> valuesList = new ArrayList<>();
         Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        for (int i = 0; i < rownum; i++) {
            ExtJzgSalary record = records.get(i);
            String partyName = null;
            String branchName = null;
            SysUserView uv = sysUserService.findByCode(record.getZgh());
            if(uv!=null){
                Member member = memberService.get(uv.getUserId());
                if(member!=null){
                    Party party = partyMap.get(member.getPartyId());
                    if(party!=null){
                        partyName = party.getName();
                    }
                    if(member.getBranchId()!=null) {
                        Branch branch = branchMap.get(member.getBranchId());
                        if (branch != null) {
                            branchName = branch.getName();
                        }
                    }
                }
            }
            String[] values = {
                    record.getRq(),
                    record.getZgh(),
                    record.getXm(),
                    partyName,
                    branchName,
                    NumberUtils.stripTrailingZeros(record.getXpgz()),
                    NumberUtils.stripTrailingZeros(record.getXjgz()),
                    
                    NumberUtils.stripTrailingZeros(record.getGwgz()),
                    NumberUtils.stripTrailingZeros(record.getGwjt()),
                    NumberUtils.stripTrailingZeros(record.getZwbt()),
                    NumberUtils.stripTrailingZeros(record.getZwbt1()),
                    NumberUtils.stripTrailingZeros(record.getShbt()),
                    
                    NumberUtils.stripTrailingZeros(record.getSbf()),
                    NumberUtils.stripTrailingZeros(record.getXlf()),
                    NumberUtils.stripTrailingZeros(record.getGzcx()),
                    NumberUtils.stripTrailingZeros(record.getSygr()),
                    NumberUtils.stripTrailingZeros(record.getYanglaogr()),
                    
                    NumberUtils.stripTrailingZeros(record.getYiliaogr()),
                    NumberUtils.stripTrailingZeros(record.getNjgr()),
                    NumberUtils.stripTrailingZeros(record.getZfgjj()),
                    NumberUtils.stripTrailingZeros(record.getZzryhj()),
                    NumberUtils.stripTrailingZeros(record.getXpryhj())
            };
            valuesList.add(values);
        }
        String fileName = "在职教职工党员工资(" + salaryMonth + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
    
    public void extRetireSalary_export(String salaryMonth, List<ExtRetireSalary> records, HttpServletResponse response) {

        int rownum = records.size();
        String[] titles = {"日期|100","工号|100","姓名|50","所在分党委|350|left","所在党支部|350|left","党费计算基数|80"};
        List<String[]> valuesList = new ArrayList<>();
    
        Map<Integer, Party> partyMap = partyService.findAll();
        Map<Integer, Branch> branchMap = branchService.findAll();
        for (int i = 0; i < rownum; i++) {
            ExtRetireSalary record = records.get(i);
            String realname = null;
            String partyName = null;
            String branchName = null;
            SysUserView uv = sysUserService.findByCode(record.getZgh());
            if(uv!=null){
                realname = uv.getRealname();
                Member member = memberService.get(uv.getUserId());
                if(member!=null){
                    Party party = partyMap.get(member.getPartyId());
                    if(party!=null){
                        partyName = party.getName();
                    }
                    if(member.getBranchId()!=null) {
                        Branch branch = branchMap.get(member.getBranchId());
                        if (branch != null) {
                            branchName = branch.getName();
                        }
                    }
                }
            }
            String[] values = {
                    record.getRq(),
                    record.getZgh(),
                    realname,
                    partyName,
                    branchName,
                    NumberUtils.stripTrailingZeros(record.getBase())
            };
            valuesList.add(values);
        }
        String fileName = "离退休党费计算基数(" + salaryMonth + ")";
        ExportHelper.export(titles, valuesList, fileName, response);
    }
    
    /*@RequiresPermissions("pmdConfigMember:del")
    @RequestMapping(value = "/pmdConfigMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdConfigMember_del(HttpServletRequest request, Integer userId) {

        if (userId != null) {

            pmdConfigMemberService.del(userId);
            logger.info(addLog(LogConstants.LOG_PMD, "删除党员缴费分类：%s", userId));
        }
        return success(FormUtils.SUCCESS);
    }*/

    /*@RequiresPermissions("pmdConfigMember:del")
    @RequestMapping(value = "/pmdConfigMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map pmdConfigMember_batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] userIds, ModelMap modelMap) {

        if (null != userIds && userIds.length > 0) {

            logger.info(addLog(LogConstants.LOG_PMD, "批量删除党员缴费分类：%s", StringUtils.join(userIds, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
