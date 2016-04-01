package controller.mobile;

import bean.ApplySelfSearchBean;
import bean.ApprovalResult;
import bean.ApproverTypeBean;
import bean.m.Breadcrumb;
import controller.BaseController;
import domain.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m")
public class MobileApplySelfController extends BaseController {

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
									 Integer pageSize, Integer pageNo, ModelMap modelMap) {

		modelMap.put("status", status);

		Integer userId = loginUser.getId();
		if (null == pageSize) {
			pageSize = springProps.mPageSize;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

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

		String applyDateStart = null;
		String applyDateEnd = null;
		if (StringUtils.isNotBlank(_applyDate)) {
			applyDateStart = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[0];
			applyDateEnd = _applyDate.split(SystemConstants.DATERANGE_SEPARTOR)[1];
		}
		ApplySelfSearchBean searchBean = new ApplySelfSearchBean(cadreId, type, applyDateStart, applyDateEnd);

		int count = 0;
		if (status == 0)
			count = selectMapper.countNotApproval(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap);
		if (status == 1)
			count = selectMapper.countHasApproval(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap, userId);

		if ((pageNo - 1) * pageSize >= count) {
			pageNo = Math.max(1, pageNo - 1);
		}
		List<ApplySelf> applySelfs = null;
		if (status == 0)
			applySelfs = selectMapper.selectNotApprovalList(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap,
					new RowBounds((pageNo - 1) * pageSize, pageSize));
		if (status == 1)
			applySelfs = selectMapper.selectHasApprovalList(searchBean, approverTypeUnitIdListMap, approverTypePostIdListMap, userId,
					new RowBounds((pageNo - 1) * pageSize, pageSize));

		modelMap.put("applySelfs", applySelfs);
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		String searchStr = "&pageSize=" + pageSize;
		searchStr += "&status=" + status;

		commonList.setSearchStr(searchStr);
		modelMap.put("commonList", commonList);

		Map<Integer, ApproverType> approverTypeMap = approverTypeService.findAll();
		modelMap.put("approverTypeMap", approverTypeMap);

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
				if (!approverTypeBean.getApprovalCadreIdSet().contains(applySelf.getCadreId()))
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
		modelMap.put("approverTypeMap", approverTypeService.findAll());


		// 本年度的申请记录
		int year = DateUtils.getCurrentYear();
		ApplySelfExample example = new ApplySelfExample();
		ApplySelfExample.Criteria criteria = example.createCriteria().andCadreIdEqualTo(cadreId);
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
