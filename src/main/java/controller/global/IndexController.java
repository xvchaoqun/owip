package controller.global;

import controller.BaseController;
import domain.member.MemberStudent;
import domain.member.MemberTeacher;
import domain.sys.HtmlFragment;
import domain.sys.SysUserView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import shiro.CurrentUser;
import shiro.ShiroUser;
import sys.CasUtils;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping("/monitor")
	public String monitor(HttpServletRequest request, String type) {

		String userAgent = RequestUtils.getUserAgent(request);
		String ip = IpUtils.getRealIp(request);

		ShiroUser shiroUser =(ShiroUser)SecurityUtils.getSubject().getPrincipal();
		logger.warn(String.format("monitor type=%s, userAgent=%s, ip=%s, username=%s, cas=%s", type,
				userAgent, ip, (shiroUser!=null)?shiroUser.getUsername():null, CasUtils.getUsername(request)));
		return "monitor";
	}

	@RequestMapping("/faq")
	public String faq() {

		return "faq";
	}

	@RequiresRoles(value = {SystemConstants.ROLE_ADMIN,
			SystemConstants.ROLE_ODADMIN,
			SystemConstants.ROLE_PARTYADMIN,
			SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
	@RequestMapping("/help")
	public String help(ModelMap modelMap) {

		List<HtmlFragment> hfDocs = htmlFragmentService.twoLevelTree(SystemConstants.HTML_FRAGMENT_HELP_DOC);
		modelMap.put("hfDocs", hfDocs);

		return "help";
	}

	@RequiresPermissions("index:home")
	@RequestMapping("/")
	public String home_page() {

		return "index";
	}
	@RequiresPermissions("index:home")
	@RequestMapping("/index")
	public String index(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

		if(ShiroHelper.hasRole(SystemConstants.ROLE_REG)){
			modelMap.put("sysUserReg", sysUserRegService.findByUserId(loginUser.getId()));
			return "user/member/sysUserReg/sysUserReg";
		}

		return "index_page";
	}

	@RequiresPermissions("index:home")
	@RequestMapping("/user_base")
	public String user_base(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

		Integer userId = loginUser.getId();
		modelMap.put("sysUser", loginUser);

		if(loginUser.getType()== SystemConstants.USER_TYPE_JZG) {
			MemberTeacher memberTeacher = memberTeacherService.get(userId);
			modelMap.put("memberTeacher", memberTeacher);
			modelMap.put("teacher", teacherService.get(userId));
			return "teacher_base";
		}else {
			MemberStudent memberStudent = memberStudentService.get(userId);
			modelMap.put("memberStudent", memberStudent);
			modelMap.put("student", studentService.get(userId));
			return "student_base";
		}
	}
}
