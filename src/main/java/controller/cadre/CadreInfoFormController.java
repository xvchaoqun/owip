package controller.cadre;

import controller.BaseController;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.cadre.CadreInfoFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 干部信息采集表
@Controller
public class CadreInfoFormController extends BaseController {

    @Autowired
    private CadreInfoFormService cadreInfoFormService;

    @RequiresPermissions("cadreInfoForm:list")
    @RequestMapping("/cadreInfoForm_page")
    public String cadreInfoForm_page(int cadreId, ModelMap modelMap) {

        modelMap.put("bean", cadreInfoFormService.getCadreInfoForm(cadreId));
        return "cadre/cadreInfoForm/cadreInfoForm_page";
    }

    // 干部信息采集表下载
    @RequiresPermissions("cadreInfoForm:download")
    @RequestMapping("/cadreInfoForm_download")
    public void cadreInfoForm_download(Integer cadreId, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, TemplateException {
        if(cadreId == null) return;
        Integer cadreIds[] = {cadreId};

        cadreInfoFormService.export(cadreIds, request, response);
    }
}
