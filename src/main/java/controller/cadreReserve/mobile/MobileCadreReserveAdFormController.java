package controller.cadreReserve.mobile;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.cadre.CadreAdformService;

@Controller
@RequestMapping("/m")
public class MobileCadreReserveAdFormController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CadreAdformService cadreAdformService;

	@RequiresPermissions("m:cadreReserve:view")
	@RequestMapping("/cadreReserveAdform")
	public String cadreReserveAdform(int cadreId, ModelMap modelMap) {

		modelMap.put("bean", cadreAdformService.getCadreAdform(cadreId));

		return "cadre/cadreAdform/adForm";
	}

}
