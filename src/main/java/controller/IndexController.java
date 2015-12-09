package controller;

import domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import sys.constants.SystemConstants;

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
	public String home_page(ModelMap modelMap) {

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

			modelMap.put("memberTeacher", memberTeacherService.get(loginUser.getId()));
			return "teacher_base";
		}
		modelMap.put("memberStudent", memberStudentService.get(loginUser.getId()));
		return "student_base";
	}

	@RequestMapping("/menu")
	public String menu(ModelMap modelMap) {

		modelMap.put("menus", sysResourceService.getSortedSysResources().values());

		return "menu";
	}
}
