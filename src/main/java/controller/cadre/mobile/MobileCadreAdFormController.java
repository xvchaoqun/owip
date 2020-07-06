package controller.cadre.mobile;

import controller.BaseController;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.cadre.CadreAdformService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/m")
public class MobileCadreAdFormController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CadreAdformService cadreAdformService;

	@RequiresPermissions("m:cadre:view")
	@RequestMapping("/cadreAdform")
	public String cadreAdform(int cadreId, ModelMap modelMap) {

		modelMap.put("bean", cadreAdformService.getCadreAdform(cadreId));

		return "cadre/cadreAdform/adForm";
	}

	// 干部任免审批表下载
	@RequiresPermissions("m:cadre:view")
	@RequestMapping("/cadreAdform_download")
	public void cadreAdform_download(Integer cadreId,
									 Boolean isWord, // 否： 中组部格式
									 Byte adFormType, // 指定格式
									 HttpServletRequest request,
									 HttpServletResponse response) throws IOException, TemplateException, DocumentException {
		if(cadreId == null) return;
		Integer cadreIds[] = {cadreId};

		cadreAdformService.export(cadreIds, BooleanUtils.isTrue(isWord), adFormType, request, response);
	}
}
