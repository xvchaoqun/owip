package controller.party;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // for test 后台数据库中导入党员数据后，需要同步信息、更新状态
    @RequiresRoles("admin")
    @RequestMapping(value = "/member_dbUpdate")
    @ResponseBody
    public Map dbUpdate(){

        List<Member> members = memberMapper.selectByExample(new MemberExample());
        for (Member member : members) {
            memberService.dbUpdate(member.getUserId());
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:edit")
    @RequestMapping(value = "/member_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_au(@CurrentUser SysUser loginUser, Member record,  String _applyTime, String _activeTime, String _candidateTime,
                            String _growTime, String _positiveTime, String _transferTime, HttpServletRequest request) {

        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();
        if(partyId!=null && branchId==null) {
            if(!partyService.isDirectBranch(partyId)){
                throw new RuntimeException("只有直属党支部或党支部可以添加党员");
            }
        }

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

        Integer userId = record.getUserId();

        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            record.setActiveTime(DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_candidateTime)){
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_positiveTime)){
            record.setPositiveTime(DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_transferTime)){
            record.setTransferTime(DateUtils.parseDate(_transferTime, DateUtils.YYYY_MM_DD));
        }

        Member member = memberService.get(userId);
        if (member == null) {
            record.setCreateTime(new Date());
            record.setSource(SystemConstants.MEMBER_SOURCE_ADMIN); // 后台添加的党员
            memberService.add(record);
            logger.info(addLog(SystemConstants.LOG_MEMBER_APPLY, "添加党员信息表：%s", record.getUserId()));
        } else {
            memberService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_MEMBER_APPLY, "更新党员信息表：%s", record.getUserId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:edit")
    @RequestMapping("/member_au")
    public String member_au(Integer userId, Integer partyId, Integer branchId, ModelMap modelMap) {

        Member member = null;
        if (userId != null) {
            member = memberMapper.selectByPrimaryKey(userId);
            modelMap.put("op", "编辑");

            partyId = member.getPartyId();
            branchId = member.getBranchId();
            modelMap.put("sysUser", sysUserService.findById(userId));
        }else{
            modelMap.put("op", "添加");
        }

        Map<Integer, Branch> branchMap = branchService.findAll();
        if(branchId!=null && partyId==null){ // 给支部添加党员
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

        if(userId==null && partyId!=null && branchId==null) { // 给直属党支部添加党员
            if(!partyService.isDirectBranch(partyId)){
                throw new RuntimeException("只有直属党支部或党支部可以添加党员");
            }
        }

        modelMap.put("member", member);

        return "party/member/member_au";
    }

    @RequiresRoles("partyAdmin")
    @RequiresPermissions("member:edit")
    @RequestMapping("/member_changeBranch")
    public String member_changeBranch(@CurrentUser SysUser loginUser, @RequestParam(value = "ids[]") Integer[] ids,
                                      int partyId, ModelMap modelMap) {

        // 判断是分党委管理员
        if(!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)){
            throw new UnauthorizedException();
        }

        modelMap.put("ids", ids);

        Integer _partyId = null;
        for (Integer userId : ids) {
            Member member = memberService.get(userId);
            if(_partyId==null) _partyId = member.getPartyId();
            if(_partyId!=null && _partyId.intValue() != member.getPartyId()){
                throw new RuntimeException("只允许在同一个分党委内部进行批量转移。");
            }
            if(partyService.isDirectBranch(member.getPartyId())){
                throw new RuntimeException("直属党支部不能进行内部转移。");
            }
        }

        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("party", partyMap.get(partyId));

        return "party/member/member_changeBranch";
    }
    // 批量分党委内部转移
    @RequiresRoles("partyAdmin")
    @RequiresPermissions("member:edit")
    @RequestMapping(value = "/member_changeBranch", method = RequestMethod.POST)
    @ResponseBody
    public Map member_changeBranch(@CurrentUser SysUser loginUser, HttpServletRequest request,
                        @RequestParam(value = "ids[]") Integer[] ids,
                        int partyId, // 用于校验
                        int branchId,
                        ModelMap modelMap) {

        // 判断是分党委管理员
        if(!partyMemberService.isPresentAdmin(loginUser.getId(), partyId)){
            throw new UnauthorizedException();
        }

        if (null != ids){
            memberService.changeBranch(ids, partyId, branchId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量分党委内部转移：%s, %s, %s", new Object[]{ids, partyId, branchId}));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("odAdmin")
    @RequiresPermissions("member:edit")
    @RequestMapping("/member_changeParty")
    public String member_changeParty() {

        //modelMap.put("ids", ids);

        return "party/member/member_changeParty";
    }
    // 批量校内组织关系转移
    @RequiresRoles("odAdmin")
    @RequiresPermissions("member:edit")
    @RequestMapping(value = "/member_changeParty", method = RequestMethod.POST)
    @ResponseBody
    public Map member_changeParty(HttpServletRequest request,
                                  @RequestParam(value = "ids[]") Integer[] ids,
                                  int partyId,
                                  Integer branchId,
                                  ModelMap modelMap) {

        if (null != ids){
            memberService.changeParty(ids, partyId, branchId);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量校内组织关系转移：%s, %s, %s", new Object[]{ids, partyId, branchId}));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:del")
    @RequestMapping(value = "/member_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_member_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberService.del(id);
            logger.info(addLog(SystemConstants.LOG_MEMBER_APPLY, "删除党员信息表：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:del")
    @RequestMapping(value = "/member_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {

        if (null != ids){
            memberService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除党员：%s", new Object[]{ids}));
        }
        return success(FormUtils.SUCCESS);
    }

    // 同步信息
    @RequiresPermissions("member:sync")
    @RequestMapping(value = "/member_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map sync(Integer userId){

        SysUser sysUser = sysUserService.findById(userId);
        Member member = memberService.get(userId);
        if(member!=null) {
            if(sysUser.getType()== SystemConstants.USER_TYPE_JZG)
                memberService.snycTeacher(sysUser.getId(), sysUser);
            else
                memberService.snycStudent(sysUser.getId(), sysUser);
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("member:list")
    @RequestMapping("/member")
    public String member() {

        return "index";
    }
    @RequiresPermissions("member:list")
    @RequestMapping("/member_page")
    public String member_page(HttpServletResponse response,@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if(cls==1) { // => member.type=3 member.status=1
            return "forward:/memberStudent_page";
        }
        /*
            cls=2教职工   =>  member.type=1 member.status=1
                3离退休   =>  member.type=2 member.status=1
                4应退休   =>  member.type=2 member.status=1
                5已退休   =>  member.type=2 memberTeacher.isRetire=1 member.status=2
         */
        return "forward:/memberTeacher_page";
    }

 /*   @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_view(HttpServletResponse response, int userId, ModelMap modelMap) {

        return "index";
    }*/

    @RequiresPermissions("member:view")
    @RequestMapping("/member_view")
    public String member_show_page(HttpServletResponse response, int userId, ModelMap modelMap) {

        SysUser sysUser = sysUserService.findById(userId);
        if(sysUser.getType() == SystemConstants.MEMBER_TYPE_TEACHER)  // 这个地方的判断可能有问题，应该用党员信息里的类别++++++++++++
            return "party/member/teacher_view";
        return "party/member/student_view";
    }
}
