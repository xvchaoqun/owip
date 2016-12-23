package controller;

import domain.member.MemberStudent;
import domain.member.MemberTeacher;
import domain.sys.HtmlFragment;
import domain.sys.SysResource;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.helper.ShiroHelper;
import shiro.CurrentUser;
import shiro.ShiroUser;
import sys.CasUtils;
import sys.constants.SystemConstants;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	/*@RequiresRoles(value = {SystemConstants.ROLE_ADMIN,SystemConstants.ROLE_ODADMIN, SystemConstants.ROLE_PARTYADMIN, SystemConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
	@RequestMapping("/help_page")
	public String help_page() {

		return "help";
	}*/

	@RequiresPermissions("index:home")
	@RequestMapping("/index")
	public String _index() {

		return "index";
	}
	@RequiresPermissions("index:home")
	@RequestMapping("/")
	public String index() {

		return "redirect:/index";
	}
	@RequiresPermissions("index:home")
	@RequestMapping("/index_page")
	public String home_page(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

		if(ShiroHelper.hasRole(SystemConstants.ROLE_REG)){
			modelMap.put("sysUserReg", sysUserRegService.findByUserId(loginUser.getId()));
			return "user/party/sysUserReg/sysUserReg";
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

	@RequestMapping("/menu")
	public String menu(ModelMap modelMap) {

		ShiroUser shiroUser =(ShiroUser)SecurityUtils.getSubject().getPrincipal();
		List<SysResource> userMenus = sysResourceService.makeMenus(shiroUser.getPermissions());

		modelMap.put("menus", userMenus);
		return "menu";
	}

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping("/menu_preview")
	public String menu_preview(Integer[] resIds, ModelMap modelMap) {

		Set<String> permissions = new HashSet<String>();
		Map<Integer, SysResource> sysResources = sysResourceService.getSortedSysResources();
		for (Integer resId : resIds) {
			SysResource sysResource = sysResources.get(resId);
			if(sysResource!=null && StringUtils.isNotBlank(sysResource.getPermission())){
				permissions.add(sysResource.getPermission().trim());
			}
		}
		List<SysResource> userMenus = sysResourceService.makeMenus(permissions);
		modelMap.put("menus", userMenus);
		return "menu";
	}

	@RequiresRoles(SystemConstants.ROLE_ADMIN)
	@RequestMapping("/menu_preview_byRoleId")
	public String menu_preview_byRoleId(int roleId, ModelMap modelMap) {

		Set<String> rolePermissions = sysRoleService.getRolePermissions(roleId);
		List<SysResource> userMenus = sysResourceService.makeMenus(rolePermissions);

		modelMap.put("menus", userMenus);
		return "menu";
	}
}
