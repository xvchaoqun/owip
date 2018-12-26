package controller.member;

import controller.global.OpException;
import domain.member.MemberReg;
import domain.member.MemberRegExample;
import domain.party.Party;
import domain.sys.SysUserView;
import mixin.MixinUtils;
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.member.ApplyApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.AuthToken;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberRegController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private VerifyAuth<MemberReg> checkVerityAuth2(int id){
        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberReg, memberReg.getPartyId());
    }

    @RequestMapping(value = "/member_reg", method = RequestMethod.POST)
	@ResponseBody
	public Map do_member_reg(String username, String passwd, Byte type,
					  String realname, String idcard, String phone,
					  Integer party, String captcha, HttpServletRequest request) {

		username = StringUtils.trimToNull(username);
		realname = StringUtils.trimToNull(realname);
		idcard = StringUtils.trimToNull(idcard);
		phone = StringUtils.trimToNull(phone);
		captcha = StringUtils.trimToNull(captcha);

		String _captcha = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isBlank(_captcha) || !_captcha.equalsIgnoreCase(captcha)) {
			return failed("验证码错误。");
		}
		if(!FormUtils.usernameFormatRight(username)){
			return failed("用户名由3-10位的字母、下划线和数字组成，且不能以数字或下划线开头。");
		}
		if(!FormUtils.match(PropertiesUtils.getString("passwd.regex"), passwd)){
			return failed("密码由6-16位的字母、下划线和数字组成");
		}
		if(type==null || SystemConstants.USER_TYPE_MAP.get(type)==null){
			return failed("类型有误");
		}
		if(StringUtils.isBlank(realname) || StringUtils.length(realname)<2){
			return failed("请填写真实姓名");
		}
		IdcardValidator idcardValidator = new IdcardValidator();
		if(!idcardValidator.isValidatedAllIdcard(idcard)){
			return failed("身份证号码有误。");
		}
		if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), phone)){
			return failed("手机号码有误");
		}
		if(party==null || partyService.findAll().get(party)==null){
			return failed("请选择分党委。");
		}
		/*try {
			memberRegService.reg(username, passwd, type, realname,
					idcard, phone, party, IpUtils.getRealIp(request));
		}catch (RegException re){
			return failed(re.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("注册失败：" + ex.getMessage());
			return failed("系统错误：" + ex.getMessage());
		}*/
		memberRegService.reg(username, passwd, type, realname,
				idcard, phone, party, IpUtils.getRealIp(request));

		logger.info(String.format("%s 注册成功", username));

		AuthToken token = new AuthToken(username,
				passwd.toCharArray(), false, request.getRemoteHost(), null, null);

		SecurityUtils.getSubject().login(token);

		logger.info(addLog(LogConstants.LOG_USER, "注册后登录成功"));

		return success();
	}
	
    @RequiresPermissions("memberReg:list")
    @RequestMapping("/memberReg")
    public String memberReg(@RequestParam(defaultValue = "1")Integer cls,
                                  Integer userId,
                                  Integer partyId, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (userId!=null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }
        Map<Integer, Party> partyMap = partyService.findAll();
        if (partyId != null) {
            modelMap.put("party", partyMap.get(partyId));
        }
        // 分党委党总支直属党支部待审核总数
        modelMap.put("partyApprovalCount", memberRegService.count(null));

        return "member/memberReg/memberReg_page";
    }

    @RequiresPermissions("memberReg:list")
    @RequestMapping("/memberReg_data")
    public void memberReg_data(@RequestParam(defaultValue = "1")Integer cls, HttpServletResponse response,
                                    Integer userId,
                                    String username,
                                    Integer partyId,
                                    String idcard,
                                     Byte type,
                                    String realname,
                                 Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        MemberRegExample example = new MemberRegExample();
        MemberRegExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if(partyId!=null)
            criteria.andPartyIdEqualTo(partyId);

        criteria.addPermits(loginUserService.adminPartyIdList());

        if (StringUtils.isNotBlank(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if(userId!=null){
            criteria.andUserIdEqualTo(userId);
        }
        if (type!=null) {
            criteria.andTypeEqualTo(type);
        }
        if (StringUtils.isNotBlank(realname)) {
            criteria.andRealnameLike("%" + realname + "%");
        }
        if(StringUtils.isNotBlank(idcard)){
            criteria.andIdcardLike("%" + idcard + "%");
        }

        if(cls==1){
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_APPLY);
        }else if(cls==2){
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_DENY);
        }else {
            criteria.andStatusEqualTo(SystemConstants.USER_REG_STATUS_PASS);
        }

        long count = memberRegMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<MemberReg> memberRegs = memberRegMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));
        CommonList commonList = new CommonList(count, pageNo, pageSize);

        Map resultMap = new HashMap();
        resultMap.put("rows", memberRegs);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        Map<Class<?>, Class<?>> baseMixins = MixinUtils.baseMixins();
        JSONUtils.jsonp(resultMap, baseMixins);
        return;
    }

    @RequiresRoles(value = {RoleConstants.ROLE_ADMIN,RoleConstants.ROLE_ODADMIN, RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequiresPermissions("memberReg:list")
    @RequestMapping("/memberReg_approval")
    public String memberReg_approval(@CurrentUser SysUserView loginUser, Integer id, ModelMap modelMap) {

        MemberReg currentMemberReg = null;
        if (id != null) {
            currentMemberReg = memberRegMapper.selectByPrimaryKey(id);
            if (currentMemberReg.getStatus() != SystemConstants.USER_REG_STATUS_APPLY)
                currentMemberReg = null;
        } else {
            currentMemberReg = memberRegService.next(null);
        }
        if (currentMemberReg == null)
            throw new OpException("当前没有需要审批的记录");

        modelMap.put("memberReg", currentMemberReg);

        // 是否是当前记录的管理员
        modelMap.put("isAdmin", partyMemberService.isPresentAdmin(loginUser.getId(), currentMemberReg.getPartyId()));

        // 读取总数
        modelMap.put("count", memberRegService.count(null));
        // 下一条记录
        modelMap.put("next", memberRegService.next(currentMemberReg));
        // 上一条记录
        modelMap.put("last", memberRegService.last(currentMemberReg));

        return "member/memberReg/memberReg_approval";
    }

    @RequiresPermissions("memberReg:update")
    @RequestMapping("/memberReg_deny")
    public String memberReg_deny(Integer id, ModelMap modelMap) {

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        modelMap.put("memberReg", memberReg);
        Integer userId = memberReg.getUserId();
        modelMap.put("sysUser", sysUserService.findById(userId));

        return "member/memberReg/memberReg_deny";
    }

    @RequiresPermissions("memberReg:update")
    @RequestMapping(value = "/memberReg_deny", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_deny(@CurrentUser SysUserView loginUser, HttpServletRequest request,
                                  Integer id,
                                  String reason) {

        VerifyAuth<MemberReg> verifyAuth = checkVerityAuth2(id);
        MemberReg memberReg = verifyAuth.entity;

        int loginUserId = loginUser.getId();
        int userId = memberReg.getUserId();

        memberRegService.deny(memberReg.getId());
        logger.info(addLog(LogConstants.LOG_PARTY, "拒绝用户注册申请：%s", id));

        ApplyApprovalLogService applyApprovalLogService = CmTag.getBean(ApplyApprovalLogService.class);
        applyApprovalLogService.add(memberReg.getId(),
                memberReg.getPartyId(), null, userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "分党委党总支直属党支部审核" , (byte) 0, reason);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:update")
    @RequestMapping(value = "/memberReg_check", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_check(@CurrentUser SysUserView loginUser,HttpServletRequest request,
                                   Integer id) {

        VerifyAuth<MemberReg> verifyAuth = checkVerityAuth2(id);
        MemberReg memberReg = verifyAuth.entity;

        memberRegService.pass(memberReg.getId());
        logger.info(addLog(LogConstants.LOG_PARTY, "用户注册-分党委审核：%s", id));

        int loginUserId = loginUser.getId();
        int userId = memberReg.getUserId();

        ApplyApprovalLogService applyApprovalLogService = CmTag.getBean(ApplyApprovalLogService.class);
        applyApprovalLogService.add(memberReg.getId(),
                memberReg.getPartyId(), null, userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG, "分党委党总支直属党支部审核", (byte) 1, null);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:edit")
    @RequestMapping(value = "/memberReg_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_au(MemberReg record, HttpServletRequest request) {

        Assert.isTrue(record.getId()!=null, "id is null");

        if (memberRegService.usernameDuplicate(record.getId(), record.getUserId(), record.getUsername())
                || sysUserService.idDuplicate(record.getUserId(), record.getUsername(), record.getCode())) {
            return failed("用户名或学工号已被注册。");
        }
        if(memberRegService.idcardDuplicate(record.getId(), record.getUserId(), record.getIdcard())){
            return failed("身份证已被注册。");
        }

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(record.getId());
        Integer partyId = memberReg.getPartyId();
        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin) throw new UnauthorizedException();
        }

        memberRegService.updateByPrimaryKeySelective(record);
        logger.info(addLog(LogConstants.LOG_ADMIN, "更新申请用户注册：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:edit")
    @RequestMapping("/memberReg_au")
    public String memberReg_au(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
            modelMap.put("memberReg", memberReg);
        }
        return "member/memberReg/memberReg_au";
    }

    @RequiresPermissions("memberReg:changepw")
    @RequestMapping(value = "/memberReg_changepw", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_changepw(Integer id, String password, HttpServletRequest request) {

        if(!FormUtils.match(PropertiesUtils.getString("passwd.regex"), password)){
            return failed("密码由6-16位的字母、下划线和数字组成");
        }

        MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
        Integer partyId = memberReg.getPartyId();
        //===========权限
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleConstants.ROLE_ADMIN)
                && !subject.hasRole(RoleConstants.ROLE_ODADMIN)) {
            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
            if(!isAdmin) throw new UnauthorizedException();
        }

        memberRegService.changepw(id ,password);

        logger.info(addLog(LogConstants.LOG_ADMIN, "更新注册用户%s登录密码", memberReg.getUsername()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:changepw")
    @RequestMapping("/memberReg_changepw")
    public String memberReg_changepw(Integer id, ModelMap modelMap) {

        if (id != null) {
            MemberReg memberReg = memberRegMapper.selectByPrimaryKey(id);
            modelMap.put("memberReg", memberReg);
        }
        return "member/memberReg/memberReg_changepw";
    }

    /*@RequiresPermissions("memberReg:del")
    @RequestMapping(value = "/memberReg_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReg_del(HttpServletRequest request, Integer id) {

        if (id != null) {

            memberRegService.del(id);
            logger.info(addLog(LogConstants.LOG_ADMIN, "删除用户注册：%s", id));
        }
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("memberReg:del")
    @RequestMapping(value = "/memberReg_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map batchDel(HttpServletRequest request, @RequestParam(value = "ids[]") Integer[] ids, ModelMap modelMap) {


        if (null != ids && ids.length>0){
            memberRegService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除用户注册：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }*/
}
