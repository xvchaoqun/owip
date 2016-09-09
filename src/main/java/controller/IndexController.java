package controller;

import domain.sys.HtmlFragment;
import domain.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
	@RequestMapping("/help")
	public String help(ModelMap modelMap) {

		List<HtmlFragment> hfDocs = htmlFragmentService.twoLevelTree(SystemConstants.HTML_FRAGMENT_HELP_DOC);
		modelMap.put("hfDocs", hfDocs);

		return "help";
	}

	/*@RequiresRoles(value = {"admin", "odAdmin", "partyAdmin", "branchAdmin"}, logical = Logical.OR)
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
	public String home_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

		if(SecurityUtils.getSubject().hasRole("reg")){
			modelMap.put("sysUserReg", sysUserRegService.findByUserId(loginUser.getId()));
			return "user/sysUserReg/sysUserReg";
		}

		return "index_page";
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
