package controller.party;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fafa on 2015/12/2.
 */
@Controller
public class MemberApplyLayoutController {

    @RequiresPermissions("memberApply:layout")
    @RequestMapping("/memberApply_layout")
    public String memberApply_layout(@RequestParam(defaultValue = "1")int cls, ModelMap modelMap) {

        return "index";
    }
    // 申请入党管理 总体布局
    @RequiresPermissions("memberApply:layout")
    @RequestMapping("/memberApply_layout_page")
    public String memberApply_layout_page(@RequestParam(defaultValue = "1")int cls, ModelMap modelMap) {

       //modelMap.put("cls", cls);

        if(cls==1)
            return "forward:/memberApply_page";
        if(cls==2)
            return "forward:/applyOpenTime_page";
        if(cls==3)
            return "forward:/memberApplyLog_page";

        return null;
    }

}
