package controller.parttime;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParttimeApproveAuthController extends ParttimeBaseController {

    @RequiresPermissions("parttimeApply:approve")
    @RequestMapping("/parttimeApproveAuth")
    public String parttimeApproveAuth(@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        switch (cls){
            case 1:
                return "forward:/parttimeApplicatType";
            case 2:
                return "forward:/parttimeApproverType";
            case 3:
                return "forward:/parttimeApprovalOrder";
        }

        return null;
    }
}
