package controller.abroad.mobile;

import controller.abroad.AbroadBaseController;
import controller.global.OpException;
import domain.abroad.ApplySelf;
import domain.abroad.ApplySelfFile;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.abroad.common.ApprovalResult;
import persistence.abroad.common.ApproverTypeBean;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.spring.DateRange;
import sys.spring.RequestDateRange;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/abroad")
public class MobileApplySelfController extends AbroadBaseController {

	@RequiresPermissions("applySelf:list")
	//@RequiresRoles(RoleConstants.ROLE_CADREADMIN)
	@RequestMapping("/applySelf")
	public String applySelf(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("applySelf:list")
	//@RequiresRoles(RoleConstants.ROLE_CADREADMIN)
	@RequestMapping("/applySelf_page")
	public String applySelf_page(HttpServletResponse response,
							   @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_apply_self") String sort,
							   @OrderParam(required = false, defaultValue = "desc") String order,
							   Integer cadreId,
								 @RequestDateRange DateRange _applyDate,
							   Byte type, // 出行时间范围
							   // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(通过或不通过)或0：未审批）
							   @RequestParam(required = false, defaultValue = "0") int status,
							   @RequestParam(required = false, defaultValue = "0") int export,
							   Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

		modelMap.put("status", status);

		Map map = applySelfService.findApplySelfList(response, cadreId, _applyDate,
				type, null, status, sort, order, pageNo, springProps.mPageSize, export);
		if(map == null) return null; // 导出

		//request.setAttribute("isView", false);

		modelMap.put("applySelfs", map.get("applySelfs"));

		String searchStr = "&status=" + status;
		CommonList commonList = (CommonList) map.get("commonList");
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		if (cadreId != null) {
			CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
			modelMap.put("cadre", cadre);
			SysUserView sysUser = sysUserService.findById(cadre.getUserId());
			modelMap.put("sysUser", sysUser);
		}

		return "abroad/mobile/applySelf_page";
	}


	//@RequiresRoles(RoleConstants.ROLE_CADRE)
	@RequiresPermissions("applySelf:approvalList")
	@RequestMapping("/applySelfList")
	public String applySelfList(ModelMap modelMap) {

		return "mobile/index";
	}

	//@RequiresRoles(RoleConstants.ROLE_CADRE)
	@RequiresPermissions("applySelf:approvalList")
	@RequestMapping("/applySelfList_page")
	public String applySelfList_page(@CurrentUser SysUserView loginUser, HttpServletResponse response,
									 Integer cadreId,
									 @RequestDateRange DateRange _applyDate,
									 Byte type, // 出行时间范围
									 // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
									 @RequestParam(required = false, defaultValue = "0") int status,
									 Integer pageNo, ModelMap modelMap) {

		modelMap.put("status", status);

		Map map = applySelfService.findApplySelfList(loginUser.getId(), cadreId, _applyDate, type, status, pageNo, null);
		modelMap.put("applySelfs", map.get("applySelfs"));

		String searchStr = "&status=" + status;
		CommonList commonList = (CommonList) map.get("commonList");
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		return "abroad/mobile/applySelfList_page";
	}

	@RequiresPermissions("applySelf:view")
	@RequestMapping("/applySelf_view")
	public String applySelf_view(Integer id, ModelMap modelMap) {

		ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
		Integer cadreId = applySelf.getCadreId();

		// 判断一下查看权限++++++++++++++++++++???
		if(ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)) {
			CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
			if(cadre.getId().intValue()!=cadreId) {
				ShiroUser shiroUser = ShiroHelper.getShiroUser();
				ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
				if (approverTypeBean==null || !approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
					throw new OpException("您没有权限");
			}
		}

		CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
		SysUserView sysUser = sysUserService.findById(cadre.getUserId());

		modelMap.put("sysUser", sysUser);
		modelMap.put("cadre", cadre);
		modelMap.put("applySelf", applySelf);

		List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
		modelMap.put("files", files);

		Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
		modelMap.put("approvalResultMap", approvalResultMap);

		int currentYear = DateUtils.getYear(applySelf.getApplyDate());
		modelMap.put("applySelfs", applySelfService.getAnnualApplyList(cadreId, currentYear));

		return "abroad/mobile/applySelf_view";
	}

	@RequiresPermissions("applySelf:approval")
	@RequestMapping("/applySelf_approval")
	public String applySelf_approval(Integer id, ModelMap modelMap) {

		ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
		modelMap.put("applySelf", applySelf);

		return "abroad/mobile/applySelf_approval";
	}

	@RequiresPermissions("applySelf:approval")
	@RequestMapping("/applySelf_detail")
	public String applySelf_detail(Integer id, ModelMap modelMap, HttpServletRequest request) {

		request.setAttribute("isView", true);

		ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
		modelMap.put("applySelf", applySelf);

		return "abroad/mobile/applySelf_detail";
	}
}
