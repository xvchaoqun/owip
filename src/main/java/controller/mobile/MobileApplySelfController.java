package controller.mobile;

import bean.ApplySelfSearchBean;
import bean.ApprovalResult;
import bean.ApproverTypeBean;
import bean.m.Breadcrumb;
import controller.BaseController;
import domain.*;
import interceptor.OrderParam;
import interceptor.SortParam;
import mixin.ApplySelfMixin;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.CurrentUser;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tool.paging.CommonList;
import sys.utils.DateUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m")
public class MobileApplySelfController extends BaseController {

	@RequiresPermissions("applySelf:list")
	@RequiresRoles("cadreAdmin")
	@RequestMapping("/applySelf")
	public String applySelf(ModelMap modelMap) {

		List breadcumbs = new ArrayList();
		/*breadcumbs.add(new Breadcrumb("/m/index", "首页"));*/
		breadcumbs.add(new Breadcrumb("/m/applySelf", "因私出国境审批"));
		breadcumbs.add(new Breadcrumb("审批管理"));
		modelMap.put("breadcumbs", breadcumbs);

		return "m/index";
	}

	@RequiresPermissions("applySelf:list")
	@RequiresRoles("cadreAdmin")
	@RequestMapping("/applySelf_page")
	public String applySelf_page(HttpServletResponse response,
							   @SortParam(required = false, defaultValue = "create_time", tableName = "abroad_apply_self") String sort,
							   @OrderParam(required = false, defaultValue = "desc") String order,
							   Integer cadreId,
							   String _applyDate,
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
			Cadre cadre = cadreService.findAll().get(cadreId);
			modelMap.put("cadre", cadre);
			SysUser sysUser = sysUserService.findById(cadre.getUserId());
			modelMap.put("sysUser", sysUser);
		}

		return "m/applySelf/applySelf_page";
	}


	@RequiresRoles("cadre")
	@RequiresPermissions("applySelf:approvalList")
	@RequestMapping("/applySelfList")
	public String applySelfList(ModelMap modelMap) {

		List breadcumbs = new ArrayList();
		/*breadcumbs.add(new Breadcrumb("/m/index", "首页"));*/
		breadcumbs.add(new Breadcrumb("/m/applySelfList", "因私出国境审批"));
		breadcumbs.add(new Breadcrumb("审批管理"));
		modelMap.put("breadcumbs", breadcumbs);

		return "m/index";
	}

	@RequiresRoles("cadre")
	@RequiresPermissions("applySelf:approvalList")
	@RequestMapping("/applySelfList_page")
	public String applySelfList_page(@CurrentUser SysUser loginUser, HttpServletResponse response,
									 Integer cadreId,
									 String _applyDate,
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

		return "m/applySelf/applySelfList_page";
	}

	@RequiresPermissions("applySelf:view")
	@RequestMapping("/applySelf_view")
	public String applySelf_view(Integer id, ModelMap modelMap) {

		ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
		Integer cadreId = applySelf.getCadreId();

		// 判断一下查看权限++++++++++++++++++++???
		if(!SecurityUtils.getSubject().hasRole("cadreAdmin")) {
			Cadre cadre = cadreService.findAll().get(cadreId);
			if(cadre.getId().intValue()!=cadreId) {
				ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
				ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
				if (approverTypeBean==null || !approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
					throw new RuntimeException("您没有权限");
			}
		}

		Cadre cadre = cadreService.findAll().get(cadreId);
		SysUser sysUser = sysUserService.findById(cadre.getUserId());

		modelMap.put("sysUser", sysUser);
		modelMap.put("cadre", cadre);
		modelMap.put("applySelf", applySelf);

		List<ApplySelfFile> files = applySelfService.getFiles(applySelf.getId());
		modelMap.put("files", files);

		Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
		modelMap.put("approvalResultMap", approvalResultMap);


		// 本年度的申请记录（只显示审批通过的申请）
		int year = DateUtils.getCurrentYear();
		ApplySelfExample example = new ApplySelfExample();
		ApplySelfExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
		criteria.andIsAgreedEqualTo(true);
		criteria.andApplyDateBetween(DateUtils.parseDate(year + "-01-01 00:00:00", DateUtils.YYYY_MM_DD),
				DateUtils.parseDate(year + "-12-30 23:59:59", DateUtils.YYYY_MM_DD));
		example.setOrderByClause("create_time desc");
		List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
		modelMap.put("applySelfs", applySelfs);

		return "m/applySelf/applySelf_view";
	}

	@RequiresPermissions("applySelf:approval")
	@RequestMapping("/applySelf_approval")
	public String applySelf_approval(Integer id, ModelMap modelMap) {

		ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
		modelMap.put("applySelf", applySelf);

		return "m/applySelf/applySelf_approval";
	}

	@RequiresPermissions("applySelf:approval")
	@RequestMapping("/applySelf_detail")
	public String applySelf_detail(Integer id, ModelMap modelMap, HttpServletRequest request) {

		request.setAttribute("isView", true);

		ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
		modelMap.put("applySelf", applySelf);

		return "m/applySelf/applySelf_detail";
	}
}
