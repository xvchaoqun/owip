package controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

	@RequiresPermissions("index:home")
	@RequestMapping("/index")
	public String _index() {

		return "index";
	}
	@RequiresPermissions("index:home")
	@RequestMapping("/")
	public String index() {

		return "redirect:/index";
	}
	@RequiresPermissions("index:home")
	@RequestMapping("/index_page")
	public String home_page(ModelMap modelMap) {

		return "index_page";
	}

	@RequestMapping("/menu")
	public String menu(ModelMap modelMap) {

		modelMap.put("menus", sysResourceService.getSortedSysResources().values());

		return "menu";
	}
}
