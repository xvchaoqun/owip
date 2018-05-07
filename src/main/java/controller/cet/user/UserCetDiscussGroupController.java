package controller.cet.user;

import controller.cet.CetBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import persistence.cet.common.ICetDiscussGroup;
import shiro.ShiroHelper;

import java.util.List;

@Controller
@RequestMapping("/user/cet")
public class UserCetDiscussGroupController extends CetBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("cetDiscussGroup:list")
    @RequestMapping("/cetDiscussGroup")
    public String cetDiscussGroup(int planId, ModelMap modelMap) {

        int userId = ShiroHelper.getCurrentUserId();
        List<ICetDiscussGroup> cetDiscussGroups = iCetMapper.userDiscussGroup(planId, userId);
        modelMap.put("cetDiscussGroups", cetDiscussGroups);

        return "cet/user/cetDiscussGroup_page";
    }
}
