package controller.verify;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fafa on 2017/4/9.
 */
@Controller
public class VerifyController extends BaseController {


    @RequiresPermissions("verify:menu")
    @RequestMapping("/verify")
    public String verify(@RequestParam(defaultValue = "1") Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);
        if (cls == 1) {
            return "forward:/verifyAge";
        }

        return "forward:/verifyWorkTime";
    }
}
