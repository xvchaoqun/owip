package controller;

import domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import sys.constants.SystemConstants;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

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
	public String home_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

		if(SecurityUtils.getSubject().hasRole("reg")){
			modelMap.put("sysUserReg", sysUserRegService.findByUserId(loginUser.getId()));
			return "user/sysUserReg/sysUserReg";
		}

		return "index_page";
	}

	@RequiresPermissions("index:home")
	@RequestMapping("/static_page")
	public String static_page(ModelMap modelMap) {

		return "static_page";
	}

	@RequiresPermissions("index:home")
	@RequestMapping("/user_base")
	public String user_base(@CurrentUser SysUser loginUser, ModelMap modelMap) {

		modelMap.put("sysUser", loginUser);

		if(loginUser.getType()== SystemConstants.USER_TYPE_JZG) {

			modelMap.put("teacher", teacherService.get(loginUser.getId()));
			return "teacher_base";
		}
		modelMap.put("student", studentService.get(loginUser.getId()));
		return "student_base";
	}

	@RequestMapping("/menu")
	public String menu(ModelMap modelMap) {

		modelMap.put("menus", sysResourceService.getSortedSysResources().values());

		return "menu";
	}
}
