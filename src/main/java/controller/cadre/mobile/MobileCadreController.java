package controller.cadre.mobile;

import controller.BaseController;
import domain.cadre.CadreView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;

@Controller
@RequestMapping("/m")
public class MobileCadreController extends BaseController {

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

		cadreBase(cadreId, modelMap);

		return "cadre/mobile/cadre_info";
	}
}
