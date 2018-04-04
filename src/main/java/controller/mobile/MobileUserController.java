package controller.mobile;

import controller.abroad.AbroadBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/m")
public class MobileUserController extends AbroadBaseController {

	@RequestMapping("/userInfo")
	public String userInfo(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequestMapping("/userInfo_page")
	public String userInfo_page(ModelMap modelMap) {

		return "mobile/userInfo/userInfo_page";
	}
}
