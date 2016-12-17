package controller.mobile;

import controller.BaseController;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/m")
public class MobileIndexController extends BaseController {

	@RequestMapping("/index")
	public String _index(ModelMap modelMap) {

		return "m/index";
	}

	@RequestMapping("/")
	public String index() {

		return "redirect:/m/index";
	}

	@RequestMapping("/index_page")
	public String home_page(@CurrentUser SysUserView loginUser,HttpServletResponse response, ModelMap modelMap) {

		Subject subject = SecurityUtils.getSubject();
		int notApprovalCount = 0;
		int hasApprovalCount = 0;
		if(!subject.hasRole(SystemConstants.ROLE_CADREADMIN)) { // 干部 登录

			Integer userId = loginUser.getId();
			{
				Map map = applySelfService.findApplySelfList(userId, null, null, null, 0, null, null);
				CommonList commonList = (CommonList) map.get("commonList");
				notApprovalCount = commonList.recNum;
			}
			{
				Map map = applySelfService.findApplySelfList(userId, null, null, null, 1, null, null);
				CommonList commonList = (CommonList) map.get("commonList");
				hasApprovalCount = commonList.recNum;
			}
		}else{ // 干部管理员 登录
			{
				Map map = applySelfService.findApplySelfList(response, null, null,
						null, null, 0, null, null, null, null, 0);
				CommonList commonList = (CommonList) map.get("commonList");
				notApprovalCount = commonList.recNum;
			}
			{
				Map map = applySelfService.findApplySelfList(response, null, null,
						null, null, 1, null, null, null, null, 0);
				CommonList commonList = (CommonList) map.get("commonList");
				hasApprovalCount = commonList.recNum;
			}
		}
		modelMap.put("notApprovalCount", notApprovalCount);
		modelMap.put("hasApprovalCount", hasApprovalCount);

		return "m/index_page";
	}
}
