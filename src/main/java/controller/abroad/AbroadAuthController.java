package controller.abroad;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/abroad")
public class AbroadAuthController extends AbroadBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("approvalAuth:list")
    @RequestMapping("/approvalAuth")
    public String approvalAuth(@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        switch (cls){
            case 1:
                return "forward:/abroad/applicatType";
            case 2:
                return "forward:/abroad/approverType";
            case 3:
                return "forward:/abroad/approvalOrder";
        }

        return null;
    }

}
