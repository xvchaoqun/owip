package controller.party;

import controller.BaseController;
import domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sys.constants.SystemConstants;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MemberFlowController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("memberFlow:list")
    @RequestMapping("/memberFlow")
    public String memberFlow() {

        return "index";
    }
    @RequiresPermissions("memberFlow:list")
    @RequestMapping("/memberFlow_page")
    public String memberFlow_page(HttpServletResponse response,@RequestParam(defaultValue = "1")Integer cls, ModelMap modelMap) {

        modelMap.put("cls", cls);

        if(cls==1||cls==2||cls==3)
            return "forward:/memberOutflow_page";
        if(cls==4||cls==5||cls==6)
            return "forward:/memberInflow_page";

        return  null;
    }
}
