package controller.dispatch.mobile;

import controller.BaseController;
import domain.dispatch.DispatchWorkFile;
import domain.dispatch.DispatchWorkFileExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/m")
public class MobileDispatchWorkFileController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequiresPermissions("m:dispatchWorkFile:list")
	@RequestMapping("/dispatchWorkFile")
	public String dispatchWorkFile(ModelMap modelMap) {

		return "mobile/index";
	}

	@RequiresPermissions("m:dispatchWorkFile:list")
	@RequestMapping("/dispatchWorkFile_page")
	public String dispatchWorkFile_page(ModelMap modelMap) {

		DispatchWorkFileExample example = new DispatchWorkFileExample();
		example.createCriteria().andStatusEqualTo(true);
		List<DispatchWorkFile> dispatchWorkFiles =
				dispatchWorkFileMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 10));
		modelMap.put("dispatchWorkFiles", dispatchWorkFiles);

		return "dispatch/mobile/dispatchWorkFile_page";
	}
}
