package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberApplyExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberApplyMixin;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.party.MemberApplyOpService;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MemberApplyOpService memberApplyOpService;

    private VerifyAuth<MemberApply> checkVerityAuth(int userId){
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth(memberApply, memberApply.getPartyId(), memberApply.getBranchId());
    }

    private VerifyAuth<MemberApply> checkVerityAuth2(int userId){
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth2(memberApply, memberApply.getPartyId());
    }
    
    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply")
    public String memberApply() {

        return "index";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_approval")
    public String memberApply_approval(@CurrentUser SysUser loginUser,Integer userId,
                                       byte type,
                                       byte stage,
                                       Byte status, // status=-1 代表对应的状态值为NULL
                                       ModelMap modelMap) {

        MemberApply currentMemberApply = null;
        if(userId!=null) {
            //SysUser sysUser = sysUserService.findById(userId);
            //modelMap.put("user", sysUser);
            currentMemberApply = memberApplyService.get(userId);
        }else{
            currentMemberApply = memberApplyService.next(type, stage, status, null);
        }
        modelMap.put("memberApply", currentMemberApply);

        // 是否是当前记录的管理员
        switch (stage){
            case SystemConstants.APPLY_STAGE_INIT:
            case SystemConstants.APPLY_STAGE_PASS:
                modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberApply.getBranchId()));
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
            case SystemConstants.APPLY_STAGE_CANDIDATE:
            case SystemConstants.APPLY_STAGE_PLAN:
                if(status==-1)
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberApply.getBranchId()));
                else
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberApply.getPartyId()));
                break;
            case SystemConstants.APPLY_STAGE_DRAW:
            case SystemConstants.APPLY_STAGE_GROW:
                if(status==-1)
                    modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberApply.getBranchId()));
                else if(status==0)
                    modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberApply.getPartyId()));
                else
                    modelMap.put("isAdmin", SecurityUtils.getSubject().hasRole("odAdmin"));
                break;
        }

        // 读取总数
        modelMap.put("count", memberApplyService.count(null, null, type, stage, status));
        // 下一条记录
        modelMap.put("next", memberApplyService.next(type, stage, status, currentMemberApply));
        // 上一条记录
        modelMap.put("last", memberApplyService.last(type, stage, status, currentMemberApply));

        return "party/memberApply/memberApply_approval";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_page")
    public String memberApply_page(@RequestParam(defaultValue = "1")int cls,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   @RequestParam(defaultValue = "1")Byte type,
                                   @RequestParam(defaultValue = "0")Byte stage,
                                   ModelMap modelMap) {

        modelMap.put("cls", cls);
        modelMap.put("type", type);
        modelMap.put("stage", stage);
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

        switch (stage){
            case SystemConstants.APPLY_STAGE_INIT:
            case SystemConstants.APPLY_STAGE_PASS:
                modelMap.put("applyCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_INIT, null));
                modelMap.put("activeCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_PASS, null));
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
                modelMap.put("candidateCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_ACTIVE, (byte)-1));
                modelMap.put("candidateCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_ACTIVE, (byte)0));
                break;
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                modelMap.put("planCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_CANDIDATE, (byte)-1));
                modelMap.put("planCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_CANDIDATE, (byte) 0));
                break;
            case SystemConstants.APPLY_STAGE_PLAN:
                modelMap.put("drawCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_PLAN, (byte)-1));
                modelMap.put("drawCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_PLAN, (byte) 0));
                break;
            case SystemConstants.APPLY_STAGE_DRAW:
                modelMap.put("growCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_DRAW, (byte)-1));
                modelMap.put("growCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_DRAW, (byte) 0));
                modelMap.put("growOdCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_DRAW, (byte) 1));
                break;
            case SystemConstants.APPLY_STAGE_GROW:
                modelMap.put("positiveCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_GROW, (byte)-1));
                modelMap.put("positiveCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_GROW, (byte) 0));
                modelMap.put("positiveOdCheckCount", memberApplyService.count(null, null, type, SystemConstants.APPLY_STAGE_GROW, (byte) 1));
                break;
        }

        return "party/memberApply/memberApply_page";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_data")
    public void memberApply_data(HttpServletResponse response,
                                   @SortParam(required = false, defaultValue = "create_time", tableName = "ow_member_apply") String sort,
                                   @OrderParam(required = false, defaultValue = "desc") String order,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   @RequestParam(defaultValue = "1")Byte type,
                                   @RequestParam(defaultValue = "0")Byte stage,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberApplyExample example = new MemberApplyExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));

        if(type !=null) {
            criteria.andTypeEqualTo(type);
        }
        if (stage != null) {
            if(stage==SystemConstants.APPLY_STAGE_INIT || stage==SystemConstants.APPLY_STAGE_PASS) {
                List<Byte> stageList = new ArrayList<>();
                stageList.add(SystemConstants.APPLY_STAGE_INIT);
                stageList.add(SystemConstants.APPLY_STAGE_PASS);
                criteria.andStageIn(stageList);
            }else
                criteria.andStageEqualTo(stage);
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

        if (export == 1) {
            memberApply_export(example, response);
            return;
        }

        int count = memberApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberApply> MemberApplys = memberApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", MemberApplys);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberApply.class, MemberApplyMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    // 后台添加入党申请
    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequestMapping("/memberApply_au")
    public String memberApply_au(Integer userId, ModelMap modelMap) {

        if(userId!=null) {
            SysUser sysUser = sysUserService.findById(userId);
            modelMap.put("sysUser", sysUser);
            MemberApply memberApply = memberApplyService.get(sysUser.getId());
            modelMap.put("memberApply", memberApply);

            if (memberApply != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Map<Integer, Party> partyMap = partyService.findAll();
                Integer partyId = memberApply.getPartyId();
                Integer branchId = memberApply.getBranchId();
                if (partyId != null) {
                    modelMap.put("party", partyMap.get(partyId));
                }
                if (branchId != null) {
                    modelMap.put("branch", branchMap.get(branchId));
                }
            }
        }

        return "party/memberApply/memberApply_au";
    }

    @RequiresRoles(value = {"admin", "odAdmin"}, logical = Logical.OR)
    @RequestMapping(value = "/memberApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_au(@CurrentUser SysUser loginUser, int userId, Integer partyId,
                              Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

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

        enterApplyService.checkMemberApplyAuth(userId);

        MemberApply memberApply = new MemberApply();
        memberApply.setUserId(userId);

        SysUser sysUser = sysUserService.findById(userId);

        if(sysUser.getType() == SystemConstants.USER_TYPE_JZG){
            memberApply.setType(SystemConstants.APPLY_TYPE_TECHER); // 教职工
        } else if(sysUser.getType() == SystemConstants.USER_TYPE_BKS
                || sysUser.getType() == SystemConstants.USER_TYPE_YJS){
            memberApply.setType(SystemConstants.APPLY_TYPE_STU); // 学生
        }else{
            throw new UnauthorizedException("没有权限");
        }

        Date birth = sysUser.getBirth();
        if(birth!=null && DateUtils.intervalYearsUntilNow(birth)<18){
            throw new RuntimeException("未满18周岁，不能申请入党。");
        }

        memberApply.setPartyId(partyId);
        memberApply.setBranchId(branchId);
        memberApply.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        memberApply.setRemark(remark);
        memberApply.setFillTime(new Date());
        memberApply.setCreateTime(new Date());
        memberApply.setStage(SystemConstants.APPLY_STAGE_INIT);
        enterApplyService.memberApply(memberApply);

        applyApprovalLogService.add(userId,
                memberApply.getPartyId(), memberApply.getBranchId(), userId,
                loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,  "提交入党申请",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_PASS, "");

        logger.info(addLog(SystemConstants.LOG_OW, "提交入党申请"));
        return success(FormUtils.SUCCESS);
    }

    // 申请不通过
    @RequiresPermissions("memberApply:deny")
    @RequestMapping(value = "/apply_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_deny(@RequestParam(value = "ids[]") int[] ids, String remark, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        for (Integer userId : ids) {
            checkVerityAuth(userId);
        }

        memberApplyOpService.apply_deny(ids, remark, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    // 申请通过
    @RequiresPermissions("memberApply:pass")
    @RequestMapping(value = "/apply_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_pass(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        for (Integer userId : ids) {
            checkVerityAuth(userId);
        }

        memberApplyOpService.apply_pass(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:active")
    @RequestMapping(value = "/apply_active")
    public String apply_active(){

        return "party/memberApply/apply_active";
    }

    // 申请通过 成为积极分子
    @RequiresPermissions("memberApply:active")
    @RequestMapping(value = "/apply_active", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_active(@RequestParam(value = "ids[]") int[] ids, String _activeTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
        for (Integer userId : ids) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;
            if (activeTime.before(memberApply.getApplyTime())) {
                throw new RuntimeException("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
        }

        memberApplyOpService.apply_active(ids, activeTime, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:candidate")
    @RequestMapping(value = "/apply_candidate")
    public String apply_candidate(){

        return "party/memberApply/apply_candidate";
    }

    // 提交 确定为发展对象
    @RequiresPermissions("memberApply:candidate")
    @RequestMapping(value = "/apply_candidate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_candidate(@RequestParam(value = "ids[]") int[] ids, String _candidateTime, String _trainTime,
                                  @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_candidate(ids, _candidateTime, _trainTime, loginUser.getId());

        return success();
    }
    // 审核 确定为发展对象
    @RequiresPermissions("memberApply:check")
    @RequestMapping(value = "/apply_candidate_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_candidate_check(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_candidate_check(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:plan")
    @RequestMapping(value = "/apply_plan")
    public String apply_plan(){

        return "party/memberApply/apply_plan";
    }

    //提交 列入发展计划
    @RequiresPermissions("memberApply:plan")
    @RequestMapping(value = "/apply_plan", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_plan(@RequestParam(value = "ids[]") int[] ids, String _planTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_plan(ids, _planTime, loginUser.getId());

        return success();
    }

    //审核 列入发展计划
    @RequiresPermissions("memberApply:plan_check")
    @RequestMapping(value = "/apply_plan_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_plan_check(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_plan_check(ids, loginUser.getId());

        return success();
    }
    @RequiresPermissions("memberApply:draw")
    @RequestMapping(value = "/apply_draw")
    public String apply_draw(){

        return "party/memberApply/apply_draw";
    }

    //提交 领取志愿书
    @RequiresPermissions("memberApply:draw")
    @RequestMapping(value = "/apply_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_draw(@RequestParam(value = "ids[]") int[] ids, String _drawTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_draw(ids, _drawTime, loginUser.getId());

        return success();
    }
    //审核 领取志愿书
    @RequiresPermissions("memberApply:draw_check")
    @RequestMapping(value = "/apply_draw_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_draw_check(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_draw_check(ids, loginUser.getId());

        return success();
    }

    @RequiresPermissions("memberApply:grow")
    @RequestMapping(value = "/apply_grow")
    public String apply_grow(){

        return "party/memberApply/apply_grow";
    }

    //提交 预备党员
    @RequiresPermissions("memberApply:grow")
    @RequestMapping(value = "/apply_grow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_grow(@RequestParam(value = "ids[]") int[] ids, String _growTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow(ids, _growTime, loginUser.getId());

        return success();
    }
    //审核 预备党员
    @RequiresPermissions("memberApply:grow_check")
    @RequestMapping(value = "/apply_grow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_check(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow_check(ids, loginUser.getId());

        return success();
    }

    //组织部管理员审核 预备党员
    @RequiresRoles("odAdmin")
    @RequiresPermissions("memberApply:grow_check2")
    @RequestMapping(value = "/apply_grow_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_check2(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_grow_check2(ids, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }
    @RequiresPermissions("memberApply:positive")
    @RequestMapping(value = "/apply_positive")
    public String apply_positive(){

        return "party/memberApply/apply_positive";
    }

    //提交 正式党员
    @RequiresPermissions("memberApply:positive")
    @RequestMapping(value = "/apply_positive", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_positive(@RequestParam(value = "ids[]") int[] ids, String _positiveTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive(ids, _positiveTime, loginUser.getId());

        return success();
    }

    //审核 正式党员
    @RequiresPermissions("memberApply:positive_check")
    @RequestMapping(value = "/apply_positive_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check(ids, loginUser.getId());

        return success();
    }

    //组织部管理员审核 正式党员
    @RequiresRoles("odAdmin")
    @RequiresPermissions("memberApply:positive_check2")
    @RequestMapping(value = "/apply_positive_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check2(@RequestParam(value = "ids[]") int[] ids, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        memberApplyOpService.apply_positive_check2(ids, loginUser.getId());

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:update")
    @RequestMapping("/memberApply_back")
    public String memberApply_back() {

        return "party/memberApply/memberApply_back";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:update")
    @RequestMapping(value = "/memberApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply_back(@CurrentUser SysUser loginUser,
                                   @RequestParam(value = "ids[]") int[] ids, byte stage,
                                   String reason) {

        for (int userId : ids) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;
            byte _stage = memberApply.getStage();
            if(_stage>=SystemConstants.APPLY_STAGE_GROW){
                return failed("已是党员，不可以打回入党申请状态。");
            }
            if(stage>_stage || stage<SystemConstants.APPLY_STAGE_INIT || stage==SystemConstants.APPLY_STAGE_PASS){
                return failed("参数有误。");
            }
        }

        memberApplyOpService.memberApply_back(ids, stage, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回入党申请：%s", ids));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApplyLog_page")
    public String memberApplyLog_page(@RequestParam(defaultValue = "1")int cls,
                                      Integer userId,
                                      String stage, Integer partyId,
                                      Integer branchId, ModelMap modelMap){

        modelMap.put("cls", cls);
        modelMap.put("stage", stage);
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

        return "party/memberApply/memberApplyLog_page";
    }

    public void memberApply_export(MemberApplyExample example, HttpServletResponse response) {

        List<MemberApply> memberApplys = memberApplyMapper.selectByExample(example);
        int rownum = memberApplyMapper.countByExample(example);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        XSSFRow firstRow = (XSSFRow) sheet.createRow(0);

        String[] titles = {"用户", "所属分党委", "所属党支部", "类型"};
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(MSUtils.getHeadStyle(wb));
        }

        for (int i = 0; i < rownum; i++) {

            MemberApply memberApply = memberApplys.get(i);
            String[] values = {
                    memberApply.getUserId() + "",
                    memberApply.getPartyId() + "",
                    memberApply.getBranchId() + "",
                    memberApply.getType() + ""
            };

            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < titles.length; j++) {

                XSSFCell cell = (XSSFCell) row.createCell(j);
                cell.setCellValue(values[j]);
                cell.setCellStyle(MSUtils.getBodyStyle(wb));
            }
        }
        try {
            String fileName = "申请入党人员_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            ServletOutputStream outputStream = response.getOutputStream();
            fileName = new String(fileName.getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
