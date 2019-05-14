package controller.global;

import controller.BaseController;
import domain.member.MemberView;
import domain.sys.HtmlFragment;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.member.MemberRegService;
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

	@Autowired(required = false)
	private MemberRegService memberRegService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping("/monitor")
	@ResponseBody
	public void monitor(HttpServletRequest request, String type) {

		String userAgent = RequestUtils.getUserAgent(request);
		String ip = IpUtils.getRealIp(request);

		logger.warn(String.format("monitor type=%s, userAgent=%s, ip=%s, username=%s, cas=%s", type,
				userAgent, ip, ShiroHelper.getCurrentUsername(), CasUtils.getName(request)));
	}

	@RequestMapping("/faq")
	public String faq() {
		return "page";
	}

	@RequestMapping("/faq_page")
	public String faq_page() {

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
			modelMap.put("memberReg", memberRegService.findByUserId(loginUser.getId()));
			return "member/user/memberReg/memberReg";
		}

		return "index_page";
	}

	@RequiresPermissions("index:home")
	@RequestMapping("/user_base")
	public String user_base(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

		Integer userId = loginUser.getId();
		modelMap.put("sysUser", loginUser);

		MemberView memberView = iMemberMapper.getMemberView(userId);
		modelMap.put("memberView", memberView);

		if(loginUser.getType()== SystemConstants.USER_TYPE_JZG) {

			modelMap.put("teacher", teacherInfoService.get(userId));
			return "teacher_base";
		}else {

			modelMap.put("student", studentService.get(userId));
			return "student_base";
		}
	}
}
