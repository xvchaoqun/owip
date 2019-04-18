package controller.member;

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

    // 党员发展流程 总体布局
    @RequiresPermissions("memberApply:layout")
    @RequestMapping("/memberApply_layout")
    public String memberApply_layout(@RequestParam(defaultValue = "1")int cls, ModelMap modelMap) {

       //modelMap.put("cls", cls);

        if(cls==1)
            return "forward:/memberApply";
        if(cls==2)
            return "forward:/applyOpenTime";
        if(cls==3)
            return "forward:/memberApplyLog";
        if(cls==4)
            return "forward:/memberApplyExport";
        if(cls==5)
            return "forward:/partyPublic";

        return null;
    }

}
