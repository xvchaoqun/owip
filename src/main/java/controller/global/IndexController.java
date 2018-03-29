package controller.global;

import controller.BaseController;
import domain.member.MemberStudent;
import domain.member.MemberTeacher;
import domain.sys.HtmlFragment;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.CasUtils;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping("/monitor")
	@ResponseBody
	public void monitor(HttpServletRequest request, String type) {

		String userAgent = RequestUtils.getUserAgent(request);
		String ip = IpUtils.getRealIp(request);

		logger.warn(String.format("monitor type=%s, userAgent=%s, ip=%s, username=%s, cas=%s", type,
				userAgent, ip, ShiroHelper.getCurrentUsername(), CasUtils.getUsername(request)));
	}

	@RequestMapping("/faq")
	public String faq() {

		return "faq";
	}

	@RequiresRoles(value = {RoleConstants.ROLE_ADMIN,
			RoleConstants.ROLE_ODADMIN,
			RoleConstants.ROLE_PARTYADMIN,
			RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
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

		if(ShiroHelper.hasRole(RoleConstants.ROLE_REG)){
			modelMap.put("sysUserReg", sysUserRegService.findByUserId(loginUser.getId()));
			return "member/user/sysUserReg/sysUserReg";
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
