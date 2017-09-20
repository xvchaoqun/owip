package controller.mobile.abroad;

import bean.m.Breadcrumb;
import controller.AbroadBaseController;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/m/abroad")
public class MobileCadreController extends AbroadBaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_CADREADMIN}, logical = Logical.OR)
	@RequestMapping("/cadre_base")
	public String cadre_base(ModelMap modelMap) {

		List breadcumbs = new ArrayList();
		breadcumbs.add(new Breadcrumb("个人资料"));
		modelMap.put("breadcumbs", breadcumbs);

		return "mobile/abroad/index";
	}

	@RequiresRoles(value = {SystemConstants.ROLE_ADMIN, SystemConstants.ROLE_CADREADMIN}, logical = Logical.OR)
	@RequestMapping("/cadre_base_page")
	public String cadre_base_page(@CurrentUser SysUserView loginUser,  ModelMap modelMap) {

		Integer userId = loginUser.getId();
		CadreView cadre = cadreService.dbFindByUserId(userId);
		modelMap.put("cadre", cadre);

		/*Map<Integer, Branch> branchMap = branchService.findAll();
		Map<Integer, Party> partyMap = partyService.findAll();
		modelMap.put("branchMap", branchMap);
		modelMap.put("partyMap", partyMap);
		modelMap.put("member", memberService.get(userId));*/


		/*// 主职
		modelMap.put("cadreMainWork", cadreMainWorkService.getByCadreId(userId));

		// 现任职务
		modelMap.put("cadrePost", cadrePostService.getPresentByCadreId(userId));

		// 兼职单位
		List<CadreSubWork> cadreSubWorks = cadreSubWorkService.findByCadreId(userId);
		if(cadreSubWorks.size()>=1){
			modelMap.put("cadreSubWork1", cadreSubWorks.get(0));
		}
		if(cadreSubWorks.size()>=2){
			modelMap.put("cadreSubWork2", cadreSubWorks.get(1));
		}

		modelMap.put("eduTypeMap", metaTypeService.metaTypes("mc_edu"));
		modelMap.put("learnStyleMap", metaTypeService.metaTypes("mc_learn_style"));
		modelMap.put("schoolTypeMap", metaTypeService.metaTypes("mc_school"));
		// 最高学历
		modelMap.put("highEdu", cadreEduService.getHighEdu(userId));
		//最高学位
		modelMap.put("highDegree", cadreEduService.getHighDegree(userId));*/

		return "mobile/abroad/cadre/cadre_base_page";
	}
}
