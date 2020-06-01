package controller.cadre.mobile;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
import sys.tags.CmTag;
import sys.tool.paging.CommonList;

import javax.servlet.http.HttpServletResponse;
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

			List<UnitPostAllocationInfoBean> cjCpcInfoBeans
					= unitPostAllocationService.cpcInfo_data(unitId, CadreConstants.CADRE_TYPE_CJ, false);
			if(cjCpcInfoBeans.size()==2){
				modelMap.put("cjBean", cjCpcInfoBeans.get(0));
			}
			if(CmTag.getBoolProperty("hasKjCadre")) {
				List<UnitPostAllocationInfoBean> kjCpcInfoBeans
						= unitPostAllocationService.cpcInfo_data(unitId, CadreConstants.CADRE_TYPE_KJ, false);
				if (kjCpcInfoBeans.size() == 2) {
					modelMap.put("kjBean", kjCpcInfoBeans.get(0));
				}
			}
		}

		return "cadre/mobile/unit_cadre_info";
	}

	@RequiresPermissions("m:cadreHistory:view")
	@RequestMapping("/cadreHistory")
	public String cadreHistory() {

		return "mobile/index";
	}

	@RequiresPermissions("m:cadreHistory:view")
	@RequestMapping("/cadreHistory_page")
	public String cadreHistory_page(HttpServletResponse response,String realnameOrCode,
									Integer pageSize, Integer pageNo, ModelMap modelMap) {

		if (null == pageSize) {
			pageSize = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		pageNo = Math.max(1, pageNo);

		CadreViewExample example = new CadreViewExample();
		example.setOrderByClause("sort_order desc");
		CadreViewExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(CadreConstants.CADRE_STATUS_CJ_LEAVE);

		if (StringUtils.isNotBlank(realnameOrCode)){
			criteria.andRealnameOrCodeLike(realnameOrCode);
		}

		long count = cadreViewMapper.countByExample(example);
		if ((pageNo - 1) * pageSize >= count) {

			pageNo = Math.max(1, pageNo - 1);
		}

		List<CadreView> cadreViews = cadreViewMapper.selectByExampleWithRowbounds(example,new RowBounds((pageNo - 1) * pageSize, pageSize));
		CommonList commonList = new CommonList(count, pageNo, pageSize);

		modelMap.put("cadres",cadreViews);
		modelMap.put("commonList",commonList);
		modelMap.put("realnameOrCode",realnameOrCode);

		return "cadre/mobile/cadre_history";
	}
}
