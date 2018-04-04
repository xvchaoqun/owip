package controller.cadre.mobile;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

		cadreBase(cadreId, modelMap);

		return "cadre/mobile/cadre_info";
	}
}
