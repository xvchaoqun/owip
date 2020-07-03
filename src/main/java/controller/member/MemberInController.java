package controller.member;

import controller.global.OpException;
import domain.member.MemberIn;
import domain.member.MemberInExample;
import domain.member.MemberInExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
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
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberInController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn")
    public String memberIn(@RequestParam(defaultValue = "1") Integer cls, // 1 分党委待审核 2未通过 3 已审核 4组织部待审核
                           Integer userId,
                           Integer partyId,
                           Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        // 分党委待审核总数
        modelMap.put("partyApprovalCount", memberInService.count(null, null, (byte) 1));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", memberInService.count(null, null, (byte) 2));

        return "member/memberIn/memberIn_page";
    }

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn_data")
    public void memberIn_data(@RequestParam(defaultValue = "1") Integer cls,// 1 分党委待审核 2未通过 3 已审核 4组织部待审核
                              HttpServletResponse response,
                              @SortParam(required = false, defaultValue = "id", tableName = "ow_member_in") String sort,
                              @OrderParam(required = false, defaultValue = "desc") String order,
                              Integer userId,
                              Byte status,
                              Boolean isBack,
                              Boolean isModify,
                              Integer type,
                              Integer partyId,
                              Integer branchId,
                              String fromUnit,
                              String fromTitle,
                              @RequestDateRange DateRange fromHandleTime,
                              @RequestDateRange DateRange handleTime,
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

        MemberInExample example = new MemberInExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (isBack != null) {
            criteria.andIsBackEqualTo(isBack);
        }
        if (isModify != null) {
            criteria.andIsModifyEqualTo(isModify);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (StringUtils.isNotBlank(fromUnit)) {
            criteria.andFromUnitLike(SqlUtils.like(fromUnit));
        }
        if (StringUtils.isNotBlank(fromTitle)) {
            criteria.andFromTitleLike(SqlUtils.like(fromTitle));
        }
        if (fromHandleTime.getStart() != null) {
            criteria.andFromHandleTimeGreaterThanOrEqualTo(fromHandleTime.getStart());
        }

        if (fromHandleTime.getEnd() != null) {
            criteria.andFromHandleTimeLessThanOrEqualTo(fromHandleTime.getEnd());
        }
        if (handleTime.getStart() != null) {
            criteria.andHandleTimeGreaterThanOrEqualTo(handleTime.getStart());
        }

        if (handleTime.getEnd() != null) {
            criteria.andHandleTimeLessThanOrEqualTo(handleTime.getEnd());
        }

        if (cls == 1) {
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_APPLY);
        } else if (cls == 2) {
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_IN_STATUS_SELF_BACK);
            statusList.add(MemberConstants.MEMBER_IN_STATUS_BACK);
            criteria.andStatusIn(statusList);
        } else if (cls == 4) { // 直属党支部审核，需要经过组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY);
        } else if (cls == 3) {
            criteria.andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_OW_VERIFY);
        } else {
            criteria.andUserIdIsNull();
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            memberIn_export(example, response);
            return;
        }

        long count = memberInMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberIn> memberIns = memberInMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberIns);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberIn:edit")
    @RequestMapping(value = "/memberIn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_au(@CurrentUser SysUserView loginUser, MemberIn record, String _payTime, String _applyTime, String _activeTime, String _candidateTime,
                              String _growTime, String _positiveTime,
                              String _fromHandleTime, String _handleTime,
                              Byte resubmit,
                              HttpServletRequest request) {


        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!branchMemberService.hasAdminAuth(loginUserId, partyId, branchId))
            throw new UnauthorizedException();

        Integer id = record.getId();

        record.setHasReceipt((record.getHasReceipt() == null) ? false : record.getHasReceipt());

        if (StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if (StringUtils.isNotBlank(_applyTime)) {
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_candidateTime)) {
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_positiveTime)) {
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_fromHandleTime)) {
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_handleTime)) {
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        if (memberIn == null) {
            enterApplyService.memberIn(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, "后台操作",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);

            logger.info(addLog(LogConstants.LOG_MEMBER, "添加组织关系转入：%s", record.getId()));
        } else {

            if (resubmit != null && resubmit == 1 && memberIn.getStatus() < MemberConstants.MEMBER_IN_STATUS_APPLY) { // 重新提交
                enterApplyService.memberIn(record);

                applyApprovalLogService.add(record.getId(),
                        record.getPartyId(), record.getBranchId(), record.getUserId(),
                        loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN, "后台重新提交",
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);
            } else {

                record.setStatus(null); // 更新的时候不能更新状态
                if (memberIn.getStatus() == MemberConstants.MEMBER_IN_STATUS_OW_VERIFY) {
                    memberInService.updateAfterOwVerify(record);
                } else {
                    memberInService.updateByPrimaryKeySelective(record);
                }

                logger.info(addLog(LogConstants.LOG_MEMBER, "更新组织关系转入：%s", record.getId()));
            }
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:list")
    @RequestMapping("/memberIn_approval")
    public String memberIn_approval(@CurrentUser SysUserView loginUser, Integer id,
                                    byte type, // 1:分党委审核 2：组织部审核
                                    ModelMap modelMap) {

        MemberIn currentMemberIn = null;
        if (id != null) {
            currentMemberIn = memberInMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberIn.getStatus() != MemberConstants.MEMBER_IN_STATUS_APPLY)
                    currentMemberIn = null;
            }
            if (type == 2) {
                if (currentMemberIn.getStatus() != MemberConstants.MEMBER_IN_STATUS_PARTY_VERIFY)
                    currentMemberIn = null;
            }
        } else {
            currentMemberIn = memberInService.next(type, null);
        }
        if (currentMemberIn == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberIn", currentMemberIn);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), currentMemberIn.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL));
        }

        // 读取总数
        modelMap.put("count", memberInService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberInService.next(type, currentMemberIn));
        // 上一条记录
        modelMap.put("last", memberInService.last(type, currentMemberIn));

        return "member/memberIn/memberIn_approval";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping("/memberIn_deny")
    public String memberIn_deny(Integer id, ModelMap modelMap) {

        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        modelMap.put("memberIn", memberIn);
        Integer userId = memberIn.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberIn/memberIn_deny";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                 //byte type, // 1:分党委审核 2：组织部审核
                                 @RequestParam(value = "ids[]") Integer[] ids) {

        memberInService.memberIn_check(ids, null, (byte) 2, loginUser.getId());
        logger.info(addLog(LogConstants.LOG_MEMBER, "组织关系转入申请-组织部审核通过：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping("/memberIn_party_check")
    public String memberIn_party_check(@RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        int id = ids[0];
        MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
        modelMap.put("memberIn", memberIn);

        return "member/memberIn/memberIn_party_check";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_party_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_party_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                       @RequestParam(value = "ids[]") Integer[] ids, Boolean hasReceipt) {

        memberInService.memberIn_check(ids, hasReceipt, (byte) 1, loginUser.getId());
        logger.info(addLog(LogConstants.LOG_MEMBER, "组织关系转入申请-分党委审核通过：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping("/memberIn_back")
    public String memberIn_back() {

        return "member/memberIn/memberIn_back";
    }

    @RequiresPermissions("memberIn:update")
    @RequestMapping(value = "/memberIn_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_back(@CurrentUser SysUserView loginUser,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                byte status,
                                String reason) {


        memberInService.memberIn_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "分党委打回组织关系转入申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:edit")
    @RequestMapping("/memberIn_au")
    public String memberIn_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberIn memberIn = memberInMapper.selectByPrimaryKey(id);
            modelMap.put("memberIn", memberIn);

            modelMap.put("userBean", userBeanService.get(memberIn.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberIn.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberIn.getPartyId()));
            }
            if (memberIn.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberIn.getBranchId()));
            }
        }
        return "member/memberIn/memberIn_au";
    }

    /*@RequiresPermissions("memberIn:del")
    @RequestMapping(value = "/memberIn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberInService.del(id);
            logger.info(addLog(LogConstants.LOG_MEMBER, "删除组织关系转入：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberIn:del")
    @RequestMapping(value = "/memberIn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberInService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除组织关系转入：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
    public void memberIn_export(MemberInExample example, HttpServletResponse response) {

        List<MemberIn> records = memberInMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100", "姓名|50", "类别|50", "所在分党委|300|left", "所在党支部|300|left",
                "转出单位|280|left", "转出单位抬头|280|left", "介绍信有效期天数|120", "转出办理时间|100", "转入办理时间|100", "状态|120"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberIn record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    record.getType() == null ? "" : metaTypeService.getName(record.getType()),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    record.getFromUnit(),
                    record.getFromTitle(),
                    record.getValidDays() + "",
                    DateUtils.formatDate(record.getFromHandleTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getHandleTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus() == null ? "" : MemberConstants.MEMBER_IN_STATUS_MAP.get(record.getStatus())
            };

            valuesList.add(values);
        }
        String fileName = String.format("组织关系转入(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
