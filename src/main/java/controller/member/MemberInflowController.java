package controller.member;

import controller.BaseController;
import controller.global.OpException;
import domain.base.MetaType;
import domain.member.MemberInflow;
import domain.member.MemberInflowExample;
import domain.member.MemberInflowExample.Criteria;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MixinUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberInflowController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow")
    public String memberInflow(@RequestParam(defaultValue = "1") byte cls, Integer userId,
                                    Integer partyId,
                                    Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        // 支部待审核总数
        modelMap.put("branchApprovalCount", memberInflowService.count(null, null, (byte) 1, cls));
        // 分党委待审核数目
        modelMap.put("partyApprovalCount", memberInflowService.count(null, null, (byte) 2, cls));

        return "member/memberInflow/memberInflow_page";
    }

    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_data")
    public void memberInflow_data(@RequestParam(defaultValue = "1") byte cls, HttpServletResponse response,
                                  @SortParam(required = false, defaultValue = "id", tableName = "ow_member_inflow") String sort,
                                  @OrderParam(required = false, defaultValue = "desc") String order,
                                  Integer userId,
                                  Byte status,
                                  Boolean isBack,
                                  Byte type,
                                  Integer partyId,
                                  Integer branchId,
                                  Integer originalJob,
                                  Integer province,
                                  String flowReason,
                                  Boolean hasPapers,
                                  String orLocation,
                                  @RequestDateRange DateRange _flowTime,
                                  @RequestDateRange DateRange  _growTime,
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

        MemberInflowExample example = new MemberInflowExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(status!=null){
            criteria.andInflowStatusEqualTo(status);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(isBack!=null){
            criteria.andIsBackEqualTo(isBack);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(originalJob!=null){
            criteria.andOriginalJobEqualTo(originalJob);
        }
        if(province!=null){
            criteria.andProvinceEqualTo(province);
        }
        if (StringUtils.isNotBlank(flowReason)) {
            criteria.andFlowReasonLike("%" + flowReason + "%");
        }
        if(hasPapers!=null){
            criteria.andHasPapersEqualTo(hasPapers);
        }
        if (StringUtils.isNotBlank(orLocation)) {
            criteria.andOrLocationLike("%" + orLocation + "%");
        }
        if (_flowTime.getStart()!=null) {
            criteria.andFlowTimeGreaterThanOrEqualTo(_flowTime.getStart());
        }

        if (_flowTime.getEnd()!=null) {
            criteria.andFlowTimeLessThanOrEqualTo(_flowTime.getEnd());
        }
        if (_growTime.getStart()!=null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd()!=null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
        }

        if(cls==1){ // 支部审核（新申请）
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
                    .andIsBackNotEqualTo(true);
        }else if(cls==4){ // 支部审核(返回修改)
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
                    .andIsBackEqualTo(true);
        }else if(cls==5 || cls==6){ // 支部已审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);
        }else if(cls==2) {// 未通过
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_INFLOW_STATUS_BACK);
            criteria.andInflowStatusIn(statusList);
        }else {// 已审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY);
            if(cls==31)// 已审核（已转出）
                criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));
            memberInflow_export(example, response);
            return;
        }

        int count = memberInflowMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberInflow> memberInflows = memberInflowMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", memberInflows);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberInflow:edit")
    @RequestMapping(value = "/memberInflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_au(@CurrentUser SysUserView loginUser,MemberInflow record,
                                  String _flowTime, String _growTime, String _outTime,
                                  HttpServletRequest request) {

        Integer id = record.getId();

        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());

        if (StringUtils.isNotBlank(_flowTime)) {
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_outTime)) {
            record.setOutTime(DateUtils.parseDate(_outTime, DateUtils.YYYY_MM_DD));
        }

        if (record.getPartyId() != null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if (record.getBranchId() != null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin && branchId!=null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if(!isAdmin) throw new UnauthorizedException();
        }

        if (id == null) {
            enterApplyService.memberInflow(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交流入党员申请");
            logger.info(addLog(SystemConstants.LOG_OW, "添加流入党员：%s", record.getId()));
        } else {

            memberInflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新流入党员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberInflow:list")
    @RequestMapping("/memberInflow_approval")
    public String memberInflow_approval(@RequestParam(defaultValue = "1")byte cls,@CurrentUser SysUserView loginUser, Integer id,
                                        byte type, // 1:支部审核 2：分党委审核
                                        ModelMap modelMap) {

        MemberInflow currentMemberInflow = null;
        if (id != null) {
            currentMemberInflow = memberInflowMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberInflow.getInflowStatus() != SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
                    currentMemberInflow = null;
            }
            if (type == 2) {
                if (currentMemberInflow.getInflowStatus() != SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY)
                    currentMemberInflow = null;
            }
        } else {
            currentMemberInflow = memberInflowService.next(null, type, cls);
        }
        if (currentMemberInflow == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberInflow", currentMemberInflow);

        Integer branchId = currentMemberInflow.getBranchId();
        Integer partyId = currentMemberInflow.getPartyId();
        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
        }

        // 读取总数
        modelMap.put("count", memberInflowService.count(null, null, type, cls));
        // 下一条记录
        modelMap.put("next", memberInflowService.next(currentMemberInflow, type, cls));
        // 上一条记录
        modelMap.put("last", memberInflowService.last(currentMemberInflow, type, cls));

        return "member/memberInflow/memberInflow_approval";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberInflow:update")
    @RequestMapping("/memberInflow_deny")
    public String memberInflow_deny(Integer id, ModelMap modelMap) {

        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        modelMap.put("memberInflow", memberInflow);
        Integer userId = memberInflow.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberInflow/memberInflow_deny";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                      byte type, // 1:支部审核 2：分党委审核
                                      @RequestParam(value = "ids[]") Integer[] ids) {


        memberInflowService.memberInflow_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "流入党员申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberInflow:update")
    @RequestMapping("/memberInflow_back")
    public String memberInflow_back() {

        return "member/memberInflow/memberInflow_back";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberInflow:update")
    @RequestMapping(value = "/memberInflow_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_back(@CurrentUser SysUserView loginUser,
                                     @RequestParam(value = "ids[]") Integer[] ids,
                                     byte status,
                                     String reason) {


        memberInflowService.memberInflow_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回流入党员申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("memberInflow:edit")
    @RequestMapping("/memberInflow_au")
    public String memberInflow_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
            modelMap.put("memberInflow", memberInflow);

            modelMap.put("sysUser", sysUserService.findById(memberInflow.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();

            if (memberInflow.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberInflow.getPartyId()));
            }
            if (memberInflow.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberInflow.getBranchId()));
            }
        }
        return "member/memberInflow/memberInflow_au";
    }

    /*@RequiresPermissions("memberInflow:del")
    @RequestMapping(value = "/memberInflow_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberInflowService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除流入党员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    // 删除审核通过的流入党员（至不通过状态）
    @RequiresPermissions("memberInflow:del")
    @RequestMapping(value = "/memberInflow_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length > 0) {
            memberInflowService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除流入党员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberInflow_export(MemberInflowExample example, HttpServletResponse response) {

        List<MemberInflow> records = memberInflowMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名", "所在分党委","所在党支部", "原职业", "流入前所在省份",
                "是否持有《中国共产党流动党员活动证》", "流入时间", "流入原因", "入党时间", "组织关系所在地"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberInflow record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();

            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record.getOriginalJob()==null?"":metaTypeMap.get(record.getOriginalJob()).getName(),
                    record.getProvince()==null?"":locationService.codeMap().get(record.getProvince()).getName(),
                    record.getHasPapers()==null?"":record.getHasPapers()?"是":"否",
                    DateUtils.formatDate(record.getFlowTime(), DateUtils.YYYY_MM_DD),
                    record.getReason(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    record.getOrLocation()
            };
            valuesList.add(values);
        }
        String fileName = "流入党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
