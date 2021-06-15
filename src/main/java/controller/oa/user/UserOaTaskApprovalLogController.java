package controller.oa.user;

import controller.oa.OaBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/oa")
public class UserOaTaskApprovalLogController extends OaBaseController {
    @RequestMapping("/oaTaskApprovalLog")
    public String oaTaskApprovalLog(Integer userId, ModelMap modelMap) {
        if (userId != null) {
            modelMap.put("userId", userId);
        }
        return "oa/user/oaTaskApprovalLog_page";
    }
}
