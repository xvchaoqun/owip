package controller.party;

import controller.BaseController;
import domain.*;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberOutflowMixin;
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
public class MemberOutflowController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow_view")
    public String memberOutflow_view(int userId, ModelMap modelMap) {

        SysUser sysUser = sysUserService.findById(userId);
        modelMap.put("sysUser", sysUser);

        MemberOutflow memberOutflow = memberOutflowService.get(sysUser.getId());
        modelMap.put("memberOutflow", memberOutflow);

        modelMap.put("locationMap", locationService.codeMap());
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);

        if(memberOutflow!=null) {
            Integer partyId = memberOutflow.getPartyId();
            Integer branchId = memberOutflow.getBranchId();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
        }

        return "party/memberOutflow/memberOutflow_view";
    }

    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow")
    public String memberOutflow() {

        return "index";
    }

    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow_page")
    public String memberOutflow_page(@RequestParam(defaultValue = "1")byte cls,Integer userId,
                                     Integer partyId,
                                     Integer branchId, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if (userId!=null) {
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
        modelMap.put("branchApprovalCount", memberOutflowService.count(null, null, (byte)1, cls));
        // 分党委待审核数目
        modelMap.put("partyApprovalCount", memberOutflowService.count(null, null, (byte)2, cls));

        return "party/memberOutflow/memberOutflow_page";
    }
    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow_data")
    public void memberOutflow_data(@RequestParam(defaultValue = "1")byte cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_outflow") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte status,
                                    Boolean isBack,
                                    Byte type,
                                    Integer partyId,
                                    Integer branchId,
                                    Integer originalJob,
                                    Integer direction,
                                    String _flowTime,
                                    Integer province,
                                    String reason,
                                    Boolean hasPapers,
                                    Byte orStatus,
                                    String _createTime,
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

        MemberOutflowViewExample example = new MemberOutflowViewExample();
        MemberOutflowViewExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId!=null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(status!=null){
            criteria.andStatusEqualTo(status);
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(isBack!=null){
            criteria.andIsBackEqualTo(isBack);
        }
        if (partyId!=null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId!=null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if(originalJob!=null){
            criteria.andOriginalJobEqualTo(originalJob);
        }
        if(direction!=null){
            criteria.andDirectionEqualTo(direction);
        }
        if (StringUtils.isNotBlank(_flowTime)) {
            String start = _flowTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _flowTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andFlowTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andFlowTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if(province!=null){
            criteria.andProvinceEqualTo(province);
        }
        if (StringUtils.isNotBlank(reason)) {
            criteria.andReasonLike("%" + reason + "%");
        }
        if(hasPapers!=null){
            criteria.andHasPapersEqualTo(hasPapers);
        }
        if(orStatus!=null){
            criteria.andOrStatusEqualTo(orStatus);
        }
        if (StringUtils.isNotBlank(_createTime)) {
            String start = _createTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _createTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andCreateTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andCreateTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        if(cls==1){ // 支部审核（新申请）
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
                    .andIsBackNotEqualTo(true);
        }else if(cls==4){ // 支部审核(返回修改)
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
                    .andIsBackEqualTo(true);;
        }else if(cls==5 ||cls==6){ // 支部已审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        }else if(cls==2) {// 未通过
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_OUTFLOW_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY);
           /* if(cls==3)// 已审核（未转出）
                criteria.andMemberStatusNotEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);*/
            if(cls==31)// 已审核（已转出）
                criteria.andMemberStatusEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andIdIn(Arrays.asList(ids));

            memberOutflow_export(example, response);
            return;
        }

        int count = memberOutflowViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOutflowView> memberOutflows = memberOutflowViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberOutflows);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberOutflowView.class, MemberOutflowMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOutflow:list")
    @RequestMapping("/memberOutflow_approval")
    public String memberOutflow_approval(@RequestParam(defaultValue = "1")byte cls,@CurrentUser SysUser loginUser, Integer id,
                                         byte type, // 1:支部审核 2：分党委审核
                                         ModelMap modelMap) {

        MemberOutflow currentMemberOutflow = null;
        if(id!=null) {
            currentMemberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
            if(type==1){
                if(currentMemberOutflow.getStatus()!=SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
                    currentMemberOutflow = null;
            }
            if(type==2){
                if(currentMemberOutflow.getStatus()!=SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY)
                    currentMemberOutflow = null;
            }
        }else{
            currentMemberOutflow = memberOutflowService.next(null, type, cls);
        }
        if(currentMemberOutflow==null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberOutflow", currentMemberOutflow);

        Integer branchId = currentMemberOutflow.getBranchId();
        Integer partyId = currentMemberOutflow.getPartyId();
        // 是否是当前记录的管理员
        if(type==1){
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
        }
        if(type==2){
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
        }

        // 读取总数
        modelMap.put("count", memberOutflowService.count(null, null, type, cls));
        // 下一条记录
        modelMap.put("next", memberOutflowService.next(currentMemberOutflow, type, cls));
        // 上一条记录
        modelMap.put("last", memberOutflowService.last(currentMemberOutflow, type, cls));

        return "party/memberOutflow/memberOutflow_approval";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOutflow:update")
    @RequestMapping("/memberOutflow_deny")
    public String memberOutflow_deny(Integer id, ModelMap modelMap) {

        MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
        modelMap.put("memberOutflow", memberOutflow);
        Integer userId = memberOutflow.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberOutflow/memberOutflow_deny";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOutflow:update")
    @RequestMapping(value = "/memberOutflow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                 byte type, // 1:支部审核 2：分党委审核
                                 @RequestParam(value = "ids[]") Integer[] ids) {


        memberOutflowService.memberOutflow_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "流出党员申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOutflow:update")
    @RequestMapping("/memberOutflow_back")
    public String memberOutflow_back() {

        return "party/memberOutflow/memberOutflow_back";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOutflow:update")
    @RequestMapping(value = "/memberOutflow_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_back(@CurrentUser SysUser loginUser,
                                @RequestParam(value = "ids[]") Integer[] ids,
                                byte status,
                                String reason) {


        memberOutflowService.memberOutflow_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回流出党员申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:edit")
    @RequestMapping(value = "/memberOutflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_au(@CurrentUser SysUser loginUser,MemberOutflow record, String _flowTime, HttpServletRequest request) {

        Integer id = record.getId();
        if (memberOutflowService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

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
            record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
            memberOutflowService.add(record);
            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交流出党员申请");
            logger.info(addLog(SystemConstants.LOG_OW, "添加流出党员：%s", record.getId()));
        } else {
            record.setStatus(null);// 不修改状态
            memberOutflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新流出党员：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:edit")
    @RequestMapping("/memberOutflow_au")
    public String memberOutflow_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
            modelMap.put("memberOutflow", memberOutflow);

            modelMap.put("sysUser", sysUserService.findById(memberOutflow.getUserId()));

            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("branchMap", branchMap);
            modelMap.put("partyMap", partyMap);
            if (memberOutflow.getPartyId() != null) {
                modelMap.put("party", partyMap.get(memberOutflow.getPartyId()));
            }
            if (memberOutflow.getBranchId() != null) {
                modelMap.put("branch", branchMap.get(memberOutflow.getBranchId()));
            }
        }
        return "party/memberOutflow/memberOutflow_au";
    }

   /* @RequiresPermissions("memberOutflow:del")
    @RequestMapping(value = "/memberOutflow_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberOutflowService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除流出党员：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOutflow:del")
    @RequestMapping(value = "/memberOutflow_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberOutflowService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除流出党员：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
    public void memberOutflow_export(MemberOutflowViewExample example, HttpServletResponse response) {

        List<MemberOutflowView> records = memberOutflowViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名","所在分党委","所在党支部","原职业","外出流向",
                "流出时间","流出省份","流出原因","是否持有《中国共产党流动党员活动证》","组织关系状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberOutflowView record = records.get(i);
            SysUser sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            Map<Integer, MetaType> metaTypeMap = metaTypeService.findAll();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record.getOriginalJob()==null?"":metaTypeMap.get(record.getOriginalJob()).getName(),
                    record.getDirection()==null?"":metaTypeMap.get(record.getDirection()).getName(),
                    DateUtils.formatDate(record.getFlowTime(), DateUtils.YYYY_MM_DD),
                    record.getProvince()==null?"":locationService.codeMap().get(record.getProvince()).getName(),
                    record.getReason(),
                    record.getHasPapers()==null?"":record.getHasPapers()?"是":"否",
                    record.getOrStatus()==null?"":SystemConstants.OR_STATUS_MAP.get(record.getOrStatus())
            };
            valuesList.add(values);
        }
        String fileName = "流出党员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
