package controller.mobile;

import controller.BaseController;
import domain.sys.SysUserView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.abroad.ApplySelfService;
import service.abroad.ApproverService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/m")
public class MobileIndexController extends BaseController {

	@RequestMapping("/index")
	public String _index(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequestMapping("/")
	public String index() {

		return "redirect:/m/index";
	}

	@RequestMapping("/index_page")
	public String index_page(@CurrentUser SysUserView loginUser,HttpServletResponse response, ModelMap modelMap) {

		Integer userId = loginUser.getId();
		ApplySelfService applySelfService = CmTag.getBean(ApplySelfService.class);
		ApproverService approverService = CmTag.getBean(ApproverService.class);
		if(applySelfService!=null) {
			int notApprovalCount = 0;
			int hasApprovalCount = 0;
			if (ShiroHelper.hasRole(RoleConstants.ROLE_CADREADMIN)) { // 干部管理员登录
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
			} else if (ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)
					&& approverService!=null && approverService.hasApproveAuth(userId)) { // 具有因私审批权限的干部登录
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
			}

			modelMap.put("notApprovalCount", notApprovalCount);
			modelMap.put("hasApprovalCount", hasApprovalCount);
		}

		return "mobile/index_page";
	}
}
