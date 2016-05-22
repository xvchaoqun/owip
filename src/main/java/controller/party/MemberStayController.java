package controller.party;

import controller.BaseController;
import domain.*;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberStayMixin;
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
public class MemberStayController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_view")
    public String memberStay_view(int userId, ModelMap modelMap) {

        MemberStay memberStay = memberStayService.get(userId);
        modelMap.put("memberStay", memberStay);

        modelMap.put("userBean", userBeanService.get(userId));

        return "party/memberStay/memberStay_view";
    }

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay")
    public String memberStay() {

        return "index";
    }

    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_page")
    public String memberStay_page(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核(未转出) 4已审核（已转出）
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
        modelMap.put("partyApprovalCount", memberStayService.count(null, null, (byte)1));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", memberStayService.count(null, null, (byte)2));

        return "party/memberStay/memberStay_page";
    }
    
    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_data")
    public void memberStay_data(@RequestParam(defaultValue = "1")Integer cls,
                                 HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "id", tableName = "ow_member_stay") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                 Byte status,
                                 Boolean isBack,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    String country,
                                    String _abroadTime,
                                    String _returnTime,
                                    String _payTime,
                                    String mobile,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberStayViewExample example = new MemberStayViewExample();
        MemberStayViewExample.Criteria criteria = example.createCriteria();

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
        if (StringUtils.isNotBlank(country)) {
            criteria.andCountryLike("%" + country + "%");
        }
        if (StringUtils.isNotBlank(_abroadTime)) {
            String start = _abroadTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _abroadTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andAbroadTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andAbroadTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if (StringUtils.isNotBlank(_returnTime)) {
            String start = _returnTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _returnTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andReturnTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andReturnTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if (StringUtils.isNotBlank(_payTime)) {
            String start = _payTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _payTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andPayTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andPayTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if (StringUtils.isNotBlank(mobile)) {
            criteria.andMobileLike("%" + mobile + "%");
        }
        if(cls==1){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_STAY_STATUS_APPLY);
            statusList.add(SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
            criteria.andStatusIn(statusList);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_STAY_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_STAY_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY);
            if(cls==3)
                criteria.andMemberStatusNotEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
            if(cls==4)
                criteria.andMemberStatusEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
        }
        
        if (export == 1) {
            memberStay_export(example, response);
            return;
        }

        int count = memberStayViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberStayView> memberStays = memberStayViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberStays);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberStayView.class, MemberStayMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberStay:list")
    @RequestMapping("/memberStay_approval")
    public String memberStay_approval(@CurrentUser SysUser loginUser, Integer id,
                                    byte type, // 1:支部审核 2：分党委审核
                                    ModelMap modelMap) {

        MemberStay currentMemberStay = null;
        if (id != null) {
            currentMemberStay = memberStayMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberStay.getStatus() != SystemConstants.MEMBER_STAY_STATUS_APPLY)
                    currentMemberStay = null;
            }
            if (type == 2) {
                if (currentMemberStay.getStatus() != SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
                    currentMemberStay = null;
            }
        } else {
            currentMemberStay = memberStayService.next(type, null);
        }
        if (currentMemberStay == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberStay", currentMemberStay);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberStay.getPartyId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", SecurityUtils.getSubject().hasRole("odAdmin"));
        }

        // 读取总数
        modelMap.put("count", memberStayService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberStayService.next(type, currentMemberStay));
        // 上一条记录
        modelMap.put("last", memberStayService.last(type, currentMemberStay));

        return "party/memberStay/memberStay_approval";
    }
    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberStay:update")
    @RequestMapping("/memberStay_deny")
    public String memberStay_deny(Integer id, ModelMap modelMap) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        modelMap.put("memberStay", memberStay);
        Integer userId = memberStay.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));
        
        return "party/memberStay/memberStay_deny";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberStay:update")
    @RequestMapping(value = "/memberStay_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                 byte type, // 1:分党委审核 3：组织部审核
                                 @RequestParam(value = "ids[]") int[] ids) {


        memberStayService.memberStay_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "暂留申请-审核：%s", ids));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberStay:update")
    @RequestMapping("/memberStay_back")
    public String memberStay_back() {

        return "party/memberStay/memberStay_back";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberStay:update")
    @RequestMapping(value = "/memberStay_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_back(@CurrentUser SysUser loginUser,
                                @RequestParam(value = "ids[]") int[] ids,
                                byte status,
                                String reason) {


        memberStayService.memberStay_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "暂留申请：%s", ids));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:edit")
    @RequestMapping(value = "/memberStay_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_au(@CurrentUser SysUser loginUser,MemberStay record,
                                String _abroadTime, String _returnTime, String _payTime,  HttpServletRequest request) {

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

        if (memberStayService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if(StringUtils.isNotBlank(_abroadTime)) {
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_returnTime)) {
            record.setReturnTime(DateUtils.parseDate(_returnTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }

        if (id == null) {
            record.setApplyTime(new Date());
            record.setStatus(SystemConstants.MEMBER_STAY_STATUS_APPLY);
            memberStayService.insertSelective(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交暂留申请");

            logger.info(addLog(SystemConstants.LOG_OW, "添加暂留：%s", record.getId()));
        } else {

            memberStayService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新暂留：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:edit")
    @RequestMapping("/memberStay_au")
    public String memberStay_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
            modelMap.put("memberStay", memberStay);

            modelMap.put("userBean", userBeanService.get(memberStay.getUserId()));
        }
        return "party/memberStay/memberStay_au";
    }

    @RequiresPermissions("memberStay:del")
    @RequestMapping(value = "/memberStay_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberStayService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除暂留：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberStay:del")
    @RequestMapping(value = "/memberStay_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberStayService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除暂留：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    public void memberStay_export(MemberStayViewExample example, HttpServletResponse response) {

        List<MemberStayView> records = memberStayViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名", "所在分党委","所在党支部","出国时间","预计回国时间","手机号码","状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberStayView record = records.get(i);
            SysUser sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    DateUtils.formatDate(record.getAbroadTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getReturnTime(), DateUtils.YYYY_MM_DD),
                    record.getMobile(),
                    record.getStatus()==null?"":SystemConstants.MEMBER_STAY_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "组织关系暂留_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

}
