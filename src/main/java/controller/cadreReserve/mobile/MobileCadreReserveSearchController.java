package controller.cadreReserve.mobile;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shiro.ShiroHelper;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/m")
public class MobileCadreReserveSearchController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("m:cadreReserve:list")
	@RequestMapping("/cadreReserve")
	public String cadreReserve(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:cadreReserve:list")
	@RequestMapping("/cadreReserve_page")
	public String cadreReserve_page() {

		return "cadreReserve/mobile/cadreReserve_page";
	}

	@RequiresPermissions("m:cadreReserve:list")
	@RequestMapping("/cadreReserve_compare")
	public String cadreReserve_compare(Integer[] cadreIds, ModelMap modelMap) {

		if(cadreIds!=null && cadreIds.length>0) {
			CadreViewExample example = new CadreViewExample();
			example.createCriteria().andIdIn(Arrays.asList(cadreIds));
			List<CadreView> cadres = cadreViewMapper.selectByExample(example);
			modelMap.put("cadres", cadres);
		}

		return "cadreReserve/mobile/cadreReserve_compare";
	}

	@RequiresPermissions("m:cadreReserve:list")
	@RequestMapping("/cadreReserve_compare_result")
	public String cadreReserve_compare_result(Integer[] cadreIds, ModelMap modelMap) {

		if(cadreIds!=null && cadreIds.length>0) {
			CadreViewExample example = new CadreViewExample();
			example.createCriteria().andIdIn(Arrays.asList(cadreIds));
			List<CadreView> cadres = cadreViewMapper.selectByExample(example);

			modelMap.put("cadres", cadres);
		}

		return "cadreReserve/mobile/cadreReserve_compare_result";
	}

	@RequiresPermissions("m:cadreReserve:list")
	@RequestMapping("/cadreReserve_search_byName")
	public String cadreReserve_search_byName(ModelMap modelMap) {

		return "cadreReserve/mobile/cadreReserve_search_byName";
	}

	@RequiresPermissions("m:cadreReserve:view")
	@RequestMapping("/cadreReserve_info")
	public String cadreReserve_info(Integer cadreId, ModelMap modelMap) {

		if(cadreId==null){
			// 默认读取本人信息
			int userId = ShiroHelper.getCurrentUserId();
			CadreReserveViewExample example = new CadreReserveViewExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<CadreReserveView> cadreReserveViews = cadreReserveViewMapper.selectByExample(example);
			if(cadreReserveViews.size()>0){
				cadreId = cadreReserveViews.get(0).getId();
			}
		}

		cadreCommonService.cadreBase(cadreId, modelMap);

		return "cadreReserve/mobile/cadreReserve_info";
	}
}
