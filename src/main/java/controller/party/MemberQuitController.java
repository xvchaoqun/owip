package controller.party;

import controller.BaseController;
import domain.*;
import domain.MemberQuitExample.Criteria;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.MemberQuitMixin;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.MSUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class MemberQuitController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit")
    public String memberQuit() {

        return "index";
    }

    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit_page")
    public String memberQuit_page(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
                                  Integer userId,
                                  Integer partyId,
                                  Integer branchId,ModelMap modelMap) {

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
        modelMap.put("branchApprovalCount", memberQuitService.count(null, null, (byte)1));
        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", memberQuitService.count(null, null, (byte)2));
        // 组织部待审核数目
        modelMap.put("odApprovalCount", memberQuitService.count(null, null, (byte)3));
        
        return "party/memberQuit/memberQuit_page";
    }
    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit_data")
    public void memberQuit_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "ow_member_quit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte status,
                                    Boolean isBack,
                                    Byte type,
                                    String _quitTime,
                                    String _growTime,
                                    Integer partyId,
                                    Integer branchId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberQuitExample example = new MemberQuitExample();
        Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        example.setOrderByClause(String.format("%s %s", sort, order));
        if(StringUtils.isNotBlank(_quitTime)) {
            String quitTimeStart = _quitTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String quitTimeEnd = _quitTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(quitTimeStart)) {
                criteria.andQuitTimeGreaterThanOrEqualTo(DateUtils.parseDate(quitTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(quitTimeEnd)) {
                criteria.andQuitTimeLessThanOrEqualTo(DateUtils.parseDate(quitTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }
        if(StringUtils.isNotBlank(_growTime)) {
            String quitTimeStart = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[0];
            String quitTimeEnd = _growTime.split(SystemConstants.DATERANGE_SEPARTOR)[1];
            if (StringUtils.isNotBlank(quitTimeStart)) {
                criteria.andGrowTimeGreaterThanOrEqualTo(DateUtils.parseDate(quitTimeStart, DateUtils.YYYY_MM_DD));
            }
            if (StringUtils.isNotBlank(quitTimeEnd)) {
                criteria.andGrowTimeLessThanOrEqualTo(DateUtils.parseDate(quitTimeEnd, DateUtils.YYYY_MM_DD));
            }
        }
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
        if (partyId != null) {
            criteria.andPartyIdEqualTo(partyId);
        }
        if (branchId != null) {
            criteria.andBranchIdEqualTo(branchId);
        }

        if(cls==1){ // 支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_QUIT_STATUS_APPLY);
        }else if(cls==11){ // 分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY);
        }else if(cls==12){// 组织部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MEMBER_QUIT_STATUS_SELF_BACK);
            statusList.add(SystemConstants.MEMBER_QUIT_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(SystemConstants.MEMBER_QUIT_STATUS_OW_VERIFY);
        }

        if (export == 1) {
            memberQuit_export(example, response);
            return;
        }

        int count = memberQuitMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberQuit> memberQuits = memberQuitMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberQuits);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> sourceMixins = sourceMixins();
        sourceMixins.put(MemberQuit.class, MemberQuitMixin.class);
        JSONUtils.jsonp(resultMap, sourceMixins);
        return;
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit_approval")
    public String memberQuit_approval(@CurrentUser SysUser loginUser, Integer id,
                                     byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                     ModelMap modelMap) {

        MemberQuit currentMemberQuit = null;
        if (id != null) {
            currentMemberQuit = memberQuitMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberQuit.getStatus() != SystemConstants.MEMBER_QUIT_STATUS_APPLY)
                    currentMemberQuit = null;
            }
            if (type == 2) {
                if (currentMemberQuit.getStatus() != SystemConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY)
                    currentMemberQuit = null;
            }
            if (type == 3) {
                if (currentMemberQuit.getStatus() != SystemConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY)
                    currentMemberQuit = null;
            }
        } else {
            currentMemberQuit = memberQuitService.next(type, null);
        }
        if (currentMemberQuit == null)
            throw new RuntimeException("当前没有需要审批的记录");

        modelMap.put("memberQuit", currentMemberQuit);

        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", branchMemberService.isPresentAdmin(loginUser.getId(), currentMemberQuit.getBranchId()));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberQuit.getPartyId()));
        }
        if (type == 3) {
            modelMap.put("isAdmin", SecurityUtils.getSubject().hasRole("odAdmin"));
        }

        // 读取总数
        modelMap.put("count", memberQuitService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberQuitService.next(type, currentMemberQuit));
        // 上一条记录
        modelMap.put("last", memberQuitService.last(type, currentMemberQuit));

        return "party/memberQuit/memberQuit_approval";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberQuit:update")
    @RequestMapping("/memberQuit_deny")
    public String memberQuit_deny(Integer id, ModelMap modelMap) {

        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(id);
        modelMap.put("memberQuit", memberQuit);
        Integer userId = memberQuit.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "party/memberQuit/memberQuit_deny";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberQuit:update")
    @RequestMapping(value = "/memberQuit_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_check(@CurrentUser SysUser loginUser, HttpServletRequest request,
                                  byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                  @RequestParam(value = "ids[]") int[] ids) {


        memberQuitService.memberQuit_check(ids, type, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "党员出党申请-审核：%s", ids));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberQuit:update")
    @RequestMapping("/memberQuit_back")
    public String memberQuit_back() {

        return "party/memberQuit/memberQuit_back";
    }

    @RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
    @RequiresPermissions("memberQuit:update")
    @RequestMapping(value = "/memberQuit_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_back(@CurrentUser SysUser loginUser,
                                   @RequestParam(value = "ids[]") int[] ids,
                                   byte status,
                                   String reason) {


        memberQuitService.memberQuit_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(SystemConstants.LOG_OW, "分党委打回党员出党申请：%s", ids));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberQuit:edit")
    @RequestMapping(value = "/memberQuit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_au(@CurrentUser SysUser loginUser,
                                MemberQuit record,
                                Byte resubmit,
                                String _quitTime, HttpServletRequest request) {

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);

        Integer partyId = member.getPartyId();
        Integer branchId = member.getBranchId();
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

        record.setPartyId(partyId);
        record.setBranchId(branchId);
        record.setGrowTime(member.getGrowTime());

        if(StringUtils.isNotBlank(_quitTime))
            record.setQuitTime(DateUtils.parseDate(_quitTime, DateUtils.YYYY_MM_DD));
        if(member.getPartyId()!=null){
            Party party = partyService.findAll().get(member.getPartyId());
            record.setPartyName(party.getName());
        }
        if(member.getBranchId()!=null){
            Branch branch = branchService.findAll().get(member.getBranchId());
            record.setBranchName(branch.getName());
        }
        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
        if (memberQuit == null) {

            if(record.getType()==SystemConstants.MEMBER_QUIT_TYPE_WITHGOD){ // 离世 直接出党
                record.setStatus(SystemConstants.MEMBER_QUIT_STATUS_OW_VERIFY);
                record.setCreateTime(new Date());
                memberQuitService.insertSelective(record);
                memberService.quit(record.getUserId(), SystemConstants.MEMBER_STATUS_QUIT);
            }else {
                record.setStatus(SystemConstants.MEMBER_QUIT_STATUS_APPLY);
                record.setCreateTime(new Date());
                memberQuitService.insertSelective(record);
            }
            applyApprovalLogService.add(record.getUserId(),
                    record.getPartyId(), record.getBranchId(), userId,
                    loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, "后台添加",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED, null);

            logger.info(addLog(SystemConstants.LOG_OW, "添加党员出党：%s", record.getUserId()));
        } else {

            if(memberQuit.getStatus()==SystemConstants.MEMBER_QUIT_STATUS_OW_VERIFY)
                throw new RuntimeException("该用户已经出党，不可以再次修改。");

            if(resubmit!=null && resubmit==1 && memberQuit.getStatus()<SystemConstants.MEMBER_QUIT_STATUS_APPLY){ // 重新提交
                record.setStatus(SystemConstants.MEMBER_QUIT_STATUS_APPLY);
            }

            memberQuitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_OW, "更新党员出党：%s", record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberQuit:edit")
    @RequestMapping("/memberQuit_au")
    public String memberQuit_au(Integer userId, ModelMap modelMap) {

        if (userId != null) {
            MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
            modelMap.put("memberQuit", memberQuit);

            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        return "party/memberQuit/memberQuit_au";
    }

    public void memberQuit_export(MemberQuitExample example, HttpServletResponse response) {

        List<MemberQuit> records = memberQuitMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号","姓名", "所在分党委","所在党支部", "类别", "入党时间", "出党时间", "审核状态"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberQuit record = records.get(i);
            SysUser sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();

            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record.getType()==null?"":SystemConstants.MEMBER_QUIT_TYPE_MAP.get(record.getType()),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getQuitTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus()==null?"":SystemConstants.MEMBER_QUIT_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = "党员出党_" + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
