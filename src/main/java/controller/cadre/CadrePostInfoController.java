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
    public String cadrePostInfo_page(int cadreId, @RequestParam(defaultValue = "1") Byte type, ModelMap modelMap) {

        modelMap.put("type", type);

        String name = null;
        switch (type){
            case 1:
                name = "post_pro";
                break;
            case 2:
                name = "post_admin";
                break;
            case 3:
                name = "post_work";
                break;
        }
        modelMap.put("canUpdateInfoCheck", cadreInfoCheckService.canUpdateInfoCheck(cadreId, name));
        modelMap.put("canUpdate", cadreInfoCheckService.canUpdate(cadreId, name));

        return "cadre/cadrePostInfo/cadrePostInfo_page";
    }
}
