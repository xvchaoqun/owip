package controller.mobile;

import controller.BaseController;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.cadre.common.CadreSearchBean;
import persistence.cadre.common.StatCadreBean;
import service.abroad.ApplySelfService;
import service.abroad.ApproverService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
			if (ShiroHelper.isPermitted(RoleConstants.PERMISSION_ABROADADMIN)) { // 干部管理员登录
				{
					Map map = applySelfService.findApplySelfList(response, null, null,
							null, null, 0, null, null, null, null, 0, null);
					CommonList commonList = (CommonList) map.get("commonList");
					notApprovalCount = commonList.recNum;
				}
				{
					Map map = applySelfService.findApplySelfList(response, null, null,
							null, null, 1, null, null, null, null, 0, null);
					CommonList commonList = (CommonList) map.get("commonList");
					hasApprovalCount = commonList.recNum;
				}
			} else if (approverService!=null && approverService.hasApproveAuth(userId)) { // 具有因私审批权限的干部登录
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
	// 干部数量统计
	@RequiresPermissions("stat:cadre")
	@RequestMapping("/stat_cadre_count")
	public String stat_cadre_count(ModelMap modelMap) {

		CadreSearchBean searchBean = new CadreSearchBean();
		searchBean.setCadreType(CadreConstants.CADRE_CATEGORY_CJ);

		int cadreCount  = statCadreMapper.countCadre(searchBean);
		int adminLevelCount=0;
		// 行政级别
		Map<String,Integer> statCadreCountMap = new HashMap<>();
		List<StatCadreBean> adminLevelList = statCadreMapper.cadre_stat_adminLevel(searchBean);
		for(StatCadreBean scb:adminLevelList){
			statCadreCountMap.put(CmTag.getMetaTypeByCode(scb.adminLevelCode).getName(),scb.num);
			adminLevelCount += scb.num;
		}
		if(cadreCount != adminLevelCount){
			statCadreCountMap.put("其他",cadreCount-adminLevelCount);
		}
		modelMap.put("statCadreCountMap", statCadreCountMap);

		return "mobile/stat_cadre_count";
	}

	// 干部年龄统计
	@RequiresPermissions("stat:cadre")
	@RequestMapping("/stat_cadreAge_count")
	public String stat_cadreAge_count(ModelMap modelMap) {

		CadreSearchBean searchBean = new CadreSearchBean();
		searchBean.setCadreType(CadreConstants.CADRE_CATEGORY_CJ);
		int cadreCount  = statCadreMapper.countCadre(searchBean);
		int ageCount = 0;

		Map cadreAgeMap = new LinkedHashMap();

		StatCadreBean totalBean = statCadreMapper.cadre_stat_age(searchBean);
		cadreAgeMap.put("30岁及以下",totalBean == null ?0:totalBean.getNum1());
		cadreAgeMap.put("31-35岁",totalBean == null ?0:totalBean.getNum2());
		cadreAgeMap.put("36-40岁",totalBean == null ?0:totalBean.getNum3());
		cadreAgeMap.put("41-45岁",totalBean == null ?0:totalBean.getNum4());
		cadreAgeMap.put("46-50岁",totalBean == null ?0:totalBean.getNum5());
		cadreAgeMap.put("51-55岁",totalBean == null ?0:totalBean.getNum6());
		cadreAgeMap.put("55岁以上",totalBean == null ?0:totalBean.getNum7());
		ageCount=totalBean.getNum1()+totalBean.getNum2()+totalBean.getNum3()+totalBean.getNum4()
				+totalBean.getNum5()+totalBean.getNum6()+totalBean.getNum7();
		if(totalBean != null && cadreCount != ageCount){
			cadreAgeMap.put("无数据",cadreCount-ageCount);
		}

		modelMap.put("cadreAgeMap", cadreAgeMap);

		return "mobile/stat_cadre_age";
	}

	// 党员数量统计
	@RequiresPermissions("stat:ow")
	@RequestMapping("/stat_member_count")
	public String stat_member_count(ModelMap modelMap) {

		Map<Byte, Integer>  statGrowMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_GROW, null, null);
		Map<Byte, Integer>  statPositiveMap = statService.typeMap(MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE, null, null);

		modelMap.put("statPoliticalStatusMap", statService.politicalStatusMap(null, null));
		modelMap.put("statGrowMap", statGrowMap);
		modelMap.put("statPositiveMap", statPositiveMap);
		return "mobile/stat_member_count";
	}
}
