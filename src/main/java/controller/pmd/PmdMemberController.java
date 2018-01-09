package controller.pmd;

import controller.PmdBaseController;
import domain.pmd.PmdBranch;
import domain.pmd.PmdConfigMember;
import domain.pmd.PmdConfigMemberType;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberExample.Criteria;
import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMonth;
import domain.pmd.PmdParty;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/pmd")
public class PmdMemberController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //@RequiresPermissions("pmdMember:list")
    @RequestMapping("/pmdMember")
    public String pmdMember(
            // 1 支部访问 2 直属支部访问 3 党委访问  4 支部或直属支部 访问补缴列表 5 全校缴费列表 6 党费代缴列表
            @RequestParam(required = false, defaultValue = "1") Byte cls,
            Integer userId,
            Integer partyId,
            Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if(cls!=6){
            SecurityUtils.getSubject().checkPermission("pmdMember:list");
        }
        if (cls == 5) {
            SecurityUtils.getSubject().checkPermission("pmdMember:allList");
            if (partyId != null)
                modelMap.put("party", partyService.findAll().get(partyId));
            if (branchId != null)
                modelMap.put("branch", branchService.findAll().get(branchId));
            return "pmd/pmdMember/pmdMemberList_page";
        }else if (cls == 3) {
            // 分党委管理员（不包含直属党支部）访问，弹出框
            modelMap.put("branch", branchService.findAll().get(branchId));
            return "pmd/pmdMember/pmdMember_party_page";
        }else if(cls==6){
            // 党费代缴列表
            SecurityUtils.getSubject().checkPermission("pmdMember:helpPay");
            return "pmd/pmdMember/pmdMemberHelpPayList_page";
        }

        // 判断当前管理员是否允许操作
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        boolean canAdmin = false;
        if (currentPmdMonth != null) {
            boolean hasReport;
            int monthId = currentPmdMonth.getId();
            if (branchId != null) {
                PmdBranch pmdBranch = pmdBranchService.get(monthId, partyId, branchId);
                hasReport = pmdBranch.getHasReport();
            } else {
                PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
                hasReport = pmdParty.getHasReport();
            }
            canAdmin = (hasReport == false);
        }
        modelMap.put("canAdmin", canAdmin);

        if(cls==4)
            return "pmd/pmdMember/pmdMember_delay_page";

        // 支部管理员或直属党支部管理员访问
        return "pmd/pmdMember/pmdMember_page";
    }

    //@RequiresPermissions("pmdMember:list")
    @RequestMapping("/pmdMember_data")
    public void pmdMember_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               Integer monthId,
                               Integer partyId,
                               Integer branchId,
                               Integer userId,
                               Byte type,
                               Boolean hasPay,
                               Boolean isDelay,
                               Boolean isSelfPay,
                               Integer pageSize, Integer pageNo) throws IOException {

        if(cls!=6){
            SecurityUtils.getSubject().checkPermission("pmdMember:list");
        }

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        PmdMemberExample example = new PmdMemberExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("month_id desc, type asc, id asc");

        if (cls == 1) {
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());
            if (adminBranchIds.size() > 0) {
                criteria.andBranchIdIn(adminBranchIds);
            } else {
                criteria.andBranchIdIsNull();
            }
        } else if (cls == 2) {
            // 直属党支部访问党员列表
            // 此时必须传入partyId
            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);
            if (adminPartyIdSet.contains(partyId)) {
                criteria.andPartyIdEqualTo(partyId);
            } else {
                criteria.andPartyIdIsNull();
            }
        } else if (cls == 3) {
            // 分党委（不包含直属党支部）访问党员列表
            // 此时必须传入partyId,branchId
            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);
            if (adminPartyIdSet.contains(partyId)) {
                criteria.andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
            } else {
                criteria.andPartyIdIsNull();
            }
        } else if(cls==5){
            SecurityUtils.getSubject().checkPermission("pmdMember:allList");
        }else if(cls==6){
            // 党费代缴列表
            SecurityUtils.getSubject().checkPermission("pmdMember:helpPay");
            criteria.andChargeUserIdEqualTo(ShiroHelper.getCurrentUserId());
        }else {
            criteria.andIdIsNull();
        }

        if (monthId != null) {
            criteria.andMonthIdEqualTo(monthId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (hasPay != null) {
            criteria.andHasPayEqualTo(hasPay);
        }
        if (isDelay != null) {
            if (BooleanUtils.isFalse(isDelay)) {
                // 按时缴费，肯定已缴费
                criteria.andHasPayEqualTo(true);
            }
            criteria.andIsDelayEqualTo(isDelay);
        }
        if (isSelfPay != null) {
            criteria.andIsSelfPayEqualTo(isSelfPay);
        }

        long count = pmdMemberMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<PmdMember> records = pmdMemberMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdMember.class, pmdMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }


    @RequiresPermissions("pmdMember:list")
    @RequestMapping("/pmdMember_delay_data")
    public void pmdMember_delay_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               int monthId,
                               Integer partyId,
                               Integer branchId,
                               Integer userId,
                               Boolean hasPay,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        if(branchId!=null) {
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());
            Set<Integer> adminBranchIdSet = new HashSet<>();
            adminBranchIdSet.addAll(adminBranchIds);
            if (!adminBranchIdSet.contains(branchId)) return;
        }else{
            // 直属党支部
            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);
            if (!partyService.isDirectBranch(partyId) || !adminPartyIdSet.contains(partyId)) return;
        }

        long count = 0;
        List<PmdMemberPayView> pmdMemberPayViews = null;
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if(currentPmdMonth!=null && monthId == currentPmdMonth.getId()) {
            // 访问当月补缴列表
            count = iPmdMapper.historyDelayMemberListCount(monthId, partyId, branchId, userId, hasPay);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            pmdMemberPayViews = iPmdMapper.historyDelayMemberList(monthId, partyId, branchId, userId, hasPay,
                            new RowBounds((pageNo - 1) * pageSize, pageSize));
        }else{
            // 访问往月已补缴列表
            count = iPmdMapper.historyDelayMemberListCount2(monthId, partyId, branchId, userId);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            pmdMemberPayViews = iPmdMapper.historyDelayMemberList2(monthId, partyId, branchId, userId,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        }

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", pmdMemberPayViews);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        //baseMixins.put(pmdMember.class, pmdMemberMixin.class);
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    // 删除未缴费记录
    @RequiresPermissions("pmdMember:del")
    @RequestMapping(value = "/pmdMember_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_del(int id, HttpServletRequest request) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);

        pmdMemberService.del(id);
        logger.info(addLog(SystemConstants.LOG_PMD, "删除未缴费记录：%s", JSONUtils.toString(pmdMember, false)));

        return success(FormUtils.SUCCESS);
    }


    // 现金支付
   /* @RequiresPermissions("pmdMember:payCash")
    @RequestMapping(value = "/pmdMember_payCash", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_payCash(int id, HttpServletRequest request) {

        pmdPayService.payCash(id);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMember:payCash")
    @RequestMapping("/pmdMember_payCash")
    public String pmdMember_payCash(int id, ModelMap modelMap) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);
        modelMap.put("pmdMember", pmdMember);
        return "pmd/pmdMember/pmdMember_payCash";
    }*/

    // 延迟缴费
    @RequiresPermissions("pmdMember:delay")
    @RequestMapping(value = "/pmdMember_delay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_delay(int id, String delayReason, HttpServletRequest request) {

        if (StringUtils.isBlank(delayReason)) {
            return failed("请填写延迟缴费原因。");
        }

        pmdPayService.delay(id, delayReason);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMember:delay")
    @RequestMapping("/pmdMember_delay")
    public String pmdMember_delay(int id, ModelMap modelMap) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);
        modelMap.put("pmdMember", pmdMember);
        return "pmd/pmdMember/pmdMember_delay";
    }

    // 取消延迟缴费
    @RequiresPermissions("pmdMember:delay")
    @RequestMapping(value = "/pmdMember_unDelay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_unDelay(int id, HttpServletRequest request) {

        pmdPayService.unDelay(id);
        return success(FormUtils.SUCCESS);
    }

    // 设定缴纳额度
    @RequiresPermissions("pmdMember:setDuePay")
    @RequestMapping("/pmdMember_setDuePay")
    public String pmdMember_setDuePay(@RequestParam(value = "ids[]") int[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("pmdMember", pmdMemberMapper.selectByPrimaryKey(ids[0]));
        }

        return "pmd/pmdMember/pmdMember_setDuePay";
    }

    /*@RequiresPermissions("pmdMember:setDuePay")
    @RequestMapping(value = "/pmdMember_setDuePay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_setDuePay(@RequestParam(value = "ids[]") int[] ids,
                                      BigDecimal amount, String remark) {

        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
            return failed("额度必须大于0");
        }

        pmdMemberService.setDuePay(ids, amount, remark);

        logger.info(addLog(SystemConstants.LOG_PMD, "[支部管理员]设定缴纳额度-%s-%s",
                StringUtils.join(ids, ","), amount));
        return success(FormUtils.SUCCESS);
    }
*/
    // 选择党员分类
    @RequiresPermissions("pmdMember:selectMemberType")
    @RequestMapping("/pmdMember_selectMemberType")
    public String pmdMember_selectMemberType(@RequestParam(value = "ids[]") int[] ids,
                                             byte configMemberType,
                                             ModelMap modelMap) {

        if (ids.length == 1) {
            PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(ids[0]);
            modelMap.put("pmdMember", pmdMember);
            Integer configMemberTypeId = pmdMember.getConfigMemberTypeId();
            if(configMemberTypeId!=null) {
                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
                modelMap.put("pmdConfigMemberType", pmdConfigMemberType);
            }
            PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(pmdMember.getUserId());
            modelMap.put("pmdConfigMember", pmdConfigMember);
        }

        modelMap.put("configMemberType",  configMemberType);
        modelMap.put("configMemberTypes",  pmdConfigMemberTypeService.list(configMemberType));

        return "pmd/pmdMember/pmdMember_selectMemberType";
    }

    @RequiresPermissions("pmdMember:selectMemberType")
    @RequestMapping(value = "/pmdMember_selectMemberType", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_selectMemberType(@RequestParam(value = "ids[]") int[] ids,
                                          Boolean hasSalary, // 学生党员必须设置
                                          byte configMemberType,
                                          int configMemberTypeId,
                                          BigDecimal amount, String remark) {


        pmdMemberService.selectMemberType(ids, hasSalary, configMemberType, configMemberTypeId, amount, remark);

        logger.info(addLog(SystemConstants.LOG_PMD, "[支部管理员]选择党员分类别-%s-%s-%s",
                StringUtils.join(ids, ","), configMemberTypeId, amount));
        return success(FormUtils.SUCCESS);
    }

    // 设定减免标准
    @RequiresPermissions("pmdMember:selectReduceNorm")
    @RequestMapping("/pmdMember_selectReduceNorm")
    public String pmdMember_selectReduceNorm(@RequestParam(value = "ids[]") int[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("pmdMember", pmdMemberMapper.selectByPrimaryKey(ids[0]));
        }

        modelMap.put("pmdNorms",  pmdNormService.list(SystemConstants.PMD_NORM_TYPE_REDUCE, null));

        return "pmd/pmdMember/pmdMember_selectReduceNorm";
    }

    @RequiresPermissions("pmdMember:selectReduceNorm")
    @RequestMapping(value = "/pmdMember_selectReduceNorm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_selectReduceNorm(@RequestParam(value = "ids[]") int[] ids,
                                          int normId, BigDecimal amount, String remark) {


        pmdMemberService.selectReduceNorm(ids, normId, amount, remark);

        logger.info(addLog(SystemConstants.LOG_PMD, "[支部管理员]选择减免标准-%s-%s-%s",
                StringUtils.join(ids, ","), normId, amount));
        return success(FormUtils.SUCCESS);
    }
}
