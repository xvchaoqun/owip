package controller.mobile;

import controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/m")
public class MobileTestController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/qr")
	public String qr(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequestMapping("/qr_page")
	public String qr_page() {

		return "mobile/qr_page";
	}
}
