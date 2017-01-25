package controller.cadre;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CadreInspectInfoController extends BaseController {


    @RequiresPermissions("cadreInspectInfo:*")
    @RequestMapping("/cadreInspectInfo_page")
    public String cadreInspectInfo_page(HttpServletResponse response,
                                        Integer cadreId,
                           @RequestParam(defaultValue = "1") Integer type,
                           ModelMap modelMap) {

        modelMap.put("type", type);

        return "cadre/cadreInspectInfo/cadreInspectInfo_page";
    }
}
