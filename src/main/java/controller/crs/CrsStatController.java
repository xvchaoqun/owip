package controller.crs;

import controller.CrsBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CrsStatController extends CrsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crsStat:*")
    @RequestMapping("/crsStat")
    public String crsStat(@RequestParam(required = false, defaultValue = "1") Byte cls,
                          Integer expertUserId,
                          ModelMap modelMap) {

        modelMap.put("cls", cls);
        return "crs/crsStat/crsStat_page";
    }
}
