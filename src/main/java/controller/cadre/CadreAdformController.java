package controller.cadre;

import bean.CadreAdform;
import controller.BaseController;
import freemarker.template.TemplateException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.cadre.CadreAdformService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fafa on 2016/10/29.
 */
@Controller
public class CadreAdformController extends BaseController {

    @Autowired
    private CadreAdformService cadreAdformService;

    @RequiresPermissions("cadreAdform:list")
    @RequestMapping("/cadreAdform_page")
    public String cadreAdform_page(
            @RequestParam(defaultValue = 1 + "") Byte type,
            int cadreId, ModelMap modelMap) {

        modelMap.put("type", type);

        modelMap.put("bean", cadreAdformService.getCadreAdform(cadreId));

        return "cadre/cadreAdform/cadreAdform_page";
    }

    // 干部任免审批表下载
    @RequiresPermissions("cadreAdform:download")
    @RequestMapping("/cadreAdform_download")
    public void adform(int cadreId, HttpServletResponse response) throws IOException, TemplateException {

        //输出文件
        String filename = "干部任免审批表";
        response.reset();
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((filename + ".doc").getBytes(), "iso-8859-1"));
        response.setContentType("application/msword;charset=UTF-8");

        cadreAdformService.process(cadreId, response.getWriter());
    }


}
