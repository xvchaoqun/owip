package controller.party;

import controller.BaseController;
import domain.member.Member;
import domain.party.*;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.GraduateAbroadMixin;
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
import service.helper.ShiroHelper;
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
public class GraduateAbroadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("graduateAbroad:list")
    @RequestMapping("/graduateAbroad_view")
    public String graduateAbroad_view(int userId, ModelMap modelMap) {

        GraduateAbroad graduateAbroad = graduateAbroadService.get(userId);
        modelMap.put("graduateAbroad", graduateAbroad);

        /*if(graduateAbroad!=null) {
            Integer partyId = graduateAbroad.getPartyId();
            Integer branchId = graduateAbroad.getBranchId();
            Integer toBranchId = graduateAbroad.getToBranchId();
            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
            if (toBranchId != null) {
                modelMap.put("toBranch", branchMap.get(toBranchId));
            }
        }*/
        modelMap.put("student", studentService.get(userId));
        modelMap.put("userBean", userBeanService.get(userId));

        return "party/graduateAbroad/graduateAbroad_view";
    }

    @RequiresPermissions("graduateAbroad:list")
    @RequestMapping("/graduateAbroad")
    public String graduateAbroad() {

        return "index";
    }

    /*
    cls==1||cls==11||cls==12
        支部审核
    cls==2||cls==21||cls==22
        分党委审核
    cls==3||cls==31
        组织部审核
    cls==4
        未通过
    cls==5||cls==6
        已完成审批 未转出  已转出

   */
    @RequiresPermissions("graduateAbroad:list")
    @RequestMapping("/graduateAbroad_page")
    public String graduateAbroad_page(@RequestParam(defaultValue = "1") Byte cls,

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

        if (cls == 1 || cls == 11) {
            // 支部待审核总数（新申请 cls=1）
            modelMap.put("approvalCountNew", graduateAbroadService.count(null, null, (byte) 1, (byte) 1));
            // 支部待审核总数（返回修改 cls=4）
            modelMap.put("approvalCountBack", graduateAbroadService.count(null, null, (byte) 1, (byte) 11));
            // 支部待审核总数
            modelMap.put("approvalCount", graduateAbroadService.count(null, null, (byte) 1, cls));
        }
        if (cls == 2 || cls == 21) {
            // 分党委待审核总数（新申请 cls=1）
            modelMap.put("approvalCountNew", graduateAbroadService.count(null, null, (byte) 2, (byte) 2));
            // 分党委待审核总数（返回修改 cls=4）
            modelMap.put("approvalCountBack", graduateAbroadService.count(null, null, (byte) 2, (byte) 21));
            // 分党委待审核总数
            modelMap.put("approvalCount", graduateAbroadService.count(null, null, (byte) 2, cls));
        }
        if (cls == 3 || cls == 31) {
            // 组织部待审核总数（新申请 cls=1）
            modelMap.put("approvalCountNew", graduateAbroadService.count(null, null, (byte) 3, (byte) 3));
            // 组织部待审核总数（返回修改 cls=4）
            modelMap.put("approvalCountBack", graduateAbroadService.count(null, null, (byte) 3, (byte) 31));

            modelMap.put("approvalCount", graduateAbroadService.count(null, null, (byte) 3, cls));
        }
       /*
        // 支部待审核总数
        modelMap.put("branchApprovalCount", graduateAbroadService.count(null, null, (byte)1));
        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", graduateAbroadService.count(null, null, (byte)2));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", graduateAbroadService.count(null, null, (byte)3));*/

        return "party/graduateAbroad/graduateAbroad_page";
    }

    @RequiresPermissions("graduateAbroad:list")
    @RequestMapping("/graduateAbroad_data")
    public void graduateAbroad_data(@RequestParam(defaultValue = "1") Byte cls,
                                    HttpServletResponse response,
                                    @SortParam(required = false, defaultValue = "id", tableName = "ow_member_stay") String sort,
                                    @OrderParam(required = false, defaultValue = "desc") String order,
                                    Byte status,
                                    Boolean isBack,
                                    Integer userId,
                                    Integer partyId,
                                    Integer branchId,
                                    String country,
                                    String _abroadTime, // 留学时间
                                    String _saveTime, // 申请保留组织关系起止时间（年/月）
                                    String _payTime,
                                    String mobile,
                                    @RequestParam(required = false, defaultValue = "0") int export,
                                    @RequestParam(required = false, value = "ids[]") Integer[] ids, // 导出的记录
                                    Integer pageSize, Integer pageNo, ModelMap modelMap) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        GraduateAbroadViewExample example = new GraduateAbroadViewExample();
        GraduateAbroadViewExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (isBack != null) {
            criteria.andIsBackEqualTo(isBack);
        }
        if (StringUtils.isNotBlank(country)) {
            criteria.andCountryLike("%" + country + "%");
        }
        if (StringUtils.isNotBlank(_abroadTime)) {
            String start = _abroadTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _abroadTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andEndTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andStartTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
            }
        }
        if (StringUtils.isNotBlank(_saveTime)) {
            String start = _saveTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String end = _saveTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(start)) {
                criteria.andSaveEndTimeGreaterThanOrEqualTo(DateUtils.parseDate(start, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(end)) {
                criteria.andSaveStartTimeLessThanOrEqualTo(DateUtils.parseDate(end, DateUtils.YYYY_MM_DD));
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
        if (cls == 1) {// 支部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
                    .andIsBackNotEqualTo(true);
        } else if (cls == 11) {// 支部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
                    .andIsBackEqualTo(true);
        } else if (cls == 12) {// 支部审核（已审核）
            criteria.andStatusGreaterThanOrEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY);
        } else if (cls == 2) { // 分党委审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY)
                    .andIsBackNotEqualTo(true);
        } else if (cls == 21) { // 分党委审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY)
                    .andIsBackEqualTo(true);
        } else if (cls == 22) {// 分党委审核（已审核）
            criteria.andStatusGreaterThanOrEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY);
        } else if (cls == 3) {// 组织部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY)
                    .andIsBackNotEqualTo(true);
        } else if (cls == 31) {// 组织部审核
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY)
                    .andIsBackEqualTo(true);
        } else if (cls == 4) {
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.GRADUATE_ABROAD_STATUS_SELF_BACK);
            statusList.add(SystemConstants.GRADUATE_ABROAD_STATUS_BACK);
            criteria.andStatusIn(statusList);
        } else {
            criteria.andStatusEqualTo(SystemConstants.GRADUATE_ABROAD_STATUS_OW_VERIFY);
            if (cls == 5)
                criteria.andMemberStatusNotEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
            if (cls == 6)
                criteria.andMemberStatusEqualTo(SystemConstants.MEMBER_STATUS_TRANSFER);
        }

        if (export == 1) {
            if (ids != null && ids.length > 0)
                criteria.andIdIn(Arrays.asList(ids));
            graduateAbroad_export(example, response);
            return;
        }

        int count = graduateAbroadViewMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<GraduateAbroadView> graduateAbroads = graduateAbroadViewMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", graduateAbroads);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(GraduateAbroadView.class, GraduateAbroadMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("graduateAbroad:list")
    @RequestMapping("/graduateAbroad_approval")
    public String graduateAbroad_approval(@RequestParam(defaultValue = "1") byte cls, @CurrentUser SysUserView loginUser, Integer id,
                                          byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                          ModelMap modelMap) {

        GraduateAbroad currentGraduateAbroad = null;
        if (id != null) {
            currentGraduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentGraduateAbroad.getStatus() != SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
                    currentGraduateAbroad = null;
            }
            if (type == 2) {
                if (currentGraduateAbroad.getStatus() != SystemConstants.GRADUATE_ABROAD_STATUS_BRANCH_VERIFY)
                    currentGraduateAbroad = null;
            }
            if (type == 3) {
                if (currentGraduateAbroad.getStatus() != SystemConstants.GRADUATE_ABROAD_STATUS_PARTY_VERIFY)
                    currentGraduateAbroad = null;
            }
        } else {
            currentGraduateAbroad = graduateAbroadService.next(type, null, cls);
        }
        if (currentGraduateAbroad == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("graduateAbroad", currentGraduateAbroad);

        Integer userId = currentGraduateAbroad.getUserId();
        modelMap.put("userBean", userBeanService.get(userId));
        modelMap.put("student", studentService.get(userId));

        Integer partyId = currentGraduateAbroad.getPartyId();
        Integer branchId = currentGraduateAbroad.getBranchId();
        Integer toBranchId = currentGraduateAbroad.getToBranchId();
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }
        if (toBranchId != null) {
            modelMap.put("toBranch", branchMap.get(toBranchId));
        }

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), partyId, branchId));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), partyId));
        }
        if (type == 3) {
            modelMap.put("isAdmin", ShiroHelper.hasRole(SystemConstants.ROLE_ODADMIN));
        }


        // 读取总数
        modelMap.put("count", graduateAbroadService.count(null, null, type, cls));
        // 下一条记录
        modelMap.put("next", graduateAbroadService.next(type, currentGraduateAbroad, cls));
        // 上一条记录
        modelMap.put("last", graduateAbroadService.last(type, currentGraduateAbroad, cls));

        return "party/graduateAbroad/graduateAbroad_approval";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("graduateAbroad:update")
    @RequestMapping("/graduateAbroad_deny")
    public String graduateAbroad_deny(Integer id, ModelMap modelMap) {

        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        modelMap.put("graduateAbroad", graduateAbroad);
        Integer userId = graduateAbroad.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/graduateAbroad/graduateAbroad_deny";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("graduateAbroad:update")
    @RequestMapping(value = "/graduateAbroad_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_graduateAbroad_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                       byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                       @RequestParam(value = "ids[]") Integer[] ids) {


        graduateAbroadService.graduateAbroad_check(ids, type, null, null, null, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "暂留申请-审核：%s", StringUtils.join(ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("graduateAbroad:update")
    @RequestMapping("/graduateAbroad_back")
    public String graduateAbroad_back() {

        return "party/graduateAbroad/graduateAbroad_back";
    }

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("graduateAbroad:update")
    @RequestMapping(value = "/graduateAbroad_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_graduateAbroad_back(@CurrentUser SysUserView loginUser,
                                      @RequestParam(value = "ids[]") Integer[] ids,
                                      byte status,
                                      String reason) {


        graduateAbroadService.graduateAbroad_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "暂留申请：%s", StringUtils.join(ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_PARTYADMIN)
    @RequiresPermissions("graduateAbroad:update")
    @RequestMapping("/graduateAbroad_transfer")
    public String graduateAbroad_transfer(@RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        int id = ids[0]; /// 分党委审核时必须在同一个分党委内部审核
        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        modelMap.put("graduateAbroad", graduateAbroad);

        Integer partyId = graduateAbroad.getPartyId();
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }

        return "party/graduateAbroad/graduateAbroad_transfer";
    }

    @RequiresRoles(SystemConstants.ROLE_PARTYADMIN)
    @RequiresPermissions("graduateAbroad:update")
    @RequestMapping(value = "/graduateAbroad_transfer", method = RequestMethod.POST)
    @ResponseBody
    public Map do_graduateAbroad_transfer(@CurrentUser SysUserView loginUser,
                                          @RequestParam(value = "ids[]") Integer[] ids,
                                          Integer branchId, Integer orgBranchAdminId, String orgBranchAdminPhone) {


        graduateAbroadService.graduateAbroad_check(ids, (byte) 2, branchId, orgBranchAdminId, orgBranchAdminPhone, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委审核暂留申请：%s，转移至支部%s", StringUtils.join(ids, ","), branchId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("graduateAbroad:edit")
    @RequestMapping(value = "/graduateAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_graduateAbroad_au(@CurrentUser SysUserView loginUser, GraduateAbroad record,
                                    String _startTime, String _endTime,
                                    String _saveStartTime, String _saveEndTime,
                                    String _payTime, HttpServletRequest request) {

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
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) { // 支部或分党委管理员都有权限
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin && branchId != null) {
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        Integer id = record.getId();

        if (graduateAbroadService.idDuplicate(id, record.getUserId())) {
            return failed("添加重复");
        }

        if (StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_saveStartTime)) {
            record.setSaveStartTime(DateUtils.parseDate(_saveStartTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_saveEndTime)) {
            record.setSaveEndTime(DateUtils.parseDate(_saveEndTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }

        if (id == null) {
            record.setCreateTime(new Date());
            record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY);
            graduateAbroadService.insertSelective(record);

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), record.getUserId(),
                    loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD,
                    "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "提交暂留申请");

            logger.info(addLog(SystemConstants.LOG_OW, "添加暂留：%s", record.getId()));
        } else {

            graduateAbroadService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新暂留：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("graduateAbroad:edit")
    @RequestMapping("/graduateAbroad_au")
    public String graduateAbroad_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
            modelMap.put("graduateAbroad", graduateAbroad);

            modelMap.put("userBean", userBeanService.get(graduateAbroad.getUserId()));
        }
        return "party/graduateAbroad/graduateAbroad_au";
    }


    @RequiresPermissions("graduateAbroad:edit")
    @RequestMapping(value = "/graduateAbroad_transfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map graduateAbroad_transfer_au(@CurrentUser SysUserView loginUser,
                                          int id, int branchId, int orgBranchAdminId, String orgBranchAdminPhone, HttpServletRequest request) {

        GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
        Map<Integer, Branch> branchMap = branchService.findAll();
        Branch branch = branchMap.get(branchId);
        if (branch == null || branch.getPartyId().intValue() != graduateAbroad.getPartyId()) {
            throw new RuntimeException("转移支部不存在");
        }

        GraduateAbroad record = new GraduateAbroad();
        record.setId(id);
        record.setToBranchId(branchId);
        record.setOrgBranchAdminId(orgBranchAdminId);
        record.setOrgBranchAdminPhone(orgBranchAdminPhone);
        graduateAbroadService.trasferAu(record);
        logger.info(addLog(SystemConstants.LOG_OW, "更新暂留党支部等信息：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    // 修改暂留支部
    @RequiresPermissions("graduateAbroad:edit")
    @RequestMapping("/graduateAbroad_transfer_au")
    public String graduateAbroad_transfer_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            GraduateAbroad graduateAbroad = graduateAbroadMapper.selectByPrimaryKey(id);
            modelMap.put("graduateAbroad", graduateAbroad);

            if (partyService.isDirectBranch(graduateAbroad.getPartyId())) {
                throw new RuntimeException("直属党支部不需要添加暂留党支部");
            }

            Map<Integer, Party> partyMap = partyService.findAll();
            modelMap.put("party", partyMap.get(graduateAbroad.getPartyId()));

            // 暂留党支部
            if (graduateAbroad.getToBranchId() != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Branch branch = branchMap.get(graduateAbroad.getToBranchId());
                modelMap.put("branch", branch);
            }

            // 原支部负责人
            if (graduateAbroad.getOrgBranchAdminId() != null) {
                SysUserView sysUser = sysUserService.findById(graduateAbroad.getOrgBranchAdminId());
                modelMap.put("sysUser", sysUser);
            }

        }
        return "party/graduateAbroad/graduateAbroad_transfer_au";
    }

  /*  @RequiresPermissions("graduateAbroad:del")
    @RequestMapping(value = "/graduateAbroad_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_graduateAbroad_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            graduateAbroadService.del(id);
            logger.info(addLog(SystemConstants.LOG_OW, "删除暂留：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN}, logical = Logical.OR)
    @RequiresPermissions("graduateAbroad:del")
    @RequestMapping(value = "/graduateAbroad_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids && ids.length > 0) {

            GraduateAbroadExample example = new GraduateAbroadExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            List<GraduateAbroad> graduateAbroads = graduateAbroadMapper.selectByExample(example);

            graduateAbroadService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_OW, "批量删除暂留：%s", JSONUtils.toString(graduateAbroads)));
        }
        return success(FormUtils.SUCCESS);
    }

    public void graduateAbroad_export(GraduateAbroadViewExample example, HttpServletResponse response) {

        List<GraduateAbroadView> records = graduateAbroadViewMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号", "姓名", "所在分党委", "所在党支部",
                "留学国家", "留学学校（院系）",
                "留学起止时间（年/月）", "留学起止时间（年/月）", "手机号码", "状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            GraduateAbroadView record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();
            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId == null ? "" : partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    record.getCountry(), record.getSchool(),
                    DateUtils.formatDate(record.getStartTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getEndTime(), DateUtils.YYYY_MM_DD),
                    record.getMobile(),
                    record.getStatus() == null ? "" : SystemConstants.GRADUATE_ABROAD_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "党员出国（境）申请组织关系暂留_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }

}
