package controller.analysis;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

// 党建待办事项
@Controller
@RequiresPermissions("suspend:page")
public class SuspendController{

    @RequestMapping("/suspend_page")
    public String suspend_page(ModelMap modelMap) {

        return "analysis/suspend/suspend_page";
    }
}
