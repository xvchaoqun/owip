package controller.party;

import controller.BaseController;
import domain.member.MemberReturn;
import domain.member.MemberReturnExample;
import domain.member.MemberReturnExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.MetaType;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberReturnMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.helper.ExportHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberReturnController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn")
    public String memberReturn() {

        return "index";
    }

    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_page")
    public String memberReturn_page(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
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

        return "party/memberReturn/memberReturn_page";
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
                                    String _returnApplyTime,
                                    String _applyTime,
                                    String _activeTime,
                                    String _candidateTime,
                                    String _growTime,
                                    String _positiveTime,
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

        if(StringUtils.isNotBlank(_returnApplyTime)) {
            String timeStart = _returnApplyTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String timeEnd = _returnApplyTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(timeStart)) {
                criteria.andReturnApplyTimeGreaterThanOrEqualTo(DateUtils.parseDate(timeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(timeEnd)) {
                criteria.andReturnApplyTimeLessThanOrEqualTo(DateUtils.parseDate(timeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_applyTime)) {
            String timeStart = _applyTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String timeEnd = _applyTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(timeStart)) {
                criteria.andApplyTimeGreaterThanOrEqualTo(DateUtils.parseDate(timeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(timeEnd)) {
                criteria.andApplyTimeLessThanOrEqualTo(DateUtils.parseDate(timeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_activeTime)) {
            String timeStart = _activeTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String timeEnd = _activeTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(timeStart)) {
                criteria.andActiveTimeGreaterThanOrEqualTo(DateUtils.parseDate(timeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(timeEnd)) {
                criteria.andActiveTimeLessThanOrEqualTo(DateUtils.parseDate(timeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_candidateTime)) {
            String timeStart = _candidateTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String timeEnd = _candidateTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(timeStart)) {
                criteria.andCandidateTimeGreaterThanOrEqualTo(DateUtils.parseDate(timeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(timeEnd)) {
                criteria.andCandidateTimeLessThanOrEqualTo(DateUtils.parseDate(timeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_growTime)) {
            String timeStart = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String timeEnd = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(timeStart)) {
                criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.parseDate(timeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(timeEnd)) {
                criteria.andGrowTimeLessThanOrEqualTo(DateUtils.parseDate(timeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_positiveTime)) {
            String timeStart = _positiveTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String timeEnd = _positiveTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(timeStart)) {
                criteria.andPositiveTimeGreaterThanOrEqualTo(DateUtils.parseDate(timeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(timeEnd)) {
                criteria.andPositiveTimeLessThanOrEqualTo(DateUtils.parseDate(timeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        
        if(cls==1){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
            statusList.add(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
            criteria.andStatusIn(statusList);

        }else if(cls==2){
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_DENY);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY);
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

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberReturn.class, MemberReturnMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
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
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {
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
            logger.info(addLog(SystemConstants.LOG_OW, "添加留学归国人员申请恢复组织生活：%s", record.getId()));

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交留学归国人员申请恢复组织生活申请");

        } else {

            record.setStatus(null); // 更新的时候不能更新状态
            memberReturnService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新留学归国人员申请恢复组织生活：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberReturn:list")
    @RequestMapping("/memberReturn_approval")
    public String memberReturn_approval(@CurrentUser SysUserView loginUser, Integer id,
                                    byte type, // 1:支部审核 2：分党委审核
                                    ModelMap modelMap) {

        MemberReturn currentMemberReturn = null;
        if (id != null) {
            currentMemberReturn = memberReturnMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberReturn.getStatus() != SystemConstants.MEMBER_RETURN_STATUS_APPLY)
                    currentMemberReturn = null;
            }
            if (type == 2) {
                if (currentMemberReturn.getStatus() != SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY)
                    currentMemberReturn = null;
            }
        } else {
            currentMemberReturn = memberReturnService.next(type, null);
        }
        if (currentMemberReturn == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberReturn", currentMemberReturn);

        Integer branchId = currentMemberReturn.getBranchId();
        Integer partyId = currentMemberReturn.getPartyId();
        // 是否是当前记录的管理员
        if(type==1){
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
        }
        if(type==2){
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
        }

        // 读取总数
        modelMap.put("count", memberReturnService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberReturnService.next(type, currentMemberReturn));
        // 上一条记录
        modelMap.put("last", memberReturnService.last(type, currentMemberReturn));

        return "party/memberReturn/memberReturn_approval";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberReturn:update")
    @RequestMapping("/memberReturn_deny")
    public String memberReturn_deny(Integer id, ModelMap modelMap) {

        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        modelMap.put("memberReturn", memberReturn);
        Integer userId = memberReturn.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberReturn/memberReturn_deny";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                 byte type, // 1:分党委审核 3：组织部审核
                                 @RequestParam(value = "ids[]") Integer[] ids) {


        memberReturnService.memberReturn_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "留学归国人员申请恢复组织生活申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberReturn:update")
    @RequestMapping("/memberReturn_back")
    public String memberReturn_back() {

        return "party/memberReturn/memberReturn_back";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberReturn:update")
    @RequestMapping(value = "/memberReturn_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_back(@CurrentUser SysUserView loginUser,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                byte status,
                                String reason) {


        memberReturnService.memberReturn_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回留学归国人员申请恢复组织生活申请：%s", StringUtils.join( ids, ",")));
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
        return "party/memberReturn/memberReturn_au";
    }

    /*@RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberReturnService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除留学归国人员申请恢复组织生活：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReturn:del")
    @RequestMapping(value = "/memberReturn_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberReturnService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除留学归国人员申请恢复组织生活：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
    public void memberReturn_export(MemberReturnExample example, HttpServletResponse response) {

        List<MemberReturn> records = memberReturnMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名", "所属分党委","所属党支部","确定为入党积极分子时间",
                "确定为发展对象时间","入党时间","转正时间","状态","备注","创建时间"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberReturn record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    DateUtils.formatDate(record.getActiveTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getCandidateTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getPositiveTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus()==null?"":SystemConstants.MEMBER_RETURN_STATUS_MAP.get(record.getStatus()),
                    record.getRemark(),
                    DateUtils.formatDate(record.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS)
            };

            valuesList.add(values);
        }
        String fileName = "留学归国党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
