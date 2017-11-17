package controller.pmd;

import controller.PmdBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pmd")
public class PmdOwController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdOw:admin")
    @RequestMapping("/pmdOw")
    public String pmdOw(@RequestParam(required = false, defaultValue = "1") byte cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 2) {
            // 党费管理账簿
            return "forward:/pmd/pmdAccount";
        }

        return "forward:/pmd/pmdMonth";
    }
}
