package controller.member;

import controller.global.OpException;
import domain.member.MemberReturn;
import domain.member.MemberReturnExample;
import domain.member.MemberReturnExample.Criteria;
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
import sys.utils.DateUtils;
import sys.utils.ExportHelper;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberReturnController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn")
    public String memberReturn(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
                                Integer userId,
                                Integer partyId,
                                Integer branchId,ModelMap modelMap) {

        modelMap.put("cls", cls);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        // 党支部待审核总数
        modelMap.put("branchApprovalCount", memberReturnService.count(null, null, (byte)1));
        // 分党委待审核数目
        modelMap.put("partyApprovalCount", memberReturnService.count(null, null, (byte)2));

        return "member/memberReturn/memberReturn_page";
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_data")
    public void memberReturn_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_return") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Byte status,
                                 Boolean isBack,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                  @RequestDateRange DateRange _returnApplyTime,
                                  @RequestDateRange DateRange  _applyTime,
                                  @RequestDateRange DateRange  _activeTime,
                                  @RequestDateRange DateRange  _candidateTime,
                                  @RequestDateRange DateRange  _growTime,
                                  @RequestDateRange DateRange  _positiveTime,
                                    Byte politicalStatus,
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

        MemberReturnExample example = new MemberReturnExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        if(isBack!=null){
            criteria.andIsBackEqualTo(isBack);
        }
        if(politicalStatus!=null){
            criteria.andPoliticalStatusEqualTo(politicalStatus);
        }
        if (_returnApplyTime.getStart()!=null) {
            criteria.andReturnApplyTimeGreaterThanOrEqualTo(_returnApplyTime.getStart());
        }

        if (_returnApplyTime.getEnd()!=null) {
            criteria.andReturnApplyTimeLessThanOrEqualTo(_returnApplyTime.getEnd());
        }

        if (_applyTime.getStart()!=null) {
            criteria.andApplyTimeGreaterThanOrEqualTo(_applyTime.getStart());
        }

        if (_applyTime.getEnd()!=null) {
            criteria.andApplyTimeLessThanOrEqualTo(_applyTime.getEnd());
        }
        if (_activeTime.getStart()!=null) {
            criteria.andActiveTimeGreaterThanOrEqualTo(_activeTime.getStart());
        }

        if (_activeTime.getEnd()!=null) {
            criteria.andActiveTimeLessThanOrEqualTo(_activeTime.getEnd());
        }
        if (_candidateTime.getStart()!=null) {
            criteria.andCandidateTimeGreaterThanOrEqualTo(_candidateTime.getStart());
        }

        if (_candidateTime.getEnd()!=null) {
            criteria.andCandidateTimeLessThanOrEqualTo(_candidateTime.getEnd());
        }
        if (_growTime.getStart()!=null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd()!=null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }

        if (_positiveTime.getStart()!=null) {
            criteria.andPositiveTimeGreaterThanOrEqualTo(_positiveTime.getStart());
        }

        if (_positiveTime.getEnd()!=null) {
            criteria.andPositiveTimeLessThanOrEqualTo(_positiveTime.getEnd());
        }
        
        if(cls==1){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_RETURN_STATUS_APPLY);
            statusList.add(MemberConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
            criteria.andStatusIn(statusList);

        }else if(cls==2){
            criteria.andStatusEqualTo(MemberConstants.MEMBER_RETURN_STATUS_DENY);
        }else {
            criteria.andStatusEqualTo(MemberConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberReturn_export(example, response);
            return;
        }

        int count = memberReturnMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberReturn> memberReturns = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberReturns);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberReturn:edit")
    @RequestMapping(value = "/memberReturn_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_au(@CurrentUser SysUserView loginUser, MemberReturn record,String _returnApplyTime,
                                  String _applyTime, String _activeTime, String _candidateTime,
                                  String _growTime, String _positiveTime,HttpServletRequest request) {

        Integer id = record.getId();

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)) {
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin && branchId!=null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId,  branchId);
            }
            if(!isAdmin) throw new UnauthorizedException();
        }

        if (memberReturnService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_returnApplyTime)){
            record.setReturnApplyTime(DateUtils.parseDate(_returnApplyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_candidateTime)){
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_positiveTime)){
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {

            enterApplyService.memberReturn(record);
            logger.info(addLog(LogConstants.LOG_MEMBER, "添加留学归国人员申请恢复组织生活：%s", record.getId()));

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN,
                    "后台添加",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交留学归国人员申请恢复组织生活申请");

        } else {

            record.setStatus(null); // 更新的时候不能更新状态
            memberReturnService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_MEMBER, "更新留学归国人员申请恢复组织生活：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_approval")
    public String memberReturn_approval(@CurrentUser SysUserView loginUser, Integer id,
                                    byte type, // 1:支部审核 2：分党委审核
                                    ModelMap modelMap) {

        MemberReturn currentMemberReturn = null;
        if (id != null) {
            currentMemberReturn = memberReturnMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberReturn.getStatus() != MemberConstants.MEMBER_RETURN_STATUS_APPLY)
                    currentMemberReturn = null;
            }
            if (type == 2) {
                if (currentMemberReturn.getStatus() != MemberConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY)
                    currentMemberReturn = null;
            }
        } else {
            currentMemberReturn = memberReturnService.next(type, null);
        }
        if (currentMemberReturn == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberReturn", currentMemberReturn);

        Integer branchId = currentMemberReturn.getBranchId();
        Integer partyId = currentMemberReturn.getPartyId();
        // 是否是当前记录的管理员
        if(type==1){
            modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)
                    || branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
        }
        if(type==2){
            modelMap.put("isAdmin", ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL)
                    || partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
        }

        // 读取总数
        modelMap.put("count", memberReturnService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberReturnService.next(type, currentMemberReturn));
        // 上一条记录
        modelMap.put("last", memberReturnService.last(type, currentMemberReturn));

        return "member/memberReturn/memberReturn_approval";
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping("/memberReturn_deny")
    public String memberReturn_deny(Integer id, ModelMap modelMap) {

        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        modelMap.put("memberReturn", memberReturn);
        Integer userId = memberReturn.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberReturn/memberReturn_deny";
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                 byte type, // 1:分党委审核 3：组织部审核
                                 @RequestParam(value = "ids[]") Integer[] ids) {


        memberReturnService.memberReturn_check(ids, type, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "留学归国人员申请恢复组织生活申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping("/memberReturn_back")
    public String memberReturn_back() {

        return "member/memberReturn/memberReturn_back";
    }

    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_back(@CurrentUser SysUserView loginUser,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                byte status,
                                String reason) {


        memberReturnService.memberReturn_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "分党委打回留学归国人员申请恢复组织生活申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }
    
    @RequiresPermissions("memberReturn:edit")
    @RequestMapping("/memberReturn_au")
    public String memberReturn_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
            modelMap.put("memberReturn", memberReturn);

            modelMap.put("userBean", userBeanService.get(memberReturn.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberReturn.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberReturn.getPartyId()));
            }
            if (memberReturn.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberReturn.getBranchId()));
            }
        }
        return "member/memberReturn/memberReturn_au";
    }

    /*@RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberReturnService.del(id);
            logger.info(addLog(LogConstants.LOG_MEMBER, "删除留学归国人员申请恢复组织生活：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberReturnService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_MEMBER, "批量删除留学归国人员申请恢复组织生活：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
    public void memberReturn_export(MemberReturnExample example, HttpServletResponse response) {

        List<MemberReturn> records = memberReturnMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100","姓名|100", "所属分党委|250|left","所属党支部|250|left","确定为入党积极分子时间",
                "确定为发展对象时间|100","入党时间|100","转正时间|100","状态|100","备注|100"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberReturn record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus()==null?"":MemberConstants.MEMBER_RETURN_STATUS_MAP.get(record.getStatus()),
                    record.getRemark()
            };

            valuesList.add(values);
        }
        String fileName = String.format("留学归国党员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
