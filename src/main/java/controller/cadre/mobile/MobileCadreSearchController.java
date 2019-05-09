package controller.cadre.mobile;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.unit.UnitPostAllocationInfoBean;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/m")
public class MobileCadreSearchController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre")
	public String cadre(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_page")
	public String cadre_page() {

		return "cadre/mobile/cadre_page";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_compare")
	public String cadre_compare(@RequestParam(value = "cadreIds[]", required = false) Integer[] cadreIds, ModelMap modelMap) {

		if(cadreIds!=null && cadreIds.length>0) {
			CadreViewExample example = new CadreViewExample();
			example.createCriteria().andIdIn(Arrays.asList(cadreIds));
			List<CadreView> cadres = cadreViewMapper.selectByExample(example);
			modelMap.put("cadres", cadres);
		}

		return "cadre/mobile/cadre_compare";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_compare_result")
	public String cadre_compare_result(@RequestParam(value = "cadreIds[]") Integer[] cadreIds, ModelMap modelMap) {

		if(cadreIds!=null && cadreIds.length>0) {
			CadreViewExample example = new CadreViewExample();
			example.createCriteria().andIdIn(Arrays.asList(cadreIds));
			List<CadreView> cadres = cadreViewMapper.selectByExample(example);

			modelMap.put("cadres", cadres);
		}

		return "cadre/mobile/cadre_compare_result";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_search_byName")
	public String cadre_search_byName(ModelMap modelMap) {

		return "cadre/mobile/cadre_search_byName";
	}
	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_search_byUnit")
	public String cadre_search_byUnit(ModelMap modelMap) {

		return "cadre/mobile/cadre_search_byUnit";
	}
	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/cadre_search")
	public String cadre_search(ModelMap modelMap) {

		return "cadre/mobile/cadre_search";
	}

	@RequiresPermissions("m:cadre:view")
	@RequestMapping("/cadre_info")
	public String cadre_info(Integer cadreId, ModelMap modelMap) {

		if(cadreId==null){
			// 默认读取本人信息
			int userId = ShiroHelper.getCurrentUserId();
			CadreView cadreView = cadreService.dbFindByUserId(userId);
			if(cadreView!=null){
				cadreId = cadreView.getId();
			}
		}

		cadreCommonService.cadreBase(cadreId, modelMap);

		return "cadre/mobile/cadre_info";
	}

	@RequiresPermissions("m:cadre:list")
	@RequestMapping("/unit_cadre_info")
	public String unit_cadre_info(Integer unitId, ModelMap modelMap) {

		if(unitId==null){
			// 默认读取本人信息
			int userId = ShiroHelper.getCurrentUserId();
			CadreView cadreView = cadreService.dbFindByUserId(userId);
			if(cadreView!=null){
				unitId = cadreView.getUnitId();
			}
		}

		if(unitId!=null){

			List<UnitPostAllocationInfoBean> cpcInfoBeans
					= unitPostAllocationService.cpcInfo_data(unitId, CadreConstants.CADRE_TYPE_CJ, false);
			if(cpcInfoBeans.size()==2){
				modelMap.put("bean", cpcInfoBeans.get(0));
			}
		}

		return "cadre/mobile/unit_cadre_info";
	}
}
