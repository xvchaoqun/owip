package controller.cla.mobile;

import controller.cla.ClaBaseController;
import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cla.ClaApply;
import domain.cla.ClaApplyFile;
import domain.sys.SysUserView;
import interceptor.OrderParam;
import interceptor.SortParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import persistence.cla.common.ClaApprovalResult;
import persistence.cla.common.ClaApproverTypeBean;
import shiro.ShiroHelper;
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
@RequestMapping("/m/cla")
public class MobileClaApplyController extends ClaBaseController {

	@RequiresPermissions("claApply:list")
	//@RequiresRoles(RoleConstants.ROLE_CADREADMIN)
	@RequestMapping("/claApply")
	public String claApply(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("claApply:list")
	//@RequiresRoles(RoleConstants.ROLE_CADREADMIN)
	@RequestMapping("/claApply_page")
	public String claApply_page(HttpServletResponse response,
							   @SortParam(required = false, defaultValue = "create_time", tableName = "cla_apply") String sort,
							   @OrderParam(required = false, defaultValue = "desc") String order,
							   Integer cadreId,
								 @RequestDateRange DateRange _applyDate,
							   Byte type, // 出行时间范围
							   // 流程状态，（查询者所属审批人身份的审批状态，1：已完成审批(通过或不通过)或0：未审批）
							   @RequestParam(required = false, defaultValue = "0") int status,
							   @RequestParam(required = false, defaultValue = "0") int export,
							   Integer pageNo, HttpServletRequest request, ModelMap modelMap) throws IOException {

		modelMap.put("status", status);

		Map map = claApplyService.findApplyList(response, cadreId, _applyDate,
				type, null, null, status, sort, order, pageNo, springProps.mPageSize, export);
		if(map == null) return null; // 导出

		//request.setAttribute("isView", false);

		modelMap.put("claApplys", map.get("applys"));

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

		return "cla/mobile/claApply_page";
	}


	//@RequiresRoles(RoleConstants.ROLE_CADRE)
	@RequiresPermissions("claApply:approvalList")
	@RequestMapping("/claApplyList")
	public String claApplyList(ModelMap modelMap) {

		return "mobile/index";
	}

	//@RequiresRoles(RoleConstants.ROLE_CADRE)
	@RequiresPermissions("claApply:approvalList")
	@RequestMapping("/claApplyList_page")
	public String claApplyList_page(@CurrentUser SysUserView loginUser, HttpServletResponse response,
									 Integer cadreId,
									 @RequestDateRange DateRange _applyDate,
									 Byte type, // 出行时间范围
									 // 流程状态，（查询者所属审批人身份的审批状态，1：已审批(通过或不通过)或0：未审批）
									 @RequestParam(required = false, defaultValue = "0") int status,
									 Integer pageNo, ModelMap modelMap) {

		modelMap.put("status", status);

		Map map = claApplyService.findApplyList(loginUser.getId(), cadreId, _applyDate, type, status, pageNo, null);
		modelMap.put("claApplys", map.get("applys"));

		String searchStr = "&status=" + status;
		CommonList commonList = (CommonList) map.get("commonList");
		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		return "cla/mobile/claApplyList_page";
	}

	@RequiresPermissions("claApply:view")
	@RequestMapping("/claApply_view")
	public String claApply_view(Integer id, ModelMap modelMap) {

		ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
		Integer cadreId = claApply.getCadreId();

		// 判断一下查看权限++++++++++++++++++++???
		if(ShiroHelper.lackRole(RoleConstants.ROLE_CADREADMIN)) {
			CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
			if(cadre.getId().intValue()!=cadreId) {
				//ShiroUser shiroUser = ShiroHelper.getShiroUser();
				ClaApproverTypeBean approverTypeBean = claApplyService.getApproverTypeBean(ShiroHelper.getCurrentUserId());
				if (approverTypeBean==null || !approverTypeBean.getApprovalCadreIdSet().contains(claApply.getCadreId()))
					throw new OpException("您没有权限");
			}
		}

		CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
		SysUserView sysUser = sysUserService.findById(cadre.getUserId());

		modelMap.put("sysUser", sysUser);
		modelMap.put("cadre", cadre);
		modelMap.put("claApply", claApply);

		List<ClaApplyFile> files = claApplyService.getFiles(claApply.getId());
		modelMap.put("files", files);

		Map<Integer, ClaApprovalResult> approvalResultMap = claApplyService.getApprovalResultMap(id);
		modelMap.put("approvalResultMap", approvalResultMap);

		int currentYear = DateUtils.getYear(claApply.getApplyDate());
		modelMap.put("claApplys", claApplyService.getAnnualApplyList(cadreId, currentYear));

		return "cla/mobile/claApply_view";
	}

	@RequiresPermissions("claApply:approval")
	@RequestMapping("/claApply_approval")
	public String claApply_approval(Integer id, ModelMap modelMap) {

		ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
		modelMap.put("claApply", claApply);

		return "cla/mobile/claApply_approval";
	}

	@RequiresPermissions("claApply:approval")
	@RequestMapping("/claApply_detail")
	public String claApply_detail(Integer id, ModelMap modelMap, HttpServletRequest request) {

		request.setAttribute("isView", true);

		ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
		modelMap.put("claApply", claApply);

		return "cla/mobile/claApply_detail";
	}
}
