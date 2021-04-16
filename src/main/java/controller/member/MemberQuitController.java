package controller.member;

import controller.global.OpException;
import domain.base.MetaType;
import domain.member.Member;
import domain.member.MemberQuit;
import domain.member.MemberQuitExample;
import domain.member.MemberQuitExample.Criteria;
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
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.helper.PartyHelper;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tags.CmTag;
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
public class MemberQuitController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit")
    public String memberQuit(@RequestParam(defaultValue = "1")Integer cls, // 1 待审核 2未通过 3 已审核
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
        // 完成审核数目
        modelMap.put("hasApprovalCount", memberQuitService.count(null, null, (byte)4));
        
        return "member/memberQuit/memberQuit_page";
    }
    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit_data")
    public void memberQuit_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                 @SortParam(required = false, defaultValue = "create_time", tableName = "ow_member_quit") String sort,
                                 @OrderParam(required = false, defaultValue = "desc") String order,
                                    Integer userId,
                                    Byte status,
                                    Boolean isBack,
                                    Integer type,
                                @RequestDateRange DateRange _quitTime,
                                @RequestDateRange DateRange  _growTime,
                                    Integer partyId,
                                    Integer branchId,
                                 @RequestParam(required = false, defaultValue = "0") int export,
                                 Integer[] ids, // 导出的记录
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
        if (_quitTime.getStart()!=null) {
            criteria.andQuitTimeGreaterThanOrEqualTo(_quitTime.getStart());
        }

        if (_quitTime.getEnd()!=null) {
            criteria.andQuitTimeLessThanOrEqualTo(_quitTime.getEnd());
        }
        if (_growTime.getStart()!=null) {
            criteria.andGrowTimeGreaterThanOrEqualTo(_growTime.getStart());
        }

        if (_growTime.getEnd()!=null) {
            criteria.andGrowTimeLessThanOrEqualTo(_growTime.getEnd());
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
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_APPLY);
        }else if(cls==11){ // 分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY);
        }else if(cls==12){// 组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        }else if(cls==2){
            List<Byte> statusList = new ArrayList<>();
            statusList.add(MemberConstants.MEMBER_QUIT_STATUS_SELF_BACK);
            statusList.add(MemberConstants.MEMBER_QUIT_STATUS_BACK);
            criteria.andStatusIn(statusList);
        }else {
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY);
        }

        if (export == 1) {
            if(ids!=null && ids.length>0)
                criteria.andUserIdIn(Arrays.asList(ids));

            memberQuit_export(example, response);
            return;
        }

        int count = (int) memberQuitMapper.countByExample(example);
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

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresPermissions("memberQuit:list")
    @RequestMapping("/memberQuit_approval")
    public String memberQuit_approval(@CurrentUser SysUserView loginUser, Integer id,
                                     byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                     ModelMap modelMap) {

        MemberQuit currentMemberQuit = null;
        if (id != null) {
            currentMemberQuit = memberQuitMapper.selectByPrimaryKey(id);
            if (type == 1) {
                if (currentMemberQuit.getStatus() != MemberConstants.MEMBER_QUIT_STATUS_APPLY)
                    currentMemberQuit = null;
            }
            if (type == 2) {
                if (currentMemberQuit.getStatus() != MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY)
                    currentMemberQuit = null;
            }
            if (type == 3) {
                if (currentMemberQuit.getStatus() != MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY)
                    currentMemberQuit = null;
            }
        } else {
            currentMemberQuit = memberQuitService.next(type, null);
        }
        if (currentMemberQuit == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberQuit", currentMemberQuit);

        Integer branchId = currentMemberQuit.getBranchId();
        Integer partyId = currentMemberQuit.getPartyId();
        // 是否是当前记录的管理员
        if (type == 1) {
            modelMap.put("isAdmin", PartyHelper.hasBranchAuth(loginUser.getId(), partyId, branchId));
        }
        if (type == 2) {
            modelMap.put("isAdmin", partyMemberService.hasAdminAuth(loginUser.getId(), partyId));
        }
        if (type == 3) {
            modelMap.put("isAdmin", ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL));
        }

        // 读取总数
        modelMap.put("count", memberQuitService.count(null, null, type));
        // 下一条记录
        modelMap.put("next", memberQuitService.next(type, currentMemberQuit));
        // 上一条记录
        modelMap.put("last", memberQuitService.last(type, currentMemberQuit));

        return "member/memberQuit/memberQuit_approval";
    }

    @RequiresPermissions("memberQuit:update")
    @RequestMapping("/memberQuit_deny")
    public String memberQuit_deny(Integer id, ModelMap modelMap) {

        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(id);
        modelMap.put("memberQuit", memberQuit);
        Integer userId = memberQuit.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberQuit/memberQuit_deny";
    }

    @RequiresPermissions("memberQuit:update")
    @RequestMapping(value = "/memberQuit_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_check(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                  byte type, // 1:支部审核 2:分党委审核 3：组织部审核
                                  Integer[] ids) {


        memberQuitService.memberQuit_check(ids, type, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "减员申请-审核：%s", StringUtils.join( ids, ",")));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberQuit:update")
    @RequestMapping("/memberQuit_back")
    public String memberQuit_back() {

        return "member/memberQuit/memberQuit_back";
    }

    @RequiresPermissions("memberQuit:update")
    @RequestMapping(value = "/memberQuit_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_back(@CurrentUser SysUserView loginUser,
                                   Integer[] ids,
                                   byte status,
                                   String reason) {


        memberQuitService.memberQuit_back(ids, status, reason, loginUser.getId());

        logger.info(addLog(LogConstants.LOG_MEMBER, "分党委退回减员申请：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    // 撤销已减员记录
    @RequiresRoles(RoleConstants.ROLE_ODADMIN)
    @RequestMapping(value = "/memberQuit_abolish", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_abolish(Integer[] ids) {

        memberQuitService.memberQuit_abolish(ids);

        logger.info(addLog(LogConstants.LOG_MEMBER, "撤销减员：%s", StringUtils.join( ids, ",")));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberQuit:edit")
    @RequestMapping(value = "/memberQuit_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberQuit_au(@CurrentUser SysUserView loginUser,
                                MemberQuit record,
                                Byte resubmit,
                                String _quitTime, HttpServletRequest request) {

        Integer userId = record.getUserId();
        Member member = memberService.get(userId);

        Integer partyId = member.getPartyId();
        Integer branchId = member.getBranchId();
        //===========权限
        Integer loginUserId = loginUser.getId();
        if (!PartyHelper.hasBranchAuth(loginUserId, partyId, branchId))
            throw new UnauthorizedException();

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

            MetaType metaType = CmTag.getMetaTypeByCode("mt_quit_withgod");
            if(record.getType()==metaType.getId()){ // 离世 直接出党
                record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY);
                record.setCreateTime(new Date());
                memberQuitService.insertSelective(record);
                memberQuitService.quit(record.getUserId(), MemberConstants.MEMBER_STATUS_QUIT);
            }else {
                record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_APPLY);
                record.setCreateTime(new Date());
                memberQuitService.insertSelective(record);
            }
            applyApprovalLogService.add(record.getUserId(),
                    record.getPartyId(), record.getBranchId(), userId,
                    loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, "后台添加",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);

            logger.info(addLog(LogConstants.LOG_MEMBER, "添加减员：%s", record.getUserId()));
        } else {

            if(memberQuit.getStatus()==MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY)
                return failed("该账号已经出党，不可以再次修改。");

            if(resubmit!=null && resubmit==1 && memberQuit.getStatus()<MemberConstants.MEMBER_QUIT_STATUS_APPLY){ // 重新提交
                record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_APPLY);
            }

            memberQuitService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_MEMBER, "更新减员：%s", record.getUserId()));
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
        return "member/memberQuit/memberQuit_au";
    }

    public void memberQuit_export(MemberQuitExample example, HttpServletResponse response) {

        List<MemberQuit> records = memberQuitMapper.selectByExample(example);
        int rownum = records.size();
        String[] titles = {"学工号|100","姓名|100", "所在"+ CmTag.getStringProperty("partyName") + "|250|left","所在党支部|250|left", "类别|150", "入党时间|150", "出党时间|150", "审核状态|150"};
        List<String[]> valuesList = new ArrayList<>();
        for (int i = 0; i < rownum; i++) {
            MemberQuit record = records.get(i);
            SysUserView sysUser = sysUserService.findById(record.getUserId());
            Integer partyId = record.getPartyId();
            Integer branchId = record.getBranchId();

            String[] values = {
                    sysUser.getCode(),
                    sysUser.getRealname(),
                    partyId==null?"":partyService.findAll().get(partyId).getName(),
                    branchId==null?"":branchService.findAll().get(branchId).getName(),
                    record.getType()==null?"":metaTypeMapper.selectByPrimaryKey(record.getType()).getName(),
                    DateUtils.formatDate(record.getGrowTime(), DateUtils.YYYY_MM_DD),
                    DateUtils.formatDate(record.getQuitTime(), DateUtils.YYYY_MM_DD),
                    record.getStatus()==null?"":MemberConstants.MEMBER_QUIT_STATUS_MAP.get(record.getStatus())
            };
            valuesList.add(values);
        }
        String fileName = String.format("减员(%s)", DateUtils.formatDate(new Date(), "yyyyMMdd"));
        ExportHelper.export(titles, valuesList, fileName, response);
    }
}
