package controller.abroad;

import controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AbroadAuthController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:*")
    @RequestMapping("/approvalAuth")
    public String approvalAuth() {

        return "index";
    }

    @RequiresPermissions("approvalAuth:list")
    @RequestMapping("/approvalAuth_page")
    public String approvalAuth_page(@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        switch (cls){
            case 1:
                return "forward:/applicatType_page";
            case 2:
                return "forward:/approverType_page";
            case 3:
                return "forward:/approvalOrder_page";
        }

        return null;
    }

}
