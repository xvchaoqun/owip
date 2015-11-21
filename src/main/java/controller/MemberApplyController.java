package controller;

import domain.MemberApply;
import domain.MemberApplyExample;
import domain.MemberApplyExample.Criteria;
import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply")
    public String memberApply() {

        return "index";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberApply:list")
    @RequestMapping("/memberApply_page")
    public String memberApply_page(HttpServletResponse response,
                                   @RequestParam(required = false, defaultValue = "create_time") String sort,
                                   @RequestParam(required = false, defaultValue = "desc") String order,
                                   Integer userId,
                                   Integer partyId,
                                   Integer branchId,
                                   @RequestParam(defaultValue = "1")Byte type,
                                   @RequestParam(defaultValue = "0")Byte stage,
                                   @RequestParam(required = false, defaultValue = "0") int export,
                                   Integer pageSize, Integer pageNo, ModelMap modelMap) {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberApplyExample example = new MemberApplyExample();
        Criteria criteria = example.createCriteria();

        example.setOrderByClause(String.format("%s %s", sort, order));

        if(type !=null) {
            modelMap.put("type", type);
            criteria.andTypeEqualTo(type);
        }
        if (stage != null) {
            modelMap.put("stage", stage);
            if(stage<=SystemConstants.APPLY_STAGE_PASS)
                criteria.andStageLessThanOrEqualTo(SystemConstants.APPLY_STAGE_PASS);
            else
                criteria.andStageEqualTo(stage);
        }
        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
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
            return null;
        }

        int count = memberApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberApply> MemberApplys = memberApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        modelMap.put("memberApplys", MemberApplys);

        CommonList commonList = new CommonList(count, pageNo, pageSize);

        String searchStr = "&pageSize=" + pageSize;
        searchStr += "&type=" + type;
        if (userId != null) {
            searchStr += "&userId=" + userId;
        }
        if (partyId != null) {
            searchStr += "&partyId=" + partyId;
        }
        if (branchId != null) {
            searchStr += "&branchId=" + branchId;
        }
        if (StringUtils.isNotBlank(sort)) {
            searchStr += "&sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            searchStr += "&order=" + order;
        }
        if (stage != null) {
            searchStr += "&stage=" + stage;
        }
        commonList.setSearchStr(searchStr);
        modelMap.put("commonList", commonList);

        modelMap.put("branchMap", branchService.findAll());
        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("APPLY_STAGE_MAP", SystemConstants.APPLY_STAGE_MAP);

        return "memberApply/memberApply_page";
    }

    // 申请不通过
    @RequestMapping(value = "/apply_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_deny(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_DENY);
        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_INIT);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_INIT, "未通过入党申请", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    // 申请通过
    @RequestMapping(value = "/apply_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_pass(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_PASS);
        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_INIT);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_INIT, "通过入党申请", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    @RequestMapping(value = "/apply_active")
    public String apply_active(){

        return "memberApply/apply_active";
    }
    // 申请通过 成为积极分子
    @RequestMapping(value = "/apply_active", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_active(int userId, String _activeTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_ACTIVE);
        record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_PASS);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_ACTIVE, "成为积极分子", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    @RequestMapping(value = "/apply_candidate")
    public String apply_candidate(){

        return "memberApply/apply_candidate";
    }

    // 提交 确定为发展对象
    @RequestMapping(value = "/apply_candidate", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_candidate(int userId, String _candidateTime, String _trainTime,
                                  @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        DateTime dt = new DateTime(memberApply.getActiveTime());
        DateTime dateTime = dt.plusYears(1);
        if (dateTime.isBeforeNow()) {
            return failed("确定为入党积极分子满1年之后才能被确定为发展对象。");
        }

        MemberApply record = new MemberApply();
        record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        record.setTrainTime(DateUtils.parseDate(_trainTime, DateUtils.YYYY_MM_DD));

        if(directParty && partyAdmin){ // 直属党支部管理员，不需要通过审核，直接确定发展对象
            record.setStage(SystemConstants.APPLY_STAGE_CANDIDATE);
            record.setCandidateStatus(SystemConstants.APPLY_STATUS_CHECKED);
        } else {
            record.setCandidateStatus(SystemConstants.APPLY_STATUS_UNCHECKED);
        }

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_ACTIVE);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_CANDIDATE, "确定为发展对象，已提交", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }
    // 审核 确定为发展对象
    @RequestMapping(value = "/apply_candidate_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_candidate_check(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该分党委管理员应是申请人所在的分党委
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer partyId = memberApply.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_CANDIDATE);
        record.setCandidateStatus(SystemConstants.APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_ACTIVE)
                .andCandidateStatusEqualTo(SystemConstants.APPLY_STATUS_UNCHECKED);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_CANDIDATE, "确定为发展对象，已审核", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    @RequestMapping(value = "/apply_plan")
    public String apply_plan(){

        return "memberApply/apply_plan";
    }

    //提交 列入发展计划
    @RequestMapping(value = "/apply_plan", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_plan(int userId, String _planTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }
        if(!applyOpenTimeService.isOpen(partyId, SystemConstants.APPLY_STAGE_PLAN)){
            return failed("不在开放时间范围");
        }
        MemberApply record = new MemberApply();
        if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过审核
            record.setStage(SystemConstants.APPLY_STAGE_PLAN);
            record.setPlanStatus(SystemConstants.APPLY_STATUS_CHECKED);
        }else{
            record.setPlanStatus(SystemConstants.APPLY_STATUS_UNCHECKED);
        }
        record.setPlanTime(DateUtils.parseDate(_planTime, DateUtils.YYYY_MM_DD));

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_CANDIDATE);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_PLAN, "列入发展计划，已提交", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    //审核 列入发展计划
    @RequestMapping(value = "/apply_plan_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_plan_check(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该分党委管理员应是申请人所在的分党委
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer partyId = memberApply.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        if(!applyOpenTimeService.isOpen(partyId, SystemConstants.APPLY_STAGE_PLAN)){
            return failed("不在开放时间范围");
        }
        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_PLAN);
        record.setPlanStatus(SystemConstants.APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_CANDIDATE)
                .andPlanStatusEqualTo(SystemConstants.APPLY_STATUS_UNCHECKED);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_PLAN, "列入发展计划，已审核", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    @RequestMapping(value = "/apply_draw")
    public String apply_draw(){

        return "memberApply/apply_draw";
    }

    //提交 领取志愿书
    @RequestMapping(value = "/apply_draw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_draw(int userId, String _drawTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过审核
            record.setStage(SystemConstants.APPLY_STAGE_DRAW);
            record.setDrawStatus(SystemConstants.APPLY_STATUS_CHECKED);
        }else {
            record.setDrawStatus(SystemConstants.APPLY_STATUS_UNCHECKED);
        }
        record.setDrawTime(DateUtils.parseDate(_drawTime, DateUtils.YYYY_MM_DD));

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_PLAN);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_DRAW, "领取志愿书，已提交", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }
    //审核 领取志愿书
    @RequestMapping(value = "/apply_draw_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_draw_check(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该分党委管理员应是申请人所在的分党委
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer partyId = memberApply.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_DRAW);
        record.setDrawStatus(SystemConstants.APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_PLAN)
                .andDrawStatusEqualTo(SystemConstants.APPLY_STATUS_UNCHECKED);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_DRAW, "领取志愿书，已审核", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    @RequestMapping(value = "/apply_grow")
    public String apply_grow(){

        return "memberApply/apply_grow";
    }

    //提交 预备党员
    @RequestMapping(value = "/apply_grow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_grow(int userId, String _growTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过分党委审核
            record.setGrowStatus(SystemConstants.APPLY_STATUS_CHECKED);
        }else {
            record.setGrowStatus(SystemConstants.APPLY_STATUS_UNCHECKED);
        }
        record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_DRAW);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_GROW, "预备党员，已提交", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }
    //审核 领取志愿书
    @RequestMapping(value = "/apply_grow_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_check(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该分党委管理员应是申请人所在的分党委
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer partyId = memberApply.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setGrowStatus(SystemConstants.APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_DRAW)
                .andGrowStatusEqualTo(SystemConstants.APPLY_STATUS_UNCHECKED);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_GROW, "预备党员，已审核1", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    //组织部管理员审核 领取志愿书
    @RequiresRoles("odAdmin")
    @RequestMapping(value = "/apply_grow_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_grow_check2(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        // 这里要添加权限验证?

        memberApplyService.memberGrow(userId);
        applyLogService.addApplyLog(userId, loginUser.getId(),
                SystemConstants.APPLY_STAGE_GROW, "预备党员，已审核2", IpUtils.getIp(request));

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value = "/apply_positive")
    public String apply_positive(){

        return "memberApply/apply_positive";
    }

    //提交 正式党员
    @RequestMapping(value = "/apply_positive", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply_positive(int userId, String _positiveTime, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该支部管理员应是申请人所在党支部或直属党支部
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer branchId = memberApply.getBranchId();
        Integer partyId = memberApply.getPartyId();
        boolean branchAdmin = branchMemberService.isAdmin(loginUserId, branchId);
        boolean partyAdmin = partyMemberService.isAdmin(loginUserId, partyId);
        boolean directParty = partyService.isDirectParty(partyId);
        if(!branchAdmin && (!directParty || !partyAdmin)){ // 不是党支部管理员， 也不是直属党支部管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过分党委审核
            record.setPositiveStatus(SystemConstants.APPLY_STATUS_CHECKED);
        }else {
            record.setPositiveStatus(SystemConstants.APPLY_STATUS_UNCHECKED);
        }
        record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_GROW);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_POSITIVE, "正式党员，已提交", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    //审核 正式党员
    @RequestMapping(value = "/apply_positive_check", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        //该分党委管理员应是申请人所在的分党委
        int loginUserId = loginUser.getId();
        MemberApply memberApply = memberApplyService.get(userId);
        Integer partyId = memberApply.getPartyId();
        if(!partyMemberService.isAdmin(loginUserId, partyId)){ // 分党委管理员
            throw new UnauthorizedException();
        }

        MemberApply record = new MemberApply();
        record.setPositiveStatus(SystemConstants.APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_GROW)
                .andPositiveStatusEqualTo(SystemConstants.APPLY_STATUS_UNCHECKED);

        if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
            applyLogService.addApplyLog(userId, loginUser.getId(),
                    SystemConstants.APPLY_STAGE_POSITIVE, "正式党员，已审核1", IpUtils.getIp(request));
            return success(FormUtils.SUCCESS);
        }

        return failed(FormUtils.FAILED);
    }

    //组织部管理员审核 正式党员
    @RequiresRoles("odAdmin")
    @RequestMapping(value = "/apply_positive_check2", method = RequestMethod.POST)
    @ResponseBody
    public Map apply_positive_check2(int userId, @CurrentUser SysUser loginUser, HttpServletRequest request) {

        // 这里要添加权限验证?

        memberApplyService.memberPositive(userId);
        applyLogService.addApplyLog(userId, loginUser.getId(),
                SystemConstants.APPLY_STAGE_POSITIVE, "正式党员，已审核2", IpUtils.getIp(request));
        return success(FormUtils.SUCCESS);
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
