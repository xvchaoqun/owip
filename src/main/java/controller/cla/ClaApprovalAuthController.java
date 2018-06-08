package controller.cla;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cla")
public class ClaApprovalAuthController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("claApprovalAuth:list")
    @RequestMapping("/claApprovalAuth")
    public String claApprovalAuth(@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        switch (cls){
            case 1:
                return "forward:/cla/claApplicatType";
            case 2:
                return "forward:/cla/claApproverType";
            case 3:
                return "forward:/cla/claApprovalOrder";
        }

        return null;
    }

}
