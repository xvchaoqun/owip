package controller.mobile;

import bean.ApproverTypeBean;
import controller.BaseController;
import domain.ApproverType;
import domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.CurrentUser;
import shiro.ShiroUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public String home_page(@CurrentUser SysUser loginUser,ModelMap modelMap) {

		Integer userId = loginUser.getId();

		//==============================================
		Map<Integer, List<Integer>> approverTypeUnitIdListMap = new HashMap<>();
		//Map<Integer, List<Integer>> approverTypePostIdListMap = new HashMap<>();

		ApproverType mainPostApproverType = approverTypeService.getMainPostApproverType();
		ApproverType leaderApproverType = approverTypeService.getLeaderApproverType();

		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();

		if (approverTypeBean.getMainPostUnitId() != null) {
			List unitIds = new ArrayList();
			unitIds.add(approverTypeBean.getMainPostUnitId());
			approverTypeUnitIdListMap.put(mainPostApproverType.getId(), unitIds);
		}
		if (approverTypeBean.getLeaderUnitIds().size() > 0) {
			approverTypeUnitIdListMap.put(leaderApproverType.getId(), approverTypeBean.getLeaderUnitIds());
		}

		Map<Integer, List<Integer>> approverTypePostIdListMap = approverTypeBean.getApproverTypePostIdListMap();

		if (approverTypeUnitIdListMap.size() == 0) approverTypeUnitIdListMap = null;
		if (approverTypePostIdListMap.size() == 0) approverTypePostIdListMap = null;
		//==============================================

		int notApprovalCount = selectMapper.countNotApproval(null, approverTypeUnitIdListMap, approverTypePostIdListMap);
		int hasApprovalCount = selectMapper.countHasApproval(null, approverTypeUnitIdListMap, approverTypePostIdListMap, userId);

		modelMap.put("notApprovalCount", notApprovalCount);
		modelMap.put("hasApprovalCount", hasApprovalCount);
		return "m/index_page";
	}
}
