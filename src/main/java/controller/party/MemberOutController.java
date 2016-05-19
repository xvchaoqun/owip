package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberOutExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberOutMixin;
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
public class MemberOutController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut/printPreview")
    public String memberOut_printPreview(int userId, ModelMap modelMap) {

        MemberOut memberOut = memberOutService.get(userId);
        modelMap.put("memberOut", memberOut);

        return "party/memberOut/print_preview";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_view")
    public String memberOut_view(int userId, ModelMap modelMap) {

        modelMap.put("userBean", userBeanService.get(userId));

        MemberOut memberOut = memberOutService.get(userId);
        modelMap.put("memberOut", memberOut);

        return "party/memberOut/memberOut_view";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut")
    public String memberOut() {

        return "index";
    }
    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_page")
    public String memberOut_page(@RequestParam(defaultValue = "1")byte cls, // 1 待审核 2未通过 3 已审核
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

        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", memberOutService.count(null, null, (byte)1, cls));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", memberOutService.count(null, null, (byte)2, cls));

        return "party/memberOut/memberOut_page";
    }

    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_data")
    public void memberOut_data(@RequestParam(defaultValue = "1")byte cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_out") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte status,
                                    Boolean isBack,
                                    Byte type,
                                    Integer partyId,
                                    Integer branchId,
                                    String toUnit,
                                    String toTitle,
                                    String fromUnit,
                                    String _handleTime,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberOutExample example = new MemberOutExample();
        Criteria criteria = example.createCriteria();

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
        if (StringUtils.isNotBlank(toUnit)) {
            criteria.andToUnitLike("%" + toUnit + "%");
        }
        if (StringUtils.isNotBlank(toTitle)) {
            criteria.andToTitleLike("%" + toTitle + "%");
        }
        if (StringUtils.isNotBlank(fromUnit)) {
            criteria.andFromUnitLike("%" + fromUnit + "%");
        }
        if (StringUtils.isNotBlank(_handleTime)) {
            String start = _handleTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _handleTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andHandleTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andHandleTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }

        if(cls==1){ // 分党委审核（新申请）
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_APPLY)
            .andIsBackNotEqualTo(true);
        }else if(cls==4){ // 分党委审核(返回修改)
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_APPLY)
                    .andIsBackEqualTo(true);;
        }else if(cls==5){ // 分党委已审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        }else if(cls==6){ // 组织部审核(新申请)
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY)
                    .andIsBackNotEqualTo(true);
        }else if(cls==7){ // 组织部审核(返回修改)
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY)
                    .andIsBackEqualTo(true);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_OUT_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_OUT_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY);
        }

        if (export == 1) {
            memberOut_export(example, response);
            return ;
        }

        int count = memberOutMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberOut> memberOuts = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberOuts);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberOut.class, MemberOutMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }


    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOut:list")
    @RequestMapping("/memberOut_approval")
    public String memberOut_approval(@RequestParam(defaultValue = "1")byte cls, @CurrentUser SysUser loginUser, Integer id,
                                    byte type, // 1:分党委审核 2：组织部审核
                                    ModelMap modelMap) {

        MemberOut currentMemberOut = null;
        if (id != null) {
            currentMemberOut = memberOutMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberOut.getStatus() != SystemConstants.MEMBER_OUT_STATUS_APPLY)
                    currentMemberOut = null;
            }
            if (type == 2) {
                if (currentMemberOut.getStatus() != SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY)
                    currentMemberOut = null;
            }
        } else {
            currentMemberOut = memberOutService.next(null, type, cls);
        }
        if (currentMemberOut == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberOut", currentMemberOut);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberOut.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", SecurityUtils.getSubject().hasRole("odAdmin"));
        }

        // 读取总数
        modelMap.put("count", memberOutService.count(null, null, type, cls));
        // 下一条记录
        modelMap.put("next", memberOutService.next(currentMemberOut, type, cls));
        // 上一条记录
        modelMap.put("last", memberOutService.last(currentMemberOut, type, cls));

        return "party/memberOut/memberOut_approval";
    }
    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOut:update")
    @RequestMapping("/memberOut_deny")
    public String memberOut_deny(Integer id, ModelMap modelMap) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        modelMap.put("memberOut", memberOut);
        Integer userId = memberOut.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberOut/memberOut_deny";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOut:update")
    @RequestMapping(value = "/memberOut_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                 byte type, // 1:分党委审核 3：组织部审核
                                 @RequestParam(value = "ids[]") int[] ids) {


        memberOutService.memberOut_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "组织关系转出申请-审核：%s", ids));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOut:update")
    @RequestMapping("/memberOut_back")
    public String memberOut_back() {

        return "party/memberOut/memberOut_back";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberOut:update")
    @RequestMapping(value = "/memberOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_back(@CurrentUser SysUser loginUser,
                                @RequestParam(value = "ids[]") int[] ids,
                                byte status,
                                String reason) {


        memberOutService.memberOut_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回组织关系转出申请：%s", ids));
        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("memberOut:edit")
    @RequestMapping(value = "/memberOut_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_au(@CurrentUser SysUser loginUser,MemberOut record, String _payTime, String _handleTime, HttpServletRequest request) {

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
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, branchId);
            }
            if(!isAdmin) throw new UnauthorizedException();
        }

        Integer id = record.getId();

        if (memberOutService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }
        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(SystemConstants.MEMBER_OUT_STATUS_APPLY);
            memberOutService.insertSelective(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(), loginUser.getId(),
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交组织关系转出申请");

            logger.info(addLog(SystemConstants.LOG_OW, "添加组织关系转出：%s", record.getId()));
        } else {

            memberOutService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新组织关系转出：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:edit")
    @RequestMapping("/memberOut_au")
    public String memberOut_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
            modelMap.put("memberOut", memberOut);

            modelMap.put("userBean", userBeanService.get(memberOut.getUserId()));
        }
        return "party/memberOut/memberOut_au";
    }

    @RequiresPermissions("memberOut:del")
    @RequestMapping(value = "/memberOut_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberOutService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除组织关系转出：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberOut:del")
    @RequestMapping(value = "/memberOut_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberOutService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除组织关系转出：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberOut_export(MemberOutExample example, HttpServletResponse response) {

        List<MemberOut> records = memberOutMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名","类别", "所在分党委","所在党支部", "转入单位抬头",
                "转入单位","转出单位","介绍信有效期天数","办理时间","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberOut record = records.get(i);
            SysUser sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    record.getType()==null?"":SystemConstants.MEMBER_INOUT_TYPE_MAP.get(record.getType()),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record.getToTitle(),
                    record.getToUnit(),
                    record.getFromUnit(),
                    record.getValidDays()+"",
                    DateUtils.formatDate(record.getHandleTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus()==null?"":SystemConstants.MEMBER_OUT_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "组织关系转出_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
