package controller.pmd;

import controller.global.OpException;
import domain.member.Member;
import domain.pmd.*;
import domain.pmd.PmdMemberExample.Criteria;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
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
import sys.constants.LogConstants;
import sys.constants.PmdConstants;
import sys.constants.RoleConstants;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
            Integer chargeUserId,
            Integer monthId,
            Integer partyId,
            Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (chargeUserId != null) {
            modelMap.put("chargeUser", sysUserService.findById(chargeUserId));
        }
        if (cls != 6) {
            ShiroHelper.checkPermission("pmdMember:list");
        }
        if (cls == 5) {
            ShiroHelper.checkPermission("pmdMember:allList");
            if (partyId != null)
                modelMap.put("party", partyService.findAll().get(partyId));
            if (branchId != null)
                modelMap.put("branch", branchService.findAll().get(branchId));

            PmdMonthExample example = new PmdMonthExample();
            example.setOrderByClause("pay_month desc");
            List<PmdMonth> pmdMonths = pmdMonthMapper.selectByExample(example);
            modelMap.put("pmdMonths", pmdMonths);

            return "pmd/pmdMember/pmdMemberList_page";
        } else if (cls == 3) {
            // 分党委管理员（不包含直属党支部）访问，弹出框
            modelMap.put("branch", branchService.findAll().get(branchId));
            return "pmd/pmdMember/pmdMember_party_page";
        } else if (cls == 6) {
            // 党费代缴列表
            ShiroHelper.checkPermission("pmdMember:helpPay");
            return "pmd/pmdMember/pmdMemberHelpPayList_page";
        }

        // 判断当前管理员是否允许操作
        if(monthId==null) {
            PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
            monthId = currentPmdMonth.getId();
        }
        boolean canAdmin = false;
        boolean hasReport;
        if (branchId != null) {
            PmdBranch pmdBranch = pmdBranchService.get(monthId, partyId, branchId);
            hasReport = pmdBranch.getHasReport();
        } else {
            PmdParty pmdParty = pmdPartyService.get(monthId, partyId);
            hasReport = pmdParty.getHasReport();
        }
        canAdmin = (hasReport == false);
        modelMap.put("canAdmin", canAdmin);

        if (cls == 4)
            return "pmd/pmdMember/pmdMember_delay_page";

        // 支部管理员或直属党支部管理员访问
        return "pmd/pmdMember/pmdMember_page";
    }

    //@RequiresPermissions("pmdMember:list")
    @RequestMapping("/pmdMember_data")
    public void pmdMember_data(HttpServletResponse response,
                               @RequestParam(required = false, defaultValue = "1") Byte cls,
                               String orderNo,
                               Integer monthId,
                               Integer partyId,
                               Integer branchId,
                               Integer userId,
                               Integer chargeUserId,
                               Byte type,
                               Boolean isOnlinePay,
                               Boolean hasPay,
                               Boolean isDelay,
                               Boolean isSelfPay,
                               @RequestDateRange DateRange _payTime,
                               Integer pageSize, Integer pageNo) throws IOException {

        if (cls != 6) {
            ShiroHelper.checkPermission("pmdMember:list");
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

            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());

            criteria.addPermits(adminPartyIds, adminBranchIds);
        } else if (cls == 2) {
            // 直属党支部访问党员列表
            // 此时必须传入partyId
            if (ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
                List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                Set<Integer> adminPartyIdSet = new HashSet<>();
                adminPartyIdSet.addAll(adminPartyIds);
                if (adminPartyIdSet.contains(partyId)) {
                    criteria.andPartyIdEqualTo(partyId);
                } else {
                    criteria.andPartyIdIsNull();
                }
            } else {
                criteria.andPartyIdEqualTo(partyId);
            }
        } else if (cls == 3) {
            // 分党委（不包含直属党支部）访问党员列表
            // 此时必须传入partyId,branchId
            if (ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
                List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                Set<Integer> adminPartyIdSet = new HashSet<>();
                adminPartyIdSet.addAll(adminPartyIds);
                if (adminPartyIdSet.contains(partyId)) {
                    criteria.andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
                } else {
                    criteria.andPartyIdIsNull();
                }
            } else {
                criteria.andPartyIdEqualTo(partyId).andBranchIdEqualTo(branchId);
            }
        } else if (cls == 5) {
            ShiroHelper.checkPermission("pmdMember:allList");

            // 根据订单号查找缴费记录
            if (StringUtils.isNotBlank(orderNo)) {
                PmdOrder pmdOrder = pmdOrderMapper.selectByPrimaryKey(orderNo);
                if (pmdOrder != null) {
                    if (!pmdOrder.getIsBatch()) {
                        criteria.andIdEqualTo(pmdOrder.getMemberId());
                    } else {
                        List<Integer> memberIds = iPmdMapper.listOrderMemberIds(orderNo);
                        if (memberIds.size() > 0)
                            criteria.andIdIn(memberIds);
                        else
                            criteria.andIdIsNull();
                    }
                } else {
                    criteria.andIdIsNull();
                }
            }

            if (_payTime.getStart() != null) {
                criteria.andPayTimeGreaterThanOrEqualTo(_payTime.getStart());
            }

            if (_payTime.getEnd() != null) {
                criteria.andPayTimeLessThanOrEqualTo(_payTime.getEnd());
            }

        } else if (cls == 6) {
            // 党费代缴列表
            ShiroHelper.checkPermission("pmdMember:helpPay");
            criteria.andChargeUserIdEqualTo(ShiroHelper.getCurrentUserId());
        } else {
            criteria.andIdIsNull();
        }

        if (monthId != null) {
            criteria.andMonthIdEqualTo(monthId);
        }
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (chargeUserId != null) {
            criteria.andChargeUserIdEqualTo(chargeUserId);
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
        if (isOnlinePay != null) {
            criteria.andIsOnlinePayEqualTo(isOnlinePay);
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

        /*if (branchId != null) {
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(ShiroHelper.getCurrentUserId());
            Set<Integer> adminBranchIdSet = new HashSet<>();
            adminBranchIdSet.addAll(adminBranchIds);
            if (!adminBranchIdSet.contains(branchId)) return;
        } else {
            // 直属党支部
            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);
            if (!partyService.isDirectBranch(partyId) || !adminPartyIdSet.contains(partyId)) return;
        }*/
        if(!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(), partyId, branchId)){
            return;
        }

        long count = 0;
        List<PmdMemberPayView> pmdMemberPayViews = null;
        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth != null && monthId == currentPmdMonth.getId()) {
            // 访问当月补缴列表
            count = iPmdMapper.countHistoryDelayMemberList(monthId, partyId, branchId, userId, hasPay);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            pmdMemberPayViews = iPmdMapper.selectHistoryDelayMemberList(monthId, partyId, branchId, userId, hasPay,
                    new RowBounds((pageNo - 1) * pageSize, pageSize));
        } else {
            // 访问往月已补缴列表
            count = iPmdMapper.countHasPayHistoryDelayMemberList(monthId, partyId, branchId, userId);
            if ((pageNo - 1) * pageSize >= count) {
                pageNo = Math.max(1, pageNo - 1);
            }
            pmdMemberPayViews = iPmdMapper.selectHasPayHistoryDelayMemberList(monthId, partyId, branchId, userId,
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

        //如果不是组织部管理员，则要求是本支部管理员才允许删除操作
        if (ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(),
                    pmdMember.getPartyId(), pmdMember.getBranchId())) {
                throw new UnauthorizedException();
            }
        }

        pmdMemberService.del(id);
        logger.info(addLog(LogConstants.LOG_PMD, "删除未缴费记录：%s", JSONUtils.toString(pmdMember, false)));

        return success(FormUtils.SUCCESS);
    }

    // 批量删除未缴费记录
    @RequiresPermissions("pmdMember:allList")
    @RequestMapping(value = "/pmdMember_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_batchDel(Integer[] ids, HttpServletRequest request) {


        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);

        pmdMemberService.batchDel(ids);

        List<String> members = new ArrayList<>();
        for (PmdMember pmdMember : pmdMembers) {
            SysUserView uv = pmdMember.getUser();
            members.add(String.format("%s,%s,%s,%s,%s,%s", DateUtils.formatDate(pmdMember.getPayMonth(), DateUtils.YYYY_MM)
                    , uv.getCode(), uv.getRealname(), pmdMember.getPartyId(),
                    pmdMember.getBranchId(), pmdMember.getDuePay()));
        }

        logger.info(addLog(LogConstants.LOG_PMD, "批量删除未缴费记录(%s条)：%s", pmdMembers.size(),
                JSONUtils.toString(members, false)));

        return success(FormUtils.SUCCESS);
    }

    // 添加党员缴费记录
    @RequiresPermissions("pmdMember:add")
    @RequestMapping(value = "/pmdMember_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_add(int userId, HttpServletRequest request) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        if (currentPmdMonth == null) {
            return failed("未开启缴费");
        }

        int monthId = currentPmdMonth.getId();
        PmdMember pmdMember = pmdMemberService.get(monthId, userId);
        if (pmdMember != null) {
            return failed(pmdMember.getUser().getRealname() + "已经在缴费列表中，请勿重复添加。");
        }

        Member member = memberService.get(userId);
        if(pmdMemberPayService.branchHasReport(member.getPartyId(), member.getBranchId(), currentPmdMonth)){

            throw  new OpException("操作失败，支部已报送。");
        }

        if (ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(),
                    member.getPartyId(), member.getBranchId())) {
                throw new UnauthorizedException();
            }
        }

        pmdMonthService.addOrResetPmdMember(userId, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdMember:add")
    @RequestMapping("/pmdMember_add")
    public String pmdMember_add(ModelMap modelMap) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        modelMap.put("pmdMonth", currentPmdMonth);

        return "pmd/pmdMember/pmdMember_add";
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

    // 清除订单号生成人
   /* @RequiresPermissions("pmdMember:add")
    @RequestMapping(value = "/pmdMember_clearOrderUser", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_clearOrderUser(int id, HttpServletRequest request) {

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(id);

        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(),
                    pmdMember.getPartyId(), pmdMember.getBranchId())) {
                throw new UnauthorizedException();
            }
        }

        pmdMemberService.clearOrderUser(id);

        return success(FormUtils.SUCCESS);
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

    // 修改延迟缴费的应交金额
    @RequiresPermissions("pmdMember:changeDuePay")
    @RequestMapping("/pmdMember_changeDuePay")
    public String pmdMember_changeDuePay(Integer[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("pmdMember", pmdMemberMapper.selectByPrimaryKey(ids[0]));
        }

        return "pmd/pmdMember/pmdMember_changeDuePay";
    }

    @RequiresPermissions("pmdMember:changeDuePay")
    @RequestMapping(value = "/pmdMember_changeDuePay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_changeDuePay(Integer[] ids,
                                      BigDecimal amount, String remark) {

        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0){
            return failed("金额必须大于0");
        }

        pmdMemberService.changeDuePay(ids, amount, remark);

        logger.info(addLog(LogConstants.LOG_PMD, "[支部管理员]修改延迟缴费的应交金额-%s-%s",
                StringUtils.join(ids, ","), amount));
        return success(FormUtils.SUCCESS);
    }

    // 选择党员分类
    @RequiresPermissions("pmdMember:selectMemberType")
    @RequestMapping("/pmdMember_selectMemberType")
    public String pmdMember_selectMemberType(Integer[] ids,
                                             byte configMemberType,
                                             ModelMap modelMap) {

        if (ids.length == 1) {
            PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(ids[0]);
            modelMap.put("pmdMember", pmdMember);
            Integer configMemberTypeId = pmdMember.getConfigMemberTypeId();
            if (configMemberTypeId != null) {
                PmdConfigMemberType pmdConfigMemberType = pmdConfigMemberTypeService.get(configMemberTypeId);
                modelMap.put("pmdConfigMemberType", pmdConfigMemberType);
            }
            PmdConfigMember pmdConfigMember = pmdConfigMemberService.getPmdConfigMember(pmdMember.getUserId());
            modelMap.put("pmdConfigMember", pmdConfigMember);
        }

        modelMap.put("configMemberType", configMemberType);
        modelMap.put("configMemberTypes", pmdConfigMemberTypeService.list(configMemberType));

        return "pmd/pmdMember/pmdMember_selectMemberType";
    }

    @RequiresPermissions("pmdMember:selectMemberType")
    @RequestMapping(value = "/pmdMember_selectMemberType", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_selectMemberType(Integer[] ids,
                                             Boolean hasSalary, // 学生党员必须设置
                                             byte configMemberType,
                                             int configMemberTypeId,
                                             BigDecimal amount, String remark) {


        pmdMemberService.selectMemberType(ids, hasSalary, configMemberType, configMemberTypeId, amount, remark);

        logger.info(addLog(LogConstants.LOG_PMD, "修改党员分类别-%s-%s",
                StringUtils.join(ids, ","), configMemberTypeId));
        return success(FormUtils.SUCCESS);
    }

    // 修改缴费方式
    @RequiresPermissions("pmdMember:setIsOnlinePay")
    @RequestMapping("/pmdMember_setIsOnlinePay")
    public String pmdMember_setIsOnlinePay(Integer[] ids,
                                           ModelMap modelMap) {

        if (ids.length == 1) {
            PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(ids[0]);
            modelMap.put("pmdMember", pmdMember);
        }

        return "pmd/pmdMember/pmdMember_setIsOnlinePay";
    }

    @RequiresPermissions("pmdMember:setIsOnlinePay")
    @RequestMapping(value = "/pmdMember_setIsOnlinePay", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_setIsOnlinePay(Integer[] ids,
                                           Boolean isOnlinePay, String remark) {


        pmdMemberService.setIsOnlinePay(ids, BooleanUtils.isTrue(isOnlinePay), remark);

        logger.info(addLog(LogConstants.LOG_PMD, "修改缴费方式-%s-%s-%s",
                StringUtils.join(ids, ","), isOnlinePay, remark));
        return success(FormUtils.SUCCESS);
    }

    // 设定减免标准
    @RequiresPermissions("pmdMember:selectReduceNorm")
    @RequestMapping("/pmdMember_selectReduceNorm")
    public String pmdMember_selectReduceNorm(Integer[] ids, ModelMap modelMap) {

        if (ids.length == 1) {
            modelMap.put("pmdMember", pmdMemberMapper.selectByPrimaryKey(ids[0]));
        }

        modelMap.put("pmdNorms", pmdNormService.list(PmdConstants.PMD_NORM_TYPE_REDUCE, null));

        return "pmd/pmdMember/pmdMember_selectReduceNorm";
    }

    @RequiresPermissions("pmdMember:selectReduceNorm")
    @RequestMapping(value = "/pmdMember_selectReduceNorm", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdMember_selectReduceNorm(Integer[] ids,
                                             int normId, BigDecimal amount, String remark) {


        pmdMemberService.selectReduceNorm(ids, normId, amount, remark);

        logger.info(addLog(LogConstants.LOG_PMD, "[支部管理员]选择减免标准-%s-%s-%s",
                StringUtils.join(ids, ","), normId, amount));
        return success(FormUtils.SUCCESS);
    }
}
