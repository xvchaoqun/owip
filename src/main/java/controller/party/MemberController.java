package controller.party;

import controller.BaseController;
import domain.member.*;
import domain.party.Branch;
import domain.party.GraduateAbroad;
import domain.party.Party;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
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
import shiro.ShiroHelper;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/member/search")
    public String search(){

        return "party/member/member_search";
    }
    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/member/search", method = RequestMethod.POST)
    @ResponseBody
    public Map do_search(String code) {

        String realname = "";
        String unit = "";
        String msg = "";
        String status = "";
        SysUserView sysUser = sysUserService.findByCode(StringUtils.trimToEmpty(code));
        if(sysUser==null){
            msg = "该用户不存在";
        }else {
            realname = sysUser.getRealname();
            unit = sysUserService.getUnit(sysUser);
            Member member = memberService.get(sysUser.getId());
            if(member==null){
                msg = "该用户不是党员";
            }else{
                Integer partyId = member.getPartyId();
                Integer branchId = member.getBranchId();
                Party party = partyService.findAll().get(partyId);
                msg = party.getName();
                if(branchId!=null){
                    Branch branch = branchService.findAll().get(branchId);
                    if(branch!=null) msg += "-" + branch.getName();
                }

                // 查询状态
                if(member.getStatus()==SystemConstants.MEMBER_STATUS_NORMAL){
                    status = "正常";
                }else if(member.getStatus()==SystemConstants.MEMBER_STATUS_TRANSFER){
                    status = "已转出";
                }else if(member.getStatus()==SystemConstants.MEMBER_STATUS_QUIT){
                    status = "已出党";
                }

                if(member.getType()==SystemConstants.MEMBER_TYPE_TEACHER){
                    MemberTeacher memberTeacher = memberTeacherService.get(sysUser.getId());
                    if(memberTeacher.getIsRetire())
                        status = "已退休";
                }

                GraduateAbroad graduateAbroad = graduateAbroadService.get(sysUser.getId());
                if(graduateAbroad!=null){
                    if(graduateAbroad.getStatus()==SystemConstants.GRADUATE_ABROAD_STATUS_OW_VERIFY){
                        status = "出国暂留申请已完成审批";
                    }else if(graduateAbroad.getStatus()>=SystemConstants.GRADUATE_ABROAD_STATUS_APPLY)
                        status = "已申请出国暂留，但还未审核通过";
                }
            }
        }


        Map<String, Object> resultMap = success(FormUtils.SUCCESS);
        resultMap.put("msg", msg);
        resultMap.put("realname", realname);
        resultMap.put("unit", unit);
        resultMap.put("status", status);
        return resultMap;
    }

    // for test 后台数据库中导入党员数据后，需要同步信息、更新状态
    @RequiresRoles(SystemConstants.ROLE_ADMIN)
    @RequestMapping(value = "/member_dbUpdate")
    @ResponseBody
    public Map dbUpdate() {

        List<Member> members = memberMapper.selectByExample(new MemberExample());
        for (Member member : members) {
            memberService.dbUpdate(member.getUserId());
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("member:edit")
    @RequestMapping(value = "/member_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_au(@CurrentUser SysUserView loginUser, Member record, String _applyTime, String _activeTime, String _candidateTime,
                            String _growTime, String _positiveTime, String _transferTime, String reason, HttpServletRequest request) {

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        if (partyId != null && branchId == null) {
            if (!partyService.isDirectBranch(partyId)) {
                throw new RuntimeException("只有直属党支部或党支部可以添加党员");
            }
        }

        //===========权限
        Integer loginUserId = loginUser.getId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(SystemConstants.ROLE_ADMIN)
                && !subject.hasRole(SystemConstants.ROLE_ODADMIN)) {

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if (!isAdmin && branchId != null) { // 只有支部管理员或分党委管理员可以添加党员
                isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
            }
            if (!isAdmin) throw new UnauthorizedException();
        }

        Integer userId = record.getUserId();

        if (StringUtils.isNotBlank(_applyTime)) {
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_candidateTime)) {
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_positiveTime)) {
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_transferTime)) {
            record.setTransferTime(DateUtils.parseDate(_transferTime, DateUtils.YYYY_MM_DD));
        }
        SysUserView sysUser = sysUserService.findById(record.getUserId());
        Member member = memberService.get(userId);
        if (member == null) {
            SecurityUtils.getSubject().checkPermission("member:add");

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if(memberApply!=null && memberApply.getStage()>=SystemConstants.APPLY_STAGE_INIT){
                throw new RuntimeException("该用户已经提交了入党申请[当前审批阶段："+SystemConstants.APPLY_STAGE_MAP.get(memberApply.getStage())+"]，不可以直接添加。");
            }

            record.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常
            record.setCreateTime(new Date());
            record.setSource(SystemConstants.MEMBER_SOURCE_ADMIN); // 后台添加的党员
            memberService.add(record);

            memberService.addModify(record.getUserId(), "后台添加");

            logger.info(addLog(SystemConstants.LOG_MEMBER,
                    "添加党员信息表：%s %s %s %s, 添加原因：%s", sysUser.getId(), sysUser.getRealname(),
                    partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(), reason));
        } else {

            SecurityUtils.getSubject().checkPermission("member:edit");

            record.setPoliticalStatus(null); // 不能修改党籍状态
            memberService.updateByPrimaryKeySelective(record, reason);

            logger.info(addLog(SystemConstants.LOG_MEMBER,
                    "更新党员信息表：%s %s %s %s, 更新原因：%s", sysUser.getId(), sysUser.getRealname(),
                    partyService.findAll().get(partyId).getName(),
                    branchId == null ? "" : branchService.findAll().get(branchId).getName(),
                    reason));
        }

        return success(FormUtils.SUCCESS);
    }

    //@RequiresPermissions("member:edit")
    @RequestMapping("/member_au")
    public String member_au(Integer userId, Integer partyId, Integer branchId, ModelMap modelMap) {

        Member member = null;
        if (userId != null) {
            SecurityUtils.getSubject().checkPermission("member:edit");

            member = memberMapper.selectByPrimaryKey(userId);
            modelMap.put("op", "编辑");

            partyId = member.getPartyId();
            branchId = member.getBranchId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        } else {
            SecurityUtils.getSubject().checkPermission("member:add");

            modelMap.put("op", "添加");
        }

        Map<Integer, Branch> branchMap = branchService.findAll();
        if (branchId != null && partyId == null) { // 给支部添加党员
            Branch branch = branchMap.get(branchId);
            partyId = branch.getPartyId();
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        if (branchId != null) {
            modelMap.put("branch", branchMap.get(branchId));
        }

        if (userId == null && partyId != null && branchId == null) { // 给直属党支部添加党员
            if (!partyService.isDirectBranch(partyId)) {
                throw new RuntimeException("只有直属党支部或党支部可以添加党员");
            }
        }

        modelMap.put("member", member);

        return "party/member/member_au";
    }

    // 后台添加预备党员，可能需要加入入党申请（预备党员阶段）
    @RequestMapping(value = "/member_addGrowApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_addGrowApply(int userId) {

        SecurityUtils.getSubject().checkPermission("member:edit");
        memberApplyService.addOrChangeToGrowApply(userId);

        return success(FormUtils.SUCCESS);
    }
    // 修改党籍状态
    @RequiresPermissions("member:modifyStatus")
    @RequestMapping("/member_modify_status")
    public String member_modify_status(int userId, ModelMap modelMap) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        modelMap.put("member", member);

        return "party/member/member_modify_status";
    }
    @RequiresPermissions("member:modifyStatus")
    @RequestMapping(value = "/member_modify_status", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_modify_status(int userId, byte politicalStatus, String remark) {

        memberService.modifyStatus(userId, politicalStatus, remark);

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_PARTYADMIN)
    @RequestMapping("/member_changeBranch")
    public String member_changeBranch(@CurrentUser SysUserView loginUser, @RequestParam(value = "ids[]") Integer[] ids,
                                      int partyId, ModelMap modelMap) {

        // 判断是分党委管理员
        if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
            throw new UnauthorizedException();
        }

        modelMap.put("ids", StringUtils.join(ids, ","));

        Integer _partyId = null;
        for (Integer userId : ids) {
            Member member = memberService.get(userId);
            if (_partyId == null) _partyId = member.getPartyId();
            if (_partyId != null && _partyId.intValue() != member.getPartyId()) {
                throw new RuntimeException("只允许在同一个分党委内部进行批量转移。");
            }
            if (partyService.isDirectBranch(member.getPartyId())) {
                throw new RuntimeException("直属党支部不能进行内部转移。");
            }
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("party", partyMap.get(partyId));

        return "party/member/member_changeBranch";
    }

    // 批量分党委内部转移
    @RequiresRoles(SystemConstants.ROLE_PARTYADMIN)
    @RequestMapping(value = "/member_changeBranch", method = RequestMethod.POST)
    @ResponseBody
    public Map member_changeBranch(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                   @RequestParam(value = "ids[]") Integer[] ids,
                                   int partyId, // 用于校验
                                   int branchId,
                                   ModelMap modelMap) {

        // 判断是分党委管理员
        if (!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)) {
            throw new UnauthorizedException();
        }

        if (null != ids) {
            memberService.changeBranch(ids, partyId, branchId);
            logger.info(addLog(SystemConstants.LOG_MEMBER, "批量分党委内部转移：%s, %s, %s", StringUtils.join(ids, ","), partyId, branchId));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_ODADMIN)
    @RequestMapping("/member_changeParty")
    public String member_changeParty() {

        //modelMap.put("ids", StringUtils.join( ids, ","));

        return "party/member/member_changeParty";
    }

    // 批量校内组织关系转移
    @RequiresRoles(SystemConstants.ROLE_ODADMIN)
    @RequestMapping(value = "/member_changeParty", method = RequestMethod.POST)
    @ResponseBody
    public Map member_changeParty(HttpServletRequest request,
                                  @RequestParam(value = "ids[]") Integer[] ids,
                                  int partyId,
                                  Integer branchId,
                                  ModelMap modelMap) {

        if (null != ids) {
            memberService.changeParty(ids, partyId, branchId);
            logger.info(addLog(SystemConstants.LOG_MEMBER, "批量校内组织关系转移：%s, %s, %s", ids, partyId, branchId));
        }
        return success(FormUtils.SUCCESS);
    }

    /*@RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN}, logical = Logical.OR)
    @RequiresPermissions("member:del")
    @RequestMapping(value = "/member_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberService.del(id);
            logger.info(addLog(SystemConstants.LOG_MEMBER_APPLY, "删除党员信息表：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }*/

    @RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_ODADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/member_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids) {

            List<Member> members = new ArrayList<>();
            for (Integer id : ids) {
                members.add(memberService.get(id));
            }

            memberService.batchDel(ids);

            logger.info(addLog(SystemConstants.LOG_MEMBER, "批量删除党员：%s", JSONUtils.toString(members)));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/member")
    public String member() {

        return "index";
    }

    @RequestMapping("/member_page")
    public String member_page(HttpServletResponse response, @RequestParam(defaultValue = "1") Integer cls, ModelMap
            modelMap) {

        modelMap.put("cls", cls);
        /**
         * cls=1 学生党员 member.type=3 member.status=1
         * cls=6 已转出的学生党员
         */
        if (cls == 1 || cls==6) {
            return "forward:/memberStudent_page";
        }
        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                （弃用）4应退休   =>  member.type=2 member.status=1
                （弃用）5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
                cls=7 已转出的教工党员
         */
         return "forward:/memberTeacher_page";
    }

 /*   @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_view(HttpServletResponse response, int userId, ModelMap modelMap) {

        return "index";
    }*/

    @RequestMapping("/member_view")
    public String member_show_page(@CurrentUser SysUserView loginUser, HttpServletResponse response, int userId, ModelMap modelMap) {

        Member member = memberService.get(userId);

        // 分党委、组织部管理员或管理员才可以操作
        if (!ShiroHelper.hasAnyRoles(SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_ADMIN)) {
            Integer partyId = member.getPartyId();
            Integer branchId = member.getBranchId();
            Integer loginUserId = loginUser.getId();
            boolean isBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, partyId, branchId);
            boolean isPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, partyId);
            if(!isBranchAdmin && !isPartyAdmin){
                throw new UnauthorizedException();
            }
        }
        if (member.getType() == SystemConstants.MEMBER_TYPE_TEACHER)  // 这个地方的判断可能有问题，应该用党员信息里的类别++++++++++++
            return "party/member/teacher_view";
        return "party/member/student_view";
    }
}
