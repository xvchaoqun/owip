package controller.cadre;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CadrePostInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cadrePostInfo:list")
    @RequestMapping("/cadrePostInfo_page")
    public String cadrePostInfo_page(@RequestParam(defaultValue = "1") Byte type, ModelMap modelMap) {

        modelMap.put("type", type);
        return "cadre/cadrePostInfo/cadrePostInfo_page";
    }
}
